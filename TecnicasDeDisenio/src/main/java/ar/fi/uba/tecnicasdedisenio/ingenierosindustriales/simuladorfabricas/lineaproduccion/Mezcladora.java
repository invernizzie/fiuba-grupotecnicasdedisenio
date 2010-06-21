package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

/**
 * Maquina ({@link Maquina}) encargada de mezclar.
 * @author santiago
 *
 */

public class Mezcladora extends Maquina {
    private static final float COSTO_MEZCLADORA = 100F;

    public Mezcladora(final Float tasaDeFallos, final Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(COSTO_MEZCLADORA);
	}
		
	@Override
	public Mezcladora clone() {
		return new Mezcladora(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		return new Producto("mezclado", 0);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto("mezclado", this.getTasaDeFallos());
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
