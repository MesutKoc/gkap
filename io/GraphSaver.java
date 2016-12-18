package io;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.jetbrains.annotations.Contract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <h1>GraphSaver.java</h1> Diese Klasse speicher den Graphen in einem File
 *
 * @author Mesut koc
 * @version 1.0
 * @since 2016-11-01
 */
public class GraphSaver {
    private GraphSaver() {
    }

	/**
	 * @param graph
	 *            der Graph
	 * @param file
	 *            die Datei, wo der Graph gespeichert wird
	 * @throws IOException
	 *             falls nicht der Graph gelesen werden kann
	 */
    @Contract("null, _ -> fail")
    public static void saveGraph(Graph graph, File file) throws IOException {
        if (graph == null) throw new IOException("Graph konnte nicht gelesen werden");
        if(!file.exists()) file.createNewFile();

		Path path = Paths.get(file.toURI());
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			for(Edge edge : graph.getEachEdge()) {
                writer.write(convertsGraph(edge));
                writer.newLine();
            }
		}
	}

	/**
	 * @param edge
	 *            die Kante die konventiert werden soll
     * @return liefert ein String in der Graph-Syntax
     */
    private static String convertsGraph(Edge edge) {
        String name = "",
                conn = edge.isDirected() ? "->" : "--",
                weight = (edge.getAttribute("capacity") != null) ? (" : " + edge.getAttribute("capacity")) : "";
        return String.format("%s%s%s%s%s;", edge.getNode0(), conn, edge.getNode1(), name, weight);
    }
}
