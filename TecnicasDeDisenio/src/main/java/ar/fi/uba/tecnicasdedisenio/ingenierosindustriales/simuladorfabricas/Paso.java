package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;
import java.util.Iterator;

public class Paso {
	
	private ArrayList<TipoMaquina> maquinas;
	
	public Paso() {
		this.setMaquinas(new ArrayList<TipoMaquina>());
	}

	public void agregarTipoMaquina(TipoMaquina tipoMaquina){
		this.getMaquinas().add(tipoMaquina);
	}

	public void setMaquinas(ArrayList<TipoMaquina> maquina) {
		this.maquinas = maquina;
	}

	public ArrayList<TipoMaquina> getMaquinas() {
		return maquinas;
	}
	
	public Iterator<TipoMaquina> iterator(){
		return new IteradorPaso();
	}
	
	public class IteradorPaso implements Iterator<TipoMaquina>{
		private int indice;
		
		public IteradorPaso(){
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
