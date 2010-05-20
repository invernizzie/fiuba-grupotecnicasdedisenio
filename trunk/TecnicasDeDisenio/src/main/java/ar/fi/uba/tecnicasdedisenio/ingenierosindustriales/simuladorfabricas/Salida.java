package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;


public class Salida implements ISalida {

	private Elemento elemento;
	
	public Salida(){ 
	}
	
	public Salida(Elemento e){
		this.elemento = e;
	}
	
	public void asignarElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	public Elemento obtenerElemento(){
		return this.elemento;
	}

}
