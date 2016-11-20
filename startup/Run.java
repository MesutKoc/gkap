package startup;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import algorithmen.BreadthFirstSearch;
import algorithmen.Dijkstra;
import graph.GraphBuilder;
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
//			displayMenu();
//			// Speicher Graphen ab
//	        saver.saveGraph(pentaCircle, new File("bspGraphen/saved/graph_new.gka"));
			Graph g = GraphReader.openFile(new File("src/graph/subwerkzeuge/bspGraphen/graph03.gka"));
			Dijkstra DK = new Dijkstra();
			DK.init(g);
			DK.setDestination(g.getNode("Paderborn"), g.getNode("Walsrode"));
			DK.compute();
			System.out.println(DK);
//			GraphBuilder.setGraphSettings(g);
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