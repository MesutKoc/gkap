package algorithmen;

import static org.junit.Assert.*;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;

public class bfsTest {
	
	@Before
	public void SetUp() throws Exception{
		
	}
	

	@Test
	public void startSearchEngineTest() throws Exception {
		
		//kette
		Graph graph = new SingleGraph("graph");
		//Nodes
		graph.addNode("a");
		graph.addNode("b");
		graph.addNode("c");
		graph.addNode("d");
		//Kanten für die Nodes
		graph.addEdge("ab", "a", "b");
		graph.addEdge("bc", "b", "c");
		graph.addEdge("cd", "c", "d");
		
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		//init und aufruf der methoden.
		bfs.initB(graph, graph.getNode("a"), graph.getNode("d"));
		bfs.startSearchEngine().getShortestPath();
		//Prüfung auf Richtigkeit.
		assertEquals(3, bfs.getSteps());
		assertEquals("[d, c, b, a]", bfs.getShortestPath().toString());
		

		
		//kreis
		Graph kreis = new SingleGraph("kreis");
		//Nodes
		kreis.addNode("a");
		kreis.addNode("b");
		kreis.addNode("c");
		kreis.addNode("d");
		kreis.addNode("e");
		kreis.addNode("f");
		
		//Kanten für die Nodes.
		kreis.addEdge("ab", "a", "b");
		kreis.addEdge("bc", "b", "c");
		kreis.addEdge("cd", "c", "d");
		kreis.addEdge("de", "d", "e");
		kreis.addEdge("ef", "e", "f");
		kreis.addEdge("fa", "f", "a");
		
		//init und Aufruf der Methoden.
		BreadthFirstSearch bfs2 = new BreadthFirstSearch();
		bfs2.initB(kreis, kreis.getNode("a"), kreis.getNode("f"));
		bfs2.startSearchEngine().getShortestPath();
		//Prüfung auf Richtigkeit.
		assertEquals(1, bfs2.getSteps());
		assertEquals("[f, a]", bfs2.getShortestPath().toString());
		 
		        
		//pentagramm
		Graph penta = new SingleGraph("penta");
		//Nodes
		penta.addNode("a");
		penta.addNode("b");
		penta.addNode("c");
		penta.addNode("d");
		penta.addNode("e");
		
		//Kanten
		penta.addEdge("ac", "a", "c");
		penta.addEdge("eb", "e", "b");
		penta.addEdge("ad", "a", "d");
		penta.addEdge("bd", "b", "d");
		penta.addEdge("ec", "e", "c");
		
		//Init und Aufruf der Methoden.
		BreadthFirstSearch bfs3 = new BreadthFirstSearch();
		bfs3.initB(penta, penta.getNode("a"), penta.getNode("e"));
		bfs3.startSearchEngine().getShortestPath();
		//Prüfung auf Richtigkeit.
		assertEquals(2, bfs3.getSteps());
		assertEquals("[e, c, a]", bfs3.getShortestPath().toString());
	}		
		
		
		@Test
		public void FatGraph() throws Exception{
			
			Graph fatGraph = new SingleGraph("fat");
			int edge = 10000;
			fatGraph.addNode("0");
			for(int i = 1; i <= edge; i++){
				fatGraph.addNode("" + i);
				fatGraph.addEdge("" + (i-1) , ""+ (i-1), ""+ i);
			
				
			}
			BreadthFirstSearch bfs_fat = new BreadthFirstSearch();
			bfs_fat.initB(fatGraph, fatGraph.getNode("0"), fatGraph.getNode(""+ edge));
			bfs_fat.startSearchEngine();
			assertEquals(edge, bfs_fat.getSteps());
			
		}
		
		
		
	}



