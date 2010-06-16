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
    private static final int DINERO_INICIAL_2 = 2000;
    private static final int DINERO_INICIAL_3 = 5000;
    private static final double VEINTE_PORCIENTO = 0.2;

    @Before
	public void setUp() throws Exception {
		fabricas = new ArrayList<Fabrica>();
		for (int i = 0; i < 5; i++) {
			fabricas.add(new Fabrica((i + 1) * PASO_SUPERFICIE, (i + 1) * PASO_COMPRA, (i + 1) * PASO_ALQUILER));
		}
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCompraFallidaXDineroInsuficiente() {
		jugador = new Jugador("Gustavo", PASO_COMPRA);
		
		try {
			this.fabricas.get(1).comprar(jugador);
			Assert.fail("Deberia haber lanzado una excepcion de DineroInsuficiente");
		} catch (DineroInsuficienteException e) {
        } catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		Assert.assertNull("El jugador no debería tener una fabrica asignada", this.jugador.getFabrica());
		Assert.assertNull("La fabrica no debería tener un jugador que la compró", this.fabricas.get(1).getJugador());
		
		
		
	}

	@Test
	public void testCompraExitosa() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("El jugador debería tener una fabrica asignada", this.jugador.getFabrica());
		Assert.assertNotNull("La fabrica debería tener un jugador que la compró", this.fabricas.get(1).getJugador());
		Assert.assertEquals("El jugador deberia tener la plata anterior menos el costo de la fabrica", jugador.getDineroActual(), plata - this.fabricas.get(1).getCostoCompra());
		Assert.assertEquals("El jugador deberia tener la fabrica que compró", this.jugador.getFabrica(), this.fabricas.get(1));
		Assert.assertEquals("La fabrica debería tener asignada al jugador que la compró", this.fabricas.get(1).getJugador(), this.jugador);
	}
	
	@Test
	public void testAlquilerExitoso() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_2);
		
		float plata = this.jugador.getDineroActual();
		
		try {
			this.fabricas.get(4).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		
		Assert.assertEquals("El jugador deberia tener la plata anterior sin ninguna reducción", jugador.getDineroActual(), plata);
		Assert.assertNotNull("El jugador debería tener una fabrica asignada", this.jugador.getFabrica());
		Assert.assertNotNull("La fabrica debería tener un jugador que la alquiló", this.fabricas.get(4).getJugador());
		Assert.assertEquals("El jugador deberia tener la fabrica que alquiló", this.jugador.getFabrica(), this.fabricas.get(4));
		Assert.assertEquals("La fabrica debería tener asignada al jugador que la alquiló", this.fabricas.get(4).getJugador(), this.jugador);
	}
	
	@Test
	public void testDoblesAsignacionesCompra() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_3);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_3);
		
		float plata = this.jugador.getDineroActual();
		float plata2 = jug2.getDineroActual();
		
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
			Assert.fail("Deberia haber lanzado una excepcion de FabricaOcupada");
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertEquals("La plata del jugador 2 no debería haberse modificado", jug2.getDineroActual(), plata2);
		Assert.assertNull("El jugador 2 no debería tener una fabrica asignada", jug2.getFabrica());
		Assert.assertEquals("La fábrica 1 debería tener asignado al jugador 1", this.fabricas.get(1).getJugador(), this.jugador);
		
		/*El jugador 1 intenta comprar otra fábrica.*/
		plata = jugador.getDineroActual();
		try {
			this.fabricas.get(0).comprar(jugador);
			Assert.fail("Deberia haber lanzado una excepcion de JugadorConFabrica");
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) { }
		
		Assert.assertEquals("La plata del jugador 1 no debería haberse modificado", this.jugador.getDineroActual(), plata);
		Assert.assertNull("La fábrica 0 no deberia tener un jugador asignado", this.fabricas.get(0).getJugador());
		Assert.assertEquals("El jugador 1 debería tener asignada la fábrica 1", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	@Test
	public void testDoblesAsignacionesAlquiler() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_3);
		Jugador jug2 = new Jugador("Gustavo", DINERO_INICIAL_3);
		
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		/*El jugador 2 intenta alquilar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
			Assert.fail("Deberia haber lanzado una excepcion de FabricaOcupada");
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNull("El jugador 2 no debería tener una fabrica asignada", jug2.getFabrica());
		Assert.assertEquals("La fábrica 1 debería tener asignado al jugador 1", this.fabricas.get(1).getJugador(), this.jugador);
		
		/*El jugador 1 intenta alquilar otra fábrica.*/
		try {
			this.fabricas.get(0).comprar(jugador);
			Assert.fail("Deberia haber lanzado una excepcion de JugadorConFabrica");
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) { }
	
		Assert.assertNull("La fábrica 0 no deberia tener un jugador asignado", this.fabricas.get(0).getJugador());
		Assert.assertEquals("El jugador 1 debería tener asignada la fábrica 1", this.jugador.getFabrica(), this.fabricas.get(1));
	}
	
	@Test
	public void testCompraYVentaExitosa() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("La fábrica debería tener un jugador asignado", this.fabricas.get(1).getJugador());
		Assert.assertNotNull("El jugador debería tener una fábrica asignada", this.jugador.getFabrica());
		
		this.fabricas.get(1).vender();
		Assert.assertNull("La fábrica no debería tener un jugador asignado", this.fabricas.get(1).getJugador());
		Assert.assertNull("El jugador no debería tener una fábrica asignada", this.jugador.getFabrica());
		Assert.assertEquals("El dinero actual del jugador debería ser el del principio menos el 20% del costo de la fábrica",
                this.jugador.getDineroActual(), (float) (plata - this.fabricas.get(1).getCostoCompra() * VEINTE_PORCIENTO));
	}
	
	@Test
	public void testCompraYVentaExitosaConMaquinas() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).comprar(jugador);
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("La fábrica debería tener un jugador asignado",
								this.fabricas.get(1).getJugador());
		Assert.assertNotNull("El jugador debería tener una fábrica asignada",
								this.jugador.getFabrica());
		
		Maquina maquina1 = new Horno(0F, 0F);
		this.jugador.getFabrica().comprarMaquina(maquina1);
		
		this.fabricas.get(1).vender();
		Assert.assertNull("La fábrica no debería tener un jugador asignado",
							this.fabricas.get(1).getJugador());
		Assert.assertNull("El jugador no debería tener una fábrica asignada",
							this.jugador.getFabrica());
		Assert.assertEquals("El dinero actual del jugador debería ser el del "
							+ "principio menos el 20% del costo de la fábrica",
								this.jugador.getDineroActual(),
								(float) (plata - this.fabricas.get(1).getCostoCompra() * VEINTE_PORCIENTO
										+ maquina1.obtenerCostoVenta() - maquina1.getCostoMaquina()));	
	}
	
	@Test
	public void testAlquilerYVentaExitoso() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("La fábrica debería tener un jugador asignado", this.fabricas.get(1).getJugador());
		Assert.assertNotNull("El jugador debería tener una fábrica asignada", this.jugador.getFabrica());
		
		this.fabricas.get(1).vender();
		Assert.assertNull("La fábrica no debería tener un jugador asignado", this.fabricas.get(1).getJugador());
		Assert.assertNull("El jugador no debería tener una fábrica asignada", this.jugador.getFabrica());
		Assert.assertEquals("El dinero actual del jugador debería ser el del principio", this.jugador.getDineroActual(), plata);	
	}
	
	@Test
	public void testVenderSinCompraOAlquiler() {
		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		float plata = this.jugador.getDineroActual();
		this.fabricas.get(1).vender();
		this.jugador.venderFabrica(PASO_COMPRA);
		
		Assert.assertEquals("El jugador no debería haber ganado plata", this.jugador.getDineroActual(), plata);
	}
}
