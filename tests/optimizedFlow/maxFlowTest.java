package tests.optimizedFlow;

import io.GraphReader;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static algorithm.optimizedFlow.MaxFlow.FlowAlgorithm.EDMONDS_KARP;
import static algorithm.optimizedFlow.MaxFlow.FlowAlgorithm.FORD_FULKERSON;
import static algorithm.optimizedFlow.MaxFlow.findMaxFlow;
import static algorithm.optimizedFlow.MaxFlow.findMaxFlowRtm;
import static graph.GraphBuilder.createNetworkWithGrid;
import static org.junit.Assert.assertEquals;

public class maxFlowTest {
    private Graph graph04, posTest, negTest, negTest2, negTest3, graphFromInternet;

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

        graphFromInternet = new SingleGraph("graphFromInternet");
        graphFromInternet.addNode("v1");
        graphFromInternet.addNode("v2");
        graphFromInternet.addNode("v3");
        graphFromInternet.addNode("v4");
        graphFromInternet.addNode("v5");

        graphFromInternet.addEdge("v1v2", "v1", "v2", true).addAttribute("capacity", 8.0);
        graphFromInternet.addEdge("v2v3", "v2", "v3", true).addAttribute("capacity", 6.0);
        graphFromInternet.addEdge("v2v4", "v2", "v4", true).addAttribute("capacity", 7.0);
        graphFromInternet.addEdge("v4v5", "v4", "v5", true).addAttribute("capacity", 8.0);

        graph04 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph04.gka"));
    }

    //#####################################################
    // FORD Tests
    //#####################################################
    @Test
    public void fordfulkerson() throws Exception {
        assertEquals(8, findMaxFlow(posTest, posTest.getNode("q"), posTest.getNode("s"), FORD_FULKERSON), 0.001);
        // Graph from Internet
        assertEquals(7, findMaxFlow(graphFromInternet, graphFromInternet.getNode("v1"), graphFromInternet.getNode("v5"), FORD_FULKERSON), 0.001);
        System.out.println("fordfulkerson() done");
    }

    //#####################################################
    // EDMONDSKARP Tests
    //#####################################################
    @Test
    public void edmondskarp() throws Exception {
        assertEquals(8, findMaxFlow(posTest, posTest.getNode("q"), posTest.getNode("s"), EDMONDS_KARP), 0.001);
        // Graph from Internet
        assertEquals(7, findMaxFlow(graphFromInternet, graphFromInternet.getNode("v1"), graphFromInternet.getNode("v5"), EDMONDS_KARP), 0.001);
        System.out.println("edmondskarp() done");
    }

    //#####################################################
    // FORD VS EDMONDSKARP  RunTime
    //#####################################################
    @Test
    public void fordLaufZeit() throws Exception {
        long runtime = findMaxFlowRtm(posTest, posTest.getNode("q"), posTest.getNode("s"), FORD_FULKERSON);
        System.out.printf("Runtime for FORD with Graph %s is %d\n", posTest.getId(), runtime);
        long runtimeEK = findMaxFlowRtm(posTest, posTest.getNode("q"), posTest.getNode("s"), EDMONDS_KARP);
        System.out.printf("Runtime for EK with Graph %s is %d\n", posTest.getId(), runtimeEK);
    }

    //#####################################################
    // Network Tests
    //#####################################################
    @Test
    public void testVerySmallNetwork() throws Exception {
        Graph smallNetwork = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/saved/testCapacity.gka"));

        // SICHERUNG!! DAS MUSS HIER BLEIBEN WEGEN DER CONVERTION ZWISCHEN WEIGHT ALIAS CAPACITY!!!!!!!!!
        for (Edge e : smallNetwork.getEachEdge()) e.addAttribute("capacity", e.getNumber("weight"));

        assertEquals(6, findMaxFlow(smallNetwork, smallNetwork.getNode("source"), smallNetwork.getNode("sink"), FORD_FULKERSON), 0.001);
        System.out.println("testVerySmallNetwork() done");
    }

    @Test
    public void smallNetwork() throws Exception {
        Graph smallNetwork = createNetworkWithGrid(50, "BigNet_50_Neu");
        assertEquals(2, findMaxFlow(smallNetwork, smallNetwork.getNode(0), smallNetwork.getNode(smallNetwork.getNodeCount() - 1), FORD_FULKERSON), 0.001);
        System.out.println("smallNetwork() done");
    }

    //#####################################################
    // negative Tests
    //#####################################################
    @Test(expected = Exception.class)
    public void fordfulkersonNegative() throws Exception {
        // Gewichtung abaer kein Gerichteter Graph
        assertEquals(0, findMaxFlow(negTest, negTest.getNode("q"), negTest.getNode("s"), FORD_FULKERSON), 0.001);
        // Gerichtet aber keine Gewichtung
        assertEquals(0, findMaxFlow(negTest2, negTest2.getNode("q"), negTest2.getNode("s"), FORD_FULKERSON), 0.001);
        // Gewichtung aber mit negativer Gewichtung
        assertEquals(0, findMaxFlow(negTest3, negTest3.getNode("q"), negTest3.getNode("s"), FORD_FULKERSON), 0.001);
        // Graph04 - kein gewichteter Graph
        assertEquals(0, findMaxFlow(graph04, graph04.getNode("q"), graph04.getNode("s"), FORD_FULKERSON), 0.001);
        System.out.println("fordfulkersonNegative() done");
    }

    @Test(expected = Exception.class)
    public void edmondskarpNegative() throws Exception {
        // Graph04!
        assertEquals(0, findMaxFlow(graph04, graph04.getNode("q"), graph04.getNode("s"), EDMONDS_KARP), 0.001);
        // Gewichtung aber kein Gerichteter Graph
        assertEquals(0, findMaxFlow(negTest, negTest.getNode("q"), negTest.getNode("s"), EDMONDS_KARP), 0.001);
        // Gerichtet aber keine Gewichtung!
        assertEquals(0, findMaxFlow(negTest2, negTest2.getNode("q"), negTest2.getNode("s"), EDMONDS_KARP), 0.001);
        // Gewichtung aber mit negativer Gewichtung!
        assertEquals(0, findMaxFlow(negTest3, negTest3.getNode("q"), negTest3.getNode("s"), EDMONDS_KARP), 0.001);
        System.out.println("edmondskarpNegative() done");
    }
}