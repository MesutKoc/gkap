package algorithm.optimizedFlow;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import static algorithm.optimizedFlow.MaxFlow.FlowAlgorithm.FORD_FULKERSON;

/**
 * <h1>Run.java</h1> Diese Klasse startet die Applikation
 *
 * @author Mesut koc
 * @version 1.1
 * @since 2016-10-18
 */
@SuppressWarnings("unused")
public class runTest {
    /**
     * Startet die Applikation
     *
     * @param args main
     */
    public static void main(String[] args) {
        run();
    }

    /**
     * run
     */
    private static void run() {
        try {
            //Graph graph = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
            Graph test = new SingleGraph("test");
            test.addNode("q");
            test.addNode("v1");
            test.addNode("v2");
            test.addNode("v3");
            test.addNode("v5");
            test.addNode("s");
            test.addEdge("qv5", "q", "v5", true).addAttribute("capacity", 1.0);
            test.addEdge("qv1", "q", "v1", true).addAttribute("capacity", 5.0);
            test.addEdge("qv2", "q", "v2", true).addAttribute("capacity", 4.0);
            test.addEdge("v2v3", "v2", "v3", true).addAttribute("capacity", 2.0);
            test.addEdge("v1v3", "v1", "v3", true).addAttribute("capacity", 1.0);
            test.addEdge("v3s", "v3", "s", true).addAttribute("capacity", 3.0);
            test.addEdge("v1s", "v1", "s", true).addAttribute("capacity", 3.0);
            test.addEdge("v1v5", "v1", "v5", true).addAttribute("capacity", 1.0);
            test.addEdge("v5s", "v5", "s", true).addAttribute("capacity", 3.0);

            MaxFlow newTest = new MaxFlow();
            double t = newTest.findMaxFlow(test, test.getNode("q"), test.getNode("s"), FORD_FULKERSON);
            System.out.print(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}