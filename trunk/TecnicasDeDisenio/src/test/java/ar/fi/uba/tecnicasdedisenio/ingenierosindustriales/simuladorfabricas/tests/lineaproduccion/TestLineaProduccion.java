package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.LineaProduccion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestLineaProduccion {

	private LineaProduccion linea;
	private Fuente fuente;
	private CintaTransportadora cinta;
	
	@Before
	public void setUp() throws Exception {
		this.linea = new LineaProduccion(new Laboratorio("Cocina"));
		this.fuente = new Fuente("Trigo", 100, 
				new Producto(ValidadorProductos.instancia(), "Trigo", 0));
		this.cinta = new CintaTransportadora();
	}

	@Test
	public void testAgregarPrimeraMaquina(){
		Maquina maquina = new Prensa(0F, 0F);
		cinta.conectar(fuente, maquina);
		linea.agregarMaquina(maquina );
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina));
		
		Assert.assertTrue("La máquina insertada no es la primera", 
							linea.esPrimeraMaquina(maquina));
		
		Assert.assertEquals("La máquina insertada no es la última", 
								linea.obtenerUltimaMaquina(), maquina);
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
