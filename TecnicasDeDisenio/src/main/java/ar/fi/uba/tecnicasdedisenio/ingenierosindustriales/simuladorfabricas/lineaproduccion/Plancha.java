package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de planchar.
 * @author santiago
 *
 */
public class Plancha extends Maquina {
    private static final float COSTO_PLANCHA = 100F;

    public Plancha(final Float tasaDeFallos, final Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(COSTO_PLANCHA);
	}
	
	@Override
	public Plancha clone() {
		return new Plancha(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		return new Producto(ValidadorProductos.instancia(), "Planchado", 0);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(), "planchado", this.getTasaDeFallos());
	}

	/**
	 * Una plancha puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
}
