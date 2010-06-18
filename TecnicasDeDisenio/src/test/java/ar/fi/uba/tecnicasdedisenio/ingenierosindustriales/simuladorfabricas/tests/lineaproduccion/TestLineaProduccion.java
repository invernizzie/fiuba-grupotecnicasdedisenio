package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.lineaproduccion;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.LineaProduccion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Plancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class TestLineaProduccion {

	private LineaProduccion linea;
	private Fuente fuenteTrigo;
	private Fuente fuenteAgua;
	private CintaTransportadora cinta;
	private Fuente fuenteSal;
    private static final int DINERO_INICIAL = 3000;
    private static final int DEFAULT_CANTIDAD = 100;

    @Before
	public void setUp() throws Exception {
		Jugador jugador = new Jugador("Santiago", DINERO_INICIAL);
		jugador.setLaboratorio(new Laboratorio("Cocina", ""));
		this.linea = new LineaProduccion(jugador);
		this.fuenteTrigo = new Fuente("trigo", DEFAULT_CANTIDAD, 
				new Producto(ValidadorProductos.instancia(), "trigo", 0));
		this.fuenteAgua = new Fuente("agua", DEFAULT_CANTIDAD,
				new Producto(ValidadorProductos.instancia(), "agua", 0));	
		this.fuenteSal = new Fuente("agua", DEFAULT_CANTIDAD,
				new Producto(ValidadorProductos.instancia(), "sal", 0));
		this.cinta = new CintaTransportadora(0);
	}

	@Test
	public void testAgregarPrimeraMaquina() {
		Maquina maquina = new Prensa(0F, 0F);
		cinta.conectar(fuenteTrigo, maquina);
		linea.agregarMaquina(maquina);
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina));
		
		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina));
		
		Assert.assertEquals("La máquina insertada no es la última",
								linea.obtenerUltimaMaquina(), maquina);
	}
	
	@Test
	public void testAgregarDosMaquinas() {
		Maquina maquina = new Prensa(0F, 0F);
		cinta.conectar(fuenteTrigo, maquina);
		Maquina maquina2 = new Plancha(0F, 0F);
		CintaTransportadora cintaMaquinas = new CintaTransportadora(0);
		cintaMaquinas.conectar(maquina, maquina2);
		
		linea.agregarMaquina(maquina);
		linea.agregarMaquina(maquina2);
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina));
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina2));

		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina));

		Assert.assertEquals("La máquina insertada no es la última", maquina2,
								linea.obtenerUltimaMaquina());
	}
	
	@Test
	public void testAgregarTresMaquinas() {
		CintaTransportadora cintaMaquinas = new CintaTransportadora(0);
		CintaTransportadora cintaPrensaAgua = new CintaTransportadora(0);
		CintaTransportadora cintaMaquina2Maquina3 = new CintaTransportadora(0);
		Maquina maquina = new Prensa(0F, 0F);
		cinta.conectar(fuenteTrigo, maquina);
		Maquina maquina2 = new Plancha(0F, 0F);
		cintaMaquinas.conectar(maquina, maquina2);
		Maquina maquina3 = new Prensa(0F, 0F);
		cintaPrensaAgua.conectar(fuenteAgua, maquina3);
		
		cintaMaquina2Maquina3.conectar(maquina3, maquina2);
		
		linea.agregarMaquina(maquina);
		linea.agregarMaquina(maquina2);
		linea.agregarMaquina(maquina3);
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina));
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina2));

		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina3));
		
		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina));

		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina3));
		
		Assert.assertEquals("La máquina insertada no es la última", maquina2,
								linea.obtenerUltimaMaquina());
		
	}
	
	@Test
	public void testAgregarCuatroMaquinas() {
		CintaTransportadora cintaMaquinas = new CintaTransportadora(0);
		CintaTransportadora cintaPrensaAgua = new CintaTransportadora(0);
		CintaTransportadora cintaMaquina2Maquina3 = new CintaTransportadora(0);
		CintaTransportadora cintaMaquina2Maquina4 = new CintaTransportadora(0);
		CintaTransportadora cintaSalMaquina4 = new CintaTransportadora(0);
		
		Maquina maquina = new Prensa(0F, 0F);
		cinta.conectar(fuenteTrigo, maquina);
		Maquina maquina2 = new Plancha(0F, 0F);
		cintaMaquinas.conectar(maquina, maquina2);
		Maquina maquina3 = new Prensa(0F, 0F);
		cintaPrensaAgua.conectar(fuenteAgua, maquina3);
		
		cintaMaquina2Maquina3.conectar(maquina3, maquina2);
		
		Maquina maquina4 = new Prensa(0F, 0F);
		cintaSalMaquina4.conectar(fuenteSal, maquina4);
		cintaMaquina2Maquina4.conectar(maquina2, maquina4);
		
		linea.agregarMaquina(maquina);
		linea.agregarMaquina(maquina2);
		linea.agregarMaquina(maquina3);
		linea.agregarMaquina(maquina4);
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina));
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina2));

		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina3));
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina4));
		
		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina));

		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina3));
		
		Assert.assertFalse("La máquina insertada es la primera",
							linea.esPrimeraMaquina(maquina4));
		
		Assert.assertEquals("La máquina insertada no es la última", maquina4,
								linea.obtenerUltimaMaquina());
		
	}
	
	
	@Test
	public void testAgregarCuatroMaquinasDesordenadas() {
		CintaTransportadora cintaMaquinas = new CintaTransportadora(0);
		CintaTransportadora cintaPrensaAgua = new CintaTransportadora(0);
		CintaTransportadora cintaMaquina2Maquina3 = new CintaTransportadora(0);
		CintaTransportadora cintaMaquina2Maquina4 = new CintaTransportadora(0);
		CintaTransportadora cintaSalMaquina4 = new CintaTransportadora(0);
		
		Maquina maquina = new Prensa(0F, 0F);
		cinta.conectar(fuenteTrigo, maquina);
		Maquina maquina2 = new Plancha(0F, 0F);
		cintaMaquinas.conectar(maquina, maquina2);
		Maquina maquina3 = new Prensa(0F, 0F);
		cintaPrensaAgua.conectar(fuenteAgua, maquina3);
		
		cintaMaquina2Maquina3.conectar(maquina3, maquina2);
		
		Maquina maquina4 = new Prensa(0F, 0F);
		cintaSalMaquina4.conectar(fuenteSal, maquina4);
		cintaMaquina2Maquina4.conectar(maquina2, maquina4);
		
		linea.agregarMaquina(maquina4);
		linea.agregarMaquina(maquina3);
		linea.agregarMaquina(maquina2);
		linea.agregarMaquina(maquina);
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina));
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina2));

		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina3));
		
		Assert.assertTrue("Se esperaba que la linea tuviera una máquina",
							this.linea.contieneMaquina(maquina4));
		
		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina));

		Assert.assertTrue("La máquina insertada no es la primera",
							linea.esPrimeraMaquina(maquina3));
		
		Assert.assertFalse("La máquina insertada es la primera",
							linea.esPrimeraMaquina(maquina4));
		
		Assert.assertEquals("La máquina insertada no es la última", maquina4,
								linea.obtenerUltimaMaquina());
		
	}
	
	
	@Test
	public void testCostoLineaUnaMaquina() {
		Maquina maquina = new Prensa(0F, 0F);
		linea.agregarMaquina(maquina);

		Assert.assertEquals("El costo de la linea no es el mismo que el de la máquina",
								linea.getCostoLinea(), maquina.getCostoMaquina());
	}
	
	@Test
	public void testCostoLineaDosMaquinas() {
		Maquina maquina = new Prensa(0F, 0F);
		Maquina maquina2 = new Plancha(0F, 0F);
		linea.agregarMaquina(maquina);
		linea.agregarMaquina(maquina2);
		
		float costoMaquinas = maquina.getCostoMaquina() + maquina2.getCostoMaquina();

		Assert.assertEquals("El costo de la linea no es el mismo que el de la máquinas",
								linea.getCostoLinea(), costoMaquinas);
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
