package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.EntradaInvalidaException;


/**
 * Abstracción que representa una entidad encargada de realizar un procesamiento sobre 
 * una serie de materiales ({@link Elemento}).
 * @author santiago
 *
 */
public abstract class Maquina implements Cloneable  {
	
	private IEntrada entrada;
	private ISalida salida;
	private List<Elemento> elementos;

	/**
	 * Verifica que ingresen los elementos correctos para realizar la tarea que 
	 * le corresponde a cada máquina.
	 * La validación puede ser por tipo, cantidad, etc.
	 * @return true si y solo si los elementos son los que precisa la máquina 
	 * para operar, false en otro caso.
	 */
	protected abstract Boolean validarEntrada();
	
	/**
	 * Método que realiza el procesamiento de los elementos, devuelve el elemento
	 * resultado de procesar los elementos de entrada.
	 * 
	 * @return
	 */
	protected abstract Elemento realizarProceso();
	
	/**
	 * Template para la realización del proceso, las subclases deberán implementar
	 * el método realizarProceso que es el que realiza efectivemente la tarea.
	 * 
	 * Este método toma los elementos de la entrada, los procesa y deposita el
	 * resultado en la salida.
	 */
	public final void procesar() throws EntradaInvalidaException{
		this.setElementos(getEntrada().getElementos());
		Boolean isEntradaValida = this.validarEntrada();
		
		if(isEntradaValida){
			Elemento elementoProcesado = this.realizarProceso();
			this.getSalida().asignarElemento(elementoProcesado); 
		}else{
			throw new EntradaInvalidaException("Los elementos que ingresaron" +
					" no se corresponden con los necesarios para que esta máquina opere");
		}
		
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}

	public List<Elemento> getElementos() {
		return elementos;
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
}
