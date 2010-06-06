package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.EntradaInvalidaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestMaquinaPrensa {
	
	private Maquina prensa;
	private ValidadorProductos val;

	@Before
	public void setUp() throws Exception {
		val = ValidadorProductos.instancia();
		val.Cargar();
		prensa = new Prensa(0F, 0F);
		prensa.setCintaSalida(new CintaTransportadora(prensa.getSalida(), new Entrada()));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcesar() {
		Producto productoAPrensar = new Producto(val, "Pan", 0F);
		this.prensa.getEntrada().agregarProducto(productoAPrensar);
		
		try {
			this.prensa.procesar(true);
		} catch (EntradaInvalidaException e) {
			Assert.fail("La entrada a la prensa es inválida");
		}
		
		Producto productoPrensado = this.prensa.getSalida().obtenerProducto();
		
		Assert.assertEquals("El Producto no es un Producto prensado", "prensado", 
				productoPrensado.getEstado());
	}
	
	@Test
	public void testEntradaInvalida() {
		Producto productoAPrensar1 = new Producto(val, "Pan", 0F);
		Producto productoAPrensar2 = new Producto(val, "Pan", 0F);
		this.prensa.getEntrada().agregarProducto(productoAPrensar1);
		this.prensa.getEntrada().agregarProducto(productoAPrensar2);
		
		try {
			this.prensa.procesar(true);
			Assert.fail("Se esperaba una excepción pero no se produjo");
		} catch (Exception e) {
			Assert.assertEquals("Se esperaba una excepción de tipo EntradaInvalidaException", 
					EntradaInvalidaException.class , e.getClass());
		}
	}
	
	@Test
	public void testCrearProductoInvalido() {
		Producto productoAPrensar = new Producto(val, "Pan", 0F);
		Producto productoTest = new Producto(val, "Desecho", 0F);
		this.prensa.getEntrada().agregarProducto(productoAPrensar);
		
		try {
			this.prensa.procesar(false);
			Producto obtenido = this.prensa.getSalida().obtenerProducto();
			Assert.assertEquals("Se esperaba un desecho", 
					productoTest, obtenido);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("No se esperaba una excepción");
		}
	}

}
