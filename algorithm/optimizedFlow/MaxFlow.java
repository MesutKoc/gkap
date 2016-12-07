package algorithm.optimizedFlow;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>MaxFlow.java</h1> Diese Klasse implementiert beide Algorithmen
 *
 * @author Mesut koc
 * @version 1.0
 * @since 2016-11-29
 */
public class MaxFlow {
    private MaxFlow() {
    }

    /**
     *
     * @param source a
     * @param sink a
     * @param selectionBehaviour 1 => Ford Fulkerson, 2 => Edmund Karp
     * @return Vertex je nach ALGO
     */
    public static int findMaxFlow(Graph graph, Node source, Node sink, BY selectionBehaviour) throws Exception {
        // Nodes buffern
        Collection<Node> nodes = graph.getNodeSet();
        ArrayList<Node> allNodes = new ArrayList<>(nodes);

        // Preconditions
        if (!preConditions(allNodes, source, sink)) return 0;

        // Edges buffern
        Collection<Edge> edges = graph.getEdgeSet();
        ArrayList<Edge> allEdges = new ArrayList<>(edges);

        // Schritt 1 Initialisierung: Der Fluss wird auf 0 gesetzt
        for (Edge currEdge : allEdges) currEdge.addAttribute("fluss", 0);

        // Jede Ecke wird als nicht inspiziert (0) und nicht markiert (0) markiert
        for (Node currentNode : allNodes) {
            currentNode.addAttribute("inspiziert", 0);
            currentNode.addAttribute("markiert", 0);
        }

        // Die Quelle muss markiert werden
        source.setAttribute("markiert", 1);
        source.addAttribute("maxFlow", Integer.MAX_VALUE);

        return 0;
    }

    private static boolean preConditions(ArrayList<Node> vertexes, Node source, Node target) {
        if ((source == null) || (target == null)) return false;
        if (source == target) return false;
        return !(!vertexes.contains(source) || !vertexes.contains(target));
    }

    public enum BY {
        FF(1), // Ford Fulkerson
        EK(2); // Edmonds Karp
        private int value;

        BY(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}