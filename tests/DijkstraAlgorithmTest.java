package tests;

import algorithm.searchPath.DijkstraAlgorithm;
import io.GraphReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static java.lang.Math.random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DijkstraAlgorithmTest {
    private static Graph generateBigOne(int numNodes, int numEdge) {
        Graph result = new MultiGraph("big");
        int edgeIdent = 0;

        for (Integer i = 0; i < numNodes; i++) result.addNode(String.format("%d", i));
        while (result.getEdgeCount() < numEdge) {
            for (int i = 0; i <= numEdge; i++) {
                int x = (int) (random() * ((numNodes - 1) + 1));
                int y = (int) (random() * ((numNodes - 1) + 1));
                result.addEdge(String.format("%d%d|%d", x, y, edgeIdent), String.format("%d", x), String.format("%d", y), true)
                        .addAttribute("weight", 1);
                edgeIdent++;
            }
        }
        return result;
    }

    //===============================
    // compute TESTS
    //===============================
    @Test
    public void compute() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getShortestPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        result.getShortestPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        assertEquals(expected.toString(), result.toString());
        System.out.println("compute() ok");
    }

    //===============================
    // getShortestPath TESTS
    //===============================
    @Test
    public void getPath() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getShortestPath(graph03.getNode("Paderborn"), graph03.getNode("Paderborn"));
        result.getShortestPath(graph03.getNode("Paderborn"), graph03.getNode("Paderborn"));
        System.out.println("getPath() ok");
    }

    @Test
    public void getPathTwo() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getShortestPath(graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        result.getShortestPath(graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        System.out.println("getPathTwo() ok");
    }

    @Test(expected = NullPointerException.class)
    public void negGetPath() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getShortestPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        result.getShortestPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        System.out.println("negGetPath() ok");
    }

    //===============================
    // init TESTS
    //===============================
    @Test
    public void init() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        assertNotNull(expected);
        assertNotNull(result);
        System.out.println("init() ok");
    }

    @Test
    public void negInit() throws IOException, ParseException {
        Graph graphNotWeighted = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph02.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graphNotWeighted);
        result.init(graphNotWeighted);
        assertEquals(expected.toString(), result.toString());
        System.out.println("negInit() ok");
    }

    //===============================
    // getDistanceLength TESTS
    //===============================
    @Test
    public void getDistanceLength() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getShortestPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        result.getShortestPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        assertEquals(expected.getDistanceLength(), result.getDistanceLength());
        System.out.println("getDistanceLength() ok");
    }

    //===============================
    // getGraphAccCounter TESTS
    //===============================
    @Test
    public void getGraphAccCounter() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getShortestPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        result.getShortestPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        assertEquals(expected.getGraphAccCounter(), result.getGraphAccCounter());
        System.out.println("getGraphAccCounter() ok");
    }

    //===============================
    // own Dijkstra with own Graph TESTS
    //===============================
    @Test
    public void testOwnDijk() throws Exception {
        DijkstraAlgorithm expected = new DijkstraAlgorithm();
        Graph owng = new SingleGraph("owng");
        owng.addNode("a");
        owng.addNode("b");
        owng.addNode("c");
        owng.addNode("d");
        owng.addNode("e");
        owng.addNode("f");
        owng.addNode("g");

        owng.addEdge("ab", "a", "b").addAttribute("weight", "2");
        owng.addEdge("ac", "a", "c").addAttribute("weight", "10");
        owng.addEdge("bc", "b", "c").addAttribute("weight", "6");
        owng.addEdge("bd", "b", "d").addAttribute("weight", "15");
        owng.addEdge("cd", "c", "d").addAttribute("weight", "2");
        owng.addEdge("de", "d", "e").addAttribute("weight", "1");
        owng.addEdge("df", "d", "f").addAttribute("weight", "5");
        owng.addEdge("dg", "d", "g").addAttribute("weight", "20");
        owng.addEdge("ef", "e", "f").addAttribute("weight", "4");
        owng.addEdge("fg", "f", "g").addAttribute("weight", "3");

        expected.init(owng);
        List<Node> listeExpected = expected.getShortestPath(owng.getNode("a"), owng.getNode("g"));
        assertEquals("[a, b, c, d, f, g]", listeExpected.toString());
        System.out.println("ownDijk() is ok");
    }

    //===============================
    // BIG TEST
    //===============================
    @Test
    public void testBIG() throws Exception {
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        Graph bigGraph = generateBigOne(100, 2500);

        expected.init(bigGraph);
        result.init(bigGraph);

        expected.getShortestPath(bigGraph.getNode(0), bigGraph.getNode(bigGraph.getNodeCount() - 1));
        result.getShortestPath(bigGraph.getNode(0), bigGraph.getNode(bigGraph.getNodeCount() - 1));
        assertEquals(expected.toString(), result.toString());
        System.out.println("testBIG() ist ok");
    }
}