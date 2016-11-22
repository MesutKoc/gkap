package algorithmen;

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
    // Angabe des Pfades
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
        tempResult.append("Graph" + DELIMETER +
                "Algorithmus" + DELIMETER +
                "Laufzeit" + DELIMETER +
                "Distance " + DELIMETER +
                "Kuerzester Weg" + DELIMETER + "\n");

        for (int i = 0; i < IMPORTLIST.length; i++) {
            Graph g = GraphReader.openFile(new File(IMPORTLIST[i]));
            // nehme alle Sourcenodes
            Node source = g.getNode(SOURCELIST[i]);
            // nehme alle Targetnodes
            Node target = g.getNode(TARGETLIST[i]);
            // Initialisiere Graphen
            dijkstra.init(g);
            // Angabe der Graphdatei
            tempResult.append(IMPORTLIST[i].replace("graph/subwerkzeuge/bspGraphen/", "") + DELIMETER);
            tempResult.append("Dijkstra" + DELIMETER);
            // Messungen der Laufzeit des Algorithmus
            tempResult.append(dijkstra.dijkstraRtm(g, source, target) + DELIMETER);
            // Messung für Distance
            tempResult.append(dijkstra.getDistanceLength() + DELIMETER);
            // Darstellung des kürzesten Weges
            List<Node> shortestRouteBF = dijkstra.getPath(source, target);
            printRoute(shortestRouteBF, tempResult);
        }
        try {
            Files.write(Paths.get(BENCHMARK_RESULT), tempResult.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printRoute(List<Node> route, StringBuilder tempResult) {
        if (route.size() != 0) {
            String printedRoute = "";
            for (Node v : route) printedRoute = printedRoute.concat(v.getId() + "->");
            tempResult.append(printedRoute.substring(0, printedRoute.length() - 2) + "\n");
        } else tempResult.append("Kein Weg gefunden oder negativer Kreis wurde entdeckt" + "\n");
    }
}