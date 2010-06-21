package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.productos;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.*;

public class TestProductos {

	private Producto producto;

    @Before
	public void setUp() throws Exception {
        String posibleEstado = "pan";
		producto = new Producto(posibleEstado, 0f);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCrearProducto() {
		Assert.assertNotNull("No se puede crear producto", producto.getTipoProducto());
	}
	
	@Test
	public void testEqualsProductoTrue() {
		Producto prod = new Producto("pan", 0);
		Assert.assertEquals("Creo el tipo de Producto", producto.getTipoProducto(), prod.getTipoProducto());
	}
	
	@Test
	public void testEqualsProductoFalse() {
		Producto prod = new Producto("harina", 0);
		Assert.assertNotSame("Error en el tipo de Producto", producto.getTipoProducto(), prod.getTipoProducto());
	}
}
