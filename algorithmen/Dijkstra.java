package algorithmen;

import java.util.Collections;
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
	private int graphAcc = 0;
	
	@Override
	public void compute() {
		allNodes = config();
		while (!allNodes.isEmpty()) {
			_queueNode.clear();
			_queueNode.addAll(allNodes);
			Node currentNode = allNodes.remove();
			// TODO getNodeWithMinDistance
			//Node currentNode = getNodeWithMinDistance();
			while (!allNodes.isEmpty())
				if (currentNode.getNumber("Distance") > allNodes.element().getNumber("Distance"))
					currentNode = allNodes.remove();
				else
					allNodes.remove();
			allNodes.addAll(_queueNode);
			allNodes.remove(currentNode);
			calcNewDistance(currentNode);
		}
	}
	
	@Override
	public void init(Graph g) {
		try {
			setGraph(g);
			setDestination(graph.getNode(0), graph.getNode(graph.getNodeCount() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Diese Methode liefert eine Liste mit dem kürzesten Weg für den Knoten
	 * 
	 * @param target
	 *            der Knoten zu dem kürzesten Weg gesucht wird
	 * @return path die Liste wo die Wege enthalten sind
	 */
	public List<Node> getShortestPath(Node target) {
		LinkedList<Node> path = new LinkedList<>();
		Node u = target;
		path.add(target);
		
		if (target.getAttribute("Predecessor") == null)
			return path;
		
		while (u.getAttribute("Predecessor") != u) {
			u = u.getAttribute("Predecessor");
			path.add(u);
		}
		
		Collections.reverse(path);
		return path;
	}
	
	/**
	 * Diese Methode durchläuft alle Kanten und guckt, ob der Graph gerichtet ist oder nicht. 
	 * @param currentNode
	 */
	private void calcNewDistance(Node currentNode) {
		for (Edge edge : graph.getEachEdge()) {
			graphAcc++;
			if (edge.getSourceNode() == currentNode && allNodes.contains(edge.getTargetNode()))
				updateDistance(currentNode, edge.getTargetNode(), edge);
		}
	}
	
	/**
	 * Akutliaisiert die Distanzen
	 * @param u 
	 * @param v
	 * @param e die Kante die gerade durchläuft wird
	 */
	private void updateDistance(Node u, Node v, Edge e){
		double alternativ =  u.getNumber("Distance") + e.getNumber("weight"); // Die Weglänge vom Startknoten nach v über u
		if (alternativ < v.getNumber("Distance")) {
			v.addAttribute("Distance", alternativ);
			v.addAttribute("Predecessor", u);
			updateLabel(v);
		}
	}
	
	/**
	 * Aktualisiert die Labels im Graph
	 * @param node der Knoten, wo die Information angezeigt werden soll
	 */
	private void updateLabel(Node node) {
		node.setAttribute("ui.label",
				node.getId() + " | Dist: " + node.getNumber("Distance")
						+ " | OK: " + node.getAttribute("OK") + " | Pred: "
						+ node.getAttribute("Predecessor"));
	}
	
	/**
	 * Diese Funktion dient dazu, um die Attribute mit Startwerten zu belegen
	 * und die Queue zu befüllen
	 * 
	 * @return veränderte Queue
	 */
	private Queue<Node> config() {
		for(Node node : graph) {
			if(node == source){
				node.addAttribute("Distance", 0.0); // endgueltige Länge zum Startknoten
				node.addAttribute("OK", true);	// sich selbst OK
				node.addAttribute("Predecessor", node); // Vorgänger self
			} else {
				node.addAttribute("Distance", Double.POSITIVE_INFINITY); // Entfernung ist unendlich
				node.addAttribute("OK", false); // Knoten noch nicht gesehen
				node.addAttribute("Predecessor", 0); // Vorgaenger noch nicht ermittelt
			}
			allNodes.add(node);
			updateLabel(node);
		}
		
		// Zeige alle Gewichtungen von dem Knoten im Graph an
		for (Edge edge : graph.getEachEdge()) 
			edge.addAttribute("ui.label", edge.getAttribute("weight").toString());
  
		return allNodes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"[%s] [von=%s], [nach=%s], [distance=%f], [zugriffe=%d]",
				this.getClass(), source, target,
				(double) target.getNumber("Distance"),
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