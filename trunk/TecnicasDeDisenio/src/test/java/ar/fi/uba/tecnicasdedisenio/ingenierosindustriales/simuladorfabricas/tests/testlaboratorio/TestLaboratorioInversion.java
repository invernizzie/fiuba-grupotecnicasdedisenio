package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;

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
		
		/*Inversión de dinero.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertTrue("El Laboratorio debería tener de dinero acumulado, lo que tenÃ­a antes mÃ¡s el porcentaje sacado del dinero total del jugador", jugador.getLaboratorio().getDineroAcumulado() == laboratorioAntesInversion + jugadorInvertido);
		Assert.assertTrue("El jugador debería tener de dinero actual, lo mismo de antes menos el porcentaje sacado", jugador.getDineroActual()+jugadorInvertido == jugadorAntesInvertir);
		
		
		/*Dinero antes de invertir y lo que se va a invertir.*/
		jugadorAntesInvertir =  jugador.getDineroActual();
		jugadorInvertido = jugador.getDineroActual()*porcentaje/100;
		laboratorioAntesInversion = jugador.getLaboratorio().getDineroAcumulado();
		
		/*Inversión de dinero.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertTrue("El Laboratorio debería tener de dinero acumulado, lo que tenÃ­a antes mÃ¡s el porcentaje sacado del dinero total del jugador", jugador.getLaboratorio().getDineroAcumulado() == laboratorioAntesInversion + jugadorInvertido);
		Assert.assertTrue("El jugador debería tener de dinero actual, lo mismo de antes menos el porcentaje sacado", jugador.getDineroActual()+jugadorInvertido == jugadorAntesInvertir);
	}
	
	@Test
	public void testLaboratorioSinProcesos() {
		Assert.assertTrue("Tiene un proceso habilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosHabilitados().size()==0);
		Assert.assertTrue("Tiene un proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
	}
	
	@Test
	public void testLaboratorioConProcesos() {
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso();
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		tipoMaq.setCosto(100);
		proc.agregarTipoMaquina(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		Assert.assertTrue("No tiene ningún proceso habilitado, cuando debería tener uno",jugador.getLaboratorio().getProcesosHabilitados().size()==1);
		Assert.assertTrue("Tiene algún proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso();
		tipoMaq = new TipoMaquinaPlancha(new Entrada(), new Salida());
		tipoMaq.setCosto(125);
		proc.agregarTipoMaquina(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		Assert.assertTrue("No tiene dos procesos habilitados, cuando debería tener dos",jugador.getLaboratorio().getProcesosHabilitados().size()==2);
		Assert.assertTrue("Tiene algún proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso();
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		tipoMaq.setCosto(1000);
		proc.agregarTipoMaquina(tipoMaq);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		tipoMaq.setCosto(1000);
		proc.agregarTipoMaquina(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso();
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		tipoMaq.setCosto(1000);
		proc.agregarTipoMaquina(tipoMaq);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		tipoMaq.setCosto(1000);
		proc.agregarTipoMaquina(tipoMaq);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		tipoMaq.setCosto(1000);
		proc.agregarTipoMaquina(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
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
