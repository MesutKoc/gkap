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
	@SuppressWarnings("unused")
	public static void run() {
		try {
			// Starte Reader
			GraphReader reader = GraphReader.create();
			
			// Erstelle Graphen
			Graph g = GraphReader.openFile(new File("bspGraphen/graph01.gka"));
			
			// Speicher Graphen ab
			GraphSaver.saveGraph(g, new File("bspGraphen/saved/graph01.gka"));
			
			// Starte BFS
			BreadthFirstSearch bfs = BreadthFirstSearch.getInstance();
			bfs.initA(g, g.getNode("a"), g.getNode("d"));
			BreadthFirstSearch.startSearchEngine();
			bfs.resultShortestWay();
			System.out.println(bfs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}