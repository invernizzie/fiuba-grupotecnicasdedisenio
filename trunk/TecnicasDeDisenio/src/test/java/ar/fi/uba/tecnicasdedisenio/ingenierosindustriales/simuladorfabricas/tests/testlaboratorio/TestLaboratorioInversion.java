package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Proceso;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPlancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPrensa;




public class TestLaboratorioInversion {
	
	
	private Jugador jugador;
	private float porcentaje;
	
	@Before
	public void setUp() throws Exception {
		jugador = new Jugador(10000);
		porcentaje = 10;
		jugador.getLaboratorio().setProcesosHabilitados(new ArrayList<Proceso>());
		jugador.getLaboratorio().setProcesosInhabilitados(new ArrayList<Proceso>());
	}

	@After
	public void tearDown() throws Exception {
	}
	
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
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(100);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		Assert.assertTrue("No tiene ningún proceso habilitado, cuando debería tener uno",jugador.getLaboratorio().getProcesosHabilitados().size()==1);
		Assert.assertTrue("Tiene algún proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(125);
		tipoMaq = new TipoMaquinaPlancha(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		Assert.assertTrue("No tiene dos procesos habilitados, cuando debería tener dos",jugador.getLaboratorio().getProcesosHabilitados().size()==2);
		Assert.assertTrue("Tiene algún proceso inhabilitado cuando no debería tenerlo",jugador.getLaboratorio().getProcesosInhabilitados().size()==0);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(2000);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(3000);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		Assert.assertTrue("No tiene dos procesos inhabilitados, cuando debería tener dos",jugador.getLaboratorio().getProcesosInhabilitados().size()==2);
	}
	
	@Test
	public void habilitarNuevosProcesos(){
		TipoMaquina tipoMaq;
		Proceso proc;
		
		jugador.habilitarLaboratorio();
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(100);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(125);
		tipoMaq = new TipoMaquinaPlancha(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(1000);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(2439);
		tipoMaq = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Se invierte dinero. El laboratorio ya puede descubrir un nuevo producto.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertEquals("La cantidad de procesos habilitados esperada es 3",jugador.getLaboratorio().getProcesosHabilitados().size(),3);
		Assert.assertEquals("La cantidad de procesos inhabilitados esperada es 1",jugador.getLaboratorio().getProcesosInhabilitados().size(),1);
		
		/*Se invierte dinero. Pero no se puede descubrir nada.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		/*Se invierte dinero. Pero no se puede descubrir nada.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		/*Se invierte dinero. El laboratorio ya puede descubrir un nuevo producto.*/
		jugador.invertirDineroLaboratorio(porcentaje);
		
		Assert.assertEquals("La cantidad de procesos habilitados esperada es 4",jugador.getLaboratorio().getProcesosHabilitados().size(),4);
		Assert.assertEquals("La cantidad de procesos inhabilitados esperada es 0",jugador.getLaboratorio().getProcesosInhabilitados().size(),0);
	}
	
}
