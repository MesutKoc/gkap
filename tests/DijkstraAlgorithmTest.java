package tests;

import algorithmen.DijkstraAlgorithm;
import io.GraphReader;
import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mesut on 23.11.16.
 */
public class DijkstraAlgorithmTest {
    private static Graph graph03, graphNotWeighted;
    private static DijkstraAlgorithm expected, result;

    @Before
    public void setUp() throws Exception {
        graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        graphNotWeighted = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph02.gka"));
        expected = new DijkstraAlgorithm();
        result = new DijkstraAlgorithm();
        init();
    }

    @After
    public void tearDown() throws Exception {

    }

    //===============================
    // compute TESTS
    //===============================
    @Test
    public void compute() throws Exception {
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
        expected.getPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        result.getPath(graph03.getNode("ExistiertNicht"), graph03.getNode("ExistiertNicht"));
        assertEquals(expected, result);
        System.out.println("negGetPath() ok");
    }

    //===============================
    // init TESTS
    //===============================
    @Test
    public void init() throws Exception {
        expected.init(graph03);
        result.init(graph03);
        assertNotNull(expected);
        assertNotNull(result);
    }

    @Test
    public void negInit() {
        expected.init(graphNotWeighted);
        result.init(graphNotWeighted);
        assertEquals(expected.toString(), result.toString());
        System.out.println("negInit() ok");
    }

    //===============================
    // getGraphAccCounter TESTS
    //===============================
    @Test
    public void getGraphAccCounter() throws Exception {
        expected.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        result.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        assertEquals(expected.getGraphAccCounter(), result.getGraphAccCounter());
        System.out.println("getGraphAccCounter() ok");
    }
}