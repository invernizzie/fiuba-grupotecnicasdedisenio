package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
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
		val = new ValidadorProductos();
		val.Cargar();
		prensa = new Prensa();
		IEntrada entrada = new Entrada();
		ISalida salida = new Salida();
		
		prensa.setEntrada(entrada);
		prensa.setSalida(salida);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcesar() {
		Producto productoAPrensar = new Producto(val, "Pan", 0F);
		this.prensa.getEntrada().agregarProducto(productoAPrensar);
		
		try {
			this.prensa.procesar();
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
			this.prensa.procesar();
			Assert.fail("Se esperaba una excepción pero no se produjo");
		} catch (Exception e) {
			Assert.assertEquals("Se esperaba una excepción de tipo EntradaInvalidaException", 
					EntradaInvalidaException.class , e.getClass());
		}
	}

}
