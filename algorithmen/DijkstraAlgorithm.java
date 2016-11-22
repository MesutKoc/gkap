package algorithmen;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * <h1>DijkstraAlgorithm.java</h1> Diese Klasse führt den Dijkstra Algorithmus aus
 *
 * @author Mesut koc
 * @version 1.0
 * @since 2016-11-22
 */
public class DijkstraAlgorithm implements Algorithm {
    private Graph graph;
    private Set<Node> settledNodes, unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Double> distance;
    private Node source, target;

    /* (non-Javadoc)
     * @see org.graphstream.algorithm.Algorithm#compute()
     */
    @Override
    public void compute() {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        // insert the source with 0.0 distance
        distance.put(source, 0.0);
        // add source to unsettled
        unSettledNodes.add(source);
        // while we settled all nodes
        while (!unSettledNodes.isEmpty()) {
            Node node = getMinimum(unSettledNodes); // Select the Node with min. Distance
            settledNodes.add(node); // Add to settledNodes
            unSettledNodes.remove(node); // Remove, cuz we settled the Node
            findMinimalDistances(node); // Find for the new Node die new Distance
        }
    }

    /* (non-Javadoc)
     * @see org.graphstream.algorithm.Algorithm#init(org.graphstream.graph.Graph)
     */
    @Override
    public void init(Graph g) {
        this.graph = Objects.requireNonNull(g);
    }

    /**
     * Computes and returns the shortest Path from the source node to the target node
     *
     * @param source a start node
     * @param target a target node
     * @return the shortest path
     */
    public List<Node> getPath(Node source, Node target) {
        this.source = Objects.requireNonNull(source);
        this.target = Objects.requireNonNull(target);
        compute();
        return getPath(target);
    }

    /**
     * Returns the path from the source to the selected target or null if no path exists
     *
     * @param target a selected target
     * @return the path from source to the
     */
    private List<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;

        if (Objects.isNull(predecessors.get(step))) return null;
        path.add(step);

        while (Objects.nonNull(predecessors.get(step))) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

	private void findMinimalDistances(Node node) {
		List<Node> adjacentNodes = getNeighbors(node);
		adjacentNodes.stream()
				.filter(target -> getShortestDistance(
						target) > getShortestDistance(node)
								+ getDistance(node, target))
				.forEach(target -> {
					distance.put(target, getShortestDistance(node) + getDistance(node, target));
					predecessors.put(target, node);
					unSettledNodes.add(target);
				});
	}

    /**
     * Returns a List with the neighbors from the node
     *
     * @param node a node
     * @return a list with the neighbors from the node
     */
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : graph.getEachEdge()) {
            if (edge.getSourceNode().equals(node) && !isSettled(edge.getTargetNode())) {
                neighbors.add(edge.getTargetNode());
            } else if (edge.getTargetNode().equals(node) && !isSettled(edge.getSourceNode())) {
                neighbors.add(edge.getSourceNode());
            }
        }
        return neighbors;
    }

    /**
     * Returns the node with min. Distance
     *
     * @param nodes the set with the nodes
     * @return the node with the min distance
     */
    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node node : nodes) {
            if (Objects.isNull(minimum)) {
                minimum = node;
            } else if (getShortestDistance(node) < getShortestDistance(minimum)) {
                minimum = node;
            }
        }
        return minimum;
    }

    /**
     * Returns the node with the Distance
     *
     * @param destination a node
     * @return INF if the Distance not settled or if true the Distance value
     */
    private double getShortestDistance(Node destination) {
        Double d = distance.get(destination);
        return Objects.isNull(d) ? Double.POSITIVE_INFINITY : d;
    }

    /**
     * Returns the Weight from the reading Graph
     *
     * @param node the startNode
     * @param target the targetNode
     *
     * @return Weight from the Graph if found else 0
     */
    private int getDistance(Node node, Node target) {
        for (Edge edge : graph.getEachEdge()) {
            boolean equalsWithNode = edge.getSourceNode().equals(node),
                    equalsWithTarget = edge.getTargetNode().equals(target),
                    equalsWithTargetNode = edge.getTargetNode().equals(node),
                    equalsWithSourceTarget = edge.getSourceNode().equals(target);

            if (equalsWithNode && equalsWithTarget || equalsWithTargetNode && equalsWithSourceTarget)
                return Integer.parseInt(edge.getAttribute("weight").toString());
        }
        throw new RuntimeException("Distance not found");
    }

    /**
     * Proofs if the node settled
     *
     * @param node a node
     * @return true if the node settled or false if not
     */
    private boolean isSettled(Node node) {
        return settledNodes.contains(node);
    }

    /**
     * Returns the Distance from the target node
     *
     * @return distance as double if the distance has a value, else INF
     */
    public Double getDistanceLength() {
        return distance.get(target);
    }

    /**
     * Proofs if the node settled
     *
     * @param g  a graph
     * @param v1 a Node
     * @param v2 a Node
     * @return the runtime value
     */
    public long dijkstraRtm(Graph g, Node v1, Node v2) {
        init(g);
        long resultTime;
        long startTime = System.nanoTime();
        getPath(v1, v2);
        long endTime = System.nanoTime();
        resultTime = endTime - startTime;
        return resultTime;
    }
}