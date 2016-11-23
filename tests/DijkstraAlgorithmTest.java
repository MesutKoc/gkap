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
    private static Graph graph03;
    private static DijkstraAlgorithm excepted, result;

    @Before
    public void setUp() throws Exception {
        graph03 = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
        excepted = new DijkstraAlgorithm();
        result = new DijkstraAlgorithm();
        init();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void compute() throws Exception {
        excepted.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        result.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        assertEquals(excepted.toString(), result.toString());
        System.out.println("compute() ok");
    }

    @Test
    public void init() throws Exception {
        excepted.init(graph03);
        result.init(graph03);
        assertNotNull(excepted);
        assertNotNull(result);
    }

    @Test
    public void getPath() throws Exception {

    }

}