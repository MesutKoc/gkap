package algorithm.optimizedFlow;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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
