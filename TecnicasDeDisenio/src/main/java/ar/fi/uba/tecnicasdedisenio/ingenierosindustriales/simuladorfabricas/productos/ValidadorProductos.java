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
	
	public boolean Existe(String producto){ 
		//TODO Devolver TRUE si existe en el HASH o FALSE sino
		return false;}

	public ValidadorProductos(String pathProductos) {
		super();
		XMLParser parser = new XMLParser();
	
		MapProductos = parser.LeerDoc(pathProductos);
	}

	
	
	
}
