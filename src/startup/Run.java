package startup;

import java.io.File;
import org.graphstream.graph.Graph;
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
			GraphReader reader = GraphReader.create();
			Graph g = GraphReader.openFile(new File("bspGraphen/graph01.gka"));
			GraphSaver.saveGraph(g, new File("bspGraphen/saved/graph01.gka"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}