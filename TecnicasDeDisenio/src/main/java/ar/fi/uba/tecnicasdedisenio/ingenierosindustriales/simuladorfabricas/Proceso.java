package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;
import java.util.Iterator;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;

public class Proceso {
	
	private ArrayList<TipoMaquina> tiposDeMaquinas;
	private float costo;
	
	public Proceso(float costo) {
		this.setMaquinas(new ArrayList<TipoMaquina>());
		this.setCosto(costo);
	}

	public void agregarTipoMaquina(TipoMaquina tipoMaquina){
		this.getMaquinas().add(tipoMaquina);
	}

	public void setMaquinas(ArrayList<TipoMaquina> maquina) {
		this.tiposDeMaquinas = maquina;
	}

	public ArrayList<TipoMaquina> getMaquinas() {
		return tiposDeMaquinas;
	}
	
	public Iterator<TipoMaquina> iterator(){
		return new IteradorMaquinas();
	}
	
	public void setCosto(float costo) {
		this.costo = costo;
	}

	public float getCosto() {
		return costo;
	}
	
	public boolean habilitar(float oferta){
		return this.getCosto()<=oferta;
	}
	
	public class IteradorMaquinas implements Iterator<TipoMaquina>{
		private int indice;
		
		public IteradorMaquinas(){
			indice = 0;
		}
		
		public boolean hasNext() {
			return indice<getMaquinas().size();
		}

		public TipoMaquina next() {
			TipoMaquina tipoMaq = getMaquinas().get(indice);
			indice ++;
			return tipoMaq;
		}

		public void remove() {
			getMaquinas().remove(indice);
		
		}
	}
	
}
