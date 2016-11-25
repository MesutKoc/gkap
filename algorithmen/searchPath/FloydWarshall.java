package algorithmen.searchPath;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

import static java.lang.Double.parseDouble;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * @author Mesut
 *
 */
public class FloydWarshall implements Algorithm {
	private Graph graph;
	private List<Node> nodes;
    private Node source, target;
    private Map<Node, Map<Node, Node>> predecessorMap;
    private Map<Node, Map<Node, Double>> costMap; // distanceMatrix[v][w] = die Länge der
    // kürzesten Paths von v->w.
    // distanceMatrix[v][w] ist INF, wenn es
    // keine Kante von u nach w gibt.

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
        predecessorMap = new HashMap<>();
        costMap = new HashMap<>();
        nodes = new ArrayList<>();
        for (Node n : graph.getEachNode()) nodes.add(n);

        Iterator<Node> nodesForI = graph.getNodeIterator();
        for (int i = 0; i < nodes.size(); i++) {
            Map<Node, Double> tempCost = new HashMap<>();
            Map<Node, Node> tempPred = new HashMap<>();
            Iterator<Node> nodesForJ = graph.getNodeIterator();
            Node nodeI = nodesForI.next();

            for (int j = 0; j < nodes.size(); j++) {
                Node nodeJ = nodesForJ.next();
                if (nodeI.equals(nodeJ)) {
                    tempCost.put(nodes.get(j), 0.0);
                    tempPred.put(nodes.get(j), null);
                } else
                    tempCost.put(nodes.get(j), nodeI.hasEdgeBetween(nodeJ) ? parseDouble(nodeI.getEdgeBetween(nodeJ).getAttribute("weight").toString()) : Double.POSITIVE_INFINITY);

                tempPred.put(nodes.get(j), null);
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

        while (!isNull(current)) {
            list.add(current);
            current = predecessorMap.get(source).get(current);
        }

        Collections.reverse(list);
        return list;
    }


    @Override
    public String toString() {
        return "FloydWarshall{" +
                "graph=" + graph +
                ", nodes=" + nodes +
                ", source=" + source +
                ", target=" + target +
                ", predecessorMap=" + predecessorMap +
                ", costMap=" + costMap +
                '}';
    }
}
