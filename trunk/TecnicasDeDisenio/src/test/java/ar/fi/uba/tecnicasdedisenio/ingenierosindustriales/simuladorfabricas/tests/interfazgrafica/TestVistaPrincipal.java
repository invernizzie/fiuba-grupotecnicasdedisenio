package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.VistaPrincipal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestVistaPrincipal {
	private VistaPrincipal ventana;
	
	@Before
	public void setUp() throws Exception {
		this.ventana = new VistaPrincipal();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPantalla() {
		this.ventana.run();
		}

}
