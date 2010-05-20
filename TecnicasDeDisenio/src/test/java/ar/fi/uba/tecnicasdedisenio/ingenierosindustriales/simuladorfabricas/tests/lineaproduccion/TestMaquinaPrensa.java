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
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;

public class TestMaquinaPrensa {
	
	private Maquina prensa;

	@Before
	public void setUp() throws Exception {
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
		Elemento elementoAPrensar = new Elemento();
		this.prensa.getEntrada().agregarElemento(elementoAPrensar);
		
		this.prensa.procesar();
		
		Elemento elementoPrensado = this.prensa.getSalida().obtenerElemento();
		
		Assert.assertEquals("El elemento no es un elemento prensado", "Prensado", elementoPrensado.getNombre());
	}

}
