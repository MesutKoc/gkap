package startup;

import algorithm.searchPath.DijkstraAlgorithm;
import algorithm.searchPath.FloydWarshall;
import io.GraphReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Die Klasse führt unterschiedliche Messungen in
 * Bezug auf Dijkstra und Floyd-Warshall Algorithmen aus
 * und zählt die Laufzeit und die Distance auf den unterliegenden Graphen.
 *
 * @author Mesut Koc
 */
public class benchmark {
    /*===================================
      CONFIG
     ====================================*/
    private static final String BENCHMARK_RESULT = "benchmark.csv";
    private static final String DELIMETER = ";";
    // Angabe des Pfades1
    private static final String graph03 = "graph/subwerkzeuge/bspGraphen/graph03.gka";
    // Graph Namen zum Importieren
    private static final String IMPORTLIST[] = {graph03, graph03, graph03};
    // Startpunkte der Suche
    private static final String SOURCELIST[] = {"Paderborn", "Paderborn", "Hamburg"};
    // Endpunkte der Suche
    private static final String TARGETLIST[] = {"Buxtehude", "Walsrode", "Walsrode"};
    private static DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();

    /*===================================
      Start
     ====================================*/
    public static void main(String args[]) throws Exception {
        StringBuilder tempResult = new StringBuilder();
        tempResult.append(String.format("Graph%sAlgorithmus%sLaufzeit(sec)%sDistance%sKuerzester Weg%s\n",
                DELIMETER, DELIMETER, DELIMETER, DELIMETER, DELIMETER));

        for (int i = 0; i < IMPORTLIST.length; i++) {
            Graph g = GraphReader.openFile(new File(IMPORTLIST[i]));
            Node source = g.getNode(SOURCELIST[i]);
            Node target = g.getNode(TARGETLIST[i]);

            dijkstra.init(g);
            // Angabe der Graphdatei
            tempResult.append(String.format("%s%s", IMPORTLIST[i].replace("graph/subwerkzeuge/bspGraphen/", ""), DELIMETER));
            tempResult.append(String.format("Dijkstra%s", DELIMETER));
            // Messungen der Laufzeit des Algorithmus
            tempResult.append(String.format("%f%s", dijkstra.algorithmRtm(g, source, target), DELIMETER));
            // Messung für Distance
            tempResult.append(String.format("%s%s", dijkstra.getDistanceLength(), DELIMETER));
            // Darstellung des kürzesten Weges
            List<Node> shortestRouteBF = dijkstra.getShortestPath(source, target);
            printRoute(shortestRouteBF, tempResult);

            FloydWarshall floyd = new FloydWarshall();
            floyd.init(g);
            // Angabe der Graphdatei
            tempResult.append(String.format("%s%s", IMPORTLIST[i].replace("graph/subwerkzeuge/bspGraphen/", ""), DELIMETER));
            tempResult.append(String.format("Floyd Warshall%s", DELIMETER));
            // Messungen der Laufzeit des Algorithmus
            tempResult.append(String.format("%f%s", floyd.algorithmRtm(g, source, target), DELIMETER));
            // Messung für Distance
            tempResult.append(String.format("%s%s", floyd.getDistance(), DELIMETER));
            // Darstellung des kürzesten Weges
            List<Node> shortestRouteFW = floyd.getShortestPath(source, target);
            printRoute(shortestRouteFW, tempResult);
        }
        try {
            Files.write(Paths.get(BENCHMARK_RESULT), tempResult.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printRoute(List<Node> route, StringBuilder tempResult) {
        if (!(route.size() == 0)) {
            String printedRoute = "";
            for (Node v : route) printedRoute = printedRoute.concat(String.format("%s->", v.getId()));
            tempResult.append(String.format("%s\n", printedRoute.substring(0, printedRoute.length() - 2)));
        } else tempResult.append("Kein Weg gefunden oder negativer Kreis wurde entdeckt\n");
    }
}