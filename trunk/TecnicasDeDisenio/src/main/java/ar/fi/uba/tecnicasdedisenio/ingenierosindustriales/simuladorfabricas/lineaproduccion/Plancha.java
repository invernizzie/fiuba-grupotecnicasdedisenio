package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Planchado;

/**
 * Maquina ({@link Maquina}) encargada de planchar un elemento ({@link Elemento}).
 * @author santiago
 *
 */
public class Plancha extends Maquina {

	@Override
	protected Elemento realizarProceso() {
		return new Planchado(this.getElementos().get(0));
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
	public Plancha clone(){
		return new Plancha();
	}

}
