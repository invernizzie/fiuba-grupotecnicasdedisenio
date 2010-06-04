package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;


import org.junit.Test;
import junit.framework.Assert;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Proceso;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestLaboratorioValidacion {
	private Laboratorio laboratorio = new Laboratorio("Comida");
	private Proceso proceso = new Proceso(1000);
	
	@Test
	public void testProcesoSinTipoMaquina(){
		Assert.assertNull("Tiene una maquina asignada", proceso.getMaquinaFinal());
	}
	
	@Test
	public void testProcesoConTipoMaquina(){
		TipoMaquina tipoMaquina = new TipoMaquinaPrensa();
		proceso.setMaquinaFinal(tipoMaquina);
		Assert.assertNotNull("No tiene una maquina asignada", proceso.getMaquinaFinal());
		Assert.assertEquals("No son la misma maquina", proceso.getMaquinaFinal(),tipoMaquina);
	}
	
	
	
	
	@Test
	public void testExisteProcesoValido(){
		TipoMaquina maq = null;
		Maquina maquina = null;
		ValidadorProductos val = new ValidadorProductos();
		val.Cargar();
		
		proceso = new Proceso(1500);
		maq = new TipoMaquinaPrensa();
		maq.addPrecedente(new TipoMaquinaPlancha());
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		proceso = new Proceso(1000);
		maq = new TipoMaquinaPrensa();
		maq.getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().add(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "pan", 0));
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.addMateriaPrima(new Producto(val, "agua", 0));
		maq.addMateriaPrima(new Producto(val, "trigo", 0));
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		
		maquina = new Prensa();
		maquina.addPrecedente(new Prensa());
		maquina.addPrecedente(new Plancha());
		maquina.getPrecedentes().get(1).addPrecedente(new Prensa());
		maquina.getPrecedentes().get(1).addMateriaPrima(new Producto(val, "agua", 0));
		maquina.getPrecedentes().get(1).addMateriaPrima(new Producto(val, "pan", 0));
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).addPrecedente(new Plancha());
		maquina.getPrecedentes().get(1).addPrecedente(new Plancha());
		maquina.addMateriaPrima(new Producto(val, "trigo", 0));
		maquina.addMateriaPrima(new Producto(val, "agua", 0));

		
		Assert.assertTrue("Deberia haber un proceso valido",laboratorio.existeProcesoValido(maquina));		

		maquina = new Prensa();
		maquina.addPrecedente(new Prensa());
		maquina.addPrecedente(new Plancha());
		maquina.getPrecedentes().get(1).addPrecedente(new Prensa());
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).addPrecedente(new Plancha());
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).addPrecedente(new Plancha());
		maquina.getPrecedentes().get(1).addPrecedente(new Plancha());
		maquina.getPrecedentes().get(1).addMateriaPrima(new Producto(val, "agua", 0));
		
		Assert.assertFalse("No debería haber un proceso valido",laboratorio.existeProcesoValido(maquina));
		
		
		proceso = new Proceso(1000);
		maq = new TipoMaquinaPrensa();
		maq.addPrecedente(new TipoMaquinaPlancha());
		maq.addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		Assert.assertTrue("Deberia haber un proceso valido",laboratorio.existeProcesoValido(maquina));
		
		
		maquina = new Prensa();
		maquina.addPrecedente(new Plancha());
		
		Assert.assertTrue("Deberia haber un proceso valido",laboratorio.existeProcesoValido(maquina));
		
	}
	
}
