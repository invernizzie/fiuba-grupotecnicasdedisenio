package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.InputOutput;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;

public class TestCintaTransporte {

	private CintaTransportadora cintaTransportadora;
	
	@Before
	public void setUp() throws Exception {
		InputOutput extremoInicial = new Entrada();
		InputOutput extremoFinal = new Salida();
		this.cintaTransportadora = new CintaTransportadora(extremoInicial, extremoFinal);
	}

	@Test
	public void testAsignarElemento(){
		Elemento testElement = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement);
		
		int cantidadElementos = this.cintaTransportadora.getExtremoInicial().getElementos().size();
		Assert.assertEquals("Se esperaba un solo elemento en la lista de elementos " +
				"del extremo inicial", 1, cantidadElementos );
		
		Elemento obtainedElement = this.cintaTransportadora.getExtremoInicial().getElementos().get(0);
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
		
		Elemento testElement2 = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement2);
		
		int cantidadElementos = this.cintaTransportadora.getExtremoInicial().getElementos().size();
		Assert.assertEquals("Se esperaban dos elementos en la lista de elementos " +
				"del extremo final", 2, cantidadElementos );
		
		Elemento obtainedElement1 = this.cintaTransportadora.getExtremoInicial().getElementos().get(0);
		Assert.assertEquals("El elemento asignado no coincide con el recuperado", 
				testElement1, obtainedElement1);
		
		Elemento obtainedElement2 = this.cintaTransportadora.getExtremoInicial().getElementos().get(1);
		Assert.assertEquals("El elemento asignado no coincide con el recuperado", 
				testElement2, obtainedElement2);
	}
	
	@Test
	public void testTrasladarElementos(){
		Elemento testElement1 = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement1);
		
		Elemento testElement2 = new Elemento();
		this.cintaTransportadora.getExtremoInicial().asignarElemento(testElement2);
		
		this.cintaTransportadora.trasladarElementos();
		
		int cantidadElementos = this.cintaTransportadora.getExtremoFinal().getElementos().size();
		Assert.assertEquals("Se esperaban dos elementos en la lista de elementos " +
				"del extremo final", 2, cantidadElementos );
		
		Elemento obtainedElement1 = this.cintaTransportadora.getExtremoFinal().getElementos().get(0);
		Assert.assertEquals("El elemento asignado no coincide con el recuperado", 
				testElement1, obtainedElement1);
		
		Elemento obtainedElement2 = this.cintaTransportadora.getExtremoFinal().getElementos().get(1);
		Assert.assertEquals("El elemento asignado no coincide con el recuperado", 
				testElement2, obtainedElement2);
	}
	
	@Test
	public void testConectarMáquina(){
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
