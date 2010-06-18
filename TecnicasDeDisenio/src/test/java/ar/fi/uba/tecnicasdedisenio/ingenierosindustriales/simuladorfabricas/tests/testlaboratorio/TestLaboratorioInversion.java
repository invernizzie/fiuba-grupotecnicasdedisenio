package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Proceso;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPlancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPrensa;

public class TestLaboratorioInversion {

    private static final float PORCENTAJE = 10;
    private static final int DINERO_INCIAL_1 = 10000;
    private static final int CIEN = 100;
    private static final int COSTO_1 = 100;
    private static final int COSTO_2 = 125;
    private static final int COSTO_3 = 1000;
    private static final int COSTO_4 = 2439;    
	
	private Jugador jugador;

    @Before
	public void setUp() throws Exception {
		jugador = new Jugador("Gustavo", DINERO_INCIAL_1);
		jugador.setLaboratorio(new Laboratorio("Comida", ""));
		jugador.getLaboratorio().setProcesosHabilitados(new ArrayList<Proceso>());
		jugador.getLaboratorio().setProcesosInhabilitados(new ArrayList<Proceso>());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testElJugadorTieneUnLaboratorioCuandoSeCrea() {
		/*Test.*/
		Assert.assertNotNull("No tiene laboratorio creado, cuando debería tenerlo.", jugador.getLaboratorio());
	}
	
	@Test
	public void testElLaboratorioNoAcumulaDineroCuandoSeInvierteEnElYEstaInhabilitado() {
		/*Asignación.*/
		jugador.invertirDineroLaboratorio(PORCENTAJE);
		
		/*Test.*/
		Assert.assertTrue("No debería tener dinero acumulado el laboratorio.",
                jugador.getLaboratorio().getDineroAcumulado() == 0);
	}
	
	@Test
	public void testElLaboratorioTieneDeDineroAcumuladoElPorcentajeDeLoInvertidoPorElJugadorYaQueElJugadorInvirtioEnElLaboratorioYEsteEstabaHabilitado() {
		/*Inicialización.*/
		float jugadorInvertido;
		float laboratorioAntesInversion;
		
		/*Asignaci�n.*/
		jugador.habilitarLaboratorio();
		
		
		/*Dinero antes de invertir y lo que se va a invertir.*/
		jugadorInvertido = jugador.getDineroActual()* PORCENTAJE / CIEN;
		laboratorioAntesInversion = jugador.getLaboratorio().getDineroAcumulado();
		
		/*Inversión de dinero.*/
		jugador.invertirDineroLaboratorio(PORCENTAJE);
		
		/*Test.*/
		Assert.assertTrue("El Laboratorio debería tener de dinero acumulado, lo que tenía antes más el porcentaje sacado del "
                + "dinero total del jugador.", jugador.getLaboratorio().getDineroAcumulado() == laboratorioAntesInversion + jugadorInvertido);
	}
	
	@Test
	public void testElJugadorTieneUnPorcentajeMenosEnSuDineroYaQueElJugadorInvirtioEnElLaboratorioYEsteEstabaHabilitado() {
		/*Inicialización.*/
		float jugadorAntesInvertir;
		float jugadorInvertido;
		
		/*Asignaci�n.*/
		jugador.habilitarLaboratorio();
		
		
		/*Dinero antes de invertir y lo que se va a invertir.*/
		jugadorAntesInvertir =  jugador.getDineroActual();
		jugadorInvertido = jugador.getDineroActual()* PORCENTAJE / CIEN;
		
		/*Inversión de dinero.*/
		jugador.invertirDineroLaboratorio(PORCENTAJE);
		
		/*Test.*/
		Assert.assertTrue("El jugador debería tener de dinero actual, lo mismo de antes menos el porcentaje sacado.",
                jugador.getDineroActual() + jugadorInvertido == jugadorAntesInvertir);
	}
	
	@Test
	public void testElLaboratorioRecienCreadoNoTieneProcesosHabilitados() {
		/*Test.*/
		Assert.assertTrue("Tiene un proceso habilitado cuando no debería tenerlo.",
                jugador.getLaboratorio().getProcesosHabilitados().size() == 0);
	}
	
	@Test
	public void testElLaboratorioRecienCreadoNoTieneProcesosInhabilitados() {
		/*Test.*/
		Assert.assertTrue("Tiene un proceso inhabilitado cuando no debería tenerlo.",
                jugador.getLaboratorio().getProcesosInhabilitados().size() == 0);
	}
	
	@Test
	public void testUnLaboratorioTieneUnProcesoHabiltadoCuandoSeLeAgregaUnProcesoASuListaDeHabilitadosVacia() {
		/*Inicialización.*/
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Asignaci�n.*/
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_1);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Test.*/
		Assert.assertTrue("No tiene ningún proceso habilitado, cuando debería tener uno.",
                jugador.getLaboratorio().getProcesosHabilitados().size() == 1);
	}
	
