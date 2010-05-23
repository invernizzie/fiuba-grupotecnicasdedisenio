package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;


/**
 * Clase que representa a cualquier Producto
 * @author Diego
 *
 */
public class Producto {
	private String estado;

	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Producto(String estado) {
		super();
		double proba= Math.random();
		if(proba > 0.2) {
			this.estado = estado;
		} else{
			this.estado= "Defectuoso";
		}
	}
	
	
	
}
