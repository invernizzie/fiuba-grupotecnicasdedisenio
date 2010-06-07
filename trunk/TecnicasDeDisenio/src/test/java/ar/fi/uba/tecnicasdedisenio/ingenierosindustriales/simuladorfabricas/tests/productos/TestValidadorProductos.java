package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.productos;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.*;

public class TestValidadorProductos {
	private ValidadorProductos val;
	
	@Before
	public void setUp() throws Exception {
		val = ValidadorProductos.instancia();
		val.Cargar();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEncontrarProducto() {
		Assert.assertEquals(true, val.Existe("pan"));
		//System.out.print("Se encontro el producto buscado\n");
	}
	
	@Test
	public void testEsValidoProducto() {
		Assert.assertEquals(false, val.esValido("miel"));
		//System.out.print("No es válido el producto buscado\n");
	}
	
	@Test
	public void testEsValidoProductoInexistente() {
		Assert.assertEquals(false, val.esValido("auto"));
	}	
	
	@Test
	public void testEsValidoToString() {
		System.out.println(val.toString());
		Assert.assertNotNull(val.toString());
	}	
}