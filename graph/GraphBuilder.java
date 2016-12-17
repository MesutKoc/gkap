package graph;

import io.GraphSaver;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.regex.Pattern.compile;

/**
 * <h1>GraphBuilder.java</h1> Diese Klasse generiert einen Graphen
 *
 * @author Mesut koc
 * @version 1.4
 * @since 2016-10-30
 */
public class GraphBuilder {
    private static Random random = new Random();

    private GraphBuilder() {
    }

    /**
     * Erstellt einen Graphen
     *
     * @param lines die zulesenden Daten aus dem Graph
     * @return ein Graph
     */
    public static Graph createGraph(List<String> lines) throws FileNotFoundException {
        if (lines.isEmpty()) return null;

        Graph graph = new MultiGraph("Test");
        final String uml = "[_öÖäÄüÜßa-zA-Z0-9]";
        final String ws = "\\p{Blank}*";
        final String edgePattern = format("(%s+)(%s(-[->])%s(%s+))?(%s\\((%s*)\\))?(%s:%s(\\d+))?%s;", uml, ws, ws, uml, ws, uml, ws, ws, ws);

        for (String line : lines) {
            Matcher lineMatcher = compile(edgePattern).matcher(line);
            if (lineMatcher.matches()) {
                Boolean isDirected = false;
                String edgeID = "", edgeWeight = "", direction, node1;
                String node0 = lineMatcher.group(1);
                createNode(graph, node0);

                if (!isNull(lineMatcher.group(3)) && !isNull(lineMatcher.group(4))) {
                    direction = lineMatcher.group(3);
                    node1 = lineMatcher.group(4);
                    createNode(graph, node1);
                    if (!isNull(lineMatcher.group(6))) {
                        edgeID = lineMatcher.group(6);
                        if (!isNull(graph.getEdge(edgeID)))
                            edgeID = format("%s_to_%s", node0, node1);
                    } else
                        edgeID = format("%s_to_%s", node0, node1);

                    if (!isNull(lineMatcher.group(8))) edgeWeight = lineMatcher.group(8);
                    if (direction.equals("->")) isDirected = true;
                } else {
                    if (!isNull(lineMatcher.group(6))) node1 = node0;
                    else break;
                }
                addEdge(graph, edgeID, node0, node1, isDirected, edgeWeight);
            }
        }
        for (Node node : graph) node.addAttribute("ui.label", node.getId());
        return graph;
    }

    private static void addEdge(Graph graph, String edge, String node0,
                                String node1, Boolean isDirected, String weight) throws EdgeRejectedException {
        if (weight.equals("")) // without edgeWeight
            graph.addEdge(edge, node0, node1, isDirected);
        else
            graph.addEdge(edge, node0, node1, isDirected).setAttribute("weight", Integer.valueOf(weight));
    }

    private static void createNode(Graph graph, String node0) {
        if (graph.getNode(node0) == null) graph.addNode(node0);
    }

    /**
     * Erstellt einen random Graphen mit anzahl der Knoten und Kanten
     *
     * @param numNodes die Anzahl der Knoten
     * @param numEdge  die Anzahl der Kanten
     * @return der erstelle Graph
     * @throws IOException falls der Graph nicht gespeichert werden kann
     */
    public static Graph generateBigOne(int numNodes, int numEdge) throws IOException {
        Graph result = new MultiGraph("big");
        int edgeIdent = 0;

        for (int i = 1; i <= numNodes; i++) result.addNode(format("%d", i));
        result.addEdge("1_100", "1", "100", true).addAttribute("weight", 1);
        while (result.getEdgeCount() < numEdge) {
            for (int i = 2; i <= numEdge; i++) {
                int x = random.nextInt(numNodes - 1) + 1;
                int y = random.nextInt(numNodes - 1) + 1;
                result.addEdge(format("%d%d|%d", x, y, edgeIdent), format("%d", x), format("%d", y), true)
                        .addAttribute("weight", random.nextInt(numNodes - 1) + 1);
                edgeIdent++;
            }
        }
        GraphSaver.saveGraph(result, new File("graph/subwerkzeuge/bspGraphen/saved/biG.gka"));
        return result;
    }

    /**
     * Erstellt ein Network
     *
     * @param nodes Anzahl der Knoten
     * @return ein Flussnetzwerk
     */
    public static Graph createNetwork(int nodes, int edgesAnz) throws IOException {
        MultiGraph resultGraph = new MultiGraph("network");
        int edges = ((nodes * (nodes - 1)) / 2);

        for (int i = 1; i <= nodes; i++) {
            if (i == 1) resultGraph.addNode("q");
            else if (i != nodes) resultGraph.addNode("v" + i);
            else resultGraph.addNode("s");
        }

        while (edgesAnz > 0) {
            int r1 = random.nextInt(nodes);
            int r2 = random.nextInt(nodes);
            int r3 = random.nextInt((nodes - 1) + 1);
            if (!hasEdge(resultGraph, r1, r2)) {
                createEdge(resultGraph, r1, r2);
                resultGraph.getEdge(format("%s_%s", resultGraph.getNode(r1).getId(), resultGraph.getNode(r2).getId())).addAttribute("weight", r3);
                edgesAnz--;
            }
        }

        for (Edge test : resultGraph.getEachEdge())
            test.addAttribute("capacity", test.getAttribute("weight").toString());

        GraphSaver.saveGraph(resultGraph, new File("graph/subwerkzeuge/bspGraphen/saved/BigNet_50_800.gka"));
        return resultGraph;
    }

    /**
     * Überprüft, ob der Graph schon eine Kante hat
     *
     * @param g  der Graph
     * @param r1 die Position
     * @param r2 die zweite Position
     * @return true, falls eine Kante existiert, andernfalls false.
     */
    private static boolean hasEdge(MultiGraph g, int r1, int r2) {
        return g.getEdge(g.getNode(r1).getId() + "_" + g.getNode(r2).getId()) != null;
    }

    /**
     * Erstellt eine Kante
     *
     * @param g  der Graph
     * @param r1 der erste k. Position von der Kante
     * @param r2 die zweite k. Position von der Kante
     * @return die erstellte Kante an pos r1 und r2
     */
    private static Edge createEdge(MultiGraph g, int r1, int r2) {
        return g.addEdge(format("%s_%s", g.getNode(r1).getId(), g.getNode(r2).getId()), g.getNode(r1).getId(), g.getNode(r2).getId(), true);
    }

    /**
     * Visualisiert den Graphen als App
     *
     * @param graph     der zu visualierende Graph
     * @param showGraph soll der Graph angezeigt werden?
     */
    public static void setGraphSettings(Graph graph, boolean showGraph) {
        if (!showGraph) return;
        graph.setStrict(false); // Überprüft zB doppelte Knotennamen,benutzung
        // von nicht existierenden Elementen usw.
        graph.setAutoCreate(true); // nodes are automatically created when
        // referenced when creating a edge, even if
        // not yet inserted in the graph.
        graph.addAttribute("ui.stylesheet",
                "url('file:graph/subwerkzeuge/stylesheet')");
        System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.display();
    }
}