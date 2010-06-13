package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParser;

/**
 * Clase que contiene las colecciones de Productos utilizables
 * @author Diego
 *
 */
public class ValidadorProductos {
	
	private final static ValidadorProductos instancia = new ValidadorProductos();
	
	private HashMap<String,Boolean> mapProductos;
	private HashMap<String,Float> mapProductosPrecio;
	private XMLParser parser;
	
	private ValidadorProductos() {
		this.mapProductos = new HashMap<String,Boolean>();
		this.mapProductosPrecio = new HashMap<String,Float>();
		this.cargar();
	}
	
	public HashMap<String, Boolean> getMapProductos() {
		return mapProductos;
	}

	public void setMapProductos(HashMap<String, Boolean> mapProductos) {
		this.mapProductos = mapProductos;
	}

	public XMLParser getParser() {
		return parser;
	}

	public void setParser(XMLParser parser) {
		this.parser = parser;
	}

	public static ValidadorProductos instancia(){
		return instancia;
	}

	// TODO Aca se debería hacer la carga con el parser XML
	public void cargar(){
		this.mapProductos.put("harina", true);
		this.mapProductos.put("agua", true);
		this.mapProductos.put("trigo", true);
		this.mapProductos.put("pan", true);
		this.mapProductos.put("centeno", true);
		this.mapProductos.put("maiz", true);
		this.mapProductos.put("sal", true);
		this.mapProductos.put("azucar", true);
		this.mapProductos.put("vainilla", true);
		this.mapProductos.put("edulcorante", true);
		this.mapProductos.put("miel", true);
		this.mapProductos.put("prensado", false);
		this.mapProductos.put("planchado", false);
		
		this.mapProductosPrecio.put("harina", 1F);
		this.mapProductosPrecio.put("agua", 2F);
		this.mapProductosPrecio.put("trigo", 3F);
		this.mapProductosPrecio.put("pan", 5F);
		this.mapProductosPrecio.put("centeno", 3F);
		this.mapProductosPrecio.put("maiz", 7F);
		this.mapProductosPrecio.put("sal", 2F);
		this.mapProductosPrecio.put("azucar", 10F);
		this.mapProductosPrecio.put("vainilla", 9F);
		this.mapProductosPrecio.put("edulcorante", 11F);
		this.mapProductosPrecio.put("prensado", 1F);
		this.mapProductosPrecio.put("planchado", 3F);
		this.mapProductosPrecio.put("miel", 15F);
	}
	
	public boolean existe(String producto){ 
		return this.mapProductos.containsKey(producto);
		}
	
	public Float obtenerPrecioMercado(String producto){
		Float precio = this.mapProductosPrecio.get(producto);
		if(precio == null){
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
			if((Boolean)e.getValue()){
				mapMateriasPrimas.put((String)e.getKey(), (Boolean)e.getValue());
			}	
		}
		Set<String> productosSet = mapMateriasPrimas.keySet();
        return productosSet.toArray(new String[productosSet.size()]);
	}

	public Float obtenerPrecioCompra(String producto) {	
		Float precio = this.mapProductosPrecio.get(producto);
		if(precio == null){
			precio = 0F;
		}else{
			precio /= 2;
		}
		return precio;
	}
}
