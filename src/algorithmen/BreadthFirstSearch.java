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
	private Queue<Node> queue = new LinkedList<Node>();
	private  int steps = -1;
	private Node startVertex, endVertex;
	private Graph graph;
	
	public BreadthFirstSearch(){}

	/**
	 * @param g
	 *            der Graph
	 * @param startVertex
	 *            wo der Travisier anfangen soll
	 * @param endVertex
	 *            wo der Travisier aufhören soll
	 * @throws Exception 
	 */
	public void initA(Graph g) throws Exception {
		setGraph(g);
		setDestination(g.getNode(0), g.getNode(g.getNodeCount() - 1));
	}

	/**
	 * Startet die Breitensuche, sollte der Graph oder die angegebenen Vertexe
	 * nicht vorhanden sein wird einfach mit return passiert. Die Methode fügt
	 * den Startvertex in eine Queue mit dem Schritt -1 ein, da es sich selbst
	 * noch zählt und dann bei Schritt 0 ankommt. Die Suchenengine muss vorher
	 * gestartet werden, bevor die Methode
	 * {@link algorithmen.BreadthFirstSearch#resultShortestWay()} aufgerufen
	 * wird.
	 */
	public void startSearchEngine() {
		queue.add(setVisited(startVertex, -1));
		setAttribute();
		while (!queue.isEmpty()) {
			Node tmp = queue.peek();
			queue.addAll(getNeighbours(tmp));
			if(isTargetTagged()){
				steps = endVertex.getAttribute("steps");
				break;
			}
			queue.remove(tmp);
		}
	}

	
	private Boolean isTargetTagged() {
        return (!endVertex.getAttribute("steps").equals(-1));
    }
	
	private void setAttribute(){
		this.steps = -1;
		for (Node node : graph.getEachNode()) node.setAttribute("steps", -1);
	}
	
	/**
	 * Die Methode liefert den kürzersten Weg der angegebenen Vertexe. Dabei
	 * geht es rücksuchend und bis ein Knoten gefunden wurde, der den Kürzsesten
	 * Weg repräsentiert.
	 * 
	 * @return allPaths die Liste wo die Wege enthalten sind
	 */
	public List<Node> resultShortestWay() {
        if (endVertex.getAttribute("steps").equals(-1))
            throw new IllegalArgumentException("do compute before this method");
        LinkedList<Node> shortestWay = new LinkedList<Node>();
        for (Node node : graph.getEachNode()) {
            node.setAttribute("ui.class", "");
        }
        shortestWay.add(endVertex);
        endVertex.setAttribute("ui.class", "markRed");
        while (!shortestWay.getLast().getAttribute("steps").equals(0)) { // TODO noch eine Abbruchbedingung
            Node next = getShortestNode(shortestWay.getLast()); // TODO Nullable
            next.setAttribute("ui.class", "markRed");
            shortestWay.add(next);
        }
        return shortestWay;
    }
	
	/**
	 * Die Methode iteriert über alle Knoten und prüft, ob der Vorgänger vom
	 * Knoten weniger Schritte braucht als der Nachgängger vom Knoten
	 * 
	 * @param node
	 * @return falls kein kürzerster Weg gefunden wurde, ansonsten der kürzseste
	 *         Knoten
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
	 * Die Methode liefert uns alle Nachbarn von einem Knoten. Dabei iteriert
	 * Sie üer alle Kanten und nimmt sich das nächste und prüft somit, ob der
	 * Knoten ungleich der Knoten von der Kante ist. Falls ja, dann wird unser
	 * Knoten der Knoten von der Kante. Falls nicht, holen wir uns so den
	 * Nachgänger.
	 * 
	 * @param node
	 * @return die Liste, die die Nachbarn enthält
	 */
	@NonNull
	private List<Node> getNeighbours(@NonNull Node node) {
		List<Node> newTaggedNeighbors = new ArrayList<Node>();
		Iterator<Edge> edgeIterator = node.getLeavingEdgeIterator();
		while (edgeIterator.hasNext()) {
			Edge nextEdge = edgeIterator.next();
			Node nextNode;
			if (node != nextEdge.getNode1())
				nextNode = nextEdge.getNode1();
			else
				nextNode = nextEdge.getNode0();
			
			if (nextNode.getAttribute("steps").toString().equals("-1"))
				newTaggedNeighbors.add(setVisited(nextNode,
						Integer.valueOf(node.getAttribute("steps").toString())));
		}
		return newTaggedNeighbors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("BreadthFirstSearch [von=%s], [nach=%s], [benötigteKanten=%d]", startVertex, endVertex, steps);
	}
	
	/**
	 * @param node der Knoten
	 * @param steps de Schritte
	 * @return der veränderte Knoten
	 */
	private Node setVisited(Node node, Integer steps) {
		node.setAttribute("steps", steps + 1);
		node.setAttribute("ui.label", node.getAttribute("ui.label") + "(" + (steps + 1) + ")");
		return node;
	}
	
	public void setDestination(Node node, Node node2) throws Exception {
		if(node == null || node2 == null) throw new Exception("Ungültige Knoten");
		if (this.startVertex != null && this.startVertex.hasAttribute("title"))
            this.startVertex.removeAttribute("title");
        if (this.endVertex != null && this.endVertex.hasAttribute("title"))
            this.endVertex.removeAttribute("title");
        this.startVertex = node;
        this.endVertex = node2;
        startVertex.setAttribute("title", "source");
        endVertex.setAttribute("title", "target");
	}
	
	/**
	 * @param g der Graph
	 * @throws Exception 
	 */
	public void setGraph(Graph g) throws Exception{
		if(g == null) throw new Exception("Ungültiger Graph");
		this.graph = g;
	}
}