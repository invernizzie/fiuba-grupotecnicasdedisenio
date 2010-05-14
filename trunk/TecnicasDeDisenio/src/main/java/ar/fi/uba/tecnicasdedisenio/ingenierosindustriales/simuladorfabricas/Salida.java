package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;

public class Salida implements InputOutput {

	private ArrayList<Elemento> elementos;
	
	public Salida(){ 
		this.setElementos(new ArrayList<Elemento>());
	}
	
	public void asignarElemento(Elemento e) {
		this.getElementos().add(e);
	}

	public void setElementos(ArrayList<Elemento> elementos) {
		this.elementos = elementos;
	}

	public ArrayList<Elemento> getElementos() {
		return elementos;
	}

}
