package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.Set;

public class LineaProduccion {
	private Set<Maquina> maquinas;
	
	public void agregarMaquina(Maquina maquina) {
		//TODO verificar si es la última y guardarla.
		this.maquinas.add(maquina);
	}

	public boolean contieneMaquina(Maquina maquina) {
		return this.maquinas.contains(maquina);
	}

}
