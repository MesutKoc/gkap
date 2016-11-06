package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

/**
 * <h1>GraphSaver.java</h1> Diese Klasse speicher den Graphen in einem File
 *
 * @author Mesut koc
 * @version 1.0
 * @since 2016-11-01
 */
public class GraphSaver {
	/**
	 * 
	 */
	public GraphSaver() {}

	/**
	 * @param graph
	 *            der Graph
	 * @param file
	 *            die Datei, wo der Graph gespeichert wird
	 * @throws IOException
	 *             falls nicht der Graph gelesen werden kann
	 */
	public static void saveGraph(Graph graph, File file) throws IOException {
		if(graph == null) throw new IOException("Graph konnte nicht gelesen werden");
		if(!file.exists()) file.createNewFile();
	
		Path path = Paths.get(file.toURI());
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			for(Edge edge : graph.getEachEdge()) {
				writer.write(convertsGraph(edge, false, edge.getAttribute("weight") != null ? true : false));
				writer.newLine();
			}
//			for (String strLines : reader1.getStrLines())
//				writer.write("" + strLines + ";"
//						+ System.getProperty("line.separator"));
		}
	}
	
	public static String convertsGraph(Edge edge, Boolean showName, Boolean showWeight){
		String name = showName ? " (" + edge.getId() + ")" : "",
			   conn = edge.isDirected() ? "-->" : "--",
			   weight = edge.getAttribute("weight") != null ? " : " + edge.getAttribute("weight") : "";
		return edge.getNode0() + conn + edge.getNode1() + name + weight + ";";
	}
}
