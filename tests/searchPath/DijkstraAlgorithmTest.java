package tests.searchPath;

import algorithm.searchPath.DijkstraAlgorithm;
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

public class DijkstraAlgorithmTest {
    //===============================
    // compute TESTS
    //===============================
    @Test
    public void getShortestPath() throws Exception {
        DijkstraAlgorithm expO = new DijkstraAlgorithm();
        DijkstraAlgorithm resO = new DijkstraAlgorithm();
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        expO.init(graph03);
        resO.init(graph03);
        List<Node> exp = expO.getShortestPath(graph03.getNode("Paderborn"), graph03.getNode("Walsrode"));
        List<Node> res = resO.getShortestPath(graph03.getNode("Paderborn"), graph03.getNode("Walsrode"));
        assertEquals(exp, res);
        System.out.println("getShortestPath() ok");
    }

    //===============================
    // getShortestPath TESTS
    //===============================
    @Test
    public void getShortestPathTwo() throws Exception {
        DijkstraAlgorithm expO = new DijkstraAlgorithm();
        DijkstraAlgorithm resO = new DijkstraAlgorithm();
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        expO.init(graph03);
        resO.init(graph03);
        List<Node> exp = expO.getShortestPath(graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        List<Node> res = resO.getShortestPath(graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        assertEquals(exp, res);
        System.out.println("getShortestPathTwo() ok");
    }

    @Test(expected = NullPointerException.class)
    public void negGetPath() throws Exception {
        DijkstraAlgorithm exp = new DijkstraAlgorithm();
        DijkstraAlgorithm res = new DijkstraAlgorithm();
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        exp.init(graph03);
        res.init(graph03);
        exp.getShortestPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        res.getShortestPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        System.out.println("negGetPath() ok");
    }

    //===============================
    // getDistanceLength TESTS
    //===============================
    @Test
    public void getDistanceLength() throws Exception {
        DijkstraAlgorithm exp = new DijkstraAlgorithm();
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        exp.init(graph03);
        exp.getShortestPath(graph03.getNode("Münster"), graph03.getNode("Hamburg"));
        assertEquals(exp.getDistanceLength(), 300, 1e-15);
        System.out.println("getDistanceLength() ok");
    }
    //===============================
    // own Dijkstra with own Graph TESTS
    //===============================
    @Test
    public void testOwnDijk() throws Exception {
        DijkstraAlgorithm exp = new DijkstraAlgorithm();
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
        exp.init(owng);
        List<Node> listeExpected = exp.getShortestPath(owng.getNode("a"), owng.getNode("g"));
        List<Node> result = new ArrayList<>();

        result.add(owng.getNode("a"));
        result.add(owng.getNode("b"));
        result.add(owng.getNode("c"));
        result.add(owng.getNode("d"));
        result.add(owng.getNode("f"));
        result.add(owng.getNode("g"));

        assertEquals(result, listeExpected);
        System.out.println("ownDijk() is ok");
    }

    //===============================
    // BIG TEST
    //===============================
    @Test
    public void testBIG() throws Exception {
        DijkstraAlgorithm exp0 = new DijkstraAlgorithm();
        Graph bigGraph = GraphBuilder.generateBigOne();
        exp0.init(bigGraph);
        List<Node> exp = exp0.getShortestPath(bigGraph.getNode("1"), bigGraph.getNode("100"));
        List<Node> res = new ArrayList<>();
        res.add(bigGraph.getNode("1"));
        res.add(bigGraph.getNode("100"));
        assertEquals(exp, res);
        System.out.println("testBIG() ist ok");
    }

    @Test
    public void testShortestWay() throws Exception{
        DijkstraAlgorithm result = new DijkstraAlgorithm();
        Graph owng2 = new SingleGraph("owng");
        owng2.addNode("a");
        owng2.addNode("b");
        owng2.addNode("c");
        owng2.addNode("d");

        owng2.addEdge("ab", "a", "b").addAttribute("weight", "1");
        owng2.addEdge("bc", "b", "c").addAttribute("weight", "2");
        owng2.addEdge("ad", "a", "d").addAttribute("weight", "1");
        owng2.addEdge("dc", "d", "c").addAttribute("weight", "2");

        result.init(owng2);
        List<Node> result01 = result.getShortestPath(owng2.getNode("a"), owng2.getNode("c"));
        List<Node> exp = new ArrayList<>();
        exp.add(owng2.getNode("a"));
        exp.add(owng2.getNode("b"));
        exp.add(owng2.getNode("c"));
        assertEquals(exp, result01);

    }
}