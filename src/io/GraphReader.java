package io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
	private List<String> strLines;
	private GraphBuilder builder = new GraphBuilder();
	/**
	 * Constructor
	 */
	public GraphReader() {}

	/**
	 * @param file
	 * 			.gka File
	 * @return builded Graph
	 * @throws ParseException
	 *             wenn beim Parsen ein Fehler auftritt
	 * @throws IOException
	 *             wenn bei I/O ein Fehler auftritt
	 */
	public Graph openFile(File file) throws ParseException, IOException {
		if (!file.exists() && !file.isDirectory()
				&& !file.toString().endsWith(".gka"))
			throw new IOException("Datei wurde nicht gefunden!");

		strLines = Files
				.lines(Paths.get(file.toURI()), Charset.forName("ISO_8859_1"))
				.map(string -> string.replaceAll(" ", ""))
				.flatMap(line -> Stream.of(line.split(";")))
				.collect(Collectors.toList());
		System.out.println("hier");
		for (String data : strLines)
			if (!builder.getRegex().matcher(data).matches())
				throw new ParseException("Ungültiger Graph", 0);
		System.out.println("hier2");
		return builder.createGraph(strLines);
	}
	
	public final List<String> getStrLines() {
		return strLines;
	}

}