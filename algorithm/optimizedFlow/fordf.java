package algorithm.optimizedFlow;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * <h1>ford.java</h1> Diese Klasse implementiert den FK Algorithmus
 *
 * @author Mesut koc
 * @version 0.0.2
 * @since 2016-12-14
 */
public class fordf {
    private static final String FLOW_ARG_NAME = "flow";
    private static final String CAPACITY_ARG_NAME = "capacity";
    private static final Logger LOG = Logger.getLogger(String.valueOf(fordf.class));

    private fordf() {
    }

    public static double fordfulkerson(Graph graph, Node source, Node senke) {
        ArrayList<Node> vertices = new ArrayList<>(graph.getNodeSet()); // Nodes
        ArrayList<Edge> edges = new ArrayList<>(graph.getEdgeSet()); // Edges
        double maxFlow = 0.0;

        // Is the graph a network?
        if (!preConditions(vertices, edges, source, senke)) return 0;

        // Schritt 1: Die Initialisierung
        init(source, vertices, edges);

        // Schritt 2 Inspektion und Markierung
        Random generator = new Random();
        while (true) {
            boolean allInspected = false;
            while (true) {
                // Falls senke markiert ist, gehe zu 3
                if (senke.getAttribute("markiert").equals(1)) break;

                // 2b: Wähle die naechste markierte, aber noch nicht inspizierte
                // Ecke vi aus der Queue (Edmonds) oder eine beliebige markierte Ecke (Ford)
                // und inspiziere sie wie folgt (Berechnung des Inkrements)
                ArrayList<Node> markedVertices = new ArrayList<>();
                Node vi;

                for (Node currentVertex : vertices)
                    if (!markedVertices.contains(currentVertex) &&
                            currentVertex.getAttribute("markiert").equals(1) &&
                            currentVertex.getAttribute("inspiziert").equals(0))
                        markedVertices.add(currentVertex);

                if (markedVertices.size() == 0) {
                    allInspected = true;
                    break;
                }

                vi = markedVertices.get(generator.nextInt(markedVertices.size()));
                vi.setAttribute("inspiziert", 1);
                LOG.info("Gewaehlte Ecke: " + vi.getId());

                // Vorwaertskante: Fuer jede Kante e mit unmarkierter Ecke vj und
                // fluss(e) < kapazitaet(e) markiere vj mit +vorgaenger (vi) ("neg" Attribut = 0) und
                // maxFlow(min(kapazitaet(e)-fluss(e), maxFlow(vi)))
                for (Edge leavingEdge : vi.getEachLeavingEdge()) {
                    // Wenn das Ziel der Kante e nicht markiert ist und deren Kapazitaet groesser als der Fluss ist
                    Node s = leavingEdge.getSourceNode();
                    Node t = leavingEdge.getTargetNode();
                    if (s.equals(vi) && t.getAttribute("markiert").equals(0) && leavingEdge.getNumber(CAPACITY_ARG_NAME) > leavingEdge.getNumber(FLOW_ARG_NAME))
                        markForward(t, s, leavingEdge);
                }
                // Rueckwaertskante: Fuer jede Kante e mit Ziel vi und unmarkierter Ecke vj und
                // fluss(e) > 0 markiere vj mit -vorgaenger(vi) ("neg" Attribut = 1) und maxFlow(min(fluss(e), maxFlow(vi)))
                for (Edge enteringEdge : vi.getEachEnteringEdge()) {
                    Node s = enteringEdge.getSourceNode();
                    Node t = enteringEdge.getTargetNode();
                    if (t.equals(vi) && s.getAttribute("markiert").equals(0) && enteringEdge.getNumber(CAPACITY_ARG_NAME) > 0)
                        markBackward(s, t, enteringEdge);
                }
            }

            // 2a Falls alle markierten Ecken inspiziert wurden, gehe nach 4
            if (allInspected) break;

            // Schritt 3 Vergroesserung der Flussstaerke
            // Bei v2 beginnend laesst sich anhand der Markierungen der gefundene vergroessernde Weg bis zur Ecke v1
            // rueckwaerts durchlaufen. Fuer jede Vorwaertskante wird fluss(e) um maxFlow(v2) erhoeht und fuer jede
            // Rueckwaertskante wird fluss(e) um maxFlow(v2) vermindert.
            augmentFlow(source, senke);

            // Anschliessend werden bei allen Ecken mit Ausnahme von v1 die Markierungen entfernt.
            // Gehe zu 2
            resetAllAttribut(vertices, source);
            source.setAttribute("inspiziert", 0);
        }
        // Schritt 4: Es gibt keinen vergroessernden Weg. Der jetzige Flusswert jeder Kante ist optimal
        for (Edge e : source.getEachEdge()) maxFlow += e.getNumber(FLOW_ARG_NAME);
        return maxFlow;
    }

