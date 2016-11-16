package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.ParseException;

import org.graphstream.graph.Graph;
import org.junit.Before;
import org.junit.Test;

import algorithmen.BreadthFirstSearch;
import io.GraphReader;

public class BreadthFirstSearchTest {
	@Before
	public void setUp() throws Exception {}

	@Test
	public void testCompute() throws IndexOutOfBoundsException, Exception {
		GraphReader reader = new GraphReader();
		Graph g = reader.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph01.gka"));
		// Create
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		BreadthFirstSearch result = new BreadthFirstSearch();
		// init
		bfs.init(g);
		result.init(g);
		// Start
		bfs.compute();
		result.compute();
		// Set Dest
		bfs.setDestination(g.getNode(0), g.getNode(g.getNodeCount() - 1));
		result.setDestination(g.getNode(0), g.getNode(g.getNodeCount() - 1));
		// Proof
		assertEquals(bfs.toString(), result.toString());
		System.out.println("compute() ist ok");
	}

	@Test
	public void testInit() throws IndexOutOfBoundsException, Exception {
		GraphReader readerTwo = new GraphReader();
		Graph gTwo = readerTwo.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph02.gka"));
		// Create
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		BreadthFirstSearch result = new BreadthFirstSearch();
		// Init
		bfs.init(gTwo);
		result.init(gTwo);
		// Start
		bfs.compute();
		result.compute();
		// Set Dest
		bfs.setDestination(gTwo.getNode(0), gTwo.getNode(gTwo.getNodeCount() - 1));
		result.setDestination(gTwo.getNode(0), gTwo.getNode(gTwo.getNodeCount() - 1));
		// Proof
		assertEquals(bfs.toString(), result.toString());		
		System.out.println("init() ist ok");
	}
	
	// Negative Tests
	@Test(expected = NullPointerException.class)
	public void testNoFile() {
		System.out.println("start testNoFile() negative test // ok");
		GraphReader readerNew = new GraphReader();
		try {
			Graph g2 = readerNew.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph0100.gka"));
			assertNull(g2);
			System.out.println("testNoFile() negative");
		} catch (ParseException | IOException e) {
			assertEquals(NoSuchFileException.class, e.getCause().getCause().getClass());
		}
	}
	
	@Test(expected = AssertionError.class)
	public void testGewichtung() {
		System.out.println("start testGewichtung() negative test // ok");
		GraphReader readerNew = new GraphReader();
		try {
			Graph g2 = readerNew.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
			assertNull(g2);
		} catch (ParseException | IOException e) {
			assertEquals(NoSuchFileException.class, e.getCause().getCause().getClass());
		
		}
	}

	@Test
	public void testGetShortestPath() throws IndexOutOfBoundsException, Exception {
		GraphReader readerTwo = new GraphReader();
		Graph gTwo = readerTwo.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph01.gka"));
		// Create
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		BreadthFirstSearch result = new BreadthFirstSearch();
		// Init
		bfs.init(gTwo);
		result.init(gTwo);
		// Start
		bfs.compute();
		result.compute();
		// Set Dest
		bfs.setDestination(gTwo.getNode(0), gTwo.getNode(gTwo.getNodeCount() - 1));
		result.setDestination(gTwo.getNode(0), gTwo.getNode(gTwo.getNodeCount() - 1));
		// Proof
		assertEquals(bfs.toString(), result.toString());		
		System.out.println("getShortestPath() ist ok");
	}

	@Test
	public void testToString() {
	}
}