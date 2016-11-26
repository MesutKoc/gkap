package algorithm.searchPath;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

import static java.lang.Double.parseDouble;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * <h1>FloydWarshall.java</h1> Diese Klasse führt den Floyd Algorithmus aus
 *
 * @author Mesut koc
 * @version 1.0
 * @since 2016-11-25
 */
public class FloydWarshall {
    /**
     * Wird für die Distance gebraucht
     */
    public static double distance;

    /**
     * Diese Methode ist öffentlich für die kürzeste Path suche.
     *
     * @param g      ein Graph
     * @param source der Startknoten
     * @param target der Zielknoten
     * @return eine Liste mit den kürzesten Wegen
     * @throws Exception wenn Graph || source || target ungültig sind!
     */
    public static List<Node> getShortestPath(Graph g, Node source, Node target) throws Exception {
        return compute(requireNonNull(g), requireNonNull(source), requireNonNull(target));
    }

    /**
     * Diese Methode führt den eigentlichen Algorithmus aus
     *
     * @param g      ein Graph
     * @param source der Startknoten
     * @param target der Zielknoten
     * @return eine Liste mit den kürzesten Wegen
     */
    private static List<Node> compute(Graph g, Node source, Node target) {
        List<Node> nodes = new ArrayList<>();   // beinhaltet alle Knoten
        Map<Node, Map<Node, Node>> predecessorMap = new HashMap<>(); // die VorgängerMatrix
        Map<Node, Map<Node, Double>> costMap = new HashMap<>(); // Die DistanceMatrix

        // Nehme alle Knoten und Init die Vorgänger und DistanceMatrix!
        init(g, predecessorMap, costMap, nodes);

        // Eigentliche Algorithmus
        for (int j = 0; j < nodes.size(); j++) { // Zeilen
            for (int i = 0; i < nodes.size(); i++) { // Spalten
                for (int k = 0; k < nodes.size(); k++) { // Schritte
                    if (i != j && j != k) {
                        Double dik = costMap.get(nodes.get(i)).get(nodes.get(k));
                        Double dij = costMap.get(nodes.get(i)).get(nodes.get(j));
                        Double djk = costMap.get(nodes.get(j)).get(nodes.get(k));
                        if (dij != null && djk != null && Math.min(dik, dij + djk) != dik) {
                            costMap.get(nodes.get(i)).replace(nodes.get(k), dij + djk);
                            predecessorMap.get(nodes.get(i)).replace(nodes.get(k), nodes.get(j));
                        }
                    }
                }
                Double dii = costMap.get(nodes.get(i)).get(nodes.get(i));
                if (dii != null && dii < 0) return null; // negative Kantenlänge
            }
        }
        distance = costMap.get(source).get(target);
        return getShortestPath(predecessorMap, source, target);
    }

    /**
     * Speichert alle Knoten in eine Liste ab, iteriert über Sie und guckt dementsprechend
     * ob I == J ist oder falls nicht, ob eine Kante existiert.
     *
     * @param g              ein Graph
     * @param predecessorMap die VorgängerMatrix
     * @param costMap        die DistanceMatrix
     * @param nodes          die Liste mit den Knoten
     */
    private static void init(Graph g, Map<Node, Map<Node, Node>> predecessorMap,
                             Map<Node, Map<Node, Double>> costMap, List<Node> nodes) {
        // save all nodes in the list
        for (Node n : g.getEachNode()) nodes.add(n);
        // Iterate over Nodes
        Iterator<Node> nodesForI = g.getNodeIterator();

        for (int i = 0; i < nodes.size(); i++) {
            Map<Node, Double> tempCost = new HashMap<>();
            Map<Node, Node> tempPred = new HashMap<>();

            Iterator<Node> nodesForJ = g.getNodeIterator();
            Node nodeI = nodesForI.next();

            for (Node node : nodes) {
                Node nodeJ = nodesForJ.next();
                if (nodeI.equals(nodeJ))
                    tempCost.put(node, 0.0);
                else
                    tempCost.put(node, nodeI.hasEdgeBetween(nodeJ) ?
                            parseDouble(nodeI.getEdgeBetween(nodeJ).getAttribute("weight").toString()) :
                            Double.POSITIVE_INFINITY);

                tempPred.put(node, null);
            }
            costMap.put(nodes.get(i), tempCost);
            predecessorMap.put(nodes.get(i), tempPred);
        }
    }

    /**
     * @param predecessorMap die VorgängerMatrix
     * @param source         der Startknoten
     * @param target         der Zielknoten
     * @return kürzester Weg
     */
    private static List<Node> getShortestPath(Map<Node, Map<Node, Node>> predecessorMap, Node source, Node target) {
        LinkedList<Node> list = new LinkedList<>();
        Node current = predecessorMap.get(source).get(target);
        list.add(target);

        while (nonNull(current)) {
            list.add(current);
            current = predecessorMap.get(source).get(current);
        }

        list.add(source);
        Collections.reverse(list);
        return list;
    }

    /**
     * Zur Messung bzgl. Laufzeit
     *
     * @param g ein gültiger Graph
     * @param v1 der Startknoten
     * @param v2 der Endknoten
     * @return Double mit dem Sekunden, wie lange der Algorithmus braucht
     */
    public static double algorithmRtm(Graph g, Node v1, Node v2) throws Exception {
        long resultTime;
        long startTime = System.nanoTime();
        getShortestPath(g, v1, v2);
        long endTime = System.nanoTime();
        resultTime = endTime - startTime;
        return (double) resultTime / 1000000000.0;
    }
}