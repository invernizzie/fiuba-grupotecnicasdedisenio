package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.fabrica;


import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.DineroInsuficienteException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.FabricaOcupadaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.JugadorConFabricaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Horno;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;



public class TestCompraVenta {
	private Jugador jugador;
	private ArrayList<Fabrica> fabricas;
    private static final int PASO_SUPERFICIE = 100;
    private static final int PASO_COMPRA = 1000;
    private static final int PASO_ALQUILER = 150;
    private static final int DINERO_INICIAL_1 = 3000;
    private static final int DINERO_INICIAL_2 = 5000;
    private static final double VEINTE_PORCIENTO = 0.2;
    private static final int CANTIDAD_DE_FABRICAS = 5;

    @Before
	public void setUp() throws Exception {
		fabricas = new ArrayList<Fabrica>();
		for (int i = 0; i < CANTIDAD_DE_FABRICAS; i++) {
			fabricas.add(new Fabrica((i + 1) * PASO_SUPERFICIE, (i + 1) * PASO_COMPRA, (i + 1) * PASO_ALQUILER));
		}
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSolamenteLanzaExcepcionDineroInsuficienteXQueJugadorNoPudoComprarLaFabricaXDineroInsuficiente() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", PASO_COMPRA);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
			/*Test.*/
			Assert.fail("Deberia haber lanzado una excepcion de DineroInsuficiente.");
		} catch (DineroInsuficienteException e) {
        } catch (FabricaOcupadaException e) {
			/*Test.*/
        	Assert.fail("No deberia haber lanzado una excepcion.");
		} catch (JugadorConFabricaException e) {
			/*Test.*/
			Assert.fail("No deberia haber lanzado una excepcion.");
		}
	}
	
	@Test
	public void testElJugadorNoTieneFabricaAsignadaXQueJugadorNoPudoComprarLaFabricaXDineroInsuficiente() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", PASO_COMPRA);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
        } catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNull("El jugador no deberÃ­a tener una fabrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testLaFabricaNoTieneJugadorAsignadoXQueJugadorNoPudoComprarLaFabricaXDineroInsuficiente(){
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", PASO_COMPRA);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
        } catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNull("La fabrica no deberÃ­a tener un jugador que la comprÃ³.", this.fabricas.get(1).getJugador());
	}
	

	@Test
	public void testNoHayExcepcionesYaQueJugadorSinFabricaAsignadaYConDineroSuficienteCompraUnaFabricaLibre() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
			/*Test.*/
			Assert.fail("No deberia haber lanzado una excepcion.");
		} catch (FabricaOcupadaException e) {
			/*Test.*/
			Assert.fail("No deberia haber lanzado una excepcion.");
		} catch (JugadorConFabricaException e) {
			/*Test.*/
			Assert.fail("No deberia haber lanzado una excepcion.");
		}
	}
	
	@Test
	public void testElJugadorTieneUnaFabricaAsignadaYaQueEseJugadorSinFabricaAsignadaYConDineroSuficienteCompraUnaFabricaLibre() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("El jugador deberÃ­a tener una fabrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testLaFabricaTieneUnJugadorAsignadoYaQueJugadorSinFabricaAsignadaYConDineroSuficienteCompraEsaFabricaLibre() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("La fabrica deberÃ­a tener un jugador que la comprÃ³.", this.fabricas.get(1).getJugador());
	}
	
	
	@Test
	public void testElJugadorTieneComoDineroActualLoQueTeniaAntesMenosElCostoDeLaFabricaQueCompro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("El jugador deberia tener la plata anterior menos el costo de la fabrica.", jugador.getDineroActual(), plata - this.fabricas.get(1).getCostoCompra());
	}
	
	@Test
	public void testElJugadorTieneAsignadaLaFabricaQueCompro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("El jugador deberia tener la fabrica que comprÃ³.", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	
	@Test
	public void testLaFabricaTieneAsignadaAlJugadorQueLaCompro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("La fabrica deberÃ­a tener asignada al jugador que la comprÃ³.", this.fabricas.get(1).getJugador(), this.jugador);
	}
	
	
	@Test
	public void testNoHayExcepcionesYaQueJugadorSinFabricaAsignadaAlquilaUnaFabricaLibre() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
			/*Test.*/
			Assert.fail("No deberia haber lanzado una excepcion.");
		} catch (JugadorConFabricaException e) {
			/*Test.*/
			Assert.fail("No deberia haber lanzado una excepcion.");
		}
	}
	
	@Test
	public void testElJugadorTieneUnaFabricaAsignadaYaQueEseJugadorSinFabricaAsignadaAlquilaUnaFabricaLibre() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("El jugador deberÃ­a tener una fabrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testLaFabricaTieneUnJugadorAsignadoYaQueJugadorSinFabricaAsignadaYAlquilaEsaFabricaLibre() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("La fabrica deberÃ­a tener un jugador que la alquiló.", this.fabricas.get(4).getJugador());
	}
	
	
	@Test
	public void testElJugadorNoSufreCambiosEnSuDineroActualYaQueAlquilarTieneCostoCero() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("El jugador deberia tener la plata anterior sin ninguna reducciÃ³n.", jugador.getDineroActual(), plata);
	}
	
	@Test
	public void testElJugadorTieneAsignadaLaFabricaQueAlquilo() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("El jugador deberia tener la fabrica que comprÃ³.", this.jugador.getFabrica(), this.fabricas.get(4));
	}
	
	@Test
	public void testLaFabricaTieneAsignadaAlJugadorQueLaAlquilo() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("La fabrica deberÃ­a tener asignada al jugador que la comprÃ³.", this.fabricas.get(4).getJugador(), this.jugador);
	}
	
	@Test
	public void testSeLanzaExcepcionDeFabricaOcupadaCuandoUnJugadorIntentaComprarUnaFabricaCompradaPorOtro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
			/*Test.*/
			Assert.fail("Deberia haber lanzado una excepcion de FabricaOcupada.");	
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {	
		} catch (JugadorConFabricaException e) {
		}
	}
	
	@Test
	public void testLaPlataDeUnJugadorNoSeModificaCuandoEseJugadorIntentaComprarUnaFabricaCompradaPorOtro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		float plata2 = jug2.getDineroActual();
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("La plata del jugador 2 no deberÃ­a haberse modificado.", jug2.getDineroActual(), plata2);
	}
	
	
	@Test
	public void testUnJugadorNoTieneAsignadaUnaFabricaCuandoEseJugadorIntentaComprarUnaFabricaCompradaPorOtro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNull("El jugador 2 no deberÃ­a tener una fabrica asignada.", jug2.getFabrica());
	}
	
	
	@Test
	public void testUnJugadorSigueTeniendoAsignadaLaFabricaQueComproAntesCuandoOtroJugadorIntentaComprarEsaFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("La fÃ¡brica 1 deberÃ­a tener asignado al jugador 1.", this.fabricas.get(1).getJugador(), this.jugador);
	}
	
	@Test
	public void testLanzaExcepcionCuandoUnJugadorConUnaFabricaAsignadaIntentaComprarOtra() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fÃ¡brica.*/
		try {
			this.fabricas.get(0).comprar(jugador);
			/*Test.*/
			Assert.fail("Deberia haber lanzado una excepcion de JugadorConFabrica.");
		} catch (JugadorConFabricaException e) {
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		}
	}
	
	@Test
	public void testLaPlataDeUnJugadorNoCambiaCuandoEseJugadorConUnaFabricaAsignadaIntentaComprarOtra(){
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		float plata = this.jugador.getDineroActual();
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fÃ¡brica.*/
		plata = jugador.getDineroActual();
		try {
			this.fabricas.get(0).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertEquals("La plata del jugador 1 no deberÃ­a haberse modificado.", this.jugador.getDineroActual(), plata);
	}
	@Test
	public void testUnaFabricaNoTieneUnJugadorAsignadoCuandoUnJugadorConUnaFabricaAsignadaIntentaComprarEsaFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fÃ¡brica.*/
		try {
			this.fabricas.get(0).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertNull("La fÃ¡brica 0 no deberia tener un jugador asignado.", this.fabricas.get(0).getJugador());
		
	}
	
	@Test
	public void testUnJugadorSigueTeniendoLaFabricaQueComproAntesCuandoEseJugadorIntentaComprarOtraFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fÃ¡brica.*/
		try {
			this.fabricas.get(0).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertEquals("El jugador 1 deberÃ­a tener asignada la fÃ¡brica 1.", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	@Test
	public void testSeLanzaExcepcionDeFabricaOcupadaCuandoUnJugadorIntentaAlquilarUnaFabricaAlquiladaPorOtro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).alquilar(jug2);
			/*Test.*/
			Assert.fail("Deberia haber lanzado una excepcion de FabricaOcupada.");	
		} catch (FabricaOcupadaException e) {	
		} catch (JugadorConFabricaException e) {
		}
	}
	
	@Test
	public void testUnJugadorNoTieneAsignadaUnaFabricaCuandoEseJugadorIntentaAlquilarUnaFabricaAlquiadaPorOtro() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).alquilar(jug2);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNull("El jugador 2 no deberÃ­a tener una fabrica asignada.", jug2.getFabrica());
	}
	
	
	@Test
	public void testUnJugadorSigueTeniendoAsignadaLaFabricaQueAlquiloAntesCuandoOtroJugadorIntentaAlquilarEsaFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).alquilar(jug2);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("La fÃ¡brica 1 deberÃ­a tener asignado al jugador 1.", this.fabricas.get(1).getJugador(), this.jugador);
	}
	
	@Test
	public void testLanzaExcepcionCuandoUnJugadorConUnaFabricaAsignadaIntentaAlquilarOtra() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fÃ¡brica.*/
		try {
			this.fabricas.get(0).alquilar(jugador);
			/*Test.*/
			Assert.fail("Deberia haber lanzado una excepcion de JugadorConFabrica.");
		} catch (JugadorConFabricaException e) {
		} catch (FabricaOcupadaException e) {
		}
	}
	
	@Test
	public void testUnaFabricaNoTieneUnJugadorAsignadoCuandoUnJugadorConUnaFabricaAsignadaIntentaAlquilarEsaFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fÃ¡brica.*/
		try {
			this.fabricas.get(0).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertNull("La fÃ¡brica 0 no deberia tener un jugador asignado.", this.fabricas.get(0).getJugador());
		
	}
	@Test
	public void testUnJugadorSigueTeniendoLaFabricaQueAlquiloAntesCuandoEseJugadorIntentaAlquilarOtraFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fÃ¡brica.*/
		try {
			this.fabricas.get(0).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertEquals("El jugador 1 deberÃ­a tener asignada la fÃ¡brica 1.", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	@Test
	public void testUnaFabricaNoTieneUnJugadorAsignadoCuandoUnJugadorLaCompraYLaVende() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("La fÃ¡brica no deberÃ­a tener un jugador asignado.", this.fabricas.get(1).getJugador());
	}
	
	@Test
	public void testUnJugadorNoTieneUnaFabricaAsignadaCuandoEseJugadorCompraUnaFabricaYLaVende(){
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("El jugador no deberÃ­a tener una fÃ¡brica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testElJugadorTieneUnVeintePorCientoMenosDePlataDeLaQueTeniaAntesDeComprarYVenderUnaFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador deberÃ­a ser el del principio menos el 20% del costo de la fÃ¡brica.",
                this.jugador.getDineroActual(), (float) (plata - this.fabricas.get(1).getCostoCompra() * VEINTE_PORCIENTO));
	}
	
	@Test
	public void testUnaFabricaNoTieneUnJugadorAsignadoCuandoUnJugadorLaAlquilaYLaVende() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("La fÃ¡brica no deberÃ­a tener un jugador asignado.", this.fabricas.get(1).getJugador());
	}
	
	@Test
	public void testUnJugadorNoTieneUnaFabricaAsignadaCuandoEseJugadorAlquilaUnaFabricaYLaVende(){
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("El jugador no deberÃ­a tener una fÃ¡brica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testElJugadorTieneElMismoDineroQueAntesDeAlquilarYVenderUnaFabrica() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador deberÃ­a ser el del principio.", this.jugador.getDineroActual(), plata);
	}
	
	@Test
	public void testElJugadorRecuperaUnPorcentajeDeLaFabricaYDeLaMaquinaQueTieneLaFabricaLuegoDeComprarYVenderEsaFabricaQueTieneUnaMaquina() {
		/*Inicialización.*/
		Maquina maquina1 = new Horno(0F, 0F);
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		float plata = this.jugador.getDineroActual();
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.jugador.getFabrica().comprarMaquina(maquina1);

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador deberÃ­a ser el del "
							+ "principio menos el 20% del costo de la fÃ¡brica," +
									"menos el costo compra de la máquina," +
									"más el costo de venta de la máquina.",
								this.jugador.getDineroActual(),
								(float) (plata - this.fabricas.get(1).getCostoCompra() * VEINTE_PORCIENTO
										+ maquina1.obtenerCostoVenta() - maquina1.getCostoMaquina()));	
	}
	
	@Test
	public void testElJugadorRecuperaUnPorcentajeDeLaMaquinaQueTieneLaFabricaLuegoDeAlquilarYVenderEsaFabricaQueTieneUnaMaquina() {
		/*Inicialización.*/
		Maquina maquina1 = new Horno(0F, 0F);
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		float plata = this.jugador.getDineroActual();
		
		/*Asignación.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.jugador.getFabrica().comprarMaquina(maquina1);

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador deberÃ­a ser el del "
							+"menos el costo compra de la máquina," +
							"más el costo de venta de la máquina.",
								this.jugador.getDineroActual(),
								(float) (plata + maquina1.obtenerCostoVenta() - maquina1.getCostoMaquina()));	
	}
	
	@Test
	public void testNoCambiaLaPlataDeUnJugadorSiSeLlamaAVenderUnaFabricaYNoTieneFabricaAsiganda() {
		/*Inicialización.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		
		/*Asignación.*/
		this.jugador.venderFabrica(PASO_COMPRA);
		
		/*Test.*/
		Assert.assertEquals("El jugador no deberÃ­a haber ganado plata.", this.jugador.getDineroActual(), plata);
	}
}
