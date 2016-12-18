package algorithm.optimizedFlow;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Objects.isNull;

/**
 * <h1>ford.java</h1> Diese Klasse implementiert den FK Algorithmus
 *
 * @author Mesut Koc, Anton Kirakozov
 * @version 0.0.3
 * @since 2016-12-16
 */
public class MaxFlow {
    private static final String FLOW_ARG_NAME = "flow";
    private static final String CAPACITY_ARG_NAME = "capacity";

    private MaxFlow() {
    }

    public static double findMaxFlow(Graph graph, Node source, Node senke, FlowAlgorithm variant) throws Exception {
        ArrayList<Node> nodes = new ArrayList<>(graph.getNodeSet()); // Nodes
        ArrayList<Edge> edges = new ArrayList<>(graph.getEdgeSet()); // Edges
        double maxFlow = 0.0;

        // Vorbedingungen
        if (!preConditions(nodes, edges, source, senke)) throw new Exception("Precondition verletzt");

        // Schritt 1: Die Initialisierung
        init(source, nodes, edges);

        Random generator = new Random();
        // Schritt 2 Inspektion und Markierung
        while (true) {
            boolean allInspected = false;
            while (true) {
                // Falls senke markiert ist, gehe zu 3
                if (senke.getAttribute("markiert").equals(1)) break;

                // 2b: Wähle die nächste markierte, aber noch nicht inspizierte
                // Ecke vi aus der Queue (Edmonds) oder eine beliebige markierte Ecke (Ford)
                // und inspiziere sie wie folgt (Berechnung des Inkrements)
                ArrayList<Node> markedNodes = new ArrayList<>();
                Node vi;

                for (Node currentVertex : nodes)
                    if (!markedNodes.contains(currentVertex) && currentVertex.getAttribute("markiert").equals(1) && currentVertex.getAttribute("inspiziert").equals(0))
                        markedNodes.add(currentVertex);

                if (markedNodes.size() == 0) {
                    allInspected = true;
                    break;
                }

                if (variant == FlowAlgorithm.EDMONDS_KARP) {
                    vi = markedNodes.get(0);
                    markedNodes.remove(0);
                } else {
                    vi = markedNodes.get(generator.nextInt(markedNodes.size()));
                }

                if (vi != null) vi.setAttribute("inspiziert", 1);

                // Vorwärtskante: Fuer jede Kante e mit unmarkierter Ecke vj und
                // fluss(e) < kapazitaet(e) markiere vj mit +vorgaenger (vi) ("neg" Attribut = 0) und
                // maxFlow(min(kapazitaet(e)-fluss(e), maxFlow(vi)))
                for (Edge leavingEdge : vi.getEachLeavingEdge()) { // Output
                    Node s = leavingEdge.getSourceNode();
                    Node t = leavingEdge.getTargetNode();
                    // Wenn das Ziel der Kante e nicht markiert ist und deren Kapazität groesser als der Fluss ist
                    if (s.equals(vi) && t.getAttribute("markiert").equals(0) && leavingEdge.getNumber(CAPACITY_ARG_NAME) > leavingEdge.getNumber(FLOW_ARG_NAME))
                        markForward(t, s, leavingEdge);
                }
                // Rückwärtskante: Fuer jede Kante e mit Ziel vi und unmarkierter Ecke vj und
                // fluss(e) > 0 markiere vj mit -vorgaenger(vi) ("neg" Attribut = 1) und maxFlow(min(fluss(e), maxFlow(vi)))
                for (Edge enteringEdge : vi.getEachEnteringEdge()) {
                    Node s = enteringEdge.getSourceNode();
                    Node t = enteringEdge.getTargetNode();
                    if (t.equals(vi) && s.getAttribute("markiert").equals(0) && enteringEdge.getNumber(CAPACITY_ARG_NAME) > 0)
                        markBackward(s, t, enteringEdge);
                }
            }

            // 2a: Falls alle markierten Ecken inspiziert wurden, gehe nach 4
            if (allInspected) break;

            // Schritt 3: Vergrösserung der Flussstärke
            // Bei senke beginnend lässt sich anhand der Markierungen der gefundene vergrössende Weg bis zur Ecke source
            // rückwärts durchlaufen. Für jede Vorwärtskante wird fluss(e) um maxFlow(senke) erhöht und für jede
            // Rückwärtskante wird fluss(e) um maxFlow(senke) vermindert.
            augmentFlow(source, senke);

            // Anschliessend werden bei allen Ecken mit Ausnahme von v1 die Markierungen entfernt.
            // Gehe zu 2
            resetAllAttribut(nodes, source);
            source.setAttribute("inspiziert", 0);
        }
        // Schritt 4: Es gibt keinen vergrössernden Weg. Der jetzige Flusswert jeder Kante ist optimal
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
        // Für jede Kante wird der Flusswert 0 gesetzt
        for (Edge currEdge : edges) currEdge.addAttribute(FLOW_ARG_NAME, 0);

        // Jeder Knoten wird als nicht inspiziert (0) und nicht markiert (0) markiert
        for (Node currentVertex : vertices) {
            currentVertex.addAttribute("inspiziert", 0);
            currentVertex.addAttribute("markiert", 0);
        }

        // Die Quelle wird markiert + delta (Flusswert) wird auf unendlich gesetzt
        source.setAttribute("markiert", 1);
        source.setAttribute("maxFlow", Double.POSITIVE_INFINITY);
    }

    /**
     * Schritt 3: Vergrösserung der Flusstärke
     *
     * @param source die Quelle
     * @param senke  die Senke
     */
    private static void augmentFlow(Node source, Node senke) {
        Node current = senke;
        double flowInkrement = senke.getNumber("maxFlow");
        while (!(current.equals(source))) {
            Node vorg = current.getAttribute("vorganger");
            Edge edge = vorg.getEdgeBetween(current);
            if (edge == null) edge = current.getEdgeBetween(vorg);
            // Wenn wir eine Vorwaertskante oder Rückwärtskante haben (0 für Vorwärts und 1 für Rückwärtskante)
            edge.setAttribute(FLOW_ARG_NAME, current.getAttribute("neg").equals(0) ? edge.getNumber(FLOW_ARG_NAME) + flowInkrement : edge.getNumber(FLOW_ARG_NAME) - flowInkrement);
            current = current.getAttribute("vorganger"); // Waehle den Vorgaenger
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
    }

    /**
     * Resettet alle Attribute
     *
     * @param vertices die Liste mit den Knoten
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
     * @return true wenn die preconditions gegeben sind ansonsten false.
     */
    private static boolean preConditions(ArrayList<Node> vertexes, ArrayList<Edge> edges, Node source, Node target) {
        for (Edge e : edges) if (!e.isDirected() || !e.hasAttribute(CAPACITY_ARG_NAME)) return false;
        return !((isNull(source)) || (isNull(target))) && (source != target) && !(!vertexes.contains(source) || !vertexes.contains(target));
    }

    public enum FlowAlgorithm {
        FORD_FULKERSON,
        EDMONDS_KARP,
    }
}