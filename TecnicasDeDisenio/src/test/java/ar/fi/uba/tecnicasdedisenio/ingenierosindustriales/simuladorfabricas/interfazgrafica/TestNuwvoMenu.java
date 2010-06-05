package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.*;

public class TestNuwvoMenu {
	private NuevoMenu ventana;
	
	@Before
	public void setUp() throws Exception {
		this.ventana = new NuevoMenu();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPantalla() {
		this.ventana.run();
		}
}
