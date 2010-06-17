package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;


public class Salida implements ISalida {

	private Producto producto;
	
	public Salida() { 
	}
	
	public Salida(final Producto producto) {
		this.producto = producto;
	}
	
	public void asignarProducto(final Producto producto) {
		this.producto = producto;
	}

	public Producto obtenerProducto() {
		return this.producto;
	}
}
