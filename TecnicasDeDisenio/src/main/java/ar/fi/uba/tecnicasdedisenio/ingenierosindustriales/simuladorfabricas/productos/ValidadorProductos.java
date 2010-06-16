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
	
	private final static ValidadorProductos instancia = new ValidadorProductos();
	
	private HashMap<String,Boolean> mapProductos;
	private HashMap<String,Float> mapProductosPrecio;
	private XMLParserProductos parser;
	private final String pathXML = new String("Productos.xml");
	
	private ValidadorProductos() {
		this.mapProductos = new HashMap<String,Boolean>();
		this.mapProductosPrecio = new HashMap<String,Float>();
		this.cargarXML();
	}
	
	private XMLParserProductos getParser() {
		return parser;
	}

	private void setParser(XMLParserProductos parser) {
		this.parser = parser;
	}
	
	/**
	 * Se cargan los productos desde el XML con sus precios.
	 */
	private void cargarXML(){
		this.setParser(new XMLParserProductos(pathXML));
		this.getParser().leerDoc();
		this.mapProductos = parser.obtenerProductos();
		this.mapProductosPrecio = parser.obtenerPrecios();
	}
	
	public HashMap<String, Boolean> getMapProductos() {
		return mapProductos;
	}

	public void setMapProductos(HashMap<String, Boolean> mapProductos) {
		this.mapProductos = mapProductos;
	}

	public static ValidadorProductos instancia(){
		return instancia;
	}

	
	public boolean existe(String producto){ 
		return this.mapProductos.containsKey(producto);
		}
	
	public Float obtenerPrecioMercado(String producto){
		Float precio = this.mapProductosPrecio.get(producto);
		if (precio == null){
			precio = 0F;
		}
		return precio;
	}
	
	public String toString(){
		return mapProductos.toString();
	}
	
	public String[] getMateriasPrimas(){
		Iterator<Map.Entry<String,Boolean>> it = mapProductos.entrySet().iterator();
		HashMap<String,Boolean> mapMateriasPrimas = new HashMap<String,Boolean>();
		while (it.hasNext()) {
		Map.Entry<String,Boolean> e = (Map.Entry<String,Boolean>)it.next();
			if ((Boolean)e.getValue()){
				mapMateriasPrimas.put((String)e.getKey(), (Boolean)e.getValue());
			}	
		}
		Set<String> productosSet = mapMateriasPrimas.keySet();
        return productosSet.toArray(new String[productosSet.size()]);
	}

	public Float obtenerPrecioCompra(String producto) {	
		Float precio = this.mapProductosPrecio.get(producto);
		if (precio == null){
			precio = 0F;
		}else{
			precio /= 2;
		}
		return precio;
	}

	@Override
	public void notificar(Evento evento) {
		/*Una vez por semana se vuelven a cargar los costos de las materias primas.*/
		if (evento==Evento.COMIENZO_DE_SEMANA){
			this.cargarXML();
		}
		
	}
}
