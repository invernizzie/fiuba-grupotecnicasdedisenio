package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de planchar.
 * @author santiago
 *
 */
public class Plancha extends Maquina {

	public Plancha(Float tasaDeFallos, Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(100F);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(),"Planchado",this.getTasaDeFallos());
	}

	/**
	 * Una plancha puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
	
	@Override
	public Plancha clone(){
		return new Plancha(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		return new Producto(ValidadorProductos.instancia(),"Planchado",0);
	}

}
