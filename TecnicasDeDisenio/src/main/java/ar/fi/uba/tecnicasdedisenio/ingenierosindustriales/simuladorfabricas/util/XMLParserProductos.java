package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Representa a un lector de XML especifico de Productos.
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public class XMLParserProductos extends XMLParser {
	
	public XMLParserProductos(String path) {
		super(path);
	}
	
	/**
	 * Obtiene cada producto con su precio.
	 * @return
	 */
	public HashMap<String,Boolean> obtenerProductos(){
		String nombreProd;
		Boolean mp;
		HashMap<String,Boolean> mapProductos = new HashMap<String,Boolean>();
		//Parsea el documento XML y extrae los datos:
	    //extrae los datos del documento XML y los almacena en beans de la clase Descarga
	    	
	    // 1. Obtener el documento raiz
	    //Se crea un objeto Element, a partir del documento XML
		Element docEle = dom.getDocumentElement();
	    // 2. Obtener un nodelist de elementos <descarga>
	    NodeList nl = docEle.getElementsByTagName("Producto");
	    if (nl != null && nl.getLength() > 0) {
	    	for (int i = 0; i < nl.getLength(); i++) {
	    		//Se obtiene el elemento y se sacan los atributos que se quieren.
	    		Element elemento = (Element) nl.item(i);
	            //Se obtiene el nombre.
	    		nombreProd = elemento.getAttributes().getNamedItem("nombre").getNodeValue();
	            //Se obtiene el producto.
	    		mp = Boolean.valueOf(elemento.getAttributes().getNamedItem("mp").getNodeValue());
	            mapProductos.put(nombreProd, mp);  
	        }
	        
	    }
		return mapProductos;
	}
	
	/**
	 * Obtiene cada producto con su precio.
	 * @return
	 */
	public HashMap<String,Float> obtenerPrecios(){
		String nombreProd;
		Float precio;
		HashMap<String,Float> mapPrecios = new HashMap<String,Float>();
		//Parsea el documento XML y extrae los datos:
	    //extrae los datos del documento XML y los almacena en beans de la clase Descarga
	    	
	    // 1. Obtener el documento raiz
	    //Se crea un objeto Element, a partir del documento XML
		Element docEle = dom.getDocumentElement();
	    // 2. Obtener un nodelist de elementos <descarga>
	    NodeList nl = docEle.getElementsByTagName("Producto");
	    if (nl != null && nl.getLength() > 0) {
	    	for (int i = 0; i < nl.getLength(); i++) {
	    		//Se obtiene el elemento y se sacan los atributos que se quieren.
	    		Element elemento = (Element) nl.item(i);
	            //Se obtiene el nombre.
	    		nombreProd = elemento.getAttributes().getNamedItem("nombre").getNodeValue();
	            //Se obtiene el producto.
	    		precio = Float.valueOf(elemento.getAttributes().getNamedItem("precio").getNodeValue());
	            mapPrecios.put(nombreProd, precio);  
	        }
	        
	    }
		return mapPrecios;
	}
}
