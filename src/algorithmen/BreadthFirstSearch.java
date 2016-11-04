package algorithmen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jdt.annotation.NonNull;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * <h1>BreadthFirstSearch.java</h1> Diese Klasse travesiert den Graphen
 *
 * @author Mesut koc
 * @version 1.4
 * @since 2016-10-30
 */
public class BreadthFirstSearch {
	private static Queue<Node> queue = new LinkedList<Node>();
	private static LinkedList<Node> visited = new LinkedList<Node>();
	private static Integer count = -1;
	private static Node startVertex,
						endVertex;
	private static Graph graph;
	
	private BreadthFirstSearch(){}
	
	/**
	 * Single Ton
	 * @return new Instance of the Class
	 */
	public static BreadthFirstSearch getInstance(){
		return new BreadthFirstSearch();
	}

	/**
	 * @param g
	 *            der Graph
	 * @param startVertex
	 *            wo der Travisier anfangen soll
	 * @param endVertex
	 *            wo der Travisier aufhören soll
	 */
	public void initA(Graph g, Node startVertex, Node endVertex) {
		setGraph(g);
		setStartVertex(startVertex);
		setEndVertex(endVertex);
	}
	
	/**
	 * Startet die Breitensuche, sollte der Graph oder die angegebenen Vertexe
	 * nicht vorhanden sein wird einfach mit return passiert. Die Methode fügt
	 * den Startvertex in eine Queue mit dem Schritt -1 ein, da es sich selbst
	 * noch zählt und dann bei Schritt 0 ankommt. Die Suchenengine muss vorher
	 * gestartet werden, bevor die Methode @see
	 * BreathFirstSearch#resultShortestWay() aufgerufen wird.
	 */
	public static void startSearchEngine() {
		if (graph == null || startVertex == null || endVertex == null) return;
		queue.add(setVisited(startVertex, -1));
		visited.add(setVisited(startVertex, -1));

		while (!queue.isEmpty()) {
			Node tmp = queue.peek();
			queue.addAll(getNeighbours(tmp));
			visited.addAll(getNeighbours(tmp));
			if (visited.contains(endVertex)) {
				count = tmp.getAttribute("steps");
			}
			queue.remove(tmp);
		}
	}

	/**
	 * Die Methode liefert den kürzersten Weg der angegebenen Vertexe. Dabei
	 * geht es rücksuchend und bis ein Knoten gefunden wurde, der den Kürzsesten
	 * Weg repräsentiert.
	 * 
	 * @return allPaths die Liste wo die Wege enthalten sind
	 */
	public List<Node> resultShortestWay() {
		LinkedList<Node> allPaths = new LinkedList<>();
		allPaths.add(endVertex);
		while (!allPaths.getLast().getAttribute("steps").equals(0)) { 
			Node next = getShortestNode(allPaths.getLast());
			allPaths.add(next);
		}
		return allPaths;
	}
	
	/**
	 * @param node
	 * @return
	 */
	@NonNull
	private Node getShortestNode(@NonNull Node node) {
		Iterator<Node> nodeIterator = node.getNeighborNodeIterator();
		while (nodeIterator.hasNext()) {
			Node next = nodeIterator.next();
			if (next.getAttribute("steps").equals((((Integer) node.getAttribute("steps")) - 1))) {
				return next;
			}
		}
		return null;
	}

	/**
	 * @param node
	 * @return
	 */
	@NonNull
	private static List<Node> getNeighbours(@NonNull Node node) {
		List<Node> newTaggedNeighbors = new ArrayList<Node>();
		Iterator<Edge> edgeIterator = node.getLeavingEdgeIterator();
		while (edgeIterator.hasNext()) {
			Edge nextEdge = edgeIterator.next();
			Node nextNode;
			if (node != nextEdge.getNode1())
				nextNode = nextEdge.getNode1();
			else
				nextNode = nextEdge.getNode0();

			newTaggedNeighbors.add(setVisited(nextNode,
					Integer.valueOf(node.getAttribute("steps").toString())));
		}
		return newTaggedNeighbors;
	}

//	private static boolean isVisited() {
//		return (!endVertex.getAttribute("steps").equals(-1));
//	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	@NonNull
	public String toString() {
		return String.format("BreadthFirstSearch [von=%s], [nach=%s], [benötigteKanten=%d]", startVertex, endVertex, count);
	}
	
	/**
	 * @param node
	 * @param steps
	 * @return
	 */
	private static Node setVisited(Node node, Integer steps){
		node.setAttribute("steps", steps + 1);
		node.setAttribute("ui.label", node.getAttribute("ui.label") + "("+(steps + 1)+")");
		return node;
	}
	
	/**
	 * @param startVertex the startVertex to set
	 */
	@SuppressWarnings("static-access")
	public void setStartVertex(Node startVertex) {
		this.startVertex = startVertex;
		this.startVertex.setAttribute("title", "startVertex");
	}

	/**
	 * @param endVertex the endVertex to set
	 */
	@SuppressWarnings("static-access")
	public void setEndVertex(Node endVertex) {
		this.endVertex = endVertex;
		this.endVertex.setAttribute("title", "endVertex");
	}
	
	/**
	 * @param g
	 */
	@SuppressWarnings("static-access")
	public void setGraph(Graph g){
		this.graph = g;
	}
}