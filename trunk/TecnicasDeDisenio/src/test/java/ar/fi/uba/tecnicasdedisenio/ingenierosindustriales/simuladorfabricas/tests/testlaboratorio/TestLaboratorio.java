package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.testlaboratorio;
import java.util.Iterator;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.*;

import static org.junit.Assert.*;

import org.junit.Test;




public class TestLaboratorio {
	
	private Jugador jugador = new Jugador();
	private Laboratorio laboratorio = Laboratorio.getInstance();
	private Proceso proceso = new Proceso();
	private Iterator<TipoMaquina> itMaq = proceso.iterator();
	private TipoMaquina maquina = new TipoMaquina();
	private Entrada entrada = new Entrada();
	private Salida salida = new Salida();
	
	@Test
	public void testJugadorSinLaboratorioVacio() {
		assertNull("Tiene laboratorio asignado",jugador.getLaboratorio());
	}
	
	@Test
	public void testJugadorConLaboratorioVacio() {
		jugador.crearLaboratorio();
		assertNotNull("No tiene laboratorio asignado",jugador.getLaboratorio());
	}
	
	@Test
	public void testLaboratorioSinRecetas() {
		assertTrue("Tiene una receta asignada",laboratorio.getRecetas().size()==0);
	}
	
	@Test
	public void testLaboratorioConRecetas() {
		laboratorio.generarReceta();
		assertTrue("No tiene una receta asignada",laboratorio.getRecetas().size()==1);
		
		laboratorio.generarReceta();
		assertTrue("No tiene dos recetas asignada",laboratorio.getRecetas().size()==2);
	}
	
	
	@Test
	public void testProcesoSinTipoMaquina(){
		assertFalse("Tiene una maquina asignada", itMaq.hasNext());
	}
	
	@Test
	public void testProcesoConTipoMaquina(){
		TipoMaquina tipoMaquina = new TipoMaquina(new Entrada(), new Salida());
		proceso.agregarTipoMaquina(tipoMaquina);
		assertTrue("No tiene una maquina asignada", itMaq.hasNext());
		assertEquals("No son la misma maquina", itMaq.next(),tipoMaquina);
	}
	
	@Test
	public void testTipoMaquinaSinEntradaSalida(){
		assertNull("Tiene una salida asignada", maquina.getSalida());
		assertNull("Tiene una entrada asignada", maquina.getEntrada());
	}
	
	@Test
	public void testTipoMaquinaConEntradaSalida(){
		maquina.setSalida(new Salida());
		maquina.setEntrada(new Entrada());
		
		assertNotNull("No tiene una salida asignada", maquina.getSalida());
		assertNotNull("No tiene una entrada asignada", maquina.getEntrada());
	}
	
	@Test
	public void testTipoMaquinaConMismaEntradaSalidaQueAsignada(){
		Salida s = new Salida();
		maquina.setSalida(s);
		
		Entrada e = new Entrada();
		maquina.setEntrada(e);
		
		assertEquals("No tiene la misma salida", maquina.getSalida(),s);
		assertEquals("No tiene la misma entrada", maquina.getEntrada(),e);
	}
	
	@Test
	public void testEntradaSalidaSinElemento(){
		assertNull("La salida tiene un elemento",salida.obtenerElemento());
		assertTrue("La entrada tiene un elemento",entrada.getElementos().size()==0);
	}
	
	@Test
	public void testEntradaConElemento(){
		this.salida.asignarElemento(new Elemento());
		this.entrada.agregarElemento(new Elemento());
		
		assertNotNull("La salida no tiene un elemento",salida.obtenerElemento());
		assertTrue("La entrada no tiene un elemento",entrada.getElementos().size()==1);
		
		
		this.salida.asignarElemento(new Elemento());
		this.entrada.agregarElemento(new Elemento());
		
		assertNotNull("La salida no tiene un elemento",salida.obtenerElemento());
		assertTrue("La entrada no tiene dos elementos",entrada.getElementos().size()==2);
	}
	
	
	@Test
	public void testEntradaSalidaConMismoElementoQueAsignado(){
		Elemento e = new Elemento();
		salida.asignarElemento(e);
		entrada.agregarElemento(e);
		
		assertEquals("No tiene el mismo elemento", salida.obtenerElemento(),e);
		assertEquals("No tiene el mismo elemento", entrada.getElementos().get(entrada.getElementos().size()-1),e);
		assertEquals("No tienen el mismo elemento entre ellos", salida.obtenerElemento(), entrada.getElementos().get(entrada.getElementos().size()-1));
	}
	
	
}
