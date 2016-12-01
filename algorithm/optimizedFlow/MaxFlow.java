package algorithm.optimizedFlow;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

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
     * @param g       der Graph
     * @param source  vom Start
     * @param target  bis zum Ziel
     * @param variant 1 für FK, 2 für EK
     * @return maxFlow result je nach Algorithmus
     */
    public static int findMaxFlow(Graph g, Node source, Node target, BY variant) {
        requireNonNull(g);
        requireNonNull(source);
        requireNonNull(target);
        requireNonNull(variant);

        // Nodes buffern
        Collection<Node> nodes = g.getNodeSet();
        List<Node> nodeList = new ArrayList<>(nodes);
        // Edges buffern
        Collection<Edge> edges = g.getEdgeSet();
        List<Edge> edgeList = new ArrayList<>(edges);

        // 1. Schritt: die Initialisierung
        // Der Fluss wird auf 0 gesetzt
        for (int indexEdges = 0; indexEdges < edgeList.size(); indexEdges = indexEdges + 2)
            g.setAttribute("fluss", 0, edgeList.get(indexEdges), edgeList.get(indexEdges + 1));

        // Jede Ecke wird als nicht inspiziert (0) und nicht markiert (0) markiert
        for (Node currentNode : nodeList) {
            g.setAttribute("inspiziert", 0, currentNode);
            g.setAttribute("markiert", 0, currentNode);
        }

        // Die Quelle markieren
        g.setAttribute("markiert", source, 1);
        g.setAttribute("maxFlow", source, Integer.MAX_VALUE);

        // TODO Schritt 2 Inspektion und Markierung
        // TODO findMaxFlow
        return 0;
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