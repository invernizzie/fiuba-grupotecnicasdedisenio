package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.fabrica;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.DineroInsuficienteException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.FabricaOcupadaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.JugadorConFabricaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;

public class TestCompraVentaCalendario {
	
	private ArrayList<Fabrica> fabricas;
	private static int SEGUNDOS_POR_DIA = 1;
	
	@Before
	public void setUp() throws Exception {
		Calendario.instancia().inicializar();
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
	
	@Test
	public void testCompraAlquilerVentaCalendario(){
		Jugador jugador, jugador2;

		jugador = new Jugador("Gustavo",3000);
		jugador.setLaboratorio(new Laboratorio("Cocina",""));
		float plata = jugador.getDineroActual();
		try {
			fabricas.get(2).alquilar(jugador);
		} 
		catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		jugador2 = new Jugador("Gustavo",2000);
		jugador2.setLaboratorio(new Laboratorio("Cocina",""));
		float plata2 = jugador2.getDineroActual();
		try {
			fabricas.get(1).comprar(jugador2);
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
		
		/*Pasa un mes.*/
		Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
		Calendario.instancia().iniciar();
		esperar(SEGUNDOS_POR_DIA * 1);
		Calendario.instancia().pausar();
	   
		Assert.assertEquals("Deberia debitarse del jugador el costo por mes de alquiler", jugador.getDineroActual(),(float)(plata-jugador.getFabrica().getCostoAlquiler()));
		Assert.assertEquals("Deberia debitarse del jugador el costo de la compra y nada del alquiler", jugador2.getDineroActual(),(float)(plata2-jugador2.getFabrica().getCostoCompra()));
		
	    plata = jugador.getDineroActual();
	    fabricas.get(2).vender();
	    
	    /*Pasa un mes.*/
	    Calendario.instancia().reanudar();
	    esperar(SEGUNDOS_POR_DIA * 31);
	    Calendario.instancia().pausar();
	    
	    Assert.assertEquals("No deberia debitarse de la plata el costo por mes", jugador.getDineroActual(),plata);
	    
		
	}
	private void esperar(int segundos) {
        Date inicio = new Date();
        long diferencia = 0;
        /* Se agrega un offset de 250ms para evitar el problema
         * de terminar la espera antes de concluir la actividad
         * de los suscriptores al calendario. Eso probablemente
         * sucede porque este thread es muy activo y se prioriza
         * ante el del calendario. */
        while (diferencia < 1000 * segundos + 250) {
            diferencia = new Date().getTime() - inicio.getTime();
        }
    }
}
