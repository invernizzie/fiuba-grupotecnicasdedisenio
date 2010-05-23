package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Prensado;

/**
 * Maquina ({@link Maquina}) encargada de prensar un elemento ({@link Elemento}).
 * @author santiago
 *
 */
public class Prensa extends Maquina {

	@Override
	protected Elemento realizarProceso() {
		return new Prensado(this.getElementos().get(0));
	}

	/**
	 * Una prensa puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getElementos().size() == 1);
	}
	
	@Override
	public Prensa clone(){
		return new Prensa();
	}

}
