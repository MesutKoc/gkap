package startup;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

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
	static Scanner reader = new Scanner(System.in);
	static Graph g;
	/**
	 * Startet die Applikation
	 * 
	 * @param args
	 * 			main
	 */
	public static void main(String[] args) {
		run();
	}
	
	public static void displayMenu() throws Exception {
		int n = 0;
		boolean exit = false;
	    do{
	    	 System.out.println("============================");
	 	    System.out.println("|   MENU SELECTION 	   |");
	 	    System.out.println("============================");
	 	    System.out.println("| Auswahl:                 |");
	 	    System.out.println("|        1. Graph load     |");
	 	    System.out.println("|        2. Graph save     |");
	 	    System.out.println("|        3. Start BFS      |");
	 	    System.out.println("|        4. Exit	       |");
	 	    System.out.println("============================");
	 	    System.out.print("Eingabe: ");
	 	    n = reader.nextInt();
	    switch(n){
			case 1 :
				getFileNameFromUser();
				break;
			case 3 :
				startBfs();
				break;
			case 4:
				exit = true;
				break;
			default :
				System.out.print("Falsche Eingabe!");
	    }
	    }while(!exit);
	}
	
	private static void startBfs() throws Exception {
		if(g == null) System.out.println("Graph muss vorher geladen werden!");

		System.out.print("StartVertex: ");
		String startVertex = reader.next();
		System.out.print("EndVertex: ");
		String endVertex = reader.next();
		
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.init(g);
		bfs.compute();
		bfs.setDestination(g.getNode(startVertex), g.getNode(endVertex));
		System.out.println(bfs);
	}

	private static void getFileNameFromUser() throws ParseException, IOException {
		System.out.print("Wie heißt die Datei?: ");
		String fileName = reader.next();
		if(!fileName.isEmpty())
			getGraphFromUser(fileName);
	}
	
	private static Graph getGraphFromUser(String fileName) throws ParseException, IOException{
		GraphReader reader   = new GraphReader();
		// Erstelle Graphen
		g = reader.openFile(new File("src/graph/subwerkzeuge/bspGraphen/"+fileName));
		return g;
	}

	/**
	 * run
	 */
	public static void run() {
		try {
			displayMenu();
//			// Speicher Graphen ab
//	        saver.saveGraph(pentaCircle, new File("bspGraphen/saved/graph_new.gka"));
			
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