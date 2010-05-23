package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones;


public class EntradaInvalidaException extends Exception {

	private static final long serialVersionUID = -6634819665116077698L;
	
	public EntradaInvalidaException() {
	}

	public EntradaInvalidaException(String message) {
		super(message);
	}

	public EntradaInvalidaException(Throwable cause) {
		super(cause);
	}

	public EntradaInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

}
