package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.fabrica;


import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.CantidadLineasMaximaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Contenedor;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Horno;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Licuadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.LineaProduccion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Plancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestFabricaLineas {

	private Fabrica fabrica;
	private Fuente fuenteTrigo;
	private Fuente fuenteAgua;
	private Fuente fuenteSal;

	@Before
	public void setUp() throws Exception {
		this.fabrica = new Fabrica(1, 1, 1, 10);
		this.fuenteTrigo = new Fuente("trigo", 100, 
				new Producto(ValidadorProductos.instancia(), "trigo", 0));
		this.fuenteAgua = new Fuente("agua", 100, 
				new Producto(ValidadorProductos.instancia(), "agua", 0));	
		this.fuenteSal = new Fuente("agua", 100, 
				new Producto(ValidadorProductos.instancia(), "sal", 0));
		
		this.fabrica.agregarFuente(fuenteTrigo);
		this.fabrica.agregarFuente(fuenteAgua);
		this.fabrica.agregarFuente(fuenteSal);
		
		Jugador jugador = new Jugador("Gustavo",1000);
		jugador.setLaboratorio(new Laboratorio("Cocina"));
		this.fabrica.comprar(jugador);
	}

	@Test
	public void testCrearLineas(){
		Maquina horno = new Horno(0F, 0F);
		Maquina licuadora = new Licuadora(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		
		fabrica.agregarMaquina(horno);
		fabrica.agregarMaquina(licuadora);
		fabrica.agregarMaquina(plancha);
		
		try {
			fabrica.conectarMaquina(fuenteTrigo, horno);
			fabrica.conectarMaquina(horno, licuadora);
		} catch (CantidadLineasMaximaException e) {
			e.printStackTrace();
		}
		
		List<LineaProduccion> lineas = fabrica.getLineas();
		Assert.assertEquals("Se esperaba una sola linea", 1, lineas.size());

		fabrica.notificar(Evento.COMIENZO_DE_DIA);
		fabrica.notificar(Evento.COMIENZO_DE_DIA);
		Contenedor contenedor = lineas.get(0).getContenedor();
		Assert.assertTrue("Se esperaba al menos un producto", contenedor.getCantidadProductos() > 0);
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
