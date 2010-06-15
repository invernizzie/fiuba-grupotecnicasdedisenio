package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.AreaFabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import org.eclipse.swt.SWTException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAreaFabricaAEmbeber {


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
			try{
				this.ventana.run();
				this.ventana.setFabrica(new Fabrica(1000, 1000, 150));
			}catch(SWTException e){
				
			}
		}
}
