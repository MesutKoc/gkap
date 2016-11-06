package tests;

import static org.junit.Assert.assertEquals;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;

import algorithmen.BreadthFirstSearch;

public class BreadthFirstSearchTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStartSearchEngine() throws Exception {
		Graph graph = new SingleGraph("graph");

		//A -- B -- C -- D
		graph.addNode("a");
		graph.addNode("b");
		graph.addNode("c");
		graph.addNode("d");
		graph.addNode("e");
		
		//Kanten
		graph.addEdge("ab", "a", "b");
        graph.addEdge("ac", "a", "c");
        graph.addEdge("de", "d", "e");
        graph.addEdge("ec", "e", "c");
        graph.addEdge("bd", "b", "d");
		
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.initA(graph, graph.getNode("a"), graph.getNode("c"));
		bfs.startSearchEngine();
		assertEquals(2, bfs.count);
//		assertEquals("[c, b, a]", bfs.resultShortestWay().toString());
	}

	@Test
	public void testResultShortestWay() {
		//fail("Not yet implemented"); // TODO
	}

}
