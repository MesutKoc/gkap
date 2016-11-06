package startup;

import java.io.File;
import org.graphstream.graph.Graph;
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
			
			// Erstelle Graphen
			Graph g = reader.openFile(new File("bspGraphen/graph01.gka"));
			
			// Speicher Graphen ab
			//GraphSaver.saveGraph(g, new File("bspGraphen/saved/graph01.gka"));
			
			// Starte BFS
			BreadthFirstSearch bfs = new BreadthFirstSearch();
			bfs.initB(g, g.getNode("a"), g.getNode("h"));
			bfs.startSearchEngine().getShortestPath();
			System.out.println(bfs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}