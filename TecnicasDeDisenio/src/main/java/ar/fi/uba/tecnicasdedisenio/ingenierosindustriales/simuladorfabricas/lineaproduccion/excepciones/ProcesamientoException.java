package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones;

public class ProcesamientoException extends Exception {

	private static final long serialVersionUID = 5165862492529076192L;

	public ProcesamientoException() {
		super();
	}

	public ProcesamientoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ProcesamientoException(final String message) {
		super(message);
	}

	public ProcesamientoException(final Throwable cause) {
		super(cause);
	}
	
	

}