	@Test
	public void testUnLaboratorioNoTieneNingunProcesoInhabiltadoCuandoSeLeAgregaUnProcesoASuListaDeHabilitadosYSuListaDeInhabilitadosEstabaVacia() {
		/*Inicialización.*/
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Asignaci�n.*/
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_1);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Test.*/
		Assert.assertTrue("Tiene algún proceso inhabilitado cuando no debería tenerlo.",
                jugador.getLaboratorio().getProcesosInhabilitados().size() == 0);
	}
	
	@Test
	public void testUnLaboratorioTieneUnProcesoInhabiltadoCuandoSeLeAgregaUnProcesoASuListaDeInhabilitadosVacia() {
		/*Inicialización.*/
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Asignaci�n.*/
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_1);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Test.*/
		Assert.assertTrue("No tiene ningún proceso inhabilitado, cuando debería tener uno.",
                jugador.getLaboratorio().getProcesosInhabilitados().size() == 1);
	}
	
	@Test
	public void testUnLaboratorioNoTieneNingunProcesoHabiltadoCuandoSeLeAgregaUnProcesoASuListaDeInhabilitadosYSuListaDeHabilitadosEstabaVacia() {
		/*Inicialización.*/
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Asignaci�n.*/
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_1);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Test.*/
		Assert.assertTrue("Tiene algún proceso habilitado cuando no debería tenerlo.",
                jugador.getLaboratorio().getProcesosHabilitados().size() == 0);
	}
	
	
	
	@Test
	public void testSeEsperanTresProcesosHabilitadosCuandoSeCarganDosProcesosHabilitadosYDosInhabilitadosYSeLlamaaHabilitarProcesosTeniendoDineroSuficienteParaHabilitarUnProcesoDeLosInhabilitados(){
		/*Inicialización.*/
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Asignaci�n.*/
		jugador.habilitarLaboratorio();
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_1);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_2);
		tipoMaq = new TipoMaquinaPlancha();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(COSTO_3);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(COSTO_4);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Se invierte dinero. El laboratorio ya puede descubrir un nuevo producto.*/
		jugador.invertirDineroLaboratorio(PORCENTAJE);
		
		/*Test.*/
		Assert.assertEquals("La cantidad de procesos habilitados esperada es 3.",
                jugador.getLaboratorio().getProcesosHabilitados().size(), 3);
	}
	
	public void testSeEsperanUnProcesoInhabilitadoCuandoSeCarganDosProcesosHabilitadosYDosInhabilitadosYSeLlamaaHabilitarProcesosTeniendoDineroSuficienteParaHabilitarUnProcesoDeLosInhabilitados(){
		/*Inicialización.*/
		TipoMaquina tipoMaq;
		Proceso proc;
		
		/*Asignaci�n.*/
		jugador.habilitarLaboratorio();
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_1);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Agrego un proceso habilitado.*/
		proc = new Proceso(COSTO_2);
		tipoMaq = new TipoMaquinaPlancha();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(COSTO_3);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Agrego un proceso inhabilitado.*/
		proc = new Proceso(COSTO_4);
		tipoMaq = new TipoMaquinaPrensa();
		proc.setMaquinaFinal(tipoMaq);
		jugador.getLaboratorio().getProcesosInhabilitados().add(proc);
		
		/*Se invierte dinero. El laboratorio ya puede descubrir un nuevo producto.*/
		jugador.invertirDineroLaboratorio(PORCENTAJE);
		
		/*Test.*/
		Assert.assertEquals("La cantidad de procesos inhabilitados esperada es 1.",
                jugador.getLaboratorio().getProcesosInhabilitados().size(), 1);	
	}
	
}
