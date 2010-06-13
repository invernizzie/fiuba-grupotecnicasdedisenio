package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;
import java.util.HashMap;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParser;

/**
 * Clase que contiene las colecciones de Productos utilizables
 * @author Diego
 *
 */
public class ValidadorProductos {
	
	private final static ValidadorProductos instancia = new ValidadorProductos();
	
	private HashMap<String,String> mapProductos;
	private HashMap<String,Float> mapProductosPrecio;
	private XMLParser parser;
	
	private ValidadorProductos() {
		this.mapProductos = new HashMap<String,String>();
		this.mapProductosPrecio = new HashMap<String,Float>();
		this.Cargar();
	}
	
	public HashMap<String, String> getMapProductos() {
		return mapProductos;
	}

	public void setMapProductos(HashMap<String, String> mapProductos) {
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
	public void Cargar(){
		this.mapProductos.put("harina", "habilitado");
		this.mapProductos.put("agua", "habilitado");
		this.mapProductos.put("trigo", "habilitado");
		this.mapProductos.put("pan", "habilitado");
		this.mapProductos.put("centeno", "habilitado");
		this.mapProductos.put("maiz", "habilitado");
		this.mapProductos.put("sal", "habilitado");
		this.mapProductos.put("azucar", "habilitado");
		this.mapProductos.put("vainilla", "habilitado");
		this.mapProductos.put("edulcorante", "habilitado");
		this.mapProductos.put("prensado", "habilitado");
		this.mapProductos.put("planchado", "habilitado");
		this.mapProductos.put("miel", "no habilitado");
		
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
	
	public boolean Existe(String producto){ 
		return this.mapProductos.containsKey(producto);
		}

	public boolean esValido(String producto){ 
		if(!this.mapProductos.containsKey(producto))
			return false;		
		if(this.mapProductos.get(producto).equals("habilitado"))
				return true;
		return false;
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
	
	public String[] getAll(){
		int cantidad = mapProductos.size();
		String productos[] = new String[cantidad]; 
		productos = mapProductos.toString().substring(1).split(",", 0); 
		for(int i=0; i<cantidad; i++){
			productos[i] = productos[i].substring(0, productos[i].indexOf('='));
		}
		return productos;
	}
}
