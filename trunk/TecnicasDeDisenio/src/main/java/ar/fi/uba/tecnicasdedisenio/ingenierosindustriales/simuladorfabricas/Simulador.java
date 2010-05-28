/**
 * 
 */
package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.XMLParser;

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
		System.out.println("Hola");
		XMLParser parser = new XMLParser();
		parser.LeerDoc("Costos.xml");
		
	}

}
