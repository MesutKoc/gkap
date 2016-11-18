package algorithmen;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;
import algorithmen.Dijkstra_v3;
import io.GraphReader;

public class DijkTest {

	@Before
	public void setUp() throws Exception {}

	@Test
	public void testCompute() throws IndexOutOfBoundsException, Exception {
		GraphReader reader = new GraphReader();
		Graph g = reader.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
		// Create
		Dijkstra_v3 dijk = new Dijkstra_v3();
		Dijkstra_v3 dijkResult = new Dijkstra_v3();
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
		System.out.println(dijk);
	}

	@Test
	public void testInit() throws IndexOutOfBoundsException, Exception {
		GraphReader reader02 = new GraphReader();
		Graph graph01 = reader02.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
		
		Dijkstra_v3 dijk02 = new Dijkstra_v3();
		Dijkstra_v3 dijkResult02 = new Dijkstra_v3();
		
		dijk02.init(graph01);
		dijkResult02.init(graph01);
		
		dijk02.compute();
		dijkResult02.compute();
		
		dijk02.setDestination(graph01.getNode(0), graph01.getNode(graph01.getNodeCount()-1));
		dijkResult02.setDestination(graph01.getNode(0), graph01.getNode(graph01.getNodeCount()-1));
		
		assertEquals(dijk02.toString(), dijk02.toString());
		System.out.println("Init () is ok");
		System.out.println(dijk02);
		
		
	}
	@Test
	public void testSetDestionation() throws IndexOutOfBoundsException, Exception {
		GraphReader reader03 = new GraphReader();
		Graph graph02 = reader03.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
		
		Dijkstra_v3 dijk03 = new Dijkstra_v3();
		Dijkstra_v3 dijkResult03 = new Dijkstra_v3();
		
		dijk03.init(graph02);
		dijkResult03.init(graph02);
		
		dijk03.compute();
		dijkResult03.compute();
		
		dijk03.setDestination(graph02.getNode(0), graph02.getNode(graph02.getNodeCount()-1));
		dijkResult03.setDestination(graph02.getNode(0), graph02.getNode(graph02.getNodeCount()-1));
		
		assertEquals(dijk03.toString(), dijk03.toString());
		System.out.println("SetDestination() is ok");
		System.out.println(dijk03);
		
		
	}
	
	

	@Test
	public void testToString() throws IndexOutOfBoundsException, Exception {
		GraphReader reader04 = new GraphReader();
		Graph graph03 = reader04.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
		
		Dijkstra_v3 dijk04 = new Dijkstra_v3();
		Dijkstra_v3 dijkResult04 = new Dijkstra_v3();
		
		dijk04.init(graph03);
		dijkResult04.init(graph03);
		
		dijk04.compute();
		dijkResult04.compute();
		
		dijk04.setDestination(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
		dijkResult04.setDestination(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
		
		assertEquals(dijk04.toString(), dijk04.toString());
		System.out.println("toString() is ok");
		System.out.println(dijk04);
		
		
	}
	
	@Test
	public void testDijkstra() throws IndexOutOfBoundsException, Exception{
		
		GraphReader reader05 = new GraphReader();
		Graph graph04 = reader05.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
		
		Dijkstra_v3 dijk05 = new Dijkstra_v3();
		Dijkstra_v3 dijkResult05 = new Dijkstra_v3();
		
		dijk05.init(graph04);
		dijkResult05.init(graph04);
		
		dijk05.compute();
		dijkResult05.compute();
		
		dijk05.setDestination(graph04.getNode(0), graph04.getNode(graph04.getNodeCount()-1));
		dijkResult05.setDestination(graph04.getNode(0), graph04.getNode(graph04.getNodeCount()-1));
		
		assertEquals(dijk05.toString(), dijk05.toString());
		System.out.println("Dijkstra() is ok");
		System.out.println(dijk05);
		
		
		
	}
	@Test
	public void testOwnDijk() throws Exception{
		
		//own Graphs
		Graph owng = new SingleGraph("owng");
		
		owng.addNode("a");
		owng.addNode("b");
		owng.addNode("c");
		owng.addNode("d");
		owng.addNode("e");
		owng.addNode("f");
		owng.addNode("g");

		owng.addEdge("ab", "a", "b").addAttribute("weight", 2.0);
		owng.addEdge("ac", "a", "c").addAttribute("weight", 10.0);
		owng.addEdge("bc", "b", "c").addAttribute("weight", 6.0);
		owng.addEdge("bd", "b", "d").addAttribute("weight", 15.0);
		owng.addEdge("cd", "c", "d").addAttribute("weight", 2.0);
		owng.addEdge("de", "d", "e").addAttribute("weight", 1.0);
		owng.addEdge("df", "d", "f").addAttribute("weight", 5.0);
		owng.addEdge("dg", "d", "g").addAttribute("weight", 20.0);
		owng.addEdge("ef", "e", "f").addAttribute("weight", 4.0);
		owng.addEdge("fg", "f", "g").addAttribute("weight", 3.0);
	    
	    
	    
	    Dijkstra_v3 ownDijk = new Dijkstra_v3();
	    ownDijk.init(owng);
	    ownDijk.setDestination(owng.getNode("a"), owng.getNode("g"));
	    ownDijk.compute();
	    System.out.println(ownDijk);
	    System.out.println("Test");

	}
	
	
}