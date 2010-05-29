package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;
import java.util.HashMap;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.*;

/**
 * Clase que contiene las colecciones de Productos utilizables
 * @author Diego
 *
 */
public class ValidadorProductos {
	
	private HashMap<String,String> MapProductos;
	private XMLParser parser;

	public HashMap<String, String> getMapProductos() {
		return MapProductos;
	}

	public void setMapProductos(HashMap<String, String> mapProductos) {
		MapProductos = mapProductos;
	}

	public XMLParser getParser() {
		return parser;
	}

	public void setParser(XMLParser parser) {
		this.parser = parser;
	}
	
	
	public ValidadorProductos() {
		super();
		this.MapProductos = new HashMap<String,String>();
	}

	// TODO Aca se debería hacer la carga con el parser XML
	public void Cargar(){
		this.MapProductos.put("harina", "habilitado");
		this.MapProductos.put("agua", "habilitado");
		this.MapProductos.put("trigo", "habilitado");
		this.MapProductos.put("pan", "habilitado");
		this.MapProductos.put("centeno", "habilitado");
		this.MapProductos.put("maiz", "habilitado");
		this.MapProductos.put("sal", "habilitado");
		this.MapProductos.put("azucar", "no habilitado");
		this.MapProductos.put("miel", "no habilitado");
		this.MapProductos.put("edulcorante", "no habilitado");
	}
	
	public boolean Existe(String producto){ 
		return this.MapProductos.containsKey(producto);
		}

	public boolean esValido(String producto){ 
		if(!this.MapProductos.containsKey(producto))
			return false;		
		if(this.MapProductos.get(producto).equals("habilitado"))
				return true;
		return false;
		}
	
}
