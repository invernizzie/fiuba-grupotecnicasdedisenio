package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.util;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParser;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParserProductos;

/**
 * 
 * @author Santiago, Diego
 *
 */
public class TestXMLParser {
	
	private XMLParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new XMLParserProductos("Productos.xml");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLeerDoc() {
		try{
			parser.leerDoc();
		}catch(Exception e){
			e.printStackTrace();
			fail("Falló la lectura del XML: " + e.getMessage());
		}
	}

}
