package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;

public class Proceso {
	
	private ArrayList<Paso> pasos;
	
	public Proceso(){
		this.setPasos(new ArrayList<Paso>());
	}
	
	public void generarPaso(){
		this.getPasos().add( new Paso());
	}

	public void setPasos(ArrayList<Paso> pasos) {
		this.pasos = pasos;
	}

	public ArrayList<Paso> getPasos() {
		return pasos;
	}
}
