package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.LinkedList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

/**
 * Fuente de una materia prima.
 * 
 * @author santiago
 *
 */
public class Contenedor {
	
	private Producto producto;
	private int cantidad;
	private List<CintaTransportadora> cintas;
	private List<Producto> productosRecibidos;
	
	public Contenedor(Producto producto){
		this.producto = producto;
		this.cintas = new LinkedList<CintaTransportadora>();
		this.productosRecibidos = new LinkedList<Producto>();
		this.cantidad = 0;
	}
	
	public void agregarCinta(CintaTransportadora cinta){
		this.cintas.add(cinta);
	}
	
	public void recibirProducto(Producto productoRecibido) {
		this.cantidad++;
		this.productosRecibidos.add(productoRecibido);
	}

	public int getCantidadProductos(){
		return cantidad;
	}
	
	public int getCantidadProductosDesecho(){
		int cantidadDesecho = 0;
		
		for (Producto producto : productosRecibidos) {
			if(producto.getEstado().equalsIgnoreCase("Desecho")){
				cantidadDesecho++;
			}
		}
		
		return cantidadDesecho;
	}
	
	public int getCantidadProductosDefectuoso(){
		int cantidadDefectuosos = 0;
		
		for (Producto producto : productosRecibidos) {
			if(producto.getEstado().equalsIgnoreCase("Defectuoso")){
				cantidadDefectuosos++;
			}
		}
		
		return cantidadDefectuosos;
	}
	
	public int getCantidadProductosValidos(){
		int cantidadValidos = 0;
		
		for (Producto producto : productosRecibidos) {
			if(producto.getEstado().equalsIgnoreCase(this.producto.getEstado())){
				cantidadValidos++;
			}
		}
		
		return cantidadValidos;
	}
	
	public int getCantidadProductosInvalidos(){
		int cantidadInvalidos = 0;
		
		for (Producto producto : productosRecibidos) {
			if(!producto.getEstado().equalsIgnoreCase(this.producto.getEstado())){
				cantidadInvalidos++;
			}
		}
		
		return cantidadInvalidos;
	}
	
	/**
	 * Devuelve un producto que representa el tipo de producto que provee esta máquina.
	 * @return
	 */
	public Producto getTipoProducto() {
		return producto;
	}

	public void removerCinta(CintaTransportadora cintaTransportadora) {
		this.cintas.remove(cintaTransportadora);
		
	}

}
