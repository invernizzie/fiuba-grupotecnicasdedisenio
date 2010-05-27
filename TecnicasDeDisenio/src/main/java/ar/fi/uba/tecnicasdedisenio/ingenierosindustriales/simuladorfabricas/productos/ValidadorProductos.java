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
	
	public boolean Existe(String producto){ 
		//TODO Devolver TRUE si existe en el HASH o FALSE sino
		
		MapProductos = parser.LeerDoc(producto);
		return false;}

	public boolean esValido(String producto){ 
		//TODO Devolver TRUE si existe se puede crear o FALSE sino

		MapProductos = parser.LeerDoc(producto);
		return false;}

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
	
}
