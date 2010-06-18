package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;

/**
 * Clase que contiene las colecciones de Productos utilizables
 * @author Diego
 *
 */
public class ValidadorProductos implements Sincronizado {
	
	private static final ValidadorProductos INSTANCIA = new ValidadorProductos();
	
	private HashMap<String, Boolean> mapProductos;
	private HashMap<String, Float> mapProductosPrecio;
	private CargadorDeProductos cargador;

    public HashMap<String, Boolean> getMapProductos() {
		return mapProductos;
	}

	public void setMapProductos(final HashMap<String, Boolean> mapProductos) {
		this.mapProductos = mapProductos;
	}

	public static ValidadorProductos instancia(){
		return INSTANCIA;
	}

	
	public boolean existe(final String producto) {
		return this.mapProductos.containsKey(producto);
    }
	
	/**
	 * Obtiene el precio con el cual se venderá el producto en el mercado.
	 * @param producto
	 * @return
	 */
	public Float obtenerPrecioMercado(final String producto) {
		Float precio = this.mapProductosPrecio.get(producto);
		if (precio == null) {
			precio = 0F;
		}
		return precio;
	}
	
	public String toString() {
		return mapProductos.toString();
	}
	
	/**
	 * Devuelve todas las que sean materias primas de los productos existentes.
	 * @return
	 */
	public String[] getMateriasPrimas() {
		Iterator<Map.Entry<String, Boolean>> it = mapProductos.entrySet().iterator();
		HashMap<String, Boolean> mapMateriasPrimas = new HashMap<String, Boolean>();
		while (it.hasNext()) {
		Map.Entry<String, Boolean> e = it.next();
			if (e.getValue()) {
				mapMateriasPrimas.put(e.getKey(), e.getValue());
			}	
		}
		Set<String> productosSet = mapMateriasPrimas.keySet();
        return productosSet.toArray(new String[productosSet.size()]);
	}
	
	/**
	 * Obtiene el precio de compra de un producto.
	 * @param producto
	 * @return
	 */
	public Float obtenerPrecioCompra(final String producto) {
		Float precio = this.mapProductosPrecio.get(producto);
		if (precio == null) {
			precio = 0F;
		} else {
			precio /= 2;
		}
		return precio;
	}

	@Override
	public void notificar(final Evento evento) {
		/*Una vez por semana se vuelven a cargar los costos de las materias primas.*/
		if (evento == Evento.COMIENZO_DE_SEMANA) {
			this.mapProductos = obtenerProductos();
			this.mapProductosPrecio = obtenerPreciosProductos();
		}
		
	}
	
	private ValidadorProductos() {
		this.setCargador(new CargadorDeProductos());
		this.mapProductos = obtenerProductos();
		this.mapProductosPrecio = obtenerPreciosProductos();
		
	}
	
	/**
	 * Devuelve los productos que devuelve el cargador.
	 */
	private HashMap<String, Boolean> obtenerProductos() {
		return this.getCargador().obtenerProductos();
	}

	/**
	 * Devuelve los precios que devuelve el cargador.
	 */
	private HashMap<String, Float> obtenerPreciosProductos() {
		return this.getCargador().obtenerPreciosProductos();
	}
	
	private void setCargador(CargadorDeProductos cargador) {
		this.cargador = cargador;
	}

	private CargadorDeProductos getCargador() {
		return cargador;
	}
}
