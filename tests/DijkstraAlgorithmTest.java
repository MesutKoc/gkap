package tests;

import algorithmen.DijkstraAlgorithm;
import io.GraphReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mesut on 23.11.16.
 */
public class DijkstraAlgorithmTest {
    //===============================
    // compute TESTS
    //===============================
    @Test
    public void compute() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        result.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        assertEquals(expected.toString(), result.toString());
        System.out.println("compute() ok");
    }

    //===============================
    // getPath TESTS
    //===============================
    @Test
    public void getPath() throws Exception {

    }

    @Test(expected = NullPointerException.class)
    public void negGetPath() throws Exception {
        Graph graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        DijkstraAlgorithm expected = new DijkstraAlgorithm(), result = new DijkstraAlgorithm();
        expected.init(graph03);
        result.init(graph03);
        expected.getPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        result.getPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
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
        List<Node> Liste2 = expected.getPath(owng.getNode("a"), owng.getNode("g"));
        assertEquals("[a, b, c, d, f, g]", Liste2.toString());
        System.out.println("ownDijk() is ok");
    }

    @Test
    public void testBIG() throws Exception {
        // TODO BigTest
    }
}