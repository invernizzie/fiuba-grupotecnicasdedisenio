package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de mezclar.
 * @author santiago
 *
 */

public class Mezcladora extends Maquina {

	public Mezcladora(Float tasaDeFallos, Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(100F);
	}
		
	@Override
	public Mezcladora clone(){
		return new Mezcladora(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		return new Producto(ValidadorProductos.instancia(),"Mezclado",0);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(),"Mezclado",this.getTasaDeFallos());
	}

	/**
	 * Una mezcladora puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
}
