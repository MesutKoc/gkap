package algorithm.optimizedFlow;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;


public class MaxFlow {
    /**
     * Benutzt den FK oder den EK zum herausfinden vom maxFlow
     *
     * @param variant wenn == 1 dann fulkersion
     *                wenn == 2 dann edmonskarp
     *                ansonsten 0
     */
    public static double findMaxFlow(Graph graph, Node source, Node target, FlowAlgorithm variant) throws Exception {
        switch (variant) {
            case FORD_FULKERSON:
                return fordf.fordfulkerson(graph, source, target);
            case EDMONDS_KARP:
                return edmondsk.edmondskarp(graph, source, target);
            default:
                return 0;
        }
    }

    public enum FlowAlgorithm {
        FORD_FULKERSON,
        EDMONDS_KARP,
    }
}