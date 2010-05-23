package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.*;

import org.junit.Test;
import junit.framework.Assert;




public class TestLaboratorioInversion {
	
	private Jugador jugador = new Jugador(10000);
	private float porcentaje = 10;
	
	
	
	@Test
	public void testJugadorConLaboratorio() {
		Assert.assertNotNull("No tiene laboratorio creado, cuando debería tenerlo",jugador.getLaboratorio());
	}
	
	@Test
	public void testInvertirEnLaboratorioInhabilitado(){
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertTrue("No debería tener dinero acumulado el laboratorio", jugador.getLaboratorio().getDineroAcumulado()==0);	
	}
	
	@Test
	public void testInvertirEnLaboratorioHabilitado(){
		float jugadorAntesInvertir;
		float jugadorInvertido;
		float laboratorioAntesInversion;
		
		
		/* Habilita el laboratorio.*/
		jugador.habilitarLaboratorio();
		
		
		/*Dinero antes de invertir y lo que se va a invertir.*/
		jugadorAntesInvertir =  jugador.getDineroActual();
		jugadorInvertido = jugador.getDineroActual()*porcentaje/100;
		laboratorioAntesInversion = jugador.getLaboratorio().getDineroAcumulado();
		
		/*Inversi�n de dinero.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertTrue("El Laboratorio debería tener de dinero acumulado, lo que tenía antes más el porcentaje sacado del dinero total del jugador", jugador.getLaboratorio().getDineroAcumulado() == laboratorioAntesInversion + jugadorInvertido);
		Assert.assertTrue("El jugador debería tener de dinero actual, lo mismo de antes menos el porcentaje sacado", jugador.getDineroActual()+jugadorInvertido == jugadorAntesInvertir);
		
		
		/*Dinero antes de invertir y lo que se va a invertir.*/
		jugadorAntesInvertir =  jugador.getDineroActual();
		jugadorInvertido = jugador.getDineroActual()*porcentaje/100;
		laboratorioAntesInversion = jugador.getLaboratorio().getDineroAcumulado();
		
		/*Inversión de dinero.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertTrue("El Laboratorio debería tener de dinero acumulado, lo que tenía antes más el porcentaje sacado del dinero total del jugador", jugador.getLaboratorio().getDineroAcumulado() == laboratorioAntesInversion + jugadorInvertido);
		Assert.assertTrue("El jugador debería tener de dinero actual, lo mismo de antes menos el porcentaje sacado", jugador.getDineroActual()+jugadorInvertido == jugadorAntesInvertir);
	}
	
	@Test
	public void testLaboratorioSinProcesos() {
		Assert.assertTrue("Tiene un proceso habilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosHabilitados().size()==0);
		Assert.assertTrue("Tiene un proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
	}
	
	@Test
	public void testLaboratorioConProcesos() {
		Proceso proc1 = new Proceso(100);
		Proceso proc2 = new Proceso(125);
		Proceso proc3 = new Proceso(2000);
		Proceso proc4 = new Proceso(3000);
		
		/*Agrego un proceso habilitado.*/
		jugador.getLaboratorio().getProcesosHabilitados().add(proc1);
		Assert.assertTrue("No tiene ningún proceso habilitado, cuando debería tener uno",jugador.getLaboratorio().getProcesosHabilitados().size()==1);
		Assert.assertTrue("Tiene algún proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
		
		/*Agrego un proceso habilitado.*/
		jugador.getLaboratorio().getProcesosHabilitados().add(proc2);
		Assert.assertTrue("No tiene dos procesos habilitados, cuando debería tener dos",jugador.getLaboratorio().getProcesosHabilitados().size()==2);
		Assert.assertTrue("Tiene algún proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
		
		/*Agrego dos procesos inhabilitados.*/
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc3);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc4);
		Assert.assertTrue("No tiene dos procesos inhabilitados, cuando debería tener dos",jugador.getLaboratorio().getProcesosInhabilitados().size()==2);
	}
	
	@Test
	public void habilitarNuevosProcesos(){
		
		/*Se invierte dinero. El laboratorio ya puede descubrir un nuevo producto.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertEquals("La cantidad de procesos habilitados esperada es 3",jugador.getLaboratorio().getProcesosHabilitados().size(),3);
		Assert.assertEquals("La cantidad de procesos inhabilitados esperada es 1",jugador.getLaboratorio().getProcesosInhabilitados().size(),1);
		
		/*Se invierte dinero. El laboratorio ya puede descubrir un nuevo producto.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		jugador.invertirDineroLaboratorio(porcentaje);
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertEquals("La cantidad de procesos habilitados esperada es 4",jugador.getLaboratorio().getProcesosHabilitados().size(),4);
		Assert.assertEquals("La cantidad de procesos inhabilitados esperada es 0",jugador.getLaboratorio().getProcesosInhabilitados().size(),0);
	}
	
}
