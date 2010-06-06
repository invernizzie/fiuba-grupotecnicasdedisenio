package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.*;

public class TestVentana {

	private Ventana ventanaPPAL;
	
	@Before
	public void setUp() throws Exception {
		this.ventanaPPAL = new Ventana(800, 600, "TP Tecnicas de Dise�o");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPantalla() {
		ventanaPPAL.dibujar5();
		//ventanaPPAL.dibujarMenu();
		//assertEquals("No se encontró el texto esperado", "Un texto", simulador.getTexto());
	}
	
}

