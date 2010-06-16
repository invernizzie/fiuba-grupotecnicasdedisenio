package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.EntradaInvalidaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;


/**
 * Abstracci�n que representa una entidad encargada de realizar un procesamiento sobre 
 * una serie de materiales ({@link Producto}).
 * @author santiago
 *
 */
public abstract class Maquina implements Cloneable, IFuente  {
	
	private IEntrada entrada;
	private ISalida salida;
	private List<Producto> productos;
	private List<Producto> materiasPrimas;
	private List<Fuente> fuentes;
	private List<Maquina> precedentes;
	private Maquina siguiente;
	private Float tasaDeFallos;
	private List<CintaTransportadora> cintasEntrada;
	private CintaTransportadora cintaSalida;
	private Boolean estaRota;
	private Float tasaRotura;
	
	private Float costoMaquina;
	private boolean conectadaAContenedor;

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
		this.fuentes = new ArrayList<Fuente>();
		this.setPrecedentes(new ArrayList<Maquina>());
		this.setTasaDeFallos(tasaDeFallos);
		this.estaRota = false;
		this.setTasaRotura(tasaRotura);
		this.setConectadaAContenedor(false);
	}
	
	/**
	 * Verifica que ingresen los elementos correctos para realizar la tarea que 
	 * le corresponde a cada m�quina.
	 * La validaci�n puede ser por tipo, cantidad, etc.
	 * @return true si y solo si los elementos son los que precisa la máquina 
	 * para operar, false en otro caso.
	 */
	protected abstract Boolean validarEntrada();
	
	/**
	 * M�todo que realiza el procesamiento de los elementos, devuelve el elemento
	 * resultado de procesar los elementos de entrada.
	 * 
	 * @return
	 */
	protected abstract Producto realizarProceso();
	
	/**
	 * Template para la realizaci�n del proceso, las subclases deber�n implementar
	 * el m�todo realizarProceso que es el que realiza efectivemente la tarea y 
	 * validarEntrada que verifica si los datos que los elementos que ingresan
	 * son v�lidos para la tarea a realizar.
	 * 
	 * Este m�todo toma los elementos de la entrada, los procesa y deposita el
	 * resultado en la salida.
	 * @param construirProductoValido 
	 */
	public final Producto procesar(Boolean construirProductoValido) throws EntradaInvalidaException{
		Producto elementoProcesado = null;
		if (!this.estaRota){
			obtenerProductosEntrada();
			
			Boolean isEntradaValida = this.validarEntrada();
			
			if (isEntradaValida){
				if (construirProductoValido){
					elementoProcesado = this.realizarProceso();
				}else{
					elementoProcesado = new Producto(ValidadorProductos.instancia(), "Desecho", 0F);
				}
				this.getProductos().clear();
				this.getEntrada().getProdcutos().clear();
				this.getSalida().asignarProducto(elementoProcesado);
				this.cintaSalida.trasladarElementos();
				this.verificarRotura();
			}else{
				throw new EntradaInvalidaException("Los elementos que ingresaron" +
													" no se corresponden con los necesarios " +
													"para que esta m�quina opere");
			}
		}else{
			elementoProcesado = new Producto(ValidadorProductos.instancia(), "Desecho", 0F);
		}
		return elementoProcesado;
	}
	
	/**
	 * Verifica si la m�quina se rompi� luego de haber sido utilizada y cambia su 
	 * estado de manera acorde.
	 */
	protected void verificarRotura(){
		double proba= Math.random();
		if (getTasaRotura() >= proba) {
			this.estaRota = true;
		} 
	}
	
	private void obtenerProductosEntrada() {
		this.setProductos(getEntrada().getProdcutos());
		
		for (Producto producto : materiasPrimas) {
			if (!this.productos.contains(producto)){
				this.productos.add(producto);
			}
		}
	}
	
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public IEntrada getEntrada() {
		return entrada;
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

    public final List<Maquina> obtenerPrecedentesFisicos() {
        return precedentes;
    }

	public void addPrecedente(Maquina precedente) {
		if (this.precedentes == null){
			this.precedentes = new ArrayList<Maquina>();
		}
		this.precedentes.add(precedente);
		
	}

	public void addMateriaPrima(Producto tipoProducto) {
		if (this.materiasPrimas == null){
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

    public CintaTransportadora getCintaSalida() {
        return cintaSalida;
    }

	public void removeCintaEntrada(CintaTransportadora cintaTransportadora) {
		this.cintasEntrada.remove(cintaTransportadora);
		
	}
	
	/**
	 * Repara la f�brica.
	 * @return 
	 */
	public float reparar(){
		this.estaRota = false;
		return this.costoMaquina/4;
	}
	
	/**
	 * Verifica el estado de la fabrica
	 * @return true si la f�brica est� rota y no puede operar, false en otro caso.
	 */
	public Boolean estaRota(){
		return estaRota;
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

	/**
	 * Devuelve el costo de venta de la m�quina. Un 50% del costo si la m�quina 
	 * no se encuentra rota, 0 en caso contrario.
	 * @return
	 */
	public Float obtenerCostoVenta(){
		Float costoVenta = 0F;
		if (!this.estaRota()){
			costoVenta = this.getCostoMaquina()/2;
		}
		return costoVenta;
	}
	
	/**
	 * Devuelve un tipo de producto que modela lo que produce esta linea.
	 * @return
	 */
	public abstract Producto getTipoProducto();

	public void addFuente(Fuente fuente) {
		this.fuentes.add(fuente);
	}
	
	public void removeFuente(Fuente fuente) {
		this.fuentes.remove(fuente);
	}

	public List<Fuente> getFuentes() {
		return this.fuentes;
	}

	public void forzarRotura() {
		this.estaRota = true;
		
	}

    public boolean tieneCintaDeSalida() {
        if (getCintaSalida() == null)
            return false;
        return !this.isConectadaAContenedor();
    }

	public void setConectadaAContenedor(Boolean conectadaAContenedor) {
		this.conectadaAContenedor = conectadaAContenedor;
	}

	public Boolean isConectadaAContenedor() {
		return conectadaAContenedor;
	}

    public void reset() {
        this.cintasEntrada = new ArrayList<CintaTransportadora>();
		this.entrada = new Entrada();
		this.salida = new Salida();
		this.setMateriasPrimas(new ArrayList<Producto>());
		this.fuentes = new ArrayList<Fuente>();
		this.setPrecedentes(new ArrayList<Maquina>());
		this.setConectadaAContenedor(false);
    };
}
