package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.interfazgrafica;


import org.eclipse.swt.SWTException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.AreaFabricaAEmbeber;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;

public class TestAreaFabricaAEmbeber {


		private AreaFabricaAEmbeber ventana;
		
		@Before
		public void setUp() throws Exception {
			this.ventana = new AreaFabricaAEmbeber();
		}

		@After
		public void tearDown() throws Exception {
		}

		@Test
		public void testPantalla() {
			try{
				this.ventana.run();
				this.ventana.setJugador(new Jugador("Jugador1", 10));
			}catch(SWTException e){
				
			}
		}
}
