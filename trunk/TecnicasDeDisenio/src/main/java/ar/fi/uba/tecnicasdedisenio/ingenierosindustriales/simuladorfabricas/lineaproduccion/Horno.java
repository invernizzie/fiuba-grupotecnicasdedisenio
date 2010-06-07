package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de hornear.
 * @author santiago
 *
 */

public class Horno extends Maquina {

	public Horno(Float tasaDeFallos, Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(100F);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(),"Horneado",this.getTasaDeFallos());
	}

	/**
	 * Un horno puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
	
	@Override
	public Horno clone(){
		return new Horno(this.getTasaDeFallos(), this.getTasaRotura());
	}

}
