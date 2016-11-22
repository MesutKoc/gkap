package algorithmen;

import java.util.*;

import io.GraphReader;
import org.graphstream.graph.*;

import java.io.*;
import java.nio.file.*;

/**
 * @author Florian Dannenberg, Igor Arkhipov
 */
public class benchmark {
    /*===================================
      CONFIG
     ====================================*/
    private static final String BENCHMARK_RESULT = "benchmark.csv";
    private static final String DELIMETER = ";";
    private static DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
    private static DijkstraAlgorithm dijkstraNew = new DijkstraAlgorithm();
    private static DijkstraAlgorithmAcc dijkstraacc = new DijkstraAlgorithmAcc();
    // Graph Namen zum Importieren
    private static final String IMPORTLIST[] = { "graph/subwerkzeuge/bspGraphen/graph03.gka", "graph/subwerkzeuge/bspGraphen/graph11.gka"};
    // Startpunkte der Suche
    private static final String SOURCELIST[] = { "Paderborn", "v1" };
    // Endpunkte der Suche
    private static final String TARGETLIST[] = { "Walsrode", "v6"  };

    /**
     * Die Methode führt unterschiedliche Messungen in
     * Bezug auf Dijkstra-Ford und Floyd-Warshall Algorithmen aus
     * und zählt die Laufzeit und die Zugriffe auf den unterliegenden Graphen.
     */
    public static void main(String args[]) throws Exception {
        StringBuilder tempResult = new StringBuilder();
        tempResult.append("Graph" + DELIMETER +
                "Algorithmus" + DELIMETER +
                "Laufzeit" + DELIMETER +
                "Anzahl der Zugriffe" + DELIMETER +
                "Distance " + DELIMETER +
                "Kuerzester Weg" + DELIMETER + "\n");

        for (int i = 0; i < IMPORTLIST.length; i++) {
            Graph g = GraphReader.openFile(new File(IMPORTLIST[i]));
            Node source = g.getNode(SOURCELIST[i]);
            Node target = g.getNode(TARGETLIST[i]);
            dijkstraacc.init(g);
            tempResult.append(IMPORTLIST[i].replace("graph/subwerkzeuge/bspGraphen/","") + DELIMETER);
            tempResult.append("Dijkstra" + DELIMETER);
            // Messungen der Laufzeit des Algorithmus
            tempResult.append(dijkstraNew.dijkstraRtm(g,source, target) + DELIMETER);
            // Messungen der Zugriffe auf den unterliegenden Graphen während des jeweiligen Algorithmus
            tempResult.append(dijkstraacc.getPath(source,target) + DELIMETER);
            // Messung für Distance
            tempResult.append(dijkstraacc.getDistanceLength() + DELIMETER);
            // Darstellung des kürzesten Weges
            dijkstra.init(g);
            List<Node> shortestRouteBF = dijkstra.getPath(source, target);
            printRoute(shortestRouteBF, tempResult);
        }

        try {
            Files.write(Paths.get(BENCHMARK_RESULT), tempResult.toString().getBytes());
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static void printRoute(List<Node> route, StringBuilder tempResult) {
        if (route.size() != 0) {
            String printedRoute = "";
            for (Node v : route) printedRoute = printedRoute.concat(v.getId() + "->");
            tempResult.append(printedRoute.substring(0, printedRoute.length() - 2) + "\n");
        }
        else tempResult.append("Kein Weg gefunden oder negativer Kreis wurde entdeckt" + "\n");
    }
}