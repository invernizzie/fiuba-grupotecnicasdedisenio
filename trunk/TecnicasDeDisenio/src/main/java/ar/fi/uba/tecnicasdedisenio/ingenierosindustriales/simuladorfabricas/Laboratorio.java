package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;

public class Laboratorio {
	
	private ArrayList<Receta> recetas;
	private static Laboratorio laboratorio = new Laboratorio();
	
	private Laboratorio(){
		this.setRecetas(new ArrayList<Receta>());
	}
	
	public static Laboratorio getInstance(){
		return laboratorio;
	}

	public void setRecetas(ArrayList<Receta> recetas) {
		this.recetas = recetas;
	}

	public ArrayList<Receta> getRecetas() {
		return recetas;
	}
	
	public void generarReceta(){
		this.getRecetas().add(new Receta());
	}
}
