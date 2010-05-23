package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion.tipomaquina;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.ComparadorDeMaquinasSimple;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPrensa;

public class TestTipoMaquina {

	private TipoMaquina tipoMaquina;
	
	@Before
	public void setUp() throws Exception {
		this.tipoMaquina = new TipoMaquinaPrensa(new ComparadorDeMaquinasSimple());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstancia() {
		Maquina maquina = tipoMaquina.getInstancia();
		Assert.assertNotNull("La máquina obtenida es nula", maquina);
		Assert.assertEquals("La máquina obtenida no es del tipo esperado", 
				Prensa.class, maquina.getClass());
	}

	@Test
	public void testVerificarTipo() {
		Maquina prensa = new Prensa();
		boolean verificacion = tipoMaquina.verificarTipo(prensa);
		Assert.assertTrue("La verificación del tipo de máquina no fue satisfactoria", verificacion);
	}

}
