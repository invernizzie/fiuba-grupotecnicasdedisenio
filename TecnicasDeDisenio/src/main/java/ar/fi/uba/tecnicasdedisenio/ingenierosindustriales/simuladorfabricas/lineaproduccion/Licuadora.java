package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de licuar.
 * @author santiago
 *
 */

public class Licuadora extends Maquina {

	public Licuadora(Float tasaDeFallos, Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(100F);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(),"Licuado",this.getTasaDeFallos());
	}

	/**
	 * Una licuadora puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
	
	@Override
	public Licuadora clone(){
		return new Licuadora(this.getTasaDeFallos(), this.getTasaRotura());
	}


}
