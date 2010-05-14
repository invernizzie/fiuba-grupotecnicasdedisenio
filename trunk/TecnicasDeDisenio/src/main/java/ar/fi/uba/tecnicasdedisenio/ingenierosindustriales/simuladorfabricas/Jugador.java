package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

public class Jugador {

	private Laboratorio laboratorio;
	
	public Jugador() {}
	
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void crearLaboratorio(){
		this.setLaboratorio(Laboratorio.getInstance());
	}
}
