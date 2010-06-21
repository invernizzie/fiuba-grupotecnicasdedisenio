package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

/**
 * Maquina ({@link Maquina}) encargada de hornear.
 * @author santiago
 *
 */

public class Horno extends Maquina {
    private static final float COSTO_HORNO = 100F;

    public Horno(final Float tasaDeFallos, final Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(COSTO_HORNO);
	}
	
	@Override
	public Horno clone() {
		return new Horno(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		return new Producto("horneado", 0);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto("horneado", this.getTasaDeFallos());
	}

	/**
	 * Un horno puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
}
