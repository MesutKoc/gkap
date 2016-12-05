package algorithm.optimizedFlow;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

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
            test.addEdge("qv5", "q", "v5", true).addAttribute("max", 1);
            test.addEdge("qv1", "q", "v1", true).addAttribute("max", 5);
            test.addEdge("qv2", "q", "v2", true).addAttribute("max", 4);
            test.addEdge("v2v3", "v2", "v3", true).addAttribute("max", 2);
            test.addEdge("v1v3", "v1", "v3", true).addAttribute("max", 1);
            test.addEdge("v1s", "v1", "s", true).addAttribute("max", 3);
            test.addEdge("v1v5", "v1", "v5", true).addAttribute("max", 1);
            test.addEdge("v5s", "v5", "s", true).addAttribute("max", 3);

            MaxFlow.findMaxFlow(test, test.getNode("q"), test.getNode("s"), MaxFlow.BY.FF);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}