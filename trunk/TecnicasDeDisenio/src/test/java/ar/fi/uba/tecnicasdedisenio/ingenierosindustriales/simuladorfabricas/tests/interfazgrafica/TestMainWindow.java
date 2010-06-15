package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.old.MainWindow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMainWindow {
	private MainWindow ventana;
	
	@Before
	public void setUp() throws Exception {
		this.ventana = new MainWindow();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPantalla() {
		this.ventana.run();
		}

}
