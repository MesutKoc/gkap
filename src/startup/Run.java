package startup;

import java.io.File;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import algorithmen.BreadthFirstSearch;
import io.GraphReader;
import io.GraphSaver;

/**
 * <h1>Run.java</h1> Diese Klasse startet die Applikation
 *
 * @author Mesut koc
 * @version 1.1
 * @since 2016-10-18
 */
@SuppressWarnings("unused")
public class Run {
	/**
	 * Startet die Applikation
	 * 
	 * @param args
	 * 			main
	 */
	public static void main(String[] args) {
		run();
	}

	/**
	 * run
	 */
	public static void run() {
		try {
			// Starte Reader
			GraphReader reader = new GraphReader();
			GraphSaver saver   = new GraphSaver();
			
			// Erstelle Graphen
//			Graph g = reader.openFile(new File("bspGraphen/graph01.gka"));
			Graph pentaCircle = new MultiGraph("pentaCircle");
	        pentaCircle.addNode("a");
	        pentaCircle.addNode("b");
	        pentaCircle.addNode("c");
	        pentaCircle.addNode("d");
	        pentaCircle.addNode("e");

	        pentaCircle.addEdge("ab", "a", "b");
	        pentaCircle.addEdge("ac", "a", "c");
	        pentaCircle.addEdge("ae", "a", "e");
	        pentaCircle.addEdge("ad", "a", "d");
	        pentaCircle.addEdge("bd", "b", "d");
	        pentaCircle.addEdge("bc", "b", "c");
	        pentaCircle.addEdge("be", "b", "e");
	        pentaCircle.addEdge("ed", "e", "d");
	        pentaCircle.addEdge("ec", "e", "c");
	        pentaCircle.addEdge("cd", "c", "d");
	        pentaCircle.display();
			
			// Speicher Graphen ab
	        saver.saveGraph(pentaCircle, new File("bspGraphen/saved/graph_new.gka"));
			
			// Starte BFS
//			BreadthFirstSearch bfs = new BreadthFirstSearch();
//			bfs.initB(g, g.getNode("a"), g.getNode("h"));
//			bfs.startSearchEngine().getShortestPath();
//			System.out.println(bfs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}