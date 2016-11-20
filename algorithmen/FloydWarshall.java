package algorithmen;

import java.util.Iterator;
import java.util.List;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
/**
 * @author Mesut
 *
 */
public class FloydWarshall implements Algorithm {
	private Graph graph;
	private List<Node> nodes;
	private int[][] distanceMatrix; // distanceMatrix[v][w] = die Länge der
									// kürzesten Paths von v->w.
									// distanceMatrix[v][w] ist INF, wenn es
									// keine Kante von u nach w gibt.

	/* (non-Javadoc)
	 * @see org.graphstream.algorithm.Algorithm#compute()
	 */
	@Override
	public void compute() {
		Iterator<Node> nodeIterator = graph.getNodeIterator();
		
		while(nodeIterator.hasNext()) {
			Node current = nodeIterator.next();
			if(hasEdge(current)){
				// TODO wenn eine Kante existiert
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.graphstream.algorithm.Algorithm#init(org.graphstream.graph.Graph)
	 */
	@Override
	public void init(Graph g) {
		if(g == null || !isWeighted(g)) new Exception();
		this.graph = g;
		
		// Füge alle Knoten in die Liste ein
		for(Node n : graph.getEachNode()) nodes.add(n);
		
		// Erstelle DistanceMatrix mit der Anzahl der Knoten vom Graphen
		int nodeCount = graph.getNodeCount();
		distanceMatrix = new int[nodeCount][nodeCount];
		
		// Initialisiere die DistanceMatrix
		Iterator<Node> nodeIteratorForI = graph.getNodeIterator();
		Iterator<Node> nodeIteratorForJ = graph.getNodeIterator();
		for (int i = 0; i <= nodeCount; i++) {
			Node nodeI = nodeIteratorForI.next();
			for (int j = 0; j <= nodeCount; j++) {
				Node nodeJ = nodeIteratorForJ.next();
				if (nodeI == nodeJ)
					distanceMatrix[i][j] = 0;
				else
					distanceMatrix[i][j] = ((!nodeI.hasEdgeBetween(nodeJ) ? Integer.MAX_VALUE : nodeI.getEdgeBetween(nodeJ).getAttribute("weight")));
			}
		}
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
	 * Prüft, ob der angegebene Knoten eine Kante hat.
	 * 
	 * @param current
	 *            der Knoten zu überprüfung
	 * @return true, falls eine Kante existiert - ansonsten false
	 */
	private boolean hasEdge(Node current) {
		Iterator<Edge> edgeIterator = current.getEachEnteringEdge().iterator();
		if (edgeIterator.hasNext())
			return true;

		return false;
	}
}
