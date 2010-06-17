package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.productos;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.*;

public class TestProductos {

    private static final double VEINTE_PORCIENTO = 0.2;

	private Producto producto;
    private ValidadorProductos val;

    @Before
	public void setUp() throws Exception {
        String posibleEstado = "pan";
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
		//Assert.assertEquals("Error en el estado de Producto", producto.getEstado(), prod.getEstado());
        Assert.assertEquals("Error en el estado de Producto", producto, prod);
	}
}
