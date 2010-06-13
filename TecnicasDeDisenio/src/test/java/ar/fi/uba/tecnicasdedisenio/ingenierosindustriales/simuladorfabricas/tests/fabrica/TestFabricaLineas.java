package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.fabrica;


import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Proceso;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Horno;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Licuadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.LineaProduccion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Plancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPlancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPrensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestFabricaLineas {

	private Fabrica fabrica;
	private Fuente fuenteTrigo;
	private Fuente fuenteAgua;
	private Fuente fuenteSal;
	private Jugador jugador;

	@Before
	public void setUp() throws Exception {
		this.fabrica = new Fabrica(1, 1, 1);
		this.fuenteTrigo = new Fuente("trigo", 100, 
				new Producto(ValidadorProductos.instancia(), "trigo", 0));
		this.fuenteAgua = new Fuente("agua", 100, 
				new Producto(ValidadorProductos.instancia(), "agua", 0));	
		this.fuenteSal = new Fuente("agua", 100, 
				new Producto(ValidadorProductos.instancia(), "sal", 0));
		
		this.fabrica.agregarFuente(fuenteTrigo);
		this.fabrica.agregarFuente(fuenteAgua);
		this.fabrica.agregarFuente(fuenteSal);
		
		this.jugador = new Jugador("Gustavo",1000);
		this.jugador.setLaboratorio(new Laboratorio("Cocina",""));
		this.fabrica.comprar(jugador);
	}

	@Test
	public void testCrearLinea(){
		Maquina horno = new Horno(0F, 0F);
		Maquina licuadora = new Licuadora(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		
		fabrica.agregarMaquina(horno);
		fabrica.agregarMaquina(licuadora);
		fabrica.agregarMaquina(plancha);
		
		fabrica.conectarMaquina(fuenteTrigo, horno);
		fabrica.conectarMaquina(horno, licuadora);
		
		
		List<LineaProduccion> lineas = fabrica.getLineas();
		Assert.assertEquals("Se esperaba una sola linea", 1, lineas.size());

	}
	
	@Test
	public void testCrearLineas(){
		Maquina horno = new Horno(0F, 0F);
		Maquina licuadora = new Licuadora(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		Maquina prensa = new Prensa(0F, 0F);
		
		fabrica.agregarMaquina(horno);
		fabrica.agregarMaquina(licuadora);
		fabrica.agregarMaquina(plancha);
		fabrica.agregarMaquina(prensa);
		
		fabrica.conectarMaquina(fuenteTrigo, horno);
		fabrica.conectarMaquina(horno, licuadora);
		
		List<LineaProduccion> lineas = fabrica.getLineas();
		Assert.assertEquals("Se esperaba una sola linea", 1, lineas.size());

		fabrica.conectarMaquina(fuenteAgua, plancha);
		fabrica.conectarMaquina(plancha, prensa);
		
		lineas = fabrica.getLineas();
		Assert.assertEquals("Se esperaban 2 lineas", 2, lineas.size());
		
		plancha = new Plancha(0F, 0F);
		prensa = new Prensa(0F, 0F);
		
		fabrica.conectarMaquina(fuenteAgua, plancha);
		fabrica.conectarMaquina(plancha, prensa);
		
		lineas = fabrica.getLineas();
		Assert.assertEquals("Se esperaban 3 lineas", 3, lineas.size());
	}
	
	@Test
	public void testCostos(){

		/*
		 * Creo un proceso de prueba para simplificar el test.
		 */
		Proceso proceso = new Proceso(1000);
		TipoMaquina maq = new TipoMaquinaPrensa();
		TipoMaquinaPlancha tipoPlancha = new TipoMaquinaPlancha();
		tipoPlancha.addMateriaPrima(new Producto(ValidadorProductos.instancia(), "trigo", 0));
		maq.addPrecedente(tipoPlancha);
		proceso.setMaquinaFinal(maq);
		jugador.getLaboratorio().getProcesosHabilitados().add(proceso);
		
		Maquina prensa = new Prensa(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		
		fabrica.agregarMaquina(prensa);
		fabrica.agregarMaquina(plancha);
		
		fabrica.conectarMaquina(fuenteTrigo, plancha);
		fabrica.conectarMaquina(plancha, prensa);
		
		fabrica.notificar(Evento.COMIENZO_DE_DIA);
		fabrica.notificar(Evento.COMIENZO_DE_DIA);
		
		float dineroJugador = jugador.getDineroActual();
		float dineroEsperado = 1000 - fabrica.getCostoCompra();
		dineroEsperado -= prensa.getCostoMaquina();
		dineroEsperado -= plancha.getCostoMaquina();
		dineroEsperado -= fuenteTrigo.getTipoProducto().getPrecioCompra();
		dineroEsperado += 200F;
		
		Assert.assertEquals("El dinero del jugador no es el esperado", dineroEsperado, dineroJugador);
		
		fabrica.notificar(Evento.COMIENZO_DE_DIA);
		fabrica.notificar(Evento.COMIENZO_DE_DIA);
		
		dineroEsperado -= fuenteTrigo.getTipoProducto().getPrecioCompra();
		dineroEsperado += 200F;
		
		dineroJugador = jugador.getDineroActual();
		Assert.assertEquals("El dinero del jugador no es el esperado", dineroEsperado, dineroJugador);
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
