package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.LineaProduccion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class Fabrica {

	private List<Maquina> maquinas;
	private List<LineaProduccion> lineas;
	private List<Fuente> fuentes;

	public Fabrica(int ancho, int largo) {
		this.maquinas = new ArrayList<Maquina>();
		this.lineas = new ArrayList<LineaProduccion>();
		this.fuentes = new ArrayList<Fuente>();
	}

	public void agregarFuente(Fuente fuente) {
		this.fuentes.add(fuente);
	}
	
	public void agregarMaquina(Maquina maquina) {
		this.maquinas.add(maquina);
	}

	public void conectarMaquina(Fuente fuente, Maquina maquina) {
		if(!maquinas.contains(maquina)){
			this.agregarMaquina(maquina);
		}
		
		CintaTransportadora cinta = new CintaTransportadora();
		cinta.conectar(fuente, maquina);
		
		boolean maquinaEnLinea = false;
		for (LineaProduccion linea : lineas) {
			if(linea.contieneMaquina(maquina)){
				maquinaEnLinea = true;
			}
		}
		
		if(!maquinaEnLinea){
			LineaProduccion linea = new LineaProduccion();
			linea.agregarMaquina(maquina);
			lineas.add(linea);
		}
		
	}

	public void conectarMaquina(Maquina origen, Maquina destino) {
		if(!maquinas.contains(origen)){
			this.agregarMaquina(origen);
		}
		
		if(!maquinas.contains(destino)){
			this.agregarMaquina(destino);
		}
		
		CintaTransportadora cinta = new CintaTransportadora();
		cinta.conectar(origen, destino);
		
		boolean maquinasEnLinea = true;
		for (LineaProduccion linea : lineas) {
			if(!linea.contieneMaquina(origen)
					&& !linea.contieneMaquina(destino)){
				maquinasEnLinea = false;
			}else if(linea.contieneMaquina(origen)){
				linea.agregarMaquina(destino);
			}else if(linea.contieneMaquina(destino)){
				linea.agregarMaquina(origen);
			}
		}
		
		/*
		 * Ninguna de las dos máquinas está en alguna de las lineas, creo una 
		 * nueva linea que las contenga.
		 */
		if(!maquinasEnLinea){
			LineaProduccion linea = new LineaProduccion();
			linea.agregarMaquina(origen);
			linea.agregarMaquina(destino);
			lineas.add(linea);
		}
		
	}

}
