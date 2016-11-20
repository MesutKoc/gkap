package algorithmen;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
/**
 * <h1>Dijkstra.java</h1> Diese Klasse implementiert den Algorithmus
 *
 * @author Mesut koc
 * @version 1.4
 * @since 2016-10-30
 */
public class Dijkstra implements Algorithm {
	private Graph graph;
	private Node source, target;
	private Queue<Node> allNodes = new LinkedList<Node>();
	private Queue<Node> _queueNode = new LinkedList<Node>();
	private LinkedList<Node> uncheckedNodes;
	private int graphAcc = 0;
	private Double distance;

	@Override
	public void compute() {
		initDijkstra(); 
		calcNewDistance(source);
		while (!uncheckedNodes.isEmpty()) {
			Node currentNode = withMinDistance();
			uncheckedNodes.remove(currentNode);
			calcNewDistance(currentNode);
		}
		distance = (Double) target.getAttribute("Distance");
		reset();
	}

	@Override
	public void init(Graph g) {
		try {
			setGraph(g);
			setDestination(graph.getNode(0), graph.getNode(graph.getNodeCount() - 1));
			uncheckedNodes = new LinkedList<Node>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calcNewDistance(Node currNode) {
		Iterator<Edge> leavingEdgeIterator = currNode.getLeavingEdgeIterator();

		while (leavingEdgeIterator.hasNext()) {
			Edge leavingEdge = leavingEdgeIterator.next();
			// Ist dieser Wert für einen Knoten kleiner als die dort gespeicherte Distanz, aktualisiere sie und setze den aktuellen Knoten als Vorgänger.
			Double newDist = ((Double) currNode.getAttribute("Distance")) + ((Double) leavingEdge.getAttribute("weight"));
			Node nodeFromLeavingEdge = getRightNode(currNode, leavingEdge); // TODO ist das notwendig?

			if (!((Boolean) nodeFromLeavingEdge.getAttribute("OK")) &&
					(newDist < (Double) nodeFromLeavingEdge.getAttribute("Distance"))) {
				nodeFromLeavingEdge.setAttribute("Distance", newDist);
				nodeFromLeavingEdge.setAttribute("Predecessor", currNode);
			}
		}
		currNode.setAttribute("ui.class", "");
	}

	private Node getRightNode(Node currNode, Edge leavingEdge) {
		Node node;
		if (leavingEdge.getNode1().equals(currNode))
			node = leavingEdge.getNode0();
		else
			node = leavingEdge.getNode1();
		return node;
	}

	public List<Node> getShortestPath(Node ziel) {
		return null;
	}

	private Node withMinDistance() {
		Node min = uncheckedNodes.getFirst();
		for (Node cur : uncheckedNodes) 
			if (((Double) cur.getAttribute("Distance") < ((Double) min.getAttribute("Distance"))))
				min = cur;

		return min;
	}

	/**
	 * Aktualisiert die Labels im Graph
	 * @param node der Knoten, wo die Information angezeigt werden soll
	 */
	private void updateLabel(Node node) {
		node.setAttribute("ui.label",
						node.getId() + " | Dist: "
						+ (double) node.getAttribute("Distance") + " | OK: "
						+ node.getAttribute("OK") + " | Pred: "
						+ node.getAttribute("Predecessor"));
	}

	/**
	 * Diese Funktion dient dazu, um die Attribute mit Startwerten zu belegen
	 * und die Queue zu befüllen
	 * 
	 * @return veränderte Queue
	 */
	private void initDijkstra() {
		for (Node node : graph) {
			if (!node.equals(source)) {
				node.addAttribute("Distance", Double.POSITIVE_INFINITY);
				node.addAttribute("OK", false);
				node.addAttribute("Predecessor", 0);
				uncheckedNodes.add(node);
				updateLabel(node);
			} else {
				source.addAttribute("Distance", 0.0);
				source.addAttribute("OK", true);
				source.addAttribute("Predecessor", source);
//				updateLabel(node);
			}
		} 
		for (Edge edge : graph.getEachEdge())
			edge.addAttribute("ui.label", edge.getAttribute("weight").toString());
	}

	private void reset() {
		for (Node node : graph.getEachNode()) {
			node.removeAttribute("Distance");
			node.removeAttribute("OK");
			node.removeAttribute("Predecessor");
		}
		source.removeAttribute("title");
		target.removeAttribute("title");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"[%s] [von=%s], [nach=%s], [distance=%f], [zugriffe=%d]",
				this.getClass(), source, target,
				distance,
				graphAcc);
	}

	/**
	 * @param g
	 * @throws Exception falls g == null oder g != gewichtet
	 */
	private void setGraph(Graph g) throws Exception{
		if(g == null) throw new Exception("Ungültiger Graph");
		if(!isWeighted(g)) throw new IllegalArgumentException();
		this.graph = g;
	}

	/**
	 * Prüft, ob der Graph gewichtet ist oder ungewichtet
	 * 
	 * @param g
	 * @return true, wenn der Graph gerichtiet ist ansonsten false
	 */
	private Boolean isWeighted(Graph g) {
		for (Edge edge : g.getEachEdge())
			if (edge.hasAttribute("weight"))
				return true;
		return false;
	}

	/**
	 * @param node
	 * @param node2
	 * @throws Exception
	 */
	public void setDestination(Node node, Node node2) throws Exception {
		if (node == null || node2 == null) throw new Exception("Ungültige Knoten"); // Ungültige Knoten
		for (Edge e : graph.getEachEdge())
			if (e.getNumber("weight") < 0)
				throw new Exception("Negative Kantengewichte nicht erlaubt!"); // Ungültiges Kantengewicht
		this.source = node;
		this.target = node2;
	}
}