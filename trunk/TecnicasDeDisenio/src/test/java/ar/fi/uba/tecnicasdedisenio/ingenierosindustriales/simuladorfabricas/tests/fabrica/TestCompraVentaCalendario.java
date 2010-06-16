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

    private static final int SEGUNDOS_POR_DIA = 1;
    private static final int PASO_SUPERFICIE = 100;
    private static final int PASO_COMPRA = 1000;
    private static final int PASO_ALQUILER = 150;
    private static final int DINERO_INICIAL_1 = 3000;
    private static final int DINERO_INICIAL_2 = 2000;
    private static final int MAX_DIAS_POR_MES = 31;
    private static final int OFFSET_MILLIS = 250;
    private static final int CANTIDAD_DE_FABRICAS = 5;

	private ArrayList<Fabrica> fabricas;

    @Before
	public void setUp() throws Exception {
		Calendario.instancia().inicializar();
		int i;

		fabricas = new ArrayList<Fabrica>();
		for (i = 0; i < CANTIDAD_DE_FABRICAS; i++) {
			fabricas.add(new Fabrica((i + 1) * PASO_SUPERFICIE, (i + 1) * PASO_COMPRA, (i + 1) * PASO_ALQUILER));
		}
	}
	
	@Test
	public void testCompraAlquilerVentaCalendario() {
		Jugador jugador, jugador2;

		jugador = new Jugador("Gustavo", DINERO_INICIAL_1);
		jugador.setLaboratorio(new Laboratorio("Cocina", ""));
		float plata = jugador.getDineroActual();
		try {
			fabricas.get(2).alquilar(jugador);
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		jugador2 = new Jugador("Gustavo", DINERO_INICIAL_2);
		jugador2.setLaboratorio(new Laboratorio("Cocina", ""));
		float plata2 = jugador2.getDineroActual();
		try {
			fabricas.get(1).comprar(jugador2);
		} catch (DineroInsuficienteException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (FabricaOcupadaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		} catch (JugadorConFabricaException e) {
			Assert.fail("No deberia haber lanzado una excepcion");
		}
		
		/* Pasa un mes. */
		Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
		Calendario.instancia().iniciar();
		esperar(SEGUNDOS_POR_DIA * 1);
		Calendario.instancia().pausar();
	   
		Assert.assertEquals("Deberia debitarse del jugador el costo por mes de alquiler", jugador.getDineroActual(),
                plata - jugador.getFabrica().getCostoAlquiler());
		Assert.assertEquals("Deberia debitarse del jugador el costo de la compra y nada del alquiler", jugador2.getDineroActual(),
                plata2 - jugador2.getFabrica().getCostoCompra());
		
	    plata = jugador.getDineroActual();
	    fabricas.get(2).vender();
	    
	    /*Pasa un mes.*/
	    Calendario.instancia().reanudar();
	    esperar(SEGUNDOS_POR_DIA * MAX_DIAS_POR_MES);
	    Calendario.instancia().pausar();
	    
	    Assert.assertEquals("No deberia debitarse de la plata el costo por mes", jugador.getDineroActual(), plata);
	    
		
	}
	private void esperar(final int segundos) {
        Date inicio = new Date();
        long diferencia = 0;
        /* Se agrega un offset de 250ms para evitar el problema
         * de terminar la espera antes de concluir la actividad
         * de los suscriptores al calendario. Eso probablemente
         * sucede porque este thread es muy activo y se prioriza
         * ante el del calendario. */
        while (diferencia < PASO_COMPRA * segundos + OFFSET_MILLIS) {
            diferencia = new Date().getTime() - inicio.getTime();
        }
    }
}
