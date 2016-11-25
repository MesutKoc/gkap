package algorithmen.searchPath;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

import static java.util.Collections.reverse;
import static java.util.Objects.*;

/**
 * @author Mesut
 *
 */
public class FloydWarshall implements Algorithm {
	private Graph graph;
	private List<Node> nodes;
    private Node source, target;
    //	private int[][] distanceMatrix; // distanceMatrix[v][w] = die Länge der
//									// kürzesten Paths von v->w.
//									// distanceMatrix[v][w] ist INF, wenn es
//									// keine Kante von u nach w gibt.
//	private int[][] transitsMatrix;
    private Map<Node, Map<Node, Node>> predecessorMap;
    private Map<Node, Map<Node, Integer>> costMap;

	/* (non-Javadoc)
	 * @see org.graphstream.algorithm.Algorithm#compute()
	 */
	@Override
	public void compute() {
        for (int j = 0; j < nodes.size(); j++) {
            for (int i = 0; i < nodes.size(); i++) {
                for (int k = 0; k < nodes.size(); k++) {
                    if (i != j && j != k) {
                        Integer dik = costMap.get(nodes.get(i)).get(nodes.get(k));
                        Integer dij = costMap.get(nodes.get(i)).get(nodes.get(j));
                        Integer djk = costMap.get(nodes.get(j)).get(nodes.get(k));
                        if (dij != null && djk != null && Math.min(dik, dij + djk) != dik) {
                            costMap.get(nodes.get(i)).replace(nodes.get(k), dij + djk);
                            predecessorMap.get(nodes.get(i)).replace(nodes.get(k), nodes.get(j));
                        }
                    }
                }
                Integer dii = costMap.get(nodes.get(i)).get(nodes.get(i));
                if (dii != null && dii < 0) throw new IllegalArgumentException("Graph has negative circles");
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

        for (Node e : graph.getEachNode()) nodes.add(e);

        Iterator<Node> nodesForI = graph.getNodeIterator();
        for (int i = 0; i < nodes.size(); i++) {
            Map<Node, Integer> tempCost = new HashMap<>();
            Map<Node, Node> tempPred = new HashMap<>();
            Iterator<Node> nodesForJ = graph.getNodeIterator();
            Node nodeI = nodesForI.next();
            for (int j = 0; j < nodes.size(); j++) {
                Node nodeJ = nodesForJ.next();
                if (nodeI == nodeJ) {
                    tempCost.put(nodes.get(j), 0);
                    tempPred.put(nodes.get(j), null);
                } else {
                    if (nodeI.hasEdgeBetween(nodeJ)) {

                    }
                }
            }
        }
//        for (int i = 0; i < nodes.size(); i++) {
//            Map<Node, Integer> tempCost = new HashMap<>();
//            Map<Node, Node> tempPred = new HashMap<>();
//
//            for (int j = 0; j < nodes.size(); j++) {
//                if (i == j) {
//                    tempCost.put(nodes.get(j), 0);
//                    tempPred.put(nodes.get(j), null);
//                } else {
//                    for(Edge edge : graph.getEachEdge()) {
//                        if(source.hasEdgeBetween(edge.getTargetNode()))
//                            tempCost.put(nodes.get(j), parseInt(edge.getAttribute("weight").toString()));
//                        else
//                            tempCost.put(nodes.get(j), Integer.MAX_VALUE);
//                    }
//                }
//
//            }
//            costMap.put(nodes.get(i), tempCost);
//            predecessorMap.put(nodes.get(i), tempPred);
//        }
    }


    public List<Node> getShortestPath(Node source, Node target) throws Exception {
        this.source = requireNonNull(source);
        this.target = requireNonNull(target);
        compute();
        return getShortestPath(target);
    }

    private List<Node> getShortestPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;

        if (isNull(predecessorMap.get(step))) return null;
        path.add(step);


        while (nonNull(predecessorMap.get(step))) {
            System.out.println(predecessorMap.get(source).get(step));
            step = predecessorMap.get(source).get(step);
            path.add(step);
        }

        reverse(path);
        return path;
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
