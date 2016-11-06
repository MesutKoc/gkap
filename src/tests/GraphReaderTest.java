/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.GraphReader;

/**
 * @author Mesut
 *
 */
public class GraphReaderTest {
	private static GraphReader object1, object2, object3;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		object1 = GraphReader.create();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link io.GraphReader#openFile(java.io.File)}.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Test
	public void testOpenFile() throws ParseException, IOException {
		/*
		 * Prüft, ob die Graphen gleich sind. Der Graph wurde zuvor bei /saved/
		 * abgespeichert nun prüfen wir die Gleichheit
		 */
		Graph g1 = object1.openFile(new File("bspGraphen/graph01.gka"));
		Graph g2 = object2.openFile(new File("bspGraphen/saved/graph01.gka"));
		Graph g3 = object3.openFile(new File("bspGraphen/graph02.gka"));
		assertEquals(g1, g3);
	}

	/**
	 * Test method for {@link io.GraphReader#getStrLines()}.
	 */
	@Test
	public void testGetStrLines() {
		//fail("Not yet implemented"); // TODO
	}

}
