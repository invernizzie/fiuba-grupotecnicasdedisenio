package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;


/**
 * Clase que representa a cualquier Producto
 * @author Diego
 *
 */
public class Producto {
	private String estado;
	private double defectuoso;

	
	public double getDefectuoso() {
		return defectuoso;
	}

	public void setDefectuoso(double defectuoso) {
		this.defectuoso = defectuoso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Producto(String estado, double defectos) {
		super();
		this.defectuoso = defectos;
		double proba= Math.random();
		if(proba > this.defectuoso) {
			this.estado = estado;
		} else{
			this.estado= "Defectuoso";
		}
	}
	
	
	
}
