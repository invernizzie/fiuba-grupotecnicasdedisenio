package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;



public interface IEntrada {
	public void agregarProducto(Producto producto);
	public List<Producto> getProdcutos();
}
