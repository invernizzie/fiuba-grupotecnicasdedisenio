package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.LinkedList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.MateriaPrimaInsuficienteException;

/**
 * Fuente de una materia prima.
 * 
 * @author santiago
 *
 */
public class Fuente {
	
	private String tipoMateria;
	private int cantidad;
	private List<CintaTransportadora> cintas;
	
	public Fuente(String tipoMateria, int cantidad){
		this.tipoMateria = tipoMateria;
		this.cantidad = cantidad;
		this.cintas = new LinkedList<CintaTransportadora>();
	}
	
	public void agregarCinta(CintaTransportadora cinta){
		this.cintas.add(cinta);
	}
	
	public void agregarMateria(int cantidad){
		this.cantidad += cantidad;
	}
	
	public void proveerMateria(CintaTransportadora cinta, int cantidad) throws MateriaPrimaInsuficienteException{
	
		if(this.cantidad < cantidad){
			throw new MateriaPrimaInsuficienteException("La fuente no posee la cantidad de " +
					"materia prima suficiente para proveer a la cinta");
		}
		
		cinta.getExtremoInicial().asignarElemento(new Elemento());
	}

}
