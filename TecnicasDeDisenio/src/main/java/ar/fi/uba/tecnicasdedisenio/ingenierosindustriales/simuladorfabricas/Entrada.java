package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;
import java.util.List;

public class Entrada implements InputOutput {
	
	private List<Elemento> elementos;
	
	public Entrada(){
		this.setElementos(new ArrayList<Elemento>());
	}
	
	public void asignarElemento(Elemento e) {
		this.getElementos().add(e);
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}

	public List<Elemento> getElementos() {
		return elementos;
	}

}
