package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.LinkedList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.MateriaPrimaInsuficienteException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

/**
 * Fuente de una materia prima.
 * 
 * @author santiago
 *
 */
public class Fuente implements IFuente {
	
	private Producto producto;
	private int cantidad;
	private List<CintaTransportadora> cintas;
	private ISalida salida;
    private String nombreProducto;

    public Fuente(String nombre, int cantidad, Producto producto){
		this.producto = producto;
		this.cantidad = cantidad;
		this.cintas = new LinkedList<CintaTransportadora>();
		this.salida = new Salida();
		this.salida.asignarProducto(producto);
        this.nombreProducto = nombre;
	}
	
	public void agregarCinta(CintaTransportadora cinta){
		this.cintas.add(cinta);
	}
	
	public void agregarMateria(int cantidad){
		this.cantidad += cantidad;
	}
	
	public void proveerMateria(CintaTransportadora cinta, int cantidad) throws MateriaPrimaInsuficienteException{
	
		if (this.cantidad < cantidad){
			throw new MateriaPrimaInsuficienteException("La fuente no posee la cantidad de " +
					"materia prima suficiente para proveer a la cinta");
		}
		
		cinta.getExtremoInicial().asignarProducto(producto.clone());
	}

	public void setSalida(ISalida salida) {
		this.salida = salida;
	}

	public ISalida getSalida() {
		return salida;
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

    public String getNombreProducto() {
        return nombreProducto;
    }
}
