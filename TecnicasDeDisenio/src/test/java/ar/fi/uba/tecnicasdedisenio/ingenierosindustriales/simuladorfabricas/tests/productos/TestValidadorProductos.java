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
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEncontrarProducto() {
		Assert.assertEquals(true, val.existe("pan"));
		//System.out.print("Se encontro el producto buscado\n");
	}
	
	@Test
	public void testNoEncontrarProducto() {
		Assert.assertEquals(false, val.existe("remera"));
		//System.out.print("Se encontro el producto buscado\n");
	}
	
	@Test
	public void testEsValidoToString() {
		System.out.println(val.toString());
		Assert.assertNotNull(val);
	}
	@Test
	public void testGetAll() {
		System.out.println(val.getMateriasPrimas().toString());
		Assert.assertNotNull(val);
	}	
}