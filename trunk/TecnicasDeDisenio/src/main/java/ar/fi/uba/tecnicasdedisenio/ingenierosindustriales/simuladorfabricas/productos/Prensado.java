package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;

/**
 * Clase que representa un producto prensado.
 * 
 * @author santiago
 */
public class Prensado extends Elemento {

	public Prensado(Elemento elementoHijo) {
		super(elementoHijo);
		this.setNombre("Prensado");
	}

}
