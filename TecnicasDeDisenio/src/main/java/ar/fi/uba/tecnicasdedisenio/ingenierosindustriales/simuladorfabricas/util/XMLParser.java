package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 
 * @author Diego
 *
 */
public class XMLParser {
	public HashMap<String,String> LeerDoc(String path){
		SAXBuilder builder = new SAXBuilder();
		try {
			// UNO
			//InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
			// DOS
			//InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			// TRES
			ClassLoader cl = this.getClass().getClassLoader();
            java.net.URL url = cl.getResource(path);
            InputStream is = url.openStream();
			Document document = builder.build(is);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		
	}
}
