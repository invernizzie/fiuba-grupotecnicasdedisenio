package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;


import org.junit.Test;
import junit.framework.Assert;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Proceso;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestLaboratorioValidacion {
	private Laboratorio laboratorio = Laboratorio.getNewInstance();
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
		
		proceso = new Proceso(1000);
		maq = new TipoMaquinaPrensa();
		maq.getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().add(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().add(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().add(new TipoMaquinaPlancha());
//		maq.getMateriasPrimas().add(new Producto(new ValidadorProductos(), new String("Producto1"), 0));
//		maq.getMateriasPrimas().add(new Producto(new ValidadorProductos(), new String("Producto2"), 0));
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		
		proceso = new Proceso(1500);
		maq = new TipoMaquinaPrensa();
		maq.getPrecedentes().add(new TipoMaquinaPlancha());
//		maq.getMateriasPrimas().add(new Producto(new ValidadorProductos(), new String("Producto1"), 0));
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		
		maquina = new Prensa();
		maquina.getPrecedentes().add(new Prensa());
		maquina.getPrecedentes().add(new Plancha());
		maquina.getPrecedentes().get(1).getPrecedentes().add(new Prensa());
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).getPrecedentes().add(new Plancha());
		maquina.getPrecedentes().get(1).getPrecedentes().add(new Plancha());
//		maquina.getMateriasPrimas().add(new Producto(new ValidadorProductos(), new String("Producto1"), 0));
//		maquina.getMateriasPrimas().add(new Producto(new ValidadorProductos(), new String("Producto2"), 0));

		
		Assert.assertTrue("Deberia haber un proceso valido",laboratorio.existeProcesoValido(maquina));		

		maquina = new Prensa();
		maquina.getPrecedentes().add(new Prensa());
		maquina.getPrecedentes().add(new Plancha());
		maquina.getPrecedentes().get(1).getPrecedentes().add(new Prensa());
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).getPrecedentes().add(new Plancha());
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).getPrecedentes().add(new Plancha());
		maquina.getPrecedentes().get(1).getPrecedentes().add(new Plancha());
		
		Assert.assertFalse("No debería haber un proceso valido",laboratorio.existeProcesoValido(maquina));
		
		
		proceso = new Proceso(1000);
		maq = new TipoMaquinaPrensa();
		maq.getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().add(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().add(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().add(new TipoMaquinaPlancha());
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		Assert.assertTrue("Deberia haber un proceso valido",laboratorio.existeProcesoValido(maquina));
		
		
		maquina = new Prensa();
		maquina.getPrecedentes().add(new Plancha());
		
		Assert.assertTrue("Deberia haber un proceso valido",laboratorio.existeProcesoValido(maquina));
		
	}
	
}
