package tests;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.graphstream.graph.Graph;
import org.junit.Before;
import org.junit.Test;
import algorithmen.Dijkstra;
import io.GraphReader;

public class DijkstraTest {

	@Before
	public void setUp() throws Exception {}

	@Test
	public void testCompute() throws IndexOutOfBoundsException, Exception {
		GraphReader reader = new GraphReader();
		Graph g = reader.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
		// Create
		Dijkstra dijk = new Dijkstra();
		Dijkstra dijkResult = new Dijkstra();
		// init
		dijk.init(g);
		dijkResult.init(g);
		// Start
		dijk.compute();
		dijkResult.compute();
		// Set Dest
		dijk.setDestination(g.getNode(0), g.getNode(g.getNodeCount() - 1));
		dijkResult.setDestination(g.getNode(0), g.getNode(g.getNodeCount() - 1));
		// Proof
		assertEquals(dijk.toString(), dijkResult.toString());
		System.out.println("compute() ist ok");
	}

	@Test
	public void testInit() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testToString() {
//		fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testDijkstra(){
		
	}

}
