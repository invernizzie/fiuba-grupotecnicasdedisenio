package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.LaboratorioInhabilitadoException;

public class Jugador {
	
	private float dineroActual;
	
	private Laboratorio laboratorio;
	
	
	public Jugador(float dineroActual){
		this.setDineroActual(dineroActual);
		this.laboratorio = Laboratorio.getNewInstance();
	}
	
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setDineroActual(float dineroActual) {
		this.dineroActual = dineroActual;
	}

	public float getDineroActual() {
		return dineroActual;
	}
	
	public void habilitarLaboratorio(){
		this.getLaboratorio().setHabilitado(true);
	}
	
	public void invertirDineroLaboratorio(float porcentaje){
		try{
			this.getLaboratorio().invertir(this.getDineroActual()*porcentaje/100);
			this.setDineroActual(this.getDineroActual()-this.getDineroActual()*porcentaje/100);
		}
		catch(LaboratorioInhabilitadoException e){
		}
	}
	
}
