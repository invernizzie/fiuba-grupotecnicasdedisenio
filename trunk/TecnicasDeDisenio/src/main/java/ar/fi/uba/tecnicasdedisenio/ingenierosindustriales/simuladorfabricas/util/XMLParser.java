package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Representa a un lector de XML.
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public abstract class XMLParser {
    private String path;
	protected Document dom; 
    
    public XMLParser(String path) {
    	this.path = path;
    }
    
    /**
     * Lee un XML y lo deja en memoria.
     */
    public void leerDoc() {
    	
        // 1. Obtener el objeto DocumentBuilderFactory, con el que se creará el documento XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try {
            // 2. Usar DocumentBuilderFactory para crear un DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 3. Parsear a partir de un archivo
            dom = db.parse(path);
            
        } catch (ParserConfigurationException pce) {  //Capturamos los errores, si los hubiera
        	
            pce.printStackTrace();
            
        } catch (SAXException se) {
            se.printStackTrace();
            
        } catch (IOException ioe) {
        	
            ioe.printStackTrace();
            
        }
        
    }   
    
		
}
