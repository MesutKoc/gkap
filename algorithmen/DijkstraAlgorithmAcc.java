package algorithmen;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstraAlgorithmAcc implements Algorithm {
    private Graph graph;
    private Set<Node> settledNodes, unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Double> distance;
    private Node source, target;
    public int graphAcc = 0;

    public static <K, V extends Comparable<? super V>> Map<K, V> sortDistanceValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /* (non-Javadoc)
     * @see org.graphstream.algorithm.Algorithm#compute()
     */
    @Override
    public void compute() {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();

        distance.put(source, 0.0);
        unSettledNodes.add(source);

        while (!unSettledNodes.isEmpty()) {
            Node node = getMinimum(unSettledNodes); // Select the Node with min. Distance
            settledNodes.add(node); // Add to settledNodes
            unSettledNodes.remove(node); // Remove, cuz we visited the Node
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
    public long getPath(Node source, Node target) {
        this.source = Objects.requireNonNull(source);
        this.target = Objects.requireNonNull(target);
        compute();
        getPath(target);
        return graphAcc;
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

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : graph.getEachEdge()) {
            graphAcc++;
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

    public Double getDistanceLength() {
        return distance.get(target);
    }

    /**
     * Returns the node with the Distance
     *
     * @param destination the set with the nodes
     * @return INF if the Distance not settled or the Distance value
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
            graphAcc++;
            if (edge.getSourceNode().equals(node) && edge.getTargetNode().equals(target) ||
                    edge.getTargetNode().equals(node) && edge.getSourceNode().equals(target)) {
                return Integer.parseInt(edge.getAttribute("weight").toString());
            }
        }
        throw new RuntimeException("Runetime Error");
    }

    private boolean isSettled(Node node) {
        return settledNodes.contains(node);
    }

    public int getGraphAcc(){
        return graphAcc;
    }
}