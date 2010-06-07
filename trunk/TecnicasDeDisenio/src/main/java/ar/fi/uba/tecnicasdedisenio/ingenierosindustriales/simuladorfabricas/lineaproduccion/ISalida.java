package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

public interface ISalida {
	public void asignarProducto(Producto producto);
	public Producto obtenerProducto();
}
