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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", PASO_COMPRA);
		
		/*Asignaci�n.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", PASO_COMPRA);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
        } catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNull("El jugador no debería tener una fabrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testLaFabricaNoTieneJugadorAsignadoXQueJugadorNoPudoComprarLaFabricaXDineroInsuficiente(){
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", PASO_COMPRA);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
        } catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNull("La fabrica no debería tener un jugador que la compró.", this.fabricas.get(1).getJugador());
	}
	

	@Test
	public void testNoHayExcepcionesYaQueJugadorSinFabricaAsignadaYConDineroSuficienteCompraUnaFabricaLibre() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("El jugador debería tener una fabrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testLaFabricaTieneUnJugadorAsignadoYaQueJugadorSinFabricaAsignadaYConDineroSuficienteCompraEsaFabricaLibre() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("La fabrica debería tener un jugador que la compró.", this.fabricas.get(1).getJugador());
	}
	
	
	@Test
	public void testElJugadorTieneComoDineroActualLoQueTeniaAntesMenosElCostoDeLaFabricaQueCompro() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("El jugador deberia tener la fabrica que compró.", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	
	@Test
	public void testLaFabricaTieneAsignadaAlJugadorQueLaCompro() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("La fabrica debería tener asignada al jugador que la compró.", this.fabricas.get(1).getJugador(), this.jugador);
	}
	
	
	@Test
	public void testNoHayExcepcionesYaQueJugadorSinFabricaAsignadaAlquilaUnaFabricaLibre() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("El jugador debería tener una fabrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testLaFabricaTieneUnJugadorAsignadoYaQueJugadorSinFabricaAsignadaYAlquilaEsaFabricaLibre() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertNotNull("La fabrica debería tener un jugador que la alquil�.", this.fabricas.get(4).getJugador());
	}
	
	
	@Test
	public void testElJugadorNoSufreCambiosEnSuDineroActualYaQueAlquilarTieneCostoCero() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("El jugador deberia tener la plata anterior sin ninguna reducción.", jugador.getDineroActual(), plata);
	}
	
	@Test
	public void testElJugadorTieneAsignadaLaFabricaQueAlquilo() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("El jugador deberia tener la fabrica que compró.", this.jugador.getFabrica(), this.fabricas.get(4));
	}
	
	@Test
	public void testLaFabricaTieneAsignadaAlJugadorQueLaAlquilo() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*Test.*/
		Assert.assertEquals("La fabrica debería tener asignada al jugador que la compró.", this.fabricas.get(4).getJugador(), this.jugador);
	}
	
	@Test
	public void testSeLanzaExcepcionDeFabricaOcupadaCuandoUnJugadorIntentaComprarUnaFabricaCompradaPorOtro() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		float plata2 = jug2.getDineroActual();
		
		/*Asignaci�n.*/
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
		Assert.assertEquals("La plata del jugador 2 no debería haberse modificado.", jug2.getDineroActual(), plata2);
	}
	
	
	@Test
	public void testUnJugadorNoTieneAsignadaUnaFabricaCuandoEseJugadorIntentaComprarUnaFabricaCompradaPorOtro() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
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
		Assert.assertNull("El jugador 2 no debería tener una fabrica asignada.", jug2.getFabrica());
	}
	
	
	@Test
	public void testUnJugadorSigueTeniendoAsignadaLaFabricaQueComproAntesCuandoOtroJugadorIntentaComprarEsaFabrica() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
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
		Assert.assertEquals("La fábrica 1 debería tener asignado al jugador 1.", this.fabricas.get(1).getJugador(), this.jugador);
	}
	
	@Test
	public void testLanzaExcepcionCuandoUnJugadorConUnaFabricaAsignadaIntentaComprarOtra() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fábrica.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		float plata = this.jugador.getDineroActual();
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fábrica.*/
		plata = jugador.getDineroActual();
		try {
			this.fabricas.get(0).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertEquals("La plata del jugador 1 no debería haberse modificado.", this.jugador.getDineroActual(), plata);
	}
	@Test
	public void testUnaFabricaNoTieneUnJugadorAsignadoCuandoUnJugadorConUnaFabricaAsignadaIntentaComprarEsaFabrica() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fábrica.*/
		try {
			this.fabricas.get(0).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertNull("La fábrica 0 no deberia tener un jugador asignado.", this.fabricas.get(0).getJugador());
		
	}
	
	@Test
	public void testUnJugadorSigueTeniendoLaFabricaQueComproAntesCuandoEseJugadorIntentaComprarOtraFabrica() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fábrica.*/
		try {
			this.fabricas.get(0).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertEquals("El jugador 1 debería tener asignada la fábrica 1.", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	@Test
	public void testSeLanzaExcepcionDeFabricaOcupadaCuandoUnJugadorIntentaAlquilarUnaFabricaAlquiladaPorOtro() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
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
		Assert.assertNull("El jugador 2 no debería tener una fabrica asignada.", jug2.getFabrica());
	}
	
	
	@Test
	public void testUnJugadorSigueTeniendoAsignadaLaFabricaQueAlquiloAntesCuandoOtroJugadorIntentaAlquilarEsaFabrica() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
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
		Assert.assertEquals("La fábrica 1 debería tener asignado al jugador 1.", this.fabricas.get(1).getJugador(), this.jugador);
	}
	
	@Test
	public void testLanzaExcepcionCuandoUnJugadorConUnaFabricaAsignadaIntentaAlquilarOtra() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fábrica.*/
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
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fábrica.*/
		try {
			this.fabricas.get(0).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertNull("La fábrica 0 no deberia tener un jugador asignado.", this.fabricas.get(0).getJugador());
		
	}
	@Test
	public void testUnJugadorSigueTeniendoLaFabricaQueAlquiloAntesCuandoEseJugadorIntentaAlquilarOtraFabrica() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		/*El jugador 1 intenta comprar otra fábrica.*/
		try {
			this.fabricas.get(0).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) { }
		
		/*Test.*/
		Assert.assertEquals("El jugador 1 debería tener asignada la fábrica 1.", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	@Test
	public void testUnaFabricaNoTieneUnJugadorAsignadoCuandoUnJugadorLaCompraYLaVende() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("La fábrica no debería tener un jugador asignado.", this.fabricas.get(1).getJugador());
	}
	
	@Test
	public void testUnJugadorNoTieneUnaFabricaAsignadaCuandoEseJugadorCompraUnaFabricaYLaVende(){
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("El jugador no debería tener una fábrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testElJugadorTieneUnVeintePorCientoMenosDePlataDeLaQueTeniaAntesDeComprarYVenderUnaFabrica() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador debería ser el del principio menos el 20% del costo de la fábrica.",
                this.jugador.getDineroActual(), (float) (plata - this.fabricas.get(1).getCostoCompra() * VEINTE_PORCIENTO));
	}
	
	@Test
	public void testUnaFabricaNoTieneUnJugadorAsignadoCuandoUnJugadorLaAlquilaYLaVende() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("La fábrica no debería tener un jugador asignado.", this.fabricas.get(1).getJugador());
	}
	
	@Test
	public void testUnJugadorNoTieneUnaFabricaAsignadaCuandoEseJugadorAlquilaUnaFabricaYLaVende(){
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertNull("El jugador no debería tener una fábrica asignada.", this.jugador.getFabrica());
	}
	
	@Test
	public void testElJugadorTieneElMismoDineroQueAntesDeAlquilarYVenderUnaFabrica() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador debería ser el del principio.", this.jugador.getDineroActual(), plata);
	}
	
	@Test
	public void testElJugadorRecuperaUnPorcentajeDeLaFabricaYDeLaMaquinaQueTieneLaFabricaLuegoDeComprarYVenderEsaFabricaQueTieneUnaMaquina() {
		/*Inicializaci�n.*/
		Maquina maquina1 = new Horno(0F, 0F);
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		float plata = this.jugador.getDineroActual();
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.jugador.getFabrica().comprarMaquina(maquina1);

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador debería ser el del "
							+ "principio menos el 20% del costo de la fábrica," +
									"menos el costo compra de la m�quina," +
									"m�s el costo de venta de la m�quina.",
								this.jugador.getDineroActual(),
								(float) (plata - this.fabricas.get(1).getCostoCompra() * VEINTE_PORCIENTO
										+ maquina1.obtenerCostoVenta() - maquina1.getCostoMaquina()));	
	}
	
	@Test
	public void testElJugadorRecuperaUnPorcentajeDeLaMaquinaQueTieneLaFabricaLuegoDeAlquilarYVenderEsaFabricaQueTieneUnaMaquina() {
		/*Inicializaci�n.*/
		Maquina maquina1 = new Horno(0F, 0F);
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		float plata = this.jugador.getDineroActual();
		
		/*Asignaci�n.*/
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
		} catch (JugadorConFabricaException e) {
		}
		
		this.jugador.getFabrica().comprarMaquina(maquina1);

		this.fabricas.get(1).vender();
		
		/*Test.*/
		Assert.assertEquals("El dinero actual del jugador debería ser el del "
							+"menos el costo compra de la m�quina," +
							"m�s el costo de venta de la m�quina.",
								this.jugador.getDineroActual(),
								(float) (plata + maquina1.obtenerCostoVenta() - maquina1.getCostoMaquina()));	
	}
	
	@Test
	public void testNoCambiaLaPlataDeUnJugadorSiSeLlamaAVenderUnaFabricaYNoTieneFabricaAsiganda() {
		/*Inicializaci�n.*/
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		
		/*Asignaci�n.*/
		this.jugador.venderFabrica(PASO_COMPRA);
		
		/*Test.*/
		Assert.assertEquals("El jugador no debería haber ganado plata.", this.jugador.getDineroActual(), plata);
	}
}
