package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import org.graphstream.graph.Node;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithmen.BreadthFirstSearch;
import algorithmen.DijkstraAlgorithm;
import graph.GraphBuilder;
import io.GraphReader;

public class DijkstraTest {
	
    private static Graph graph03;
    private static DijkstraAlgorithm expected, result;
 
	
	
	@Before
	public void setUp() throws Exception {}
	
	public void init(){
		expected.init(graph03);
		result.init(graph03);
		assertNotNull(expected);
        assertNotNull(result);
	}

	@Test
	public void testCompute() throws IndexOutOfBoundsException, Exception {
	
		graph03 = GraphReader.openFile(new File("src/graph/subwerkzeuge/graph03.gka"));
		// Create
		 expected = new DijkstraAlgorithm();
		 result = new DijkstraAlgorithm();
		// init
		 init();
		// Start
		expected.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
		result.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
		// Proof
		assertEquals(expected.toString(), result.toString());
		System.out.println("testCompute() is ok");
	}

	@Test
	public void testInit() throws IndexOutOfBoundsException, Exception {
		
		graph03 = GraphReader.openFile(new File("src/graph/subwerkzeuge/graph03.gka"));
		
		 expected = new DijkstraAlgorithm();
		 result = new DijkstraAlgorithm();
		// init
		 init();
		// Proof
		assertEquals(expected.toString(), result.toString());
		System.out.println("testInit() is ok");
	}
	

	@Test
	public void testToString() throws IndexOutOfBoundsException, Exception {
		
		graph03 = GraphReader.openFile(new File("src/graph/subwerkzeuge/graph03.gka"));
		// Create
		 expected = new DijkstraAlgorithm();
		 result = new DijkstraAlgorithm();
		// init
		 init();
		// Start
		expected.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
		result.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount()-1));
		// Proof
		assertEquals(expected.toString(), result.toString());
		
		System.out.println("testToString() is ok");
	}
	
	@Test
	public void testDijkstra() throws IndexOutOfBoundsException, Exception{

		graph03 = GraphReader.openFile(new File("src/graph/subwerkzeuge/graph03.gka"));
		
		expected = new DijkstraAlgorithm();
		result = new DijkstraAlgorithm();
		// init
		 init();
		// Start
		expected.getPath(graph03.getNode("Paderborn"), graph03.getNode("Walsrode"));
		result.getPath(graph03.getNode("Paderborn"), graph03.getNode("Walsrode"));
		// Proof
		assertEquals(expected.toString(), result.toString());
	
		System.out.println("TestDijkstra() is ok");

	}
	//Negative Test
	@Test (expected = NullPointerException.class)
    public void negGetPath() throws Exception {
		graph03 = GraphReader.openFile(new File("src/graph/subwerkzeuge/graph03.gka"));
		init();
		expected.getPath(graph03.getNode("Exist"), graph03.getNode("Exist"));
        result.getPath(graph03.getNode("Exist"), graph03.getNode("Exist"));
        assertEquals(expected.toString(), result.toString());
        
        System.out.println("negGetPath() is ok");
    }
	
	@Test (expected = NullPointerException.class)
    public void negNode() throws Exception {
		graph03 = GraphReader.openFile(new File("src/graph/subwerkzeuge/graph03.gka"));
		expected = new DijkstraAlgorithm();
		result = new DijkstraAlgorithm();
		init();
		expected.getPath(graph03.getNode("Paderborn"), graph03.getNode("DoesNotExist"));
        result.getPath(graph03.getNode("Paderborn"), graph03.getNode("DoesNotExist"));
        assertEquals(expected.toString(), result.toString());
        
        System.out.println("negNode() is ok");
    }
	
	@Test
    public void getGraphAccCounter() throws Exception {
        expected.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        result.getPath(graph03.getNode(0), graph03.getNode(graph03.getNodeCount() - 1));
        assertEquals(expected.getGraphAccCounter(), result.getGraphAccCounter());
        System.out.println("getGraphAccCounter() ok");
    }
	
	
	@Test
	public void testOwnDijk() throws Exception{
		Graph owng = new SingleGraph("owng");
		
		//List<Node> Liste2 = new ArrayList<>();
		
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
	    
	    
		DijkstraAlgorithm ownDijk = new DijkstraAlgorithm();
	    ownDijk.init(owng);
	    List<Node> Liste2 = ownDijk.getPath(owng.getNode("a"), owng.getNode("g"));
	    assertEquals("[a, b, c, d, f, g]", Liste2.toString());
	    System.out.println("ownDijk() is ok");

	}
	
	@Test
	public void testBIG() throws Exception{
		
		expected = new DijkstraAlgorithm();
		result = new DijkstraAlgorithm();
		Graph BIG = new SingleGraph("BIG");
		int edge = 2500;
		BIG.addNode("0");
		for(int i = 1; i <= edge; i++){
			BIG.addNode("" + i);
			BIG.addEdge("" + (i-1) , ""+ (i-1), ""+ i);
			System.out.println(i);
			for(int w = 1; w <= edge; w++){
				BIG.addAttribute("weight", ""+ w+1);
			}
		}
		
		init();
		BIG.display();
		expected.getPath(BIG.getNode(2), BIG.getNode(2500));
		result.getPath(BIG.getNode(2), BIG.getNode(2500));;
		assertEquals(expected.toString(),result.toString());
		
		System.out.println("testBIG() is ok");
	}
	
	
}