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
	
	@Before
	public void setUp() throws Exception {
		int i;
		/*
		 * Fabrica 0: Tamanio 100, Costo Compra 1000, Costo Alquiler 150, Cantidad Lineas 1.
		 * Fabrica 1: Tamanio 200, Costo Compra 2000, Costo Alquiler 300, Cantidad Lineas 2.
		 * Fabrica 2: Tamanio 300, Costo Compra 3000, Costo Alquiler 450, Cantidad Lineas 3.
		 * Fabrica 3: Tamanio 400, Costo Compra 4000, Costo Alquiler 600, Cantidad Lineas 4.
		 * Fabrica 4: Tamanio 500, Costo Compra 5000, Costo Alquiler 750, Cantidad Lineas 5.
		 */
		fabricas = new ArrayList<Fabrica>();
		for (i=0;i<5;i++){
			fabricas.add(new Fabrica((i+1)*100,(i+1)*1000, (i+1)*150));
		}
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCompraFallidaXDineroInsuficiente(){
		jugador = new Jugador("Gustavo",1000);
		
		try {
			this.fabricas.get(1).comprar(jugador);
			Assert.fail("Deberia haber lanzado una excepcion de DineroInsuficiente");
		} 
		catch (DineroInsuficienteException e) {}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		Assert.assertNull("El jugador no deber�a tener una fabrica asignada",this.jugador.getFabrica());
		Assert.assertNull("La fabrica no deber�a tener un jugador que la compr�",this.fabricas.get(1).getJugador());
		
		
		
	}

	@Test
	public void testCompraExitosa(){
		jugador = new Jugador("Gustavo",3000);
		
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).comprar(jugador);
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("El jugador deber�a tener una fabrica asignada",this.jugador.getFabrica());
		Assert.assertNotNull("La fabrica deber�a tener un jugador que la compr�",this.fabricas.get(1).getJugador());
		Assert.assertEquals("El jugador deberia tener la plata anterior menos el costo de la fabrica", jugador.getDineroActual(),plata-this.fabricas.get(1).getCostoCompra());
		Assert.assertEquals("El jugador deberia tener la fabrica que compr�",this.jugador.getFabrica(),this.fabricas.get(1));
		Assert.assertEquals("La fabrica deber�a tener asignada al jugador que la compr�",this.fabricas.get(1).getJugador(),this.jugador);
	}
	
	@Test
	public void testAlquilerExitoso(){
		jugador = new Jugador("Gustavo",2000);
		
		float plata = this.jugador.getDineroActual();
		
		try {
			this.fabricas.get(4).alquilar(jugador);
		} 
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		
		Assert.assertEquals("El jugador deberia tener la plata anterior sin ninguna reducci�n", jugador.getDineroActual(),plata);
		Assert.assertNotNull("El jugador deber�a tener una fabrica asignada",this.jugador.getFabrica());
		Assert.assertNotNull("La fabrica deber�a tener un jugador que la alquil�",this.fabricas.get(4).getJugador());
		Assert.assertEquals("El jugador deberia tener la fabrica que alquil�",this.jugador.getFabrica(),this.fabricas.get(4));
		Assert.assertEquals("La fabrica deber�a tener asignada al jugador que la alquil�",this.fabricas.get(4).getJugador(),this.jugador);
	}
	
	@Test
	public void testDoblesAsignacionesCompra(){
		jugador = new Jugador("Gustavo",5000);
		Jugador jug2 = new Jugador("Gustavo",5000);
		
		float plata = this.jugador.getDineroActual();
		float plata2 = jug2.getDineroActual();
		
		try {
			this.fabricas.get(1).comprar(jugador);
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		/*El jugador 2 intenta comprar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
			Assert.fail("Deberia haber lanzado una excepcion de FabricaOcupada");
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertEquals("La plata del jugador 2 no deber�a haberse modificado", jug2.getDineroActual(),plata2);
		Assert.assertNull("El jugador 2 no deber�a tener una fabrica asignada",jug2.getFabrica());
		Assert.assertEquals("La f�brica 1 deber�a tener asignado al jugador 1",this.fabricas.get(1).getJugador(),this.jugador);
		
		/*El jugador 1 intenta comprar otra f�brica.*/
		plata = jugador.getDineroActual();
		try {
			this.fabricas.get(0).comprar(jugador);
			Assert.fail("Deberia haber lanzado una excepcion de JugadorConFabrica");
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			
		}
		
		Assert.assertEquals("La plata del jugador 1 no deber�a haberse modificado", this.jugador.getDineroActual(), plata);
		Assert.assertNull("La f�brica 0 no deberia tener un jugador asignado",this.fabricas.get(0).getJugador());
		Assert.assertEquals("El jugador 1 deber�a tener asignada la f�brica 1", this.jugador.getFabrica(),this.fabricas.get(1));
	}
	
	@Test
	public void testDoblesAsignacionesAlquiler(){
		jugador = new Jugador("Gustavo",5000);
		Jugador jug2 = new Jugador("Gustavo",5000);
		
		try {
			this.fabricas.get(1).comprar(jugador);
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		/*El jugador 2 intenta alquilar la fabrica que ya esta comprada.*/
		try {
			this.fabricas.get(1).comprar(jug2);
			Assert.fail("Deberia haber lanzado una excepcion de FabricaOcupada");
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNull("El jugador 2 no deber�a tener una fabrica asignada",jug2.getFabrica());
		Assert.assertEquals("La f�brica 1 deber�a tener asignado al jugador 1",this.fabricas.get(1).getJugador(),this.jugador);
		
		/*El jugador 1 intenta alquilar otra f�brica.*/
		try {
			this.fabricas.get(0).comprar(jugador);
			Assert.fail("Deberia haber lanzado una excepcion de JugadorConFabrica");
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			
		}
	
		Assert.assertNull("La f�brica 0 no deberia tener un jugador asignado",this.fabricas.get(0).getJugador());
		Assert.assertEquals("El jugador 1 deber�a tener asignada la f�brica 1", this.jugador.getFabrica(),this.fabricas.get(1));
	}
	
	@Test
	public void testCompraYVentaExitosa(){
		jugador = new Jugador("Gustavo",3000);
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).comprar(jugador);
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("La f�brica deber�a tener un jugador asignado",this.fabricas.get(1).getJugador());
		Assert.assertNotNull("El jugador deber�a tener una f�brica asignada",this.jugador.getFabrica());
		
		this.fabricas.get(1).vender();
		Assert.assertNull("La f�brica no deber�a tener un jugador asignado",this.fabricas.get(1).getJugador());
		Assert.assertNull("El jugador no deber�a tener una f�brica asignada",this.jugador.getFabrica());
		Assert.assertEquals("El dinero actual del jugador deber�a ser el del principio menos el 20% del costo de la f�brica",this.jugador.getDineroActual(),(float)(plata-this.fabricas.get(1).getCostoCompra()*0.2));	
	}
	
	@Test
	public void testCompraYVentaExitosaConMaquinas(){
		jugador = new Jugador("Gustavo",3000);
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).comprar(jugador);
		} 
		catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("La f�brica deber�a tener un jugador asignado",
								this.fabricas.get(1).getJugador());
		Assert.assertNotNull("El jugador deber�a tener una f�brica asignada",
								this.jugador.getFabrica());
		
		Maquina maquina1 = new Horno(0F, 0F);
		this.jugador.getFabrica().comprarMaquina(maquina1);
		
		this.fabricas.get(1).vender();
		Assert.assertNull("La f�brica no deber�a tener un jugador asignado",
							this.fabricas.get(1).getJugador());
		Assert.assertNull("El jugador no deber�a tener una f�brica asignada",
							this.jugador.getFabrica());
		Assert.assertEquals("El dinero actual del jugador deber�a ser el del " +
								"principio menos el 20% del costo de la f�brica",
								this.jugador.getDineroActual(),
								(float)(plata - this.fabricas.get(1).getCostoCompra()*0.2 
										+ maquina1.obtenerCostoVenta() - 
										maquina1.getCostoMaquina()));	
	}
	
	@Test
	public void testAlquilerYVentaExitoso(){
		jugador = new Jugador("Gustavo",3000);
		float plata = this.jugador.getDineroActual();
		try {
			this.fabricas.get(1).alquilar(jugador);
		} 
		
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}	
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		Assert.assertNotNull("La f�brica deber�a tener un jugador asignado",this.fabricas.get(1).getJugador());
		Assert.assertNotNull("El jugador deber�a tener una f�brica asignada",this.jugador.getFabrica());
		
		this.fabricas.get(1).vender();
		Assert.assertNull("La f�brica no deber�a tener un jugador asignado",this.fabricas.get(1).getJugador());
		Assert.assertNull("El jugador no deber�a tener una f�brica asignada",this.jugador.getFabrica());
		Assert.assertEquals("El dinero actual del jugador deber�a ser el del principio",this.jugador.getDineroActual(),plata);	
	}
	
	@Test
	public void testVenderSinCompraOAlquiler(){
		jugador = new Jugador("Gustavo",3000);
		float plata = this.jugador.getDineroActual();
		this.fabricas.get(1).vender();
		this.jugador.venderFabrica(1000);
		
		Assert.assertEquals("El jugador no deber�a haber ganado plata", this.jugador.getDineroActual(),plata);
	}
}
