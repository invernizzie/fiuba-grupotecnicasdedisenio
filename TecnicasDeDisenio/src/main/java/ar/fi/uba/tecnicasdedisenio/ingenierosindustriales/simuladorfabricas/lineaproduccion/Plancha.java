package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de planchar un elemento ({@link Elemento}).
 * @author santiago
 *
 */
public class Plancha extends Maquina {

	@Override
	protected Producto realizarProceso() {
		ValidadorProductos val = new ValidadorProductos();
		val.Cargar();
		return new Producto(val,"Planchado",this.getTasaDeFallos());
	}

	/**
	 * Una prensa puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
	
	@Override
	public Plancha clone(){
		return new Plancha();
	}

}
