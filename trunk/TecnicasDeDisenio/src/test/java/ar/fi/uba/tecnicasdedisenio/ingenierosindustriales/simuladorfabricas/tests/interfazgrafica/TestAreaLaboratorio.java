package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.*;

public class TestAreaLaboratorio {
	private AreaLaboratorio ventana;
	
	@Before
	public void setUp() throws Exception {
		this.ventana = new AreaLaboratorio();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPantalla() {
		this.ventana.run();
		}
}