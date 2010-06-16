package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParserProductos;

/**
 * Clase que contiene las colecciones de Productos utilizables
 * @author Diego
 *
 */
public class ValidadorProductos implements Sincronizado {
	
	private static final ValidadorProductos INSTANCIA = new ValidadorProductos();
	
	private HashMap<String, Boolean> mapProductos;
	private HashMap<String, Float> mapProductosPrecio;
	private XMLParserProductos parser;
	private final String pathXML = "Productos.xml";


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
			this.cargarXML();
		}
		
	}
	
	private ValidadorProductos() {
		this.mapProductos = new HashMap<String, Boolean>();
		this.mapProductosPrecio = new HashMap<String, Float>();
		this.cargarXML();
	}
	
	private XMLParserProductos getParser() {
		return parser;
	}

	private void setParser(final XMLParserProductos parser) {
		this.parser = parser;
	}
	
	/**
	 * Se cargan los productos desde el XML con sus precios.
	 */
	private void cargarXML() {
		this.setParser(new XMLParserProductos(pathXML));
		this.getParser().leerDoc();
		this.mapProductos = parser.obtenerProductos();
		this.mapProductosPrecio = parser.obtenerPrecios();
	}
}
