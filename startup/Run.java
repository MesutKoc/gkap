package startup;

import algorithm.searchPath.FloydWarshall;
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
     * @param args main
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
            //Graph test = GraphReader.openFile(new File("graph/subwerkzeuge/bspGraphen/graph03.gka"));
            //GraphBuilder.setGraphSettings(graph, true);
            Graph test = new SingleGraph("test");
            test.addNode("0");
            test.addNode("1");
            test.addNode("2");
            test.addNode("3");
            test.addNode("4");
            test.addNode("5");
            test.addNode("6");
            test.addNode("7");
            test.addEdge("01", "0", "1", true).addAttribute("weight", 3);
            test.addEdge("03", "0", "3", true).addAttribute("weight", 2);
            test.addEdge("10", "1", "0", true).addAttribute("weight", 2);
            test.addEdge("15", "1", "5", true).addAttribute("weight", 3);
            test.addEdge("16", "1", "6", true).addAttribute("weight", 8);
            test.addEdge("26", "2", "6", true).addAttribute("weight", 8);
            test.addEdge("42", "4", "2", true).addAttribute("weight", 3);
            test.addEdge("46", "4", "6", true).addAttribute("weight", 1);
            test.addEdge("53", "5", "3", true).addAttribute("weight", 0);
            test.addEdge("56", "5", "6", true).addAttribute("weight", 2);
            test.addEdge("67", "6", "7", true).addAttribute("weight", 1);


            FloydWarshall floyd = new FloydWarshall();
            floyd.init(test);
            List<Node> path1 = floyd.getShortestPath(test.getNode("0"), test.getNode("7"));
            System.out.println(path1.toString());
            System.out.println("Zugriffe: " + floyd.getAcc());
            System.out.println("length: " + floyd.getDistance());

/*            DijkstraAlgorithm DK = new DijkstraAlgorithm();
            DK.init(graph);
            //DK.getShortestPath(graph.getNode("a"), graph.getNode("g"));
            List<Node> path1 = DK.getShortestPath(graph.getNode("Paderborn"), graph.getNode("Walsrode"));
            System.out.println(path1.toString());
            System.out.println(DK.toString());
            System.out.println(DK.getDistanceLength());*/
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