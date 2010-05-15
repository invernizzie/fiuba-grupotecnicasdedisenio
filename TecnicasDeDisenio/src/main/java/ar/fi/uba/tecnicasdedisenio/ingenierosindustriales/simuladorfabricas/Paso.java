package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

public class Paso {
	
	private TipoMaquina maquina;
	
	public Paso() {
		
	}

	public void elegirMaquina(){
		this.setMaquina(new TipoMaquina());
		this.getMaquina().setEntrada(new Entrada());
		this.getMaquina().setSalida(new Salida());
	}

	public void setMaquina(TipoMaquina maquina) {
		this.maquina = maquina;
	}

	public TipoMaquina getMaquina() {
		return maquina;
	}
	
}
