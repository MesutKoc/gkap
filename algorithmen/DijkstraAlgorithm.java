package algorithmen;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Integer.parseInt;
import static java.util.Objects.*;
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
    private Map<Node, Boolean> ok;
    private Node source, target;
    private int graphAccCounter;

    /* (non-Javadoc)
     * @see org.graphstream.algorithm.Algorithm#compute()
     */
    @Override
    public void compute() {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        ok = new HashMap<>();
        //Der Startwert ist 0 fürr i = 1 und ∞ sonst
        distance.put(source, 0.0);
        // Der Startwert ist v1 für i = 1 und undefiniert sonst.
        unSettledNodes.add(source);
        // Der Startwert für alle Werte von i ist false.
        for (Node n : graph.getEachNode()) ok.put(n, false);

        do {
            // 1.) Suche unter den Knoten v(i) mit OK(i) = false einen Knoten v(h) mit dem kleinsten Wert von Entf(i)
            Node currentNode = getMinimum(unSettledNodes);

            // 2.) Setze OK(h) = true
            ok.replace(currentNode, false, true);
            settledNodes.add(currentNode);
            unSettledNodes.remove(currentNode);

            // 3.) Für alle Knoten v(j) mit OK(j) = false, für die die Kante v(h),v(j)
            // exisitiert die Entfernung und gegebenenfalls den Vorgänger neuberechnen
            findMinimalDistances(currentNode);
        } while (whileWeHaveFalse());
    }

    /* (non-Javadoc)
     * @see org.graphstream.algorithm.Algorithm#init(org.graphstream.graph.Graph)
     */
    @Override
    public void init(@NotNull Graph g) {
        this.graph = requireNonNull(g);
    }

    /**
     * Computes and returns the shortest Path from the source node to the target node
     *
     * @param source a start node
     * @param target a target node
     * @return the shortest path
     */
    public List<Node> getPath(Node source, Node target) throws Exception {
        this.source = requireNonNull(source);
        this.target = requireNonNull(target);
        compute();
        return getPath(target);
    }

    /**
     * Returns the path from the source to the selected target or null if no path exists
     *
     * @param target a selected target
     * @return the path from source to the
     */
    @Nullable
    private List<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;

        if (isNull(predecessors.get(step))) return null;
        path.add(step);

        while (nonNull(predecessors.get(step))) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        adjacentNodes.stream()
                .filter(target -> getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target))
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
            graphAccCounter++;
            if (edge.getSourceNode().equals(node) && isNotSettled(edge.getTargetNode())) {
                neighbors.add(edge.getTargetNode());
            } else if (edge.getTargetNode().equals(node) && isNotSettled(edge.getSourceNode())) {
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
            if (isNull(minimum) || (getShortestDistance(node) < getShortestDistance(minimum)))
                minimum = node;
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
        return !isNull(distance.get(destination)) ? distance.get(destination) : POSITIVE_INFINITY;
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
            graphAccCounter++;
            boolean equalsWithNode = Objects.equals(edge.getSourceNode(), node),
                    equalsWithTarget = Objects.equals(edge.getTargetNode(), target),
                    equalsWithTargetNode = Objects.equals(edge.getTargetNode(), node),
                    equalsWithSourceTarget = Objects.equals(edge.getSourceNode(), target);

            if ((equalsWithNode && equalsWithTarget) || (equalsWithTargetNode && equalsWithSourceTarget))
                return parseInt(edge.getAttribute("weight").toString());
        }
        throw new RuntimeException("Distance not found");
    }

    /**
     * Returns if we have some 'false' values in the OK MATRIX
     *
     * @return true if we have false values
     * else false if we processed all nodes with true
     */
    private boolean whileWeHaveFalse() {
        for (Boolean test : ok.values())
            if (!test) return true;

        return false;
    }

    /**
     * Proofs if the node settled
     *
     * @param node a node
     * @return true if the node settled or false if not
     */
    private boolean isNotSettled(Node node) {
        return !settledNodes.contains(node);
    }

    @Override
    public String toString() {
        return "DijkstraAlgorithm{" +
                "graph=" + graph +
                ", \nsettledNodes=" + settledNodes +
                ", \nunSettledNodes=" + unSettledNodes +
                ", \npredecessors=" + predecessors +
                ", \ndistance=" + distance +
                ", \nok=" + ok +
                ", \nsource=" + source +
                ", \ntarget=" + target +
                ", \ngraphAccCounter=" + graphAccCounter +
                '}';
    }

//    public void showMatrizen() {
//        System.out.print("\t\t");
//        for (Node node1 : graph.getEachNode())
//            System.out.printf("%s\t\t\t", node1.getId());
//
//        System.out.println();
//        System.out.print("entf |\t");
//
//        for (Node node1 : graph.getEachNode())
//            distance.forEach((key, value) -> {
//                if (Objects.equals(node1, key))
//                    System.out.printf("%s\t\t\t", distance.get(node1));
//            });
//
//        System.out.println();
//        System.out.print("vorg |\t");
//
//        for (Node node1 : graph.getEachNode())
//            predecessors.forEach((key, value) -> {
//                if (Objects.equals(node1, key))
//                    System.out.printf("%s\t\t\t", predecessors.get(node1));
//            });
//
//        System.out.println();
//        System.out.print("ok  |\t");
//
//        for (Node t : settledNodes) {
//            System.out.printf("ok\t\t");
//        }
//
//        System.out.println();
//        System.out.println();
//    }

    /**
     * Returns the Distance from the target node
     *
     * @return distance as double if the distance has a value, else INF
     */
    public Double getDistanceLength() {
        return distance.get(target);
    }

    public int getGraphAccCounter() {
        return graphAccCounter;
    }

    /**
     * Proofs if the node settled
     *
     * @param g  a graph
     * @param v1 a Node
     * @param v2 a Node
     * @return the runtime value
     */
    public long dijkstraRtm(Graph g, Node v1, Node v2) throws Exception {
        init(g);
        long resultTime;
        long startTime = System.nanoTime();
        getPath(v1, v2);
        long endTime = System.nanoTime();
        resultTime = endTime - startTime;
        return resultTime;
    }
}