    /**
     * Initialisiert die Attribute für den FF Algorithmus
     *
     * @param source   Die Quelle
     * @param vertices die Knotenliste
     * @param edges    die Kantenliste
     */
    private static void init(Node source, ArrayList<Node> vertices, ArrayList<Edge> edges) {
        // Der Fluss wird auf 0 gesetzt
        for (Edge currEdge : edges) currEdge.addAttribute(FLOW_ARG_NAME, 0);

        // Jede Ecke wird als nicht inspiziert (0) und nicht markiert (0) markiert
        for (Node currentVertex : vertices) {
            currentVertex.addAttribute("inspiziert", 0);
            currentVertex.addAttribute("markiert", 0);
        }

        // Die Quelle wird markiert
        source.setAttribute("markiert", 1);
        source.setAttribute("maxFlow", Integer.MAX_VALUE);
        LOG.info("init wurde abgeschlossen");
    }

    /**
     * Schritt 3 Vergroesserung der Flussstaerke
     *
     * @param source die Quelle
     * @param senke  die Senke
     */
    private static void augmentFlow(Node source, Node senke) {
        Node current = senke;
        double flowInkrement = senke.getNumber("maxFlow");
        LOG.info("mit aktuellem Inkrement: " + flowInkrement);
        while (!(current.equals(source))) {
            Node vorg = current.getAttribute("vorganger");
            Edge edge = vorg.getEdgeBetween(current);
            if (edge == null) edge = current.getEdgeBetween(vorg);
            // Wenn wir eine Vorwaertskante oder Rückwärtskante haben (0 für Vorwärts und 1 für Rückwärtskante)
            edge.setAttribute(FLOW_ARG_NAME, current.getAttribute("neg").equals(0) ? edge.getNumber(FLOW_ARG_NAME) + flowInkrement : edge.getNumber(FLOW_ARG_NAME) - flowInkrement);
            // Waehle den Vorgaenger
            current = current.getAttribute("vorganger");
        }
    }

    /**
     * Markiert den Knoten mit der Deltaberechnung
     *
     * @param source      die Quelle
     * @param vorg        der Vorgänger
     * @param leavingEdge Input Kante
     */
    private static void markForward(Node source, Node vorg, Edge leavingEdge) {
        source.addAttribute("neg", 0);
        source.addAttribute("maxFlow", Math.min(leavingEdge.getNumber(CAPACITY_ARG_NAME) - leavingEdge.getNumber(FLOW_ARG_NAME), vorg.getNumber("maxFlow")));
        source.setAttribute("markiert", 1);
        source.addAttribute("vorganger", vorg);
        LOG.warning("Neue Vorwaertskante zu: " + source.getId());
    }

    /**
     * Markiert den Knoten mit der Deltaberechnung
     *
     * @param source       die Quelle
     * @param vorg         der Vorgänger
     * @param enteringEdge Output Kante
     */
    private static void markBackward(Node source, Node vorg, Edge enteringEdge) {
        source.addAttribute("neg", 1);
        source.addAttribute("vorganger", vorg);
        source.addAttribute("maxFlow", Math.min(enteringEdge.getNumber(FLOW_ARG_NAME), vorg.getNumber("maxFlow")));
        source.setAttribute("markiert", 1);
        LOG.warning("Neue Rueckwaertskante zu: " + source.getId());
    }

    /**
     * Resettet alle Attribute
     *
     * @param vertices die Knoten
     * @param source   die Quelle
     */
    private static void resetAllAttribut(ArrayList<Node> vertices, Node source) {
        for (Node currentVertex : vertices) {
            if (!(currentVertex.equals(source))) {
                currentVertex.addAttribute("markiert", 0);
                currentVertex.addAttribute("inspiziert", 0);
                currentVertex.addAttribute("vorganger", 0);
                currentVertex.addAttribute("neg", 0);
                currentVertex.addAttribute("maxFlow", 0);
                LOG.info("Alle Attribute bis auf von Source wurden resettet!");
            }
        }
    }

    /**
     * Hier wird überprüft, ob der Graph ein Flussnetzwerk ist
     *
     * @param vertexes die zuüberprüfende Knotenliste
     * @param edges    die zu überprüfende Kantenliste
     * @param source   die Quelle
     * @param target   das Ziel
     * @return true falls der Graph: gewichtet und gerichtet ist. Und der Source und Target ein Knoten ist +
     * wenn source ungleich target ist und diese in der Knotenliste enthalten sind. Andernfalls FALSE
     */
    private static boolean preConditions(ArrayList<Node> vertexes, ArrayList<Edge> edges, Node source, Node target) {
        for (Edge e : edges) if (!e.isDirected() || !e.hasAttribute(CAPACITY_ARG_NAME)) return false;
        return !((source == null) || (target == null)) && (source != target) && !(!vertexes.contains(source) || !vertexes.contains(target));
    }

    /**
     * Zur Messung der Laufzeit
     *
     * @param graph der Graph
     * @param v1    die Quelle
     * @param v2    die Senke
     * @return die Laufzeit in nanosekunden
     */
    public static long findMaxFlowRtm(Graph graph, Node v1, Node v2) {
        long resultTime;
        long startTime = System.nanoTime();
        fordfulkerson(graph, v1, v2);
        long endTime = System.nanoTime();
        resultTime = endTime - startTime;
        return resultTime;
    }
}