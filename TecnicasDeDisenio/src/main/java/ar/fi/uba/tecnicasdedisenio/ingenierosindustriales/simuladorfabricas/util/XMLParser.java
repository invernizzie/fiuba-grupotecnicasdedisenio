package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Representa a un lector de XML.
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public abstract class XMLParser {
    private String path;
	private Document dom;
    
    public XMLParser(final String path) {
    	this.path = path;
    }
    
    /**
     * Lee un XML y lo deja en memoria.
     */
    public void leerDoc() {
    	InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
        // 1. Obtener el objeto DocumentBuilderFactory, con el que se creará el documento XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try {
            // 2. Usar DocumentBuilderFactory para crear un DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 3. Parsear a partir de un archivo
            setDom(db.parse(is));
            
        } catch (ParserConfigurationException pce) {  //Capturamos los errores, si los hubiera
        	
            pce.printStackTrace();
            
        } catch (SAXException se) {
            se.printStackTrace();
            
        } catch (IOException ioe) {
        	
            ioe.printStackTrace();
            
        }
        
    }


    public Document getDom() {
        return dom;
    }

    public void setDom(final Document dom) {
        this.dom = dom;
    }
}
