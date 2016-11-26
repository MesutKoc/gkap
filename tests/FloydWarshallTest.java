package tests;

import algorithm.searchPath.DijkstraAlgorithm;
import algorithm.searchPath.FloydWarshall;
import io.GraphReader;
import io.GraphSaver;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class FloydWarshallTest {
    private static Graph generateBigOne(int numNodes, int numEdge) throws IOException {
        Random random = new Random();
        Graph result = new MultiGraph("big");
        int edgeIdent = 0;

        for (int i = 1; i <= numNodes; i++) result.addNode(String.format("%d", i));
        result.addEdge("1_100", "1", "100", true).addAttribute("weight", 1);
        while (result.getEdgeCount() < numEdge) {
            for (int i = 2; i <= numEdge; i++) {
                int x = random.nextInt(numNodes - 1) + 1;
                int y = random.nextInt(numNodes - 1) + 1;
                result.addEdge(String.format("%d%d|%d", x, y, edgeIdent), String.format("%d", x), String.format("%d", y), true)
                        .addAttribute("weight", random.nextInt(numNodes - 1) + 1);
                edgeIdent++;
            }
        }
        GraphSaver.saveGraph(result, new File("graph/subwerkzeuge/bspGraphen/saved/biG.gka"));
        return result;
    }

    //===============================
    // compute TESTS
    //===============================
    @Test
    public void getShortestPath() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        List<Node> exp = FloydWarshall.getShortestPath(graph03, graph03.getNode("Paderborn"), graph03.getNode("Walsrode"));
        List<Node> res = FloydWarshall.getShortestPath(graph03, graph03.getNode("Paderborn"), graph03.getNode("Walsrode"));
        assertEquals(exp, res);
        System.out.println("getShortestPath() ok");
    }

    //===============================
    // getShortestPath TESTS
    //===============================
    @Test
    public void getShortestPathTwo() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        List<Node> exp = FloydWarshall.getShortestPath(graph03, graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        List<Node> res = FloydWarshall.getShortestPath(graph03, graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        assertEquals(exp, res);
        System.out.println("getShortestPathTwo() ok");
    }

    @Test(expected = NullPointerException.class)
    public void negGetPath() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        FloydWarshall.getShortestPath(graph03, graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        FloydWarshall.getShortestPath(graph03, graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        System.out.println("negGetPath() ok");
    }

    //===============================
    // getDistanceLength TESTS
    //===============================
    @Test
    public void getDistanceLength() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        FloydWarshall.getShortestPath(graph03, graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        assertEquals(FloydWarshall.distance, 300, 1e-15);
        System.out.println("getDistanceLength() ok");
    }

    //===============================
    // own Dijkstra with own Graph TESTS
    //===============================
    @Test
    public void testOwnDijk() throws Exception {
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

        List<Node> listeExpected = FloydWarshall.getShortestPath(owng, owng.getNode("a"), owng.getNode("g"));
        assertEquals("[a, b, c, d, f, g]", listeExpected.toString());
        System.out.println("ownDijk() is ok");
    }

    //===============================
    // BIG TEST
    //===============================
    @Test
    public void testBIG() throws Exception {
        Graph bigGraph = generateBigOne(100, 2500);
        List<Node> exp = FloydWarshall.getShortestPath(bigGraph, bigGraph.getNode("1"), bigGraph.getNode("100"));
        List<Node> res = FloydWarshall.getShortestPath(bigGraph, bigGraph.getNode("1"), bigGraph.getNode("100"));
        assertEquals(exp, res);
        System.out.println("testBIG() ist ok");
    }

    @Test
    public void floydVSDijkstra() throws Exception {
        DijkstraAlgorithm dk = new DijkstraAlgorithm();
        Graph bigGraph = generateBigOne(100, 2500);
        FloydWarshall.getShortestPath(bigGraph, bigGraph.getNode("1"), bigGraph.getNode("100"));
        dk.init(bigGraph);
        dk.getShortestPath(bigGraph.getNode("1"), bigGraph.getNode("100"));
        assertEquals(FloydWarshall.distance, dk.getDistanceLength(), 1e-15);
        System.out.println("testBIG() ist ok");
    }
}