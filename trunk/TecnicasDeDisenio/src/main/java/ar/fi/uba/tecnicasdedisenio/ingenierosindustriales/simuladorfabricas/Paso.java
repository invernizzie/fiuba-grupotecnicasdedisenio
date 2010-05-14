package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

public class Paso {
	
	private Maquina maquina;
	
	public Paso() {
		
	}

	public void elegirMaquina(){
		this.setMaquina(new Maquina());
		this.getMaquina().setEntrada(new Entrada());
		this.getMaquina().setSalida(new Salida());
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Maquina getMaquina() {
		return maquina;
	}
	
}
