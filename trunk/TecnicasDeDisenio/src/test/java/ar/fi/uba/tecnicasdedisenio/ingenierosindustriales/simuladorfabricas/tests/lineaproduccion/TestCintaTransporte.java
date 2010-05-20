package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;

public class TestCintaTransporte {

	private CintaTransportadora cintaTransportadora;
	
	@Before
	public void setUp() throws Exception {
		ISalida extremoInicial = new Salida();
		IEntrada extremoFinal = new Entrada();
		this.cintaTransportadora = new CintaTransportadora(extremoInicial, extremoFinal);
	}

	@Test
	public void testAsignarElemento(){
		Elemento testElement = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement);
		
		Assert.assertNotNull("Se esperaba un solo elemento en la lista de elementos " +
				"del extremo inicial", this.cintaTransportadora.getExtremoInicial().obtenerElemento());
		
		Elemento obtainedElement = this.cintaTransportadora.getExtremoInicial().obtenerElemento();
		Assert.assertEquals("El elemento asignado no coincide con el recuperado", 
				testElement, obtainedElement);
		
	}
	
	@Test
	public void testTrasladarElemento(){
		Elemento testElement = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement);
		
		this.cintaTransportadora.trasladarElementos();
		
		int cantidadElementos = this.cintaTransportadora.getExtremoFinal().getElementos().size();
		Assert.assertEquals("Se esperaba un solo elemento en la lista de elementos " +
				"del extremo final", 1, cantidadElementos );
		
		Elemento obtainedElement = this.cintaTransportadora.getExtremoFinal().getElementos().get(0);
		Assert.assertEquals("El elemento asignado no coincide con el recuperado", 
				testElement, obtainedElement);
		
	}
	
	@Test
	public void testAsignarElementos(){
		Elemento testElement1 = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement1);
		
		Assert.assertSame("Se esperaba el ultimo elemento asignado", this.cintaTransportadora.getExtremoInicial().obtenerElemento(), testElement1 );
		
		Elemento testElement2 = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement2);
		
		Assert.assertNotSame("Se esperaba el ultimo elemento asignado", this.cintaTransportadora.getExtremoInicial().obtenerElemento(), testElement1 );
		Assert.assertSame("Se esperaba el ultimo elemento asignado", this.cintaTransportadora.getExtremoInicial().obtenerElemento(), testElement2 );
		
	}
	
	@Test
	public void testConectarMaquina(){
		Maquina prensa = new Prensa();
		Elemento testElement = new Elemento();
		
		prensa.setEntrada(this.cintaTransportadora.getExtremoFinal());
		
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement);
		
		this.cintaTransportadora.trasladarElementos();
		
		Elemento obtainedElement = prensa.getEntrada().getElementos().get(0);
		
		Assert.assertEquals("El elemento asignado no coincide con el recuperado", 
				testElement, obtainedElement);
		
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
