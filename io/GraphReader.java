package io;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.graphstream.graph.Graph;
import graph.GraphBuilder;

/**
 * <h1>GraphReader.java</h1> Diese Klasse implementiert die Funktion, dass
 * Graphen direkt mit einer Fileendung .gka eingelesen werden können.
 *
 * @author Mesut koc
 * @version 1.5
 * @since 2016-10-30
 */
public class GraphReader {
	private GraphReader() {}

	/**
	 * @param file
	 * 			.gka File
	 * @return builded Graph
	 * @throws ParseException
	 *             wenn beim Parsen ein Fehler auftritt
	 * @throws IOException
	 *             wenn bei I/O ein Fehler auftritt
	 */
	public static Graph openFile(File file) throws ParseException, IOException {
		List<String> result = new ArrayList<>();
		Scanner scanner = new Scanner(file, "utf-8");
	
		while(scanner.hasNextLine()) 
			result.add(scanner.nextLine());
		
		scanner.close();
		return GraphBuilder.createGraph(result);
	}
}