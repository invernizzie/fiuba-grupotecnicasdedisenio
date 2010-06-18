package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;

import java.util.HashMap;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParserProductos;

public class CargadorDeProductos {
	private final String pathXML = "Productos.xml";
	
	private XMLParserProductos parser;
	
	public CargadorDeProductos(){
		this.setParser(new XMLParserProductos(pathXML));
		this.getParser().leerDoc();
	}
	
	/**
	 * Devuelve los productos que le da el parser.
	 */
	public HashMap<String, Boolean> obtenerProductos() {
		return this.getParser().obtenerProductos();
	}

	/**
	 * Devuelve los precios que devuelve el parser.
	 */
	public HashMap<String, Float> obtenerPreciosProductos() {
		return this.getParser().obtenerPrecios();
	}
	
	private XMLParserProductos getParser() {
		return parser;
	}

	private void setParser(final XMLParserProductos parser) {
		this.parser = parser;
	}
	
}
