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
    private static final double VEINTE_PORCIENTO = 0.2;

    @Before
	public void setUp() throws Exception {
		posibleEstado = "pan";
		val = ValidadorProductos.instancia();
		producto = new Producto(val, posibleEstado, VEINTE_PORCIENTO);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCrearProducto() {
		Assert.assertNotNull("No se puede crear producto", producto.getEstado());
	}
	
	@Test
	public void testEqualsProducto() {
		Producto prod = new Producto(val, "pan", 0);
		Assert.assertEquals("Error en el estado de Producto", producto.getEstado(), prod.getEstado());
	}
}
