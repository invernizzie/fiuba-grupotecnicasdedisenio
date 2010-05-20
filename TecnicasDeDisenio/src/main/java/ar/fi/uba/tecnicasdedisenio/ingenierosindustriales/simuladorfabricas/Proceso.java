package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;
import java.util.Iterator;

public class Proceso {
	
	private ArrayList<TipoMaquina> tiposDeMaquinas;
	
	public Proceso() {
		this.setMaquinas(new ArrayList<TipoMaquina>());
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
		return new IteradorProceso();
	}
	
	public class IteradorProceso implements Iterator<TipoMaquina>{
		private int indice;
		
		public IteradorProceso(){
			indice = 0;
		}
		
		public boolean hasNext() {
			return indice<getMaquinas().size();
		}

		public TipoMaquina next() {
			return getMaquinas().get(indice);
		}

		public void remove() {
			getMaquinas().remove(indice);
		
		}
	}
	
}
