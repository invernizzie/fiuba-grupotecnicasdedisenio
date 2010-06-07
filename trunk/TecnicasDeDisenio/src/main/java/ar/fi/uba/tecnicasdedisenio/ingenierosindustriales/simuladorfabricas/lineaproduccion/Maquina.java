package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.EntradaInvalidaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;


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
	private Maquina siguiente;
	private Float tasaDeFallos;
	private List<CintaTransportadora> cintasEntrada;
	private CintaTransportadora cintaSalida;
	private Boolean estaRota;
	private Float tasaRotura;
	
	private Float costoMaquina;

	/** 
	 * Usar Maquina(Float)
	 */
	@Deprecated
	public Maquina(){
		this(0F, 0F);
	}
	
	public Maquina(Float tasaDeFallos, Float tasaRotura){
		this.cintasEntrada = new ArrayList<CintaTransportadora>();
		this.entrada = new Entrada();
		this.salida = new Salida();
		this.setMateriasPrimas(new ArrayList<Producto>());
		this.setPrecedentes(new ArrayList<Maquina>());
		this.setTasaDeFallos(tasaDeFallos);
		this.estaRota = false;
		this.setTasaRotura(tasaRotura);
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
	 * @param construirProductoValido 
	 */
	public final void procesar(Boolean construirProductoValido) throws EntradaInvalidaException{
		this.setProductos(getEntrada().getProdcutos());
		Boolean isEntradaValida = this.validarEntrada();
		
		if(isEntradaValida){
			Producto elementoProcesado = null;
			if(construirProductoValido){
				elementoProcesado = this.realizarProceso();
			}else{
				elementoProcesado = new Producto(ValidadorProductos.instancia(), "Desecho", 0F);
			}
			this.getSalida().asignarProducto(elementoProcesado);
			this.cintaSalida.trasladarElementos();
			this.verificarRotura();
		}else{
			throw new EntradaInvalidaException("Los elementos que ingresaron" +
												" no se corresponden con los necesarios " +
												"para que esta máquina opere");
		}
		
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<Producto> getProductos() {
		return productos;
	}

//	public void setEntrada(IEntrada entrada) {
//		this.entrada = entrada;
//	}

	public IEntrada getEntrada() {
		return entrada;
	}

//	public void setSalida(ISalida salida) {
//		this.salida = salida;
//	}

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

	public void addCintaEntrada(CintaTransportadora cintaTransportadora) {
		this.cintasEntrada.add(cintaTransportadora);
	}

	public void setCintaSalida(CintaTransportadora cintaTransportadora) {
		this.cintaSalida = cintaTransportadora;
	}

	public void removeCintaEntrada(CintaTransportadora cintaTransportadora) {
		this.cintasEntrada.remove(cintaTransportadora);
		
	}
	
	/**
	 * Repara la fábrica.
	 * @return 
	 */
	public float reparar(){
		this.estaRota = false;
		return this.costoMaquina/4;
	}
	
	/**
	 * Verifica el estado de la fabrica
	 * @return true si la fábrica está rota y no puede operar, false en otro caso.
	 */
	public Boolean estaRota(){
		return estaRota;
	}
	
	/**
	 * Verifica si la máquina se rompió luego de haber sido utilizada y cambia su 
	 * estado de manera acorde.
	 */
	protected void verificarRotura(){
		double proba= Math.random();
		if(getTasaRotura() >= proba) {
			this.estaRota = true;
		} 
	}

	public void setSiguiente(Maquina siguiente) {
		this.siguiente = siguiente;
	}

	public Maquina getSiguiente() {
		return siguiente;
	}

	public void setTasaRotura(Float tasaRotura) {
		this.tasaRotura = tasaRotura;
	}

	public Float getTasaRotura() {
		return tasaRotura;
	}

	public void setCostoMaquina(Float costoMaquina) {
		this.costoMaquina = costoMaquina;
	}

	public Float getCostoMaquina() {
		return costoMaquina;
	}
	
}
