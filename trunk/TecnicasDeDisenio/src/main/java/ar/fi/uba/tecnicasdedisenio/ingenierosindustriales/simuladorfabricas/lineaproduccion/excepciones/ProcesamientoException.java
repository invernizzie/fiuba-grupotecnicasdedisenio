package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones;

public class ProcesamientoException extends Exception {

	private static final long serialVersionUID = 3961174673404196557L;

	public ProcesamientoException() {
		super();
	}

	public ProcesamientoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcesamientoException(String message) {
		super(message);
	}

	public ProcesamientoException(Throwable cause) {
		super(cause);
	}
	
	

}
