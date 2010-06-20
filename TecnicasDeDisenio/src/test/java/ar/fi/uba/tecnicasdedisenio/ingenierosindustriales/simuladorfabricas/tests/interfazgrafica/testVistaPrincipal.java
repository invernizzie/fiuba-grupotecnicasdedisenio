package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.*;

public class testVistaPrincipal {
    private VistaPrincipal vista;

    @Before
	public void setUp() throws Exception {
        this.vista = new VistaPrincipal();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAbrirVentanaPrincipal() {
		this.vista.run();
		Assert.assertNotNull("Abre la Ventana Principal", vista.getShellPrincipal());
	}
}
