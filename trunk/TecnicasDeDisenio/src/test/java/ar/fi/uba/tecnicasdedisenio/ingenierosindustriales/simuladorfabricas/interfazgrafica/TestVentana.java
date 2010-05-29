package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.*;

public class TestVentana {

	private Ventana testsnippet;
	
	@Before
	public void setUp() throws Exception {
		this.testsnippet = new Ventana(800, 600, "TP_Tecnicas");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPantalla() {
		//testsnippet.dibujar();
		testsnippet.dibujarMenu();
		//assertEquals("No se encontró el texto esperado", "Un texto", simulador.getTexto());
	}
	
}

