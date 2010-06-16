package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de prensar.
 * @author santiago
 *
 */
public class Prensa extends Maquina {

    private static final float COSTO_PRENSA = 150F;

    public Prensa(final Float tasaDeFallos, final Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(COSTO_PRENSA);
	}
	
	@Override
	public Prensa clone() {
		return new Prensa(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		return new Producto(ValidadorProductos.instancia(), "prensado", 0);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(), "prensado", this.getTasaDeFallos());
	}

	/**
	 * Una prensa puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
}
