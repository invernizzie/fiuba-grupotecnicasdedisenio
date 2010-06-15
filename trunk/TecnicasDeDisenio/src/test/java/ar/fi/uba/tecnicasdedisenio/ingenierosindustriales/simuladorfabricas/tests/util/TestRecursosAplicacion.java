package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.util;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.RecursosAplicacion;

public class TestRecursosAplicacion {

	private RecursosAplicacion instanciaRecursosAplicacion;
	
	@Before
	public void setUp() throws Exception {
		instanciaRecursosAplicacion = RecursosAplicacion.instance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetProperty() {
		Assert.assertEquals("No se recuperó la property esperada", "testValue", instanciaRecursosAplicacion.getProperty("testProperty"));
	}

	@Test
	public void testGetIntProperty() {
		Assert.assertEquals("", 1, instanciaRecursosAplicacion.getIntProperty("testIntProperty"));
	}

}
