package io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static io.GraphSaver.*;
import static io.GraphReader.*;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;

public class gsTest {

	@Before
	public void SetUp() throws Exception{
		
	}
	
	
	@Test
	public void test() throws IOException, ParseException {
		
		Graph saveGraph =  new SingleGraph("test2");
		
		saveGraph.addNode("A");
		saveGraph.addNode("B");
		saveGraph.addNode("C");
		saveGraph.addNode("D");
		
		saveGraph.addEdge("AB", "A", "B");
		saveGraph.addEdge("BC", "B", "C");
		saveGraph.addEdge("CD", "C", "D");
		
		File test2 = new File("src/test2.gka");
		GraphSaver.saveGraph(saveGraph,test2);
		assertTrue(test2.exists());
				
		GraphReader reader = GraphReader.create();
		GraphReader.openFile(test2);
		
		
	}

}
