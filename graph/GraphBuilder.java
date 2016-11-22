package graph;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>GraphBuilder.java</h1> Diese Klasse generiert einen Graphen
 *
 * @author Mesut koc
 * @version 1.4
 * @since 2016-10-30
 */
public class GraphBuilder {	
	/**
	 * Konstruktor
	 */
	private GraphBuilder() {}

	/**
	 * Erstellt einen Graphen
	 * 
	 * @param lines
	 *            die zulesenden Daten aus dem Graph
	 * @return ein Graph
	 * @throws FileNotFoundException 
	 */
	public static Graph createGraph(List<String> arr) throws FileNotFoundException {
		final String uml = "[_öÖäÄüÜßa-zA-Z0-9]";
		final String ws = "\\p{Blank}*";
		final String edgePattern = "(" + uml + "+)(" + ws + "(-[->])"
				+ ws + "(" + uml + "+))?(" + ws + "\\((" + uml + "*)\\))?(" + ws
				+ ":" + ws + "(\\d+))?" + ws + ";";
		
		if(arr.isEmpty()) new Exception();
		Graph graph = new MultiGraph("Test");

		// Durchlaufe Zeile für Zeile
		for(String line : arr){
			Matcher lineMatcher = Pattern.compile(edgePattern).matcher(line);

			if (lineMatcher.matches()) {
				Boolean isDirected = false;
				String edgeID = "", edgeWeight = "", direction = "", node1 = ""; 
				String node0 = lineMatcher.group(1); 
				createNode(graph, node0);

				if (lineMatcher.group(3) != null && lineMatcher.group(4) != null) {
					direction = lineMatcher.group(3);
					node1 = lineMatcher.group(4);
					createNode(graph, node1);

					if (lineMatcher.group(6) != null) { 
						edgeID = lineMatcher.group(6);
						if (graph.getEdge(edgeID) != null) 
							edgeID = node0 + "_to_" + node1;
					} else
						edgeID = node0 + "_to_" + node1;
					if (lineMatcher.group(8) != null) edgeWeight = lineMatcher.group(8);
					if (direction.equals("->")) isDirected = true;
				} else { // SINGLE NODE OR LOOP
					if (lineMatcher.group(6) != null) { // edgeID is given so we create a node with loop
						node1 = node0;
					} else {
						break; 
					}
				}
//				graph.addEdge(edgeID, node0, node1, isDirected);
//				if(edgeWeight != null){
//					graph.getEdge(edgeID).addAttribute("weight", Integer.valueOf(edgeWeight));
//				}
				addEdge(graph, edgeID, node0, node1, isDirected, edgeWeight);
			}
		}
		for (Node node : graph) node.addAttribute("ui.label", node.getId());
		return graph;
	}

	private static void addEdge(Graph graph, String edge, String node0,
			String node1, Boolean isDirected, String weight) {
		try {
			if (weight.equals("") || weight == null) // without edgeWeight
				graph.addEdge(edge, node0, node1, isDirected);
			else 
			  graph.addEdge(edge, node0, node1, isDirected).setAttribute("weight", Integer.valueOf(weight));
		} catch (EdgeRejectedException e) {
			System.err.println(e);
		}
	}
	
	private static void createNode(Graph graph, String node0) {
        if (graph.getNode(node0) == null) graph.addNode(node0);
    }

	/**
	 * setGraphSettings()
	 */
    private static void setGraphSettings(Graph graph, boolean showGraph) {
        if (showGraph) {
            graph.setStrict(false); // Überprüft zB doppelte Knotennamen,benutzung
            // von nicht existierenden Elementen usw.
            graph.setAutoCreate(true); // nodes are automatically created when
            // referenced when creating a edge, even if
            // not yet inserted in the graph.
            graph.addAttribute("ui.stylesheet",
                    "url('file:graph/subwerkzeuge/stylesheet')");
            System.setProperty("org.graphstream.ui.renderer",
                    "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
            graph.display();
        }
    }
}