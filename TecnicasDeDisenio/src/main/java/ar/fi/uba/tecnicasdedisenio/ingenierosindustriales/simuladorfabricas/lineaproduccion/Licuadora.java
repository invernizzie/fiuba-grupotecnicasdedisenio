package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Maquina ({@link Maquina}) encargada de licuar.
 * @author santiago
 *
 */

public class Licuadora extends Maquina {
    private static final float COSTO_LICUADORA = 100F;

    public Licuadora(final Float tasaDeFallos, final Float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(COSTO_LICUADORA);
	}
		
	@Override
	public Licuadora clone(){
		return new Licuadora(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		return new Producto(ValidadorProductos.instancia(), "Licuado", 0);
	}
	
	@Override
	protected Producto realizarProceso() {
		return new Producto(ValidadorProductos.instancia(), "Licuado", this.getTasaDeFallos());
	}

	/**
	 * Una licuadora puede operar con cualquier tipo de elemento, pero solo con uno por vez.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#validarEntrada()
	 */
	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}
}
