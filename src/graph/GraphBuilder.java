package graph;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * <h1>GraphBuilder.java</h1> Diese Klasse generiert einen Graphen
 *
 * @author Mesut koc
 * @version 1.4
 * @since 2016-10-30
 */
public class GraphBuilder {
	private static MultiGraph _graph = new MultiGraph("Graph");
	/*
	 * Node1: Suche mit Wiederholung, welches Alparumerisches Zeichen enthält
	 * Kanten: Suche mit Wiederhoung, welche die Zeichen -- oder -> enthalten
	 * Node2: Suche mit Wiederholung, welche Alpanumerische Zeichen enthält,
	 * dabei wiederholt sich das alles nur 1x. Kantennamen: Suche mit
	 * Wiederholung, welche Zeichenklasse "(" und danch ein Alpanumerisches
	 * Zeichen entählt und danac Zeichenklasse ")" enthält. Kantengewichtung:
	 * Suche mit Wiederholung nach einer Ziffer, die danach ein beliebes Zeichen
	 * enthält und weitere Ziffern enthalten kann. Gewicht kann sich
	 * wiederholen. Und das ganze Pattern kann sich wiederholen.
	 */
	private static final Pattern REGEX = Pattern.compile(
			"((?<source>\\w+)" + "((((?<edge>--|->)(?<target>\\w+)){1}"
					+ "(?<eName>[(]\\w+[)])?"
					+ "(?<eWeight>:\\d+(\\.\\d+)?)?))?)",
					Pattern.UNICODE_CHARACTER_CLASS);
	private static Edge e;
	private static boolean directed;
	private static String target, src, eName, eWeight, edg, eLabel = "";

	/**
	 * Konstruktor
	 */
	public GraphBuilder() {
		setGraphSettings();
	}

	/**
	 * Erstellt einen Graphen
	 * 
	 * @param lines
	 *            die zulesenden Daten aus dem Graph
	 * @return ein Graph
	 */
	public MultiGraph createGraph(List<String> lines) {
		if (_graph == null || lines.isEmpty()) return null;
		// Durchlaufe Zeile für Zeile
		for (String line : lines) {
			Matcher m = REGEX.matcher(line);
			// Suche solange Substring gefunden wird
			while (m.find()) {
				src = m.group("source");
				edg = m.group("edge");
				target = m.group("target");
				eName = m.group("eName");
				eWeight = m.group("eWeight");
			}
			// Wenn die Knanten und die Knoten vom Source null sind
			if (edg == null & _graph.getNode(src) == null)
				_graph.addNode(src);
			else {
				setDirected(edg.equals("--") ? false : true);
				seteLabel(eName);
				e = _graph.addEdge(eName, src, target, isDirected());
				e.addAttribute("ui.label", geteLabel());
				if (eWeight != null)
					e.addAttribute("weight", eWeight);
			}
		}
		// Füge Labels hinzu zu den Knoten
		for (Node n : _graph) n.addAttribute("ui.label", n.getId());
		return _graph;
	}

	/**
	 * setGraphSettings()
	 */
	private void setGraphSettings() {
		_graph.setStrict(false); // Überprüft zB doppelte Knotennamen,benutzung
									// von nicht existierenden Elementen usw.
		_graph.setAutoCreate(true); // nodes are automatically created when
									// referenced when creating a edge, even if
									// not yet inserted in the graph.
		_graph.addAttribute("ui.stylesheet",
				"url('file:src/graph/subwerkzeuge/stylesheet')");
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		_graph.display();
	}

	/**
	 * @return the directed
	 */
	public boolean isDirected() {
		return directed;
	}

	/**
	 * @param directed the directed to set
	 */
	public void setDirected(boolean directed) {
		GraphBuilder.directed = directed;
	}

	/**
	 * @return the eLabel
	 */
	public String geteLabel() {
		return eLabel;
	}

	/**
	 * @param eLabel the eLabel to set
	 */
	public void seteLabel(String eLabel) {
		if(eName == null){
			eName = src.concat(edg).concat(target);
			GraphBuilder.eLabel = "";
		} else {
			GraphBuilder.eLabel = eName;
		}
	}

	/**
	 * @return REGEX
	 */
	public final Pattern getRegex() {
		return REGEX;
	}
}