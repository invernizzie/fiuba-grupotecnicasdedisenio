package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.productos;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.*;

public class TestProductos {
	private Producto producto;
	private String posibleEstado;
	private ValidadorProductos val;
	
	@Before
	public void setUp() throws Exception {
		posibleEstado="pan";
		val = new ValidadorProductos();
		val.Cargar();
		producto = new Producto(val, posibleEstado, 0.2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCrearProducto() {
		Assert.assertNotNull("No se puede crear producto",producto.getEstado());
		//System.out.print("Estado: " + producto.getEstado());
	}
	
	@Test
	public void testEqualsProducto() {
		Producto prod = new Producto(val, "auto",0);
		//Assert.assertEquals(true, producto.equals( prod));
		Assert.assertEquals("Error en el estado de Producto", prod.getEstado(), producto.getEstado());
	}
}
