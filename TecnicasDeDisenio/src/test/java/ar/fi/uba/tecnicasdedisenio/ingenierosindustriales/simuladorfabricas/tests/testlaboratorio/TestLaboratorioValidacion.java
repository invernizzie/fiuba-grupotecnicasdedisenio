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
	private Laboratorio laboratorio = Laboratorio.getInstance();
	private Proceso proceso = new Proceso();
	private Iterator<TipoMaquina> itMaq = proceso.iterator();
	private TipoMaquina maquina = new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple());
	private Entrada entrada = new Entrada();
	private Salida salida = new Salida();
	
	@Test
	public void testProcesoSinTipoMaquina(){
		Assert.assertFalse("Tiene una maquina asignada", itMaq.hasNext());
	}
	
	@Test
	public void testProcesoConTipoMaquina(){
		TipoMaquina tipoMaquina = new TipoMaquinaPrensa(new Entrada(), new Salida());
		proceso.agregarTipoMaquina(tipoMaquina);
		Assert.assertTrue("No tiene una maquina asignada", itMaq.hasNext());
		Assert.assertEquals("No son la misma maquina", itMaq.next(),tipoMaquina);
	}
	
	@Test
	public void testTipoMaquinaSinEntradaSalida(){
		Assert.assertNull("Tiene una salida asignada", maquina.getSalida());
		Assert.assertNull("Tiene una entrada asignada", maquina.getEntrada());
	}
	
	@Test
	public void testTipoMaquinaConEntradaSalida(){
		maquina.setSalida(new Salida());
		maquina.setEntrada(new Entrada());
		
		Assert.assertNotNull("No tiene una salida asignada", maquina.getSalida());
		Assert.assertNotNull("No tiene una entrada asignada", maquina.getEntrada());
	}
	
	@Test
	public void testTipoMaquinaConMismaEntradaSalidaQueAsignada(){
		Salida s = new Salida();
		maquina.setSalida(s);
		
		Entrada e = new Entrada();
		maquina.setEntrada(e);
		
		Assert.assertEquals("No tiene la misma salida", maquina.getSalida(),s);
		Assert.assertEquals("No tiene la misma entrada", maquina.getEntrada(),e);
	}
	
	@Test
	public void testEntradaSalidaSinElemento(){
		Assert.assertNull("La salida tiene un elemento",salida.obtenerElemento());
		Assert.assertTrue("La entrada tiene un elemento",entrada.getElementos().size()==0);
	}
	
	@Test
	public void testEntradaConElemento(){
		this.salida.asignarElemento(new Elemento());
		this.entrada.agregarElemento(new Elemento());
		
		Assert.assertNotNull("La salida no tiene un elemento",salida.obtenerElemento());
		Assert.assertTrue("La entrada no tiene un elemento",entrada.getElementos().size()==1);
		
		
		this.salida.asignarElemento(new Elemento());
		this.entrada.agregarElemento(new Elemento());
		
		Assert.assertNotNull("La salida no tiene un elemento",salida.obtenerElemento());
		Assert.assertTrue("La entrada no tiene dos elementos",entrada.getElementos().size()==2);
	}
	
	
	@Test
	public void testEntradaSalidaConMismoElementoQueAsignado(){
		Elemento e = new Elemento();
		salida.asignarElemento(e);
		entrada.agregarElemento(e);
		
		Assert.assertEquals("No tiene el mismo elemento", salida.obtenerElemento(),e);
		Assert.assertEquals("No tiene el mismo elemento", entrada.getElementos().get(entrada.getElementos().size()-1),e);
		Assert.assertEquals("No tienen el mismo elemento entre ellos", salida.obtenerElemento(), entrada.getElementos().get(entrada.getElementos().size()-1));
	}
	
	@Test
	public void testExisteProcesoValidoSinEntradas(){
		
		/*Agrego dos procesos distintos.*/
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
		
		
		
		/*Creo un conjunto de maquinas y veo si hay algun proceso que sea igual.*/
		ArrayList<Maquina> maquinas = new ArrayList<Maquina>();
		maquinas.add(new Plancha());
		maquinas.add(new Prensa());
		
		Assert.assertNull("No debería haber ningun proceso",laboratorio.procesoValido(maquinas));
		
		/*Modifico la linea y vuelvo a ver si hay algun proceso que sea igual.*/
		maquinas.add(new Plancha());
		Assert.assertNotNull("Debería haber algun proceso",laboratorio.procesoValido(maquinas));
		
		/*Modifico la linea y vuelvo a ver si hay algun proceso que sea igual.*/
		maquinas.add(new Prensa());
		Assert.assertNotNull("Debería haber algun proceso",laboratorio.procesoValido(maquinas));
		
		/*Modifico la linea y vuelvo a ver si hay algun proceso que sea igual.*/
		maquinas.add(new Plancha());
		Assert.assertNull("No debería haber ningun proceso",laboratorio.procesoValido(maquinas));

		/*Agrego un nuevo proceso y vuelvo a validar.*/
		proceso = new Proceso();
		proceso.agregarTipoMaquina(new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple()));
		proceso.agregarTipoMaquina(new TipoMaquinaPlancha(new ComparadorDeMaquinasSimple()));
		
		laboratorio.getProcesosHabilitados().add(proceso);
		
		
		Assert.assertNotNull("Debería haber algun proceso",laboratorio.procesoValido(maquinas));
		
	}
	
}
