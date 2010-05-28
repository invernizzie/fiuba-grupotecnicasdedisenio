package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;

public class Proceso {
	
	TipoMaquina tipoMaquinaFinal;
	private float costo;
	
	public Proceso(float costo) {
		this.setCosto(costo);
	}

	public void setMaquinaFinal(TipoMaquina maquina) {
		this.tipoMaquinaFinal = maquina;
	}

	public TipoMaquina getMaquinaFinal() {
		return tipoMaquinaFinal;
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
		
	public boolean esProcesoIgualALinea(Maquina maquinaFinalLinea) {
		return this.getMaquinaFinal().comparar(maquinaFinalLinea);
		
	/*
		if(this.getMaquinas().size()!=maquinas.size()){
			return false;
		}
		
		
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
		
		return false;*/
	}
	
}
