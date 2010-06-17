package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones;

public class MateriaPrimaInsuficienteException extends Exception {

	public MateriaPrimaInsuficienteException() { }

	public MateriaPrimaInsuficienteException(final String message) {
		super(message);
	}

	public MateriaPrimaInsuficienteException(Throwable cause) {
		super(cause);
	}

	public MateriaPrimaInsuficienteException(String message, Throwable cause) {
		super(message, cause);
	}

}
