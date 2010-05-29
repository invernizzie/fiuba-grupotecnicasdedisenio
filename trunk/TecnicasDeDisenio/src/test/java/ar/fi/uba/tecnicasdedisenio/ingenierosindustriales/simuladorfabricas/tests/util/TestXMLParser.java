package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.util;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParser;

/**
 * 
 * @author Santiago, Diego
 *
 */
public class TestXMLParser {
	
	private XMLParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new XMLParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLeerDoc() {
		try{
			parser.LeerDoc("Costos.xml");
		}catch(Exception e){
			e.printStackTrace();
			fail("Falló la lectura del XML: " + e.getMessage());
		}
	}

}
