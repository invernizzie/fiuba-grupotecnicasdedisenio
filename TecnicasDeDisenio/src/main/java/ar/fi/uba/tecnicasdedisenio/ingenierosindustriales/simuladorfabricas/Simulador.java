/**
 * 
 */
package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.*;

/**
 * @author santiago
 *
 */
public class Simulador {

	public String getTexto(){
		return "Un texto";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XMLParserProductos parser = new XMLParserProductos("D:\\Gustavo\\Facultad\\2010\\Tecnicas\\TP\\TecnicasDeDisenio\\src\\main\\resources\\Productos.xml");
		parser.leerDoc();
		parser.obtenerProductos();
		System.out.println("Hola");
	}
	
}
