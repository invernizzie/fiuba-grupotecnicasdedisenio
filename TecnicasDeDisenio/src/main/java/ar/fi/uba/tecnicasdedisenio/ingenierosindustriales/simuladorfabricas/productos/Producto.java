package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

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
		if(!this.estado.equals("Defectuoso") || !this.estado.equals("Desecho"))
			this.estado = estado;
	}

	public Producto(ValidadorProductos val, String estado, double tasa_falla) {
		super();
		if(val.esValido(estado)){
			double proba= Math.random();
			if(tasa_falla < proba) {
				this.estado = estado;
			} else{
				this.estado= "Defectuoso";
			}
		}
		else {
			this.estado= "Desecho";
		}
			
	}
	
	public boolean equals(Producto prod){
		return this.estado.equals(prod.estado);
	}
	
	
}
