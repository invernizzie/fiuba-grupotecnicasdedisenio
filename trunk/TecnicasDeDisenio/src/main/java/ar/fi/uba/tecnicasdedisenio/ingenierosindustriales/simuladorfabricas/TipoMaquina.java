package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

public class TipoMaquina {
	private Entrada entrada;
	private Salida salida;
	
	public TipoMaquina(){
		
	}
	
	public TipoMaquina(Entrada entrada, Salida salida){
		this.setEntrada(entrada);
		this.setSalida(salida);
	}
	
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	
	public Entrada getEntrada() {
		return entrada;
	}
	
	public void setSalida(Salida salida) {
		this.salida = salida;
	}
	
	public Salida getSalida() {
		return salida;
	}
	
	public void procesar(){
		this.salida.asignarElemento(new Elemento());
	}
}
