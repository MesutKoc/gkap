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
	private static final Pattern REGEX = Pattern.compile(
			"((?<source>\\w+)" + "((((?<edge>--|->)(?<target>\\w+)){1}"
					+ "(?<eName>[(]\\w+[)])?"
					+ "(?<eWeight>:\\d+(\\.\\d+)?)?))?)",
					Pattern.UNICODE_CHARACTER_CLASS);
	private static Edge e;
	private static boolean directed;
	private static String eLabel = "";
	private static String target, src, eName, eWeight, edg;

	/**
	 * Konstruktor
	 */
	private GraphBuilder() {
		setGraphSettings();
	}

	/**
	 * @return GraphBuilder
	 */
	public static GraphBuilder create() {
		return new GraphBuilder();
	}

	/**
	 * Erstellt einen Graphen
	 * 
	 * @param lines
	 *            die zulesenden Daten aus dem Graph
	 * @return ein Graph
	 */
	public static MultiGraph createGraph(List<String> lines) {
		if (_graph == null || lines.isEmpty())
			return null;

		for (String line : lines) {
			Matcher m = REGEX.matcher(line);
			while (m.find()) {
				src = m.group("source");
				edg = m.group("edge");
				target = m.group("target");
				eName = m.group("eName");
				eWeight = m.group("eWeight");
			}

			if (edg == null && _graph.getNode(src) == null)
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
		for (Node n : _graph)
			n.addAttribute("ui.label", n.getId());

		return _graph;
	}

	/**
	 * setGraphSettings()
	 */
	private static void setGraphSettings() {
		_graph.setStrict(false);
		_graph.setAutoCreate(true);
		_graph.addAttribute("ui.stylesheet",
				"url('file:src/graph/subwerkzeuge/stylesheet')");
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		_graph.display();
	}

	/**
	 * @return the directed
	 */
	public static boolean isDirected() {
		return directed;
	}

	/**
	 * @param directed the directed to set
	 */
	public static void setDirected(boolean directed) {
		GraphBuilder.directed = directed;
	}

	/**
	 * @return the eLabel
	 */
	public static String geteLabel() {
		return eLabel;
	}

	/**
	 * @param eLabel the eLabel to set
	 */
	public static void seteLabel(String eLabel) {
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
	public static final Pattern getRegex() {
		return REGEX;
	}
}