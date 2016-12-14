package algorithm;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * <h1>BreadthFirstSearch.java</h1> Diese Klasse travesiert den Graphen
 *
 * @author Mesut koc
 * @version 1.4
 * @since 2016-10-30
 */
public class BreadthFirstSearch implements Algorithm {
    private Queue<Node> queue = new LinkedList<>();
    private Node startVertex, endVertex;
    private Graph graph;
    private int steps = -1;

    public BreadthFirstSearch() {
    }

    @Override
    public void compute() {
        resetAllAtributes();
        queue.add(setVisited(startVertex, -1));
        while (!queue.isEmpty()) {
            Node tmp = queue.poll();
            queue.addAll(getNeighbours(tmp));
            if (!endVertex.getAttribute("steps").equals(-1)) {
                steps = endVertex.getAttribute("steps");
                break;
            }
        }
    }

    @Override
    public void init(Graph arg0) {
        try {
            setGraph(arg0);
            setDestination(graph.getNode(0), graph.getNode(graph.getNodeCount() - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Methode liefert uns alle Nachbarn von einem Knoten. Dabei iteriert
     * Sie üer alle Kanten und nimmt sich das nächste und prüft somit, ob der
     * Knoten ungleich der Knoten von der Kante ist. Falls ja, dann wird unser
     * Knoten der Knoten von der Kante. Falls nicht, holen wir uns so den
     * Nachgänger.
     *
     * @param node der aktuelle Knoten
     * @return die Liste, die die Nachbarn enthält
     */
    private List<Node> getNeighbours(Node node) {
        List<Node> allNeighbours = new ArrayList<>();
        Iterator<Edge> edgeIterator = node.getLeavingEdgeIterator();
        while (edgeIterator.hasNext()) {
            Edge nextEdge = edgeIterator.next();
            Node nextNode;
            if (node != nextEdge.getNode1()) // Falls es zwei gleiche Nodes existieren
                nextNode = nextEdge.getNode1(); //Liefert den Second Node über die Kanten
            else
                nextNode = nextEdge.getNode0(); //Liefer den first Node über die Kanten

            // Wenn der Node noch nicht besucht wurde
            if (nextNode.getAttribute("steps").toString().equals("-1"))
                allNeighbours.add(setVisited(nextNode,
                        Integer.valueOf(node.getAttribute("steps").toString())));
        }
        return allNeighbours;
    }

    /**
     * Diese Methode liefert eine Liste mit dem kürzesten Weg, dabei arbeitet
     * der Algorithmus rückwärts.
     *
     * @return allPaths die Liste wo die Wege enthalten sind
     */
    public List<Node> getShortestPath() {
        LinkedList<Node> allPaths = new LinkedList<>();
        allPaths.add(endVertex);
        while (!allPaths.getLast().getAttribute("steps").equals(0)) {
            Node next = getShortestNode(allPaths.getLast());
            allPaths.add(next);
        }
        return allPaths;
    }

    /**
     * Die Methode sucht den kürsestzen Weg wieder rückwärts an den Anfang über
     * die Knoten. Dabei vergleicht es die Schritte der Knoten - wenn sie gleich
     * sind wird der aktuelle Knoten auf next gesetzt bzw. zurückgegeben
     *
     * @param node der aktuelle Knoten
     * @return falls kein kürzerster Weg gefunden wurde, ansonsten der kürzseste
     * Knoten
     */
    private Node getShortestNode(Node node) {
        Iterator<Node> nodeIterator = node.getNeighborNodeIterator();
        while (nodeIterator.hasNext()) {
            Node next = nodeIterator.next();
            if (next.getAttribute("steps").equals((((Integer) node.getAttribute("steps")) - 1))) {
                return next;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("[%s] [von=%s], [nach=%s], [benötigteKanten=%d]", this.getClass(), startVertex, endVertex, steps);
    }

    /**
     * Resetet alle Attribute
     */
    private void resetAllAtributes() {
        for (Node node : graph.getEachNode())
            node.setAttribute("steps", -1);
    }

    /**
     * @param node  der Knoten
     * @param steps de Schritte
     * @return der veränderte Knoten
     */
    private Node setVisited(Node node, Integer steps) {
        node.setAttribute("steps", steps + 1);
        node.setAttribute("ui.label", node.getAttribute("ui.label") + "(" + (steps + 1) + ")");
        return node;
    }

    /**
     * @param node  Quelle
     * @param node2 Source
     * @throws Exception falls ungültiger Knoten
     */
    public void setDestination(Node node, Node node2) throws Exception {
        if (node == null || node2 == null) throw new Exception("Ungültige Knoten");
        this.startVertex = node;
        this.endVertex = node2;
        startVertex.setAttribute("title", "source");
        endVertex.setAttribute("title", "target");
    }

    /**
     * @param g der Graph
     * @throws Exception falls ungültiger Graph
     */
    private void setGraph(Graph g) throws Exception {
        if (g == null) throw new Exception("Ungültiger Graph");
        this.graph = g;
    }

    /**
     * Gibt die Schritte zurück
     *
     * @return die Schritte
     */
    public final int getSteps() {
        return steps;
    }
}