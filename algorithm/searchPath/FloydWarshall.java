package algorithm.searchPath;

import org.graphstream.algorithm.Algorithm;
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
public class FloydWarshall implements Algorithm {
	private Graph graph;
	private List<Node> nodes;
    private Node source, target;
    private Map<Node, Map<Node, Node>> predecessorMap; // die VorgängerMap
    private Map<Node, Map<Node, Double>> costMap; // costMap[v]{[v][w] = die Länge der kürzesten Paths von v->w.

	/* (non-Javadoc)
	 * @see org.graphstream.algorithm.Algorithm#compute()
	 */
	@Override
	public void compute() {
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
                if (dii != null && dii < 0) return; // negative Kantenlänge
            }
        }
    }

	/* (non-Javadoc)
	 * @see org.graphstream.algorithm.Algorithm#init(org.graphstream.graph.Graph)
	 */
    @Override
    public void init(Graph g) {
        this.graph = Objects.requireNonNull(g);
        // Init
        predecessorMap = new HashMap<>();
        costMap = new HashMap<>();
        nodes = new ArrayList<>();
        // save all nodes in the list
        for (Node n : graph.getEachNode()) nodes.add(n);
        // Iterate over Nodes
        Iterator<Node> nodesForI = graph.getNodeIterator();

        for (int i = 0; i < nodes.size(); i++) {
            Map<Node, Double> tempCost = new HashMap<>();
            Map<Node, Node> tempPred = new HashMap<>();

            Iterator<Node> nodesForJ = graph.getNodeIterator();
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

    public List<Node> getShortestPath(Node source, Node target) throws Exception {
        this.source = requireNonNull(source);
        this.target = requireNonNull(target);
        compute();
        return getShortestPath(target);
    }

    private List<Node> getShortestPath(Node target) {
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

    @Override
    public String toString() {
        return "FloydWarshall{" +
                " \ngraph=" + graph +
                " \nnodes=" + nodes +
                " \nsource=" + source +
                " \ntarget=" + target +
                " \npredecessorMap=" + predecessorMap +
                " \ncostMap=" + costMap +
                '}';
    }

    /**
     * Liefert die gesamt Zugriffe auf den Grphen
     *
     * @return int mit dem Zugriffen
     */
    public int getAcc() {
        return nodes.size() * nodes.size();
    }

    /**
     * Liefert die Distance zurück
     *
     * @return Double mit dem Distance
     */
    public Double getDistance() {
        return costMap.get(source).get(target);
    }

    /**
     * Zur Messung bzgl. Laufzeit
     *
     * @param g ein gültiger Graph
     * @param v1 der Startknoten
     * @param v2 der Endknoten
     * @return Double mit dem Sekunden, wie lange der Algorithmus braucht
     */
    public double algorithmRtm(Graph g, Node v1, Node v2) throws Exception {
        init(g);
        long resultTime;
        long startTime = System.nanoTime();
        getShortestPath(v1, v2);
        long endTime = System.nanoTime();
        resultTime = endTime - startTime;
        return (double) resultTime / 1000000000.0;
    }
}