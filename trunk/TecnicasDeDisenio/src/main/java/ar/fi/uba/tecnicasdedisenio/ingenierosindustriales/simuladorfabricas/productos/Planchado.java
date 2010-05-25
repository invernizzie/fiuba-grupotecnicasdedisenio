package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;

/**
 * Clase que representa un producto planchado.
 * 
 * @author santiago
 */
public class Planchado extends Elemento {

	public Planchado(Elemento elementoHijo) {
		super(elementoHijo);
		this.setNombre("Planchado");
	}

}
