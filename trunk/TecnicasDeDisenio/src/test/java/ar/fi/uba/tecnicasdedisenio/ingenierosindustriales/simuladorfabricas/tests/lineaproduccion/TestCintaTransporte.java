package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestCintaTransporte {

	private CintaTransportadora cintaTransportadora;
	private ValidadorProductos val;
	
	@Before
	public void setUp() throws Exception {
		val = new ValidadorProductos();
		val.Cargar();
		ISalida extremoInicial = new Salida();
		IEntrada extremoFinal = new Entrada();
		this.cintaTransportadora = new CintaTransportadora(extremoInicial, extremoFinal);
	}

	@Test
	public void testAsignarProducto(){
		Producto testProduct = new Producto(val, "Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct);
		
		Assert.assertNotNull("Se esperaba un solo Producto en la lista de Productos " +
				"del extremo inicial", this.cintaTransportadora.getExtremoInicial().obtenerProducto());
		
		Producto obtainedProduct = this.cintaTransportadora.getExtremoInicial().obtenerProducto();
		Assert.assertEquals("El Producto asignado no coincide con el recuperado", 
				testProduct, obtainedProduct);
		
	}
	
	@Test
	public void testTrasladarProducto(){
		Producto testProduct = new Producto(val, "Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct);
		
		this.cintaTransportadora.trasladarElementos();
		
		int cantidadProductos = this.cintaTransportadora.getExtremoFinal().getProdcutos().size();
		Assert.assertEquals("Se esperaba un solo Producto en la lista de Productos " +
				"del extremo final", 1, cantidadProductos );
		
		Producto obtainedProduct = this.cintaTransportadora.getExtremoFinal().getProdcutos().get(0);
		Assert.assertEquals("El Producto asignado no coincide con el recuperado", 
				testProduct, obtainedProduct);
		
	}
	
	@Test
	public void testAsignarProductos(){
		Producto testProduct1 = new Producto(val, "Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct1);
		
		Assert.assertSame("Se esperaba el ultimo Producto asignado", this.cintaTransportadora.getExtremoInicial().obtenerProducto(), testProduct1 );
		
		Producto testProduct2 = new Producto(val, "Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct2);
		
		Assert.assertNotSame("Se esperaba el ultimo Producto asignado", this.cintaTransportadora.getExtremoInicial().obtenerProducto(), testProduct1 );
		Assert.assertSame("Se esperaba el ultimo Producto asignado", this.cintaTransportadora.getExtremoInicial().obtenerProducto(), testProduct2 );
		
	}
	
	@Test
	public void testConectarMaquina(){
		Maquina prensa = new Prensa();
		Producto testProduct = new Producto(val, "Pan", 0F);
		
		prensa.setEntrada(this.cintaTransportadora.getExtremoFinal());
		
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct);
		
		this.cintaTransportadora.trasladarElementos();
		
		Producto obtainedProduct = prensa.getEntrada().getProdcutos().get(0);
		
		Assert.assertEquals("El Producto asignado no coincide con el recuperado", 
				testProduct, obtainedProduct);
		
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
