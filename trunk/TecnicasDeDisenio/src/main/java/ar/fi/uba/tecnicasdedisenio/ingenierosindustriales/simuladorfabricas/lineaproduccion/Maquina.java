package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.EntradaInvalidaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;


/**
 * Abstracción que representa una entidad encargada de realizar un procesamiento sobre 
 * una serie de materiales ({@link Elemento}).
 * @author santiago
 *
 */
public abstract class Maquina implements Cloneable  {
	
	private IEntrada entrada;
	private ISalida salida;
	private List<Producto> productos;
	private List<Producto> materiasPrimas;
	private List<Maquina> precedentes;
	private Float tasaDeFallos;

	public Maquina(){
		this(0F);
	}
	
	public Maquina(Float tasaDeFallos){
		this.setMateriasPrimas(new ArrayList<Producto>());
		this.setPrecedentes(new ArrayList<Maquina>());
		this.setTasaDeFallos(tasaDeFallos);
	}
	
	/**
	 * Verifica que ingresen los elementos correctos para realizar la tarea que 
	 * le corresponde a cada máquina.
	 * La validación puede ser por tipo, cantidad, etc.
	 * @return true si y solo si los elementos son los que precisa la mÃ¡quina 
	 * para operar, false en otro caso.
	 */
	protected abstract Boolean validarEntrada();
	
	/**
	 * Método que realiza el procesamiento de los elementos, devuelve el elemento
	 * resultado de procesar los elementos de entrada.
	 * 
	 * @return
	 */
	protected abstract Producto realizarProceso();
	
	/**
	 * Template para la realización del proceso, las subclases deberán implementar
	 * el método realizarProceso que es el que realiza efectivemente la tarea y 
	 * validarEntrada que verifica si los datos que los elementos que ingresan
	 * son válidos para la tarea a realizar.
	 * 
	 * Este método toma los elementos de la entrada, los procesa y deposita el
	 * resultado en la salida.
	 */
	public final void procesar() throws EntradaInvalidaException{
		this.setProductos(getEntrada().getProdcutos());
		Boolean isEntradaValida = this.validarEntrada();
		
		if(isEntradaValida){
			Producto elementoProcesado = this.realizarProceso();
			this.getSalida().asignarProducto(elementoProcesado); 
		}else{
			throw new EntradaInvalidaException("Los elementos que ingresaron" +
					" no se corresponden con los necesarios para que esta mÃ¡quina opere");
		}
		
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<Producto> getProductos() {
		return productos;
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
	
	public void setMateriasPrimas(List<Producto> materiasPrimas) {
		this.materiasPrimas = materiasPrimas;
	}

	public List<Producto> getMateriasPrimas() {
		return materiasPrimas;
	}

	public void setPrecedentes(List<Maquina> precedentes) {
		this.precedentes = precedentes;
	}

	public List<Maquina> getPrecedentes() {
		return precedentes;
	}

	public void addPrecedente(Maquina precedente) {
		if(this.precedentes == null){
			this.precedentes = new ArrayList<Maquina>();
		}
		this.precedentes.add(precedente);
		
	}

	public void addMateriaPrima(Producto tipoProducto) {
		if(this.materiasPrimas == null){
			this.materiasPrimas = new ArrayList<Producto>();
		}
		this.materiasPrimas.add(tipoProducto);
		
	}
	
	public void removePrecedente(Maquina precedente) {
		this.precedentes.remove(precedente);
		
	}

	public void removeMateriaPrima(Producto tipoProducto) {
		this.materiasPrimas.remove(tipoProducto);
		
	}

	public void setTasaDeFallos(Float tasaDeFallos) {
		this.tasaDeFallos = tasaDeFallos;
	}

	public Float getTasaDeFallos() {
		return tasaDeFallos;
	}
	
}
