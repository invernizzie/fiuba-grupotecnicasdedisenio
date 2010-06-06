package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de prensar un elemento ({@link Elemento}).
 * @author santiago
 *
 */
public class Prensa extends Maquina {

	public Prensa(Float tasaDeFallos, Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
	}

	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(),"prensado",this.getTasaDeFallos());
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
	public Prensa clone(){
		return new Prensa(this.getTasaDeFallos(), this.getTasaRotura());
	}

}
