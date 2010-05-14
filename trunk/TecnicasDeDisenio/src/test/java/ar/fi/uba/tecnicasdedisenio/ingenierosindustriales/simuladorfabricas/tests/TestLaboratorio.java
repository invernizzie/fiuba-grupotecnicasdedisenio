package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.*;

import static org.junit.Assert.*;

import org.junit.Test;




public class TestLaboratorio {
	
	private Jugador jugador = new Jugador();
	private Laboratorio laboratorio = Laboratorio.getInstance();
	private Receta receta = new Receta();
	private Paso paso = new Paso();
	private Maquina maquina = new Maquina();
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
	public void testRecetaSinPasos() {
		assertTrue("Tiene una paso creado",receta.getPasos().size()==0);
		
	}
	
	@Test
	public void testRecetaConPasos() {
		
		receta.generarPaso();
		assertTrue("No tiene un paso creado",receta.getPasos().size()==1);
		
		receta.generarPaso();
		assertTrue("No tiene dos pasos creados",receta.getPasos().size()==2);
		
	}
	
	@Test
	public void testPasoSinMaquina(){
		assertNull("Tiene maquina asignada", paso.getMaquina());
	}
	
	@Test
	public void testPasoConMaquina(){
		paso.elegirMaquina();
		assertNotNull("No tiene maquina asignada", paso.getMaquina());
	}
	
	@Test
	public void testPasoGeneraMaquinaConEntradaSalida(){
		paso.elegirMaquina();
		assertNotNull("La maquina creada no tiene una entrada asignada", paso.getMaquina().getEntrada());
		assertNotNull("La maquina creada no tiene una salida asignada", paso.getMaquina().getSalida());
	}
	
	
	@Test
	public void testMaquinaSinEntradaSalida(){
		assertNull("Tiene una salida asignada", maquina.getSalida());
		assertNull("Tiene una entrada asignada", maquina.getEntrada());
	}
	
	@Test
	public void testMaquinaConEntradaSalida(){
		maquina.setSalida(new Salida());
		maquina.setEntrada(new Entrada());
		
		assertNotNull("No tiene una salida asignada", maquina.getSalida());
		assertNotNull("No tiene una entrada asignada", maquina.getEntrada());
	}
	
	@Test
	public void testMaquinaConMismaEntradaSalidaQueAsignada(){
		Salida s = new Salida();
		maquina.setSalida(s);
		
		Entrada e = new Entrada();
		maquina.setEntrada(e);
		
		assertEquals("No tiene la misma salida", maquina.getSalida(),s);
		assertEquals("No tiene la misma entrada", maquina.getEntrada(),e);
	}
	
	@Test
	public void testEntradaSalidaSinElemento(){
		assertTrue("La salida tiene un elemento",salida.getElementos().size()==0);
		assertTrue("La entrada tiene un elemento",entrada.getElementos().size()==0);
	}
	
	@Test
	public void testEntradaConElemento(){
		this.salida.asignarElemento(new Elemento());
		this.entrada.asignarElemento(new Elemento());
		
		assertTrue("La salida no tiene un elemento",salida.getElementos().size()==1);
		assertTrue("La entrada no tiene un elemento",entrada.getElementos().size()==1);
		
		
		this.salida.asignarElemento(new Elemento());
		this.entrada.asignarElemento(new Elemento());
		
		assertTrue("La salida no tiene dos elementos",salida.getElementos().size()==2);
		assertTrue("La entrada no tiene dos elementos",entrada.getElementos().size()==2);
	}
	
	
	@Test
	public void testEntradaSalidaConMismoElementoQueAsignado(){
		Elemento e = new Elemento();
		salida.asignarElemento(e);
		entrada.asignarElemento(e);
		
		assertEquals("No tiene el mismo elemento", salida.getElementos().get(salida.getElementos().size()-1),e);
		assertEquals("No tiene el mismo elemento", entrada.getElementos().get(entrada.getElementos().size()-1),e);
		assertEquals("No tienen el mismo elemento entre ellos", salida.getElementos().get(salida.getElementos().size()-1), entrada.getElementos().get(entrada.getElementos().size()-1));
	}
	
	
}
