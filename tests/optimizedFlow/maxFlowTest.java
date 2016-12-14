package tests.optimizedFlow;

import algorithm.optimizedFlow.MaxFlow;
import io.GraphReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static algorithm.optimizedFlow.MaxFlow.FlowAlgorithm.FORD_FULKERSON;
import static org.junit.Assert.assertEquals;

public class maxFlowTest {
    private Graph graph04;
    private Graph posTest;
    private Graph negTest;
    private Graph negTest2;
    private Graph negTest3;

    //#####################################################
    // setup
    //#####################################################
    @Before
    public void setUp() throws Exception {
        posTest = new SingleGraph("positive");
        posTest.addNode("q");
        posTest.addNode("v1");
        posTest.addNode("v2");
        posTest.addNode("v3");
        posTest.addNode("v5");
        posTest.addNode("s");
        posTest.addEdge("qv5", "q", "v5", true).addAttribute("capacity", 1.0);
        posTest.addEdge("qv1", "q", "v1", true).addAttribute("capacity", 5.0);
        posTest.addEdge("qv2", "q", "v2", true).addAttribute("capacity", 4.0);
        posTest.addEdge("v2v3", "v2", "v3", true).addAttribute("capacity", 2.0);
        posTest.addEdge("v1v3", "v1", "v3", true).addAttribute("capacity", 1.0);
        posTest.addEdge("v3s", "v3", "s", true).addAttribute("capacity", 3.0);
        posTest.addEdge("v1s", "v1", "s", true).addAttribute("capacity", 3.0);
        posTest.addEdge("v1v5", "v1", "v5", true).addAttribute("capacity", 1.0);
        posTest.addEdge("v5s", "v5", "s", true).addAttribute("capacity", 3.0);

        negTest = new SingleGraph("negativeTests");
        negTest.addNode("q");
        negTest.addNode("v1");
        negTest.addNode("v2");
        negTest.addNode("v3");
        negTest.addNode("v5");
        negTest.addNode("s");
        negTest.addEdge("qv5", "q", "v5", false).addAttribute("capacity", 1.0);
        negTest.addEdge("qv1", "q", "v1", false).addAttribute("capacity", 5.0);
        negTest.addEdge("qv2", "q", "v2", false).addAttribute("capacity", 4.0);
        negTest.addEdge("v2v3", "v2", "v3", false).addAttribute("capacity", 2.0);
        negTest.addEdge("v1v3", "v1", "v3", false).addAttribute("capacity", 1.0);
        negTest.addEdge("v3s", "v3", "s", false).addAttribute("capacity", 3.0);
        negTest.addEdge("v1s", "v1", "s", false).addAttribute("capacity", 3.0);
        negTest.addEdge("v1v5", "v1", "v5", false).addAttribute("capacity", 1.0);
        negTest.addEdge("v5s", "v5", "s", false).addAttribute("capacity", 3.0);

        negTest2 = new SingleGraph("negativeTests");
        negTest2.addNode("q");
        negTest2.addNode("v1");
        negTest2.addNode("v2");
        negTest2.addNode("v3");
        negTest2.addNode("v5");
        negTest2.addNode("s");
        negTest2.addEdge("qv5", "q", "v5", true);
        negTest2.addEdge("qv1", "q", "v1", true);
        negTest2.addEdge("qv2", "q", "v2", true);
        negTest2.addEdge("v2v3", "v2", "v3", true);
        negTest2.addEdge("v1v3", "v1", "v3", true);
        negTest2.addEdge("v3s", "v3", "s", true);
        negTest2.addEdge("v1s", "v1", "s", true);
        negTest2.addEdge("v1v5", "v1", "v5", true);
        negTest2.addEdge("v5s", "v5", "s", true);

        negTest3 = new SingleGraph("negativeTests");
        negTest3.addNode("q");
        negTest3.addNode("v1");
        negTest3.addNode("v2");
        negTest3.addNode("v3");
        negTest3.addNode("v5");
        negTest3.addNode("s");
        negTest3.addEdge("qv5", "q", "v5", false).addAttribute("capacity", -1.0);
        negTest3.addEdge("qv1", "q", "v1", false).addAttribute("capacity", -5.0);
        negTest3.addEdge("qv2", "q", "v2", false).addAttribute("capacity", -4.0);
        negTest3.addEdge("v2v3", "v2", "v3", false).addAttribute("capacity", -2.0);
        negTest3.addEdge("v1v3", "v1", "v3", false).addAttribute("capacity", -1.0);
        negTest3.addEdge("v3s", "v3", "s", false).addAttribute("capacity", -3.0);
        negTest3.addEdge("v1s", "v1", "s", false).addAttribute("capacity", -3.0);
        negTest3.addEdge("v1v5", "v1", "v5", false).addAttribute("capacity", -1.0);
        negTest3.addEdge("v5s", "v5", "s", false).addAttribute("capacity", -3.0);

        graph04 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph04.gka"));
    }

    //#####################################################
    // Positive Tests
    //#####################################################
    @Test
    public void fordfulkerson() throws Exception {
        assertEquals(8, MaxFlow.findMaxFlow(posTest, posTest.getNode("q"), posTest.getNode("s"), FORD_FULKERSON), 0.001);
        System.out.println("fordfulkerson() done");
    }

    //#####################################################
    // negative Tests
    //#####################################################
    @Test
    public void fordfulkersonNegative() throws Exception {
        // Gewichtung abaer kein Gerichteter Graph
        assertEquals(0, MaxFlow.findMaxFlow(negTest, negTest.getNode("q"), negTest.getNode("s"), FORD_FULKERSON), 0.001);
        // Gerichtet aber keine Gewichtung
        assertEquals(0, MaxFlow.findMaxFlow(negTest2, negTest2.getNode("q"), negTest2.getNode("s"), FORD_FULKERSON), 0.001);
        // Gewichtung aber mit negativer Gewichtung
        assertEquals(0, MaxFlow.findMaxFlow(negTest3, negTest3.getNode("q"), negTest3.getNode("s"), FORD_FULKERSON), 0.001);
        // Graph04 - kein gewichteter Graph
        assertEquals(0, MaxFlow.findMaxFlow(graph04, graph04.getNode("q"), graph04.getNode("s"), FORD_FULKERSON), 0.001);
        System.out.println("fordfulkersonNegative() done");
    }
}