package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.util;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		parser.LeerDoc("Costos.xml");
		//Assert.assertEquals("Se leyo correctamente el archivo");
	}

}