package tests;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;
import algorithmen.Dijkstra;
import io.GraphReader;

public class DijkstraTest {

	private List<Node> listOne;

	@Before
	public void setUp() throws Exception {
		
		
	}

//	@Test
//	public void testCompute() throws IndexOutOfBoundsException, Exception {
//		GraphReader reader = new GraphReader();
//		Graph g = reader.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
//		// Create
//		Dijkstra dijk = new Dijkstra();
//		Dijkstra dijkResult = new Dijkstra();
//		// init
//		dijk.init(g);
//		dijkResult.init(g);
//		// Start
//		dijk.compute();
//		dijkResult.compute();
//		// Set Dest
//		dijk.setDestination(g.getNode(0), g.getNode(g.getNodeCount() - 1));
//		dijkResult.setDestination(g.getNode(0), g.getNode(g.getNodeCount() - 1));
//		// Proof
//		assertEquals(dijk.toString(), dijkResult.toString());
//		System.out.println("compute() ist ok");
////		System.out.println(dijk);
//	}
//
//	@Test
//	public void testInit() throws IndexOutOfBoundsException, Exception {
//		GraphReader reader02 = new GraphReader();
//		Graph graph01 = reader02.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
//		
//		Dijkstra dijk02 = new Dijkstra();
//		Dijkstra dijkResult02 = new Dijkstra();
//		
//		dijk02.init(graph01);
//		dijkResult02.init(graph01);
//		
//		dijk02.compute();
//		dijkResult02.compute();
//		
//		dijk02.setDestination(graph01.getNode(0), graph01.getNode(graph01.getNodeCount()-1));
//		dijkResult02.setDestination(graph01.getNode(0), graph01.getNode(graph01.getNodeCount()-1));
//		
//		assertEquals(dijk02.toString(), dijk02.toString());
//		System.out.println("Init () is ok");
//
//		
//		
//	}
//	@Test
//	public void testSetDestionation() throws IndexOutOfBoundsException, Exception {
//		GraphReader reader03 = new GraphReader();
//		Graph graph02 = reader03.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
//		
//		Dijkstra dijk03 = new Dijkstra();
//		Dijkstra dijkResult03 = new Dijkstra();
//		
//		dijk03.init(graph02);
//		dijkResult03.init(graph02);
//		
//		dijk03.compute();
//		dijkResult03.compute();
//		
//		dijk03.setDestination(graph02.getNode(0), graph02.getNode(graph02.getNodeCount()-1));
//		dijkResult03.setDestination(graph02.getNode(0), graph02.getNode(graph02.getNodeCount()-1));
//		
//		assertEquals(dijk03.toString(), dijk03.toString());
//		System.out.println("SetDestination() is ok");
//	}
//	
//	@Test
//	public void testToString() throws IndexOutOfBoundsException, Exception {
//		GraphReader reader04 = new GraphReader();
//		Graph graph03 = reader04.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
//		
//		Dijkstra dijk04 = new Dijkstra();
//		Dijkstra dijkResult04 = new Dijkstra();
//		
//		dijk04.init(graph03);
//		dijkResult04.init(graph03);
//		
//		dijk04.compute();
//		dijkResult04.compute();
//		
//		dijk04.setDestination(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
//		dijkResult04.setDestination(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
//		
//		assertEquals(dijk04.toString(), dijk04.toString());
//		System.out.println("toString() is ok");
//	}
	
	@Test
	public void testDijkstra() throws IndexOutOfBoundsException, Exception{
		List<Node> liste1 = new ArrayList<>();
		List<Node> liste2 = new ArrayList<>();
//		GraphReader reader05 = new GraphReader();
		Graph graph04 = GraphReader.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
		liste1.add(graph04.getNode("Paderborn"));
		liste1.add(graph04.getNode("Hameln"));
		liste1.add(graph04.getNode("Walsrode"));
		
		Dijkstra DK = new Dijkstra();
		Dijkstra DKresult = new Dijkstra();
		
		DK.init(graph04);
		DK.setDestination(graph04.getNode("Paderborn"), graph04.getNode("Walsrode"));
		DK.compute();
		
//		DKresult.init(graph04);
//		DKresult.setDestination(graph04.getNode("Paderborn"), graph04.getNode("Walsrode"));
//		DKresult.compute();
		System.out.println(DK);
		assertEquals(DK, liste1.toString());
	}
	
//	@Test
//	public void testOwnDijk() throws Exception{
//		//own Graphs
//		Graph owng = new SingleGraph("owng");
//		
//		owng.addNode("a");
//		owng.addNode("b");
//		owng.addNode("c");
//		owng.addNode("d");
//		owng.addNode("e");
//		owng.addNode("f");
//		owng.addNode("g");
//
//		owng.addEdge("ab", "a", "b").addAttribute("weight", 2.0);
//		owng.addEdge("ac", "a", "c").addAttribute("weight", 10.0);
//		owng.addEdge("bc", "b", "c").addAttribute("weight", 6.0);
//		owng.addEdge("bd", "b", "d").addAttribute("weight", 15.0);
//		owng.addEdge("cd", "c", "d").addAttribute("weight", 2.0);
//		owng.addEdge("de", "d", "e").addAttribute("weight", 1.0);
//		owng.addEdge("df", "d", "f").addAttribute("weight", 5.0);
//		owng.addEdge("dg", "d", "g").addAttribute("weight", 20.0);
//		owng.addEdge("ef", "e", "f").addAttribute("weight", 4.0);
//		owng.addEdge("fg", "f", "g").addAttribute("weight", 3.0);
//	    
//		Dijkstra ownDijk = new Dijkstra();
//	    ownDijk.init(owng);
//	    ownDijk.setDestination(owng.getNode("a"), owng.getNode("g"));
//	    ownDijk.compute();
//	    System.out.println(ownDijk);
//	}
}