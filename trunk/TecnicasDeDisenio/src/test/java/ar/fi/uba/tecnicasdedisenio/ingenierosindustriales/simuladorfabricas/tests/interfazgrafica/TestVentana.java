package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.*;

@Deprecated
public class TestVentana {

	@SuppressWarnings("deprecation")
	private Ventana ventanaPPAL;
    private static final int ALTO = 800;
    private static final int ANCHO = 600;

    @SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		this.ventanaPPAL = new Ventana(ALTO, ANCHO, "TP Tecnicas de Diseño");
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testPantalla() {
		ventanaPPAL.dibujar5();
		//ventanaPPAL.dibujarMenu();
		//assertEquals("No se encontró el texto esperado", "Un texto", simulador.getTexto());
	}
	
}

