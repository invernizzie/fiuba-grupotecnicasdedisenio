package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.AreaFabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import org.eclipse.swt.SWTException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAreaFabricaAEmbeber {

    private static final int SUPERFICIE = 1000;
    private static final int PRECIO_ALQUILER = 150;
    private static final float PRECIO_COMPRA = 1000;

    private AreaFabrica ventana;

    @Before
		public void setUp() throws Exception {
			this.ventana = new AreaFabrica();
		}

		@After
		public void tearDown() throws Exception {
		}

		@Test
		public void testPantalla() {
			try {
				this.ventana.run();
				this.ventana.setFabrica(new Fabrica(SUPERFICIE, PRECIO_COMPRA, PRECIO_ALQUILER));
			} catch (SWTException e) {
				
			}
		}
}
