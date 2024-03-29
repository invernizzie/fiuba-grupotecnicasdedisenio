package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IFuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Plancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

public class TestCintaTransporte {

    private static final int CANTIDAD_DE_PRUEBA = 100;

	private CintaTransportadora cintaTransportadora;

    @Before
	public void setUp() throws Exception {
		ISalida extremoInicial = new Salida();
		IEntrada extremoFinal = new Entrada();
		this.cintaTransportadora = new CintaTransportadora(extremoInicial, extremoFinal);
	}

	@Test
	public void asignarProductoAlExtremoInicialLoDejaEnElExtremoInicial() {
		Producto testProduct = new Producto("Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct);
		
		Assert.assertNotNull("Se esperaba un solo Producto en la lista de Productos "
				+ "del extremo inicial", this.cintaTransportadora.getExtremoInicial().obtenerProducto());
		
		Producto obtainedProduct = this.cintaTransportadora.getExtremoInicial().obtenerProducto();
		Assert.assertEquals("El Producto asignado no coincide con el recuperado", 
				testProduct, obtainedProduct);
		
	}
	
	@Test
	public void trasladarProductoSacaLoSacaDelExtremoInicialYLoPoneEnElFinal() {
		Producto testProduct = new Producto("Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct);
		
		this.cintaTransportadora.trasladarElementos();
		
		int cantidadProductos = this.cintaTransportadora.getExtremoFinal().getProdcutos().size();
		Assert.assertEquals("Se esperaba un solo Producto en la lista de Productos "
				+ "del extremo final", 1, cantidadProductos);
		
		Producto obtainedProduct = this.cintaTransportadora.getExtremoFinal().getProdcutos().get(0);
		Assert.assertEquals("El Producto asignado no coincide con el recuperado", 
				testProduct, obtainedProduct);
		
	}
	
	@Test
	public void asignarDosProductosEnElExtremoInicialDejaDosProductosEnEl() {
		Producto testProduct1 = new Producto("Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct1);
		
		Assert.assertSame("Se esperaba el ultimo Producto asignado", 
							this.cintaTransportadora.getExtremoInicial().obtenerProducto(), testProduct1);
		
		Producto testProduct2 = new Producto("Pan", 0F);
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct2);
		
		Assert.assertNotSame("Se esperaba el ultimo Producto asignado", 
								this.cintaTransportadora.getExtremoInicial().obtenerProducto(), testProduct1);
		Assert.assertSame("Se esperaba el ultimo Producto asignado", 
								this.cintaTransportadora.getExtremoInicial().obtenerProducto(), testProduct2);
		
	}
	
	@Test
	public void crearDosMaquinasYConectarlasMedianteUnaCintaHabilitaElTrasladoEntreLasMaquinas() {
		Maquina prensa = new Prensa(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		Producto testProduct = new Producto( "Pan", 0F);
		
		this.cintaTransportadora.conectar(prensa, plancha);
		
		this.cintaTransportadora.getExtremoInicial().asignarProducto(testProduct);
		
		this.cintaTransportadora.trasladarElementos();
		
		Producto obtainedProduct = plancha.getEntrada().getProdcutos().get(0);
		
		Assert.assertEquals("El Producto asignado no coincide con el recuperado", 
				testProduct, obtainedProduct);
		
	}
	
	@Test
	public void conectarDosMaquinasAUnaTerceraLasSeteaComoPrecedentesDeLaTercera() {
		Maquina prensa = new Prensa(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		Maquina plancha2 = new Plancha(0F, 0F);
		
		this.cintaTransportadora.conectar(prensa, plancha);
		this.cintaTransportadora.conectar(plancha2, plancha);
		
		Assert.assertEquals("La plancha no tiene la cantidad de precedentes esperada", 
							2, plancha.getPrecedentes().size());
		
		Assert.assertEquals("El tipo de producto recibido por la máquina no se "
                            + "condice con el entregado",	prensa, plancha.getPrecedentes().get(0));
		
		Assert.assertEquals("El tipo de producto recibido por la máquina no se "
                            + "condice con el entregado",	plancha2, plancha.getPrecedentes().get(1));
		
	}
	
	@Test
	public void conectarUnaMaquinaAOtraLaSeteaComoPrecedente() {
		Maquina prensa = new Prensa(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		
		this.cintaTransportadora.conectar(prensa, plancha);
		
		Assert.assertEquals("La plancha tiene más de un precedente",
								1, plancha.getPrecedentes().size());
		
		Assert.assertEquals("El precedente de la plancha no es la prensa", 
				prensa, plancha.getPrecedentes().get(0));
	}
	
	@Test
	public void alDesconectarDosMaquinasDejanDeEstarConectadas() {
		Maquina prensa = new Prensa(0F, 0F);
		Maquina plancha = new Plancha(0F, 0F);
		
		this.cintaTransportadora.conectar(prensa, plancha);
		
		this.cintaTransportadora.desconectar((IFuente)prensa, plancha);
		
		Assert.assertEquals("La plancha tiene al menos un precedente", 
								0, plancha.getPrecedentes().size());
		
		Assert.assertFalse("El precedente de la plancha sigue siendo la prensa", 
								plancha.getPrecedentes().contains(prensa));
	}
	
	@Test
	public void conectarUnaFuenteAUnaMaquinaPermiteTrasladarLosProductosDeLaFuenteALaMaquina() {
		Producto testProduct = new Producto( "Pan", 0F);
		Maquina prensa = new Prensa(0F, 0F);
		Fuente fuente = new Fuente("pan", CANTIDAD_DE_PRUEBA, testProduct);
		
		this.cintaTransportadora.conectar(fuente, prensa);
		
		this.cintaTransportadora.trasladarElementos();
		
		Producto obtainedProduct = prensa.getEntrada().getProdcutos().get(0);
		
		Assert.assertEquals("El Producto asignado no coincide con el recuperado", 
								testProduct, obtainedProduct);
		
	}
	
	@Test
	public void conectarUnaFuenteAUnaMaquinaAgregaLaFuenteAlListadoDeMateriasPrimas() {
		Producto testProduct = new Producto( "Pan", 0F);
		Maquina prensa = new Prensa(0F, 0F);
		Fuente fuente = new Fuente("pan", CANTIDAD_DE_PRUEBA, testProduct);
		
		this.cintaTransportadora.conectar(fuente, prensa);
		
		Assert.assertEquals("La prensa tiene más de una materia prima",
								1, prensa.getMateriasPrimas().size());
		
		Assert.assertEquals("El tipo de producto recibido por la máquina no se "
				+ "condice con el entregado", testProduct, prensa.getMateriasPrimas().get(0));
	}
	
	@Test
	public void conectarDosFuentesAUnaMaquinaAgregaLasFuentesAlListadoDeMateriasPrimas() {
		Producto testProduct = new Producto( "Pan", 0F);
		Producto testProduct2 = new Producto( "trigo", 0F);
		Maquina prensa = new Prensa(0F, 0F);
		Fuente fuente = new Fuente("pan", CANTIDAD_DE_PRUEBA, testProduct);
		Fuente fuente2 = new Fuente("pan", CANTIDAD_DE_PRUEBA, testProduct2);
		
		this.cintaTransportadora.conectar(fuente, prensa);
		this.cintaTransportadora.conectar(fuente2, prensa);
		
		Assert.assertEquals("La prensa no tiene la cantidad de materia prima esperada", 
								2, prensa.getMateriasPrimas().size());
		
		Assert.assertEquals("El tipo de producto recibido por la máquina no se "
				+ "condice con el entregado",	testProduct, prensa.getMateriasPrimas().get(0));
		
		Assert.assertEquals("El tipo de producto recibido por la máquina no se "
				+ "condice con el entregado",	testProduct2, prensa.getMateriasPrimas().get(1));
	}
	
	@Test
	public void desconectarUnaFuenteLaEliminaDeLaListaDeMateriasPrimas() {
		Producto testProduct = new Producto( "Pan", 0F);
		Maquina prensa = new Prensa(0F, 0F);
		Fuente fuente = new Fuente("pan", CANTIDAD_DE_PRUEBA, testProduct);
		
		this.cintaTransportadora.conectar(fuente, prensa);
		
		this.cintaTransportadora.desconectar((IFuente)fuente, prensa);
		
		Assert.assertEquals("La plancha tiene al menos una materia prima", 
								0, prensa.getMateriasPrimas().size());
		
		Assert.assertFalse("La prensa sigue conteniendo la materia prima", 
							prensa.getMateriasPrimas().contains(fuente.getTipoProducto()));
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
