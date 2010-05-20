package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

public class TipoMaquina {
	private IEntrada entrada;
	private ISalida salida;
	
	public TipoMaquina(){
		
	}
	
	public TipoMaquina(IEntrada entrada, ISalida salida){
		this.setEntrada(entrada);
		this.setSalida(salida);
	}
	
	public void setEntrada(IEntrada entrada) {
		this.entrada = entrada;
	}
	
	public IEntrada getEntrada() {
		return entrada;
	}
	
	public void setSalida(ISalida salida) {
		this.salida = salida;
	}
	
	public ISalida getSalida() {
		return salida;
	}
	
	public void procesar(){
		
	}
}
