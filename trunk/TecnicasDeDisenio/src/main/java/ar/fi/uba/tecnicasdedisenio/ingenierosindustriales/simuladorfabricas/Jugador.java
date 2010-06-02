package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class Jugador {
	
	private float dineroActual;
	
	private Laboratorio laboratorio;

	private Fabrica fabrica;
	
	
	public Jugador(float dineroActual){
		this.setDineroActual(dineroActual);
		//Aca deberia dejarte elegir.//
		this.laboratorio = new LaboratorioComidas();
	}
	
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setDineroActual(float dineroActual) {
		this.dineroActual = dineroActual;
	}

	public float getDineroActual() {
		return dineroActual;
	}
	
	public void habilitarLaboratorio(){
		this.getLaboratorio().setHabilitado(true);
	}
	
	public void invertirDineroLaboratorio(float porcentaje){
		try{
			this.getLaboratorio().invertir(this.getDineroActual()*porcentaje/100);
			this.setDineroActual(this.getDineroActual()-this.getDineroActual()*porcentaje/100);
		}
		catch(LaboratorioInhabilitadoException e){
		}
	}
	
	/**
	 * Construye una fábrica con las dimensiones especificadas.
	 * @param ancho
	 * @param largo
	 */
	public void construirFabrica(int ancho, int largo){
		this.fabrica = new Fabrica(ancho, largo);
	}
	
	public void agregarMaquina(Maquina maquina){
		this.fabrica.agregarMaquina(maquina);
	}
	
	public void agregarFuente(Fuente fuente){
		this.fabrica.agregarFuente(fuente);
	}
	
	public void conectarMaquina(Fuente fuente, Maquina maquina){
		this.fabrica.conectarMaquina(fuente, maquina);
	}
	
	public void conectarMaquina(Maquina origen, Maquina destino){
		this.fabrica.conectarMaquina(origen, destino);
	}
	
}
