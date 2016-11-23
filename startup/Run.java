package startup;

import algorithmen.DijkstraAlgorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;

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
            //Graph graph = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
            //GraphBuilder.setGraphSettings(graph, true);
            Graph graph = new SingleGraph("graph");
//
//            graph.addNode("v1");
//            graph.addNode("v2");
//            graph.addNode("v3");
//            graph.addNode("v4");
//            graph.addNode("v5");
//            graph.addNode("v6");
//
//            graph.addEdge("v1v2", "v1", "v2").addAttribute("weight", "1");
//            graph.addEdge("v1v6", "v1", "v6").addAttribute("weight", "3");
//            graph.addEdge("v2v3", "v2", "v3").addAttribute("weight", "5");
//            graph.addEdge("v2v5", "v2", "v5").addAttribute("weight", "3");
//            graph.addEdge("v2v6", "v2", "v6").addAttribute("weight", "2");
//
//            graph.addEdge("v3v6", "v3", "v6").addAttribute("weight", "2");
//            graph.addEdge("v3v5", "v3", "v5").addAttribute("weight", "2");
//            graph.addEdge("v3v4", "v3", "v4").addAttribute("weight", "1");
//            graph.addEdge("v5v4", "v5", "v4").addAttribute("weight", "3");
//            graph.addEdge("v5v6", "v5", "v6").addAttribute("weight", "1");

            graph.addNode("a");
            graph.addNode("b");
            graph.addNode("c");
            graph.addNode("d");
            graph.addNode("e");
            graph.addNode("f");
            graph.addNode("g");

            graph.addEdge("ab", "a", "b").addAttribute("weight", "2");
            graph.addEdge("ac", "a", "c").addAttribute("weight", "10");
            graph.addEdge("bc", "b", "c").addAttribute("weight", "6");
            graph.addEdge("bd", "b", "d").addAttribute("weight", "15");
            graph.addEdge("cd", "c", "d").addAttribute("weight", "2");
            graph.addEdge("de", "d", "e").addAttribute("weight", "1");
            graph.addEdge("df", "d", "f").addAttribute("weight", "5");
            graph.addEdge("dg", "d", "g").addAttribute("weight", "20");
            graph.addEdge("ef", "e", "f").addAttribute("weight", "4");
            graph.addEdge("fg", "f", "g").addAttribute("weight", "3");


			DijkstraAlgorithm DK = new DijkstraAlgorithm();
			DK.init(graph);
            //DK.getPath(graph.getNode("a"), graph.getNode("g"));
            List<Node> path1 = DK.getPath(graph.getNode("a"), graph.getNode("g"));
            System.out.println(path1.toString());
			System.out.println(DK.toString());
			//System.out.println(DK1.getGraphAcc());

//			Dijkstra DK = new Dijkstra();
//			DK.init(g);
//			DK.setDestination(g.getNode("Paderborn"), g.getNode("Walsrode"));
//			DK.compute();
//			System.out.println(DK);

//			System.out.println(DK.getShortestPath());
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