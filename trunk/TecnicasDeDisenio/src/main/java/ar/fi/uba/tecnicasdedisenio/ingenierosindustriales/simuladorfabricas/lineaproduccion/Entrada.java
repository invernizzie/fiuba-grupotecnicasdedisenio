package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

public class Entrada implements IEntrada {
	
	private List<Producto> productos;
	
	public Entrada(){
		this.setElementos(new ArrayList<Producto>());
	}
	
	public void agregarProducto(Producto producto) {
		this.getProdcutos().add(producto);
	}

	public void setElementos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<Producto> getProdcutos() {
		return productos;
	}

}
