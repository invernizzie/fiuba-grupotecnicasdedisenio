package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;


import org.junit.Test;
import junit.framework.Assert;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestLaboratorioValidacion {
    
    private static final int COSTO_1 = 1000;
    private static final int COSTO_2 = 1500;

	private Laboratorio laboratorio = new Laboratorio("Comida", "");
	private Proceso proceso = new Proceso(COSTO_1);

    @Test
	public void testProcesoSinTipoMaquina() {
		Assert.assertNull("Tiene una maquina asignada", proceso.getMaquinaFinal());
	}
	
	@Test
	public void testProcesoConTipoMaquina() {
		TipoMaquina tipoMaquina = new TipoMaquinaPrensa();
		proceso.setMaquinaFinal(tipoMaquina);
		Assert.assertNotNull("No tiene una maquina asignada", proceso.getMaquinaFinal());
		Assert.assertEquals("No son la misma maquina", proceso.getMaquinaFinal(), tipoMaquina);
	}
	
	@Test
	public void testExisteProcesoValido(){
		TipoMaquina maq = null;
		Maquina maquina = null;
		ValidadorProductos val = ValidadorProductos.instancia();
		
		proceso = new Proceso(COSTO_2);
		maq = new TipoMaquinaPrensa();
		maq.addPrecedente(new TipoMaquinaPlancha());
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		proceso = new Proceso(COSTO_1);
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
		
		
		maquina = new Prensa(0F, 0F);
		maquina.addPrecedente(new Prensa(0F, 0F));
		maquina.addPrecedente(new Plancha(0F, 0F));
		maquina.getPrecedentes().get(1).addPrecedente(new Prensa(0F, 0F));
		maquina.getPrecedentes().get(1).addMateriaPrima(new Producto(val, "agua", 0));
		maquina.getPrecedentes().get(1).addMateriaPrima(new Producto(val, "pan", 0));
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).addPrecedente(new Plancha(0F, 0F));
		maquina.getPrecedentes().get(1).addPrecedente(new Plancha(0F, 0F));
		maquina.addMateriaPrima(new Producto(val, "trigo", 0));
		maquina.addMateriaPrima(new Producto(val, "agua", 0));

		Assert.assertTrue("Deberia haber un proceso valido", laboratorio.existeProcesoValido(maquina));

		maquina = new Prensa(0F, 0F);
		maquina.addPrecedente(new Prensa(0F, 0F));
		maquina.addPrecedente(new Plancha(0F, 0F));
		maquina.getPrecedentes().get(1).addPrecedente(new Prensa(0F, 0F));
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).addPrecedente(new Plancha(0F, 0F));
		maquina.getPrecedentes().get(1).getPrecedentes().get(0).addPrecedente(new Plancha(0F, 0F));
		maquina.getPrecedentes().get(1).addPrecedente(new Plancha(0F, 0F));
		maquina.getPrecedentes().get(1).addMateriaPrima(new Producto(val, "harina", 0));
		
		Assert.assertFalse("No deber?a haber un proceso valido", laboratorio.existeProcesoValido(maquina));
		
		
		proceso = new Proceso(COSTO_1);
		maq = new TipoMaquinaPrensa();
		maq.addPrecedente(new TipoMaquinaPlancha());
		maq.addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "harina", 0));
		proceso.setMaquinaFinal(maq);
		laboratorio.getProcesosHabilitados().add(proceso);
		
		Assert.assertTrue("Deberia haber un proceso valido", laboratorio.existeProcesoValido(maquina));
		
		
		maquina = new Prensa(0F, 0F);
		maquina.addPrecedente(new Plancha(0F, 0F));
		
		Assert.assertTrue("Deberia haber un proceso valido", laboratorio.existeProcesoValido(maquina));
		
	}
	
}
