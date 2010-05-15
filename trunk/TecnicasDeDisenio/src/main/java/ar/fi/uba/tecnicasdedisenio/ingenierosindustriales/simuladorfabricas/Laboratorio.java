package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;

public class Laboratorio {
	
	private ArrayList<Proceso> recetas;
	private static Laboratorio laboratorio = new Laboratorio();
	
	private Laboratorio(){
		this.setRecetas(new ArrayList<Proceso>());
	}
	
	public static Laboratorio getInstance(){
		return laboratorio;
	}

	public void setRecetas(ArrayList<Proceso> recetas) {
		this.recetas = recetas;
	}

	public ArrayList<Proceso> getRecetas() {
		return recetas;
	}
	
	public void generarReceta(){
		this.getRecetas().add(new Proceso());
	}
}
