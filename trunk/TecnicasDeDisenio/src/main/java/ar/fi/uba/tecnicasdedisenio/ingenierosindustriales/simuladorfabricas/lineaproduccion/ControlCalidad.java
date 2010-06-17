package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class ControlCalidad extends Maquina {

	
	public ControlCalidad(final float tasaDeFallos, final float tasaRotura) {
		super(tasaDeFallos, tasaRotura);
		this.setCostoMaquina(200F);
	}
	
	@Override
	public ControlCalidad clone() {
		return new ControlCalidad(this.getTasaDeFallos(), this.getTasaRotura());
	}

	@Override
	public Producto getTipoProducto() {
		// El control de calidad se aplica sobre una �nica m�quina y su tipo de
		// producto es el de esta última
		return this.obtenerPrecedentesFisicos().get(0).getTipoProducto();
	}

	@Override
	public List<Producto> getMateriasPrimas() {
		return this.obtenerPrecedentesFisicos().get(0).getMateriasPrimas();
	}

	@Override
	public List<Maquina> getPrecedentes() {
		return this.obtenerPrecedentesFisicos().get(0).getPrecedentes();
	}
	
	/**
	 * Si un producto está defectuoso lo desecha, sino lo pasa a la siguiente instancia
	 * sin modificaciones.
	 * @see ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina#realizarProceso()
	 */
	@Override
	protected Producto realizarProceso() {
		Producto salida = this.getProductos().get(0); 
		
		if (salida.getEstado().equals("Defectuoso")) {
			salida = new Producto(ValidadorProductos.instancia(), "Desecho", 0);
		}
		
		return salida;
	}

	@Override
	protected Boolean validarEntrada() {
		return (this.getProductos().size() == 1);
	}	
}
