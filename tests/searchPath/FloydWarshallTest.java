package tests.searchPath;

import algorithm.searchPath.DijkstraAlgorithm;
import algorithm.searchPath.FloydWarshall;
import graph.GraphBuilder;
import io.GraphReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FloydWarshallTest {
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
    public void testOwnFlyod() throws Exception {
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
        Graph bigGraph = GraphBuilder.generateBigOne();
        List<Node> exp = FloydWarshall.getShortestPath(bigGraph, bigGraph.getNode("1"), bigGraph.getNode("100"));
        List<Node> res = FloydWarshall.getShortestPath(bigGraph, bigGraph.getNode("1"), bigGraph.getNode("100"));
        assertEquals(exp, res);
        System.out.println("testBIG() ist ok");
    }

    @Test
    public void floydVSDijkstra() throws Exception {
        DijkstraAlgorithm dk = new DijkstraAlgorithm();
        Graph bigGraph = GraphBuilder.generateBigOne();
        FloydWarshall.getShortestPath(bigGraph, bigGraph.getNode("1"), bigGraph.getNode("100"));
        dk.init(bigGraph);
        dk.getShortestPath(bigGraph.getNode("1"), bigGraph.getNode("100"));
        assertEquals(FloydWarshall.distance, dk.getDistanceLength(), 1e-15);
        System.out.println("flyodVSdijkstra() ist ok");
    }
    
    @Test
    public void testShortestWay() throws Exception{
    	Graph owng2 = new SingleGraph("owng");
        owng2.addNode("a");
        owng2.addNode("b");
        owng2.addNode("c");
        owng2.addNode("d");
        
        owng2.addEdge("ab", "a", "b").addAttribute("weight", "1");
        owng2.addEdge("bc", "b", "c").addAttribute("weight", "2");
        owng2.addEdge("ad", "a", "d").addAttribute("weight", "1");
        owng2.addEdge("dc", "d", "c").addAttribute("weight", "2");
        
        List<Node> result = FloydWarshall.getShortestPath(owng2, owng2.getNode("a"), owng2.getNode("c")); 
    	List<Node> exp = new ArrayList<>();
    	exp.add(owng2.getNode("a"));
    	exp.add(owng2.getNode("b"));
    	exp.add(owng2.getNode("c"));
    	assertEquals(exp, result);
    	System.out.println("testShortestWay() is ok");
    }
    
    @Test
    public void testNegShortestWay() throws Exception{
    	Graph owng2 = new SingleGraph("owng");
        owng2.addNode("a");
        owng2.addNode("b");
        owng2.addNode("c");
        owng2.addNode("d");

        owng2.addEdge("ab", "a", "b").addAttribute("weight", "1");
        owng2.addEdge("bc", "b", "c").addAttribute("weight", "2");
        owng2.addEdge("ad", "a", "d").addAttribute("weight", "2");
        owng2.addEdge("dc", "d", "c").addAttribute("weight", "1");
        
        List<Node> result = FloydWarshall.getShortestPath(owng2, owng2.getNode("a"), owng2.getNode("c")); 
        List<Node> exp = new ArrayList<>();
        exp.add(owng2.getNode("a"));

        assertNotEquals(result, exp);
        System.out.println("testNegShortestWay() is ok");
    }
}