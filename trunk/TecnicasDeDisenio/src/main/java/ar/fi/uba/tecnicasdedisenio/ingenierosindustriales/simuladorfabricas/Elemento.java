package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

public class Elemento {
	
	private Elemento elementoHijo;
	private String nombre;
	
	public Elemento(Elemento elementoHijo){
		this.elementoHijo = elementoHijo;
	}

	public Elemento() {
		this.nombre = "Elemento";
	}

	public void setElementoHijo(Elemento elementoHijo) {
		this.elementoHijo = elementoHijo;
	}

	public Elemento getElementoHijo() {
		return elementoHijo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String toString(){
		
		StringBuilder nameBuilder = new StringBuilder(this.getNombre());
		
		if(elementoHijo != null){
			nameBuilder.append(elementoHijo.toString());
		}
		
		return nameBuilder.toString();
	}
	
}
