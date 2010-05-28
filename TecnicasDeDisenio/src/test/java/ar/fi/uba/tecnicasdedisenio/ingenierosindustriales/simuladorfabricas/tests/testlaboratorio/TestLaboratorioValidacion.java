package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;
import junit.framework.Assert;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Proceso;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;

public class TestLaboratorioValidacion {
	private Laboratorio laboratorio = Laboratorio.getNewInstance();
	private Proceso proceso = new Proceso(1000);
	private TipoMaquina maquina = new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple());
	private Entrada entrada = new Entrada();
	private Salida salida = new Salida();
	
	@Test
	public void testProcesoSinTipoMaquina(){
		Assert.assertNull("Tiene una maquina asignada", proceso.getMaquinaFinal());
	}
	
	@Test
	public void testProcesoConTipoMaquina(){
		TipoMaquina tipoMaquina = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proceso.setMaquinaFinal(tipoMaquina);
		Assert.assertNotNull("No tiene una maquina asignada", proceso.getMaquinaFinal());
		Assert.assertEquals("No son la misma maquina", proceso.getMaquinaFinal(),tipoMaquina);
	}
	
	
	
	
	@Test
	public void testExisteProcesoValidoSinEntradas(){
		
	/*	Agrego dos procesos distintos.
		proceso = new Proceso();
		proceso.agregarTipoMaquina(new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		
		laboratorio.getProcesosHabilitados().add(proceso);
		
		
		proceso = new Proceso();
		proceso.agregarTipoMaquina(new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple()));
		
		laboratorio.getProcesosHabilitados().add(proceso);
		
		
		
		Creo un conjunto de maquinas y veo si hay algun proceso que sea igual.
		ArrayList<Maquina> maquinas = new ArrayList<Maquina>();
		maquinas.add(new Plancha());
		maquinas.add(new Prensa());
		
		Assert.assertNull("No deberia haber ningun proceso",laboratorio.procesoValido(maquinas));
		
		Modifico la linea y vuelvo a ver si hay algun proceso que sea igual.
		maquinas.add(new Plancha());
		Assert.assertNotNull("Deberia haber algun proceso",laboratorio.procesoValido(maquinas));
		
		Modifico la linea y vuelvo a ver si hay algun proceso que sea igual.
		maquinas.add(new Prensa());
		Assert.assertNotNull("Deberia haber algun proceso",laboratorio.procesoValido(maquinas));
		
		Modifico la linea y vuelvo a ver si hay algun proceso que sea igual.
		maquinas.add(new Plancha());
		Assert.assertNull("No deberia haber ningun proceso",laboratorio.procesoValido(maquinas));

		Agrego un nuevo proceso y vuelvo a validar.
		proceso = new Proceso();
		proceso.agregarTipoMaquina(new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		
		laboratorio.getProcesosHabilitados().add(proceso);
		
		
		Assert.assertNotNull("Deber�a haber algun proceso",laboratorio.procesoValido(maquinas));
		*/
	}
	
}
