package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;
import java.util.Iterator;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;

public class Proceso {
	
	private ArrayList<TipoMaquina> tiposDeMaquinas;
	private float costo;
	
	public Proceso() {
		this.setMaquinas(new ArrayList<TipoMaquina>());
	}

	public void agregarTipoMaquina(TipoMaquina tipoMaquina){
		this.getMaquinas().add(tipoMaquina);
		this.setCosto(this.getCosto()+tipoMaquina.getCosto());
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
	
	public Elemento validarLinea(ArrayList<Maquina> listaMaq){
		if(this.esProcesoIgualALinea(listaMaq))
			return new Elemento();
		else
			return null;
	}
	
	/*Se deberian pasar las maquinas de la linea.*/
	public boolean esProcesoIgualALinea(ArrayList<Maquina> listaMaq) {
		/*Se copia el array a otro para poder comparar.*/
		ArrayList<Maquina> maquinas =  new ArrayList<Maquina>(listaMaq);
		TipoMaquina tipoMaq = null;
		int i;
		
		/*Si los dos tienen tamaños distintos entonces no son el mismo proceso. */
		if(this.getMaquinas().size()!=maquinas.size()){
			return false;
		}
		
		/*Por cada tipo de maquina que tiene el proceso.*/
		Iterator<TipoMaquina> itTipos = this.iterator();
		while(itTipos.hasNext()){
			tipoMaq = itTipos.next();
			for(i=0;i<maquinas.size();i++){
				if(tipoMaq.verificarTipo(maquinas.get(i))){
					//Aca habria que comparar las entradas.
					maquinas.remove(i);
					i=maquinas.size();
				}
			}
		}
		if(maquinas.size()==0)
				return true;
		
		return false;
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
			getMaquinas().remove(--indice);
		
		}
	}
	
}
