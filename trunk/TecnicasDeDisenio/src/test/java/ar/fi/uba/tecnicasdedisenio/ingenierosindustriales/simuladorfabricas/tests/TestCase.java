package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Simulador;

public class TestCase {

	private Simulador simulador;
	
	@Before
	public void setUp() throws Exception {
		this.simulador = new Simulador();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTexto() {
		assertEquals("No se encontró el texto esperado", "Un texto", simulador.getTexto());
	}

}
