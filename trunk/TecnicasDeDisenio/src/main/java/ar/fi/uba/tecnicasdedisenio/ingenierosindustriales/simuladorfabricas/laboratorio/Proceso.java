package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio;

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
		return this.getMaquinaFinal().equals(maquinaFinalLinea);
	}
	
}
