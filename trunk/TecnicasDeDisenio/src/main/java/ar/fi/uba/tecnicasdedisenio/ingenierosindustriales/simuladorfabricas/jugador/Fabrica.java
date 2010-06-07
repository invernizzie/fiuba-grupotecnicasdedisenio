package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador;

import java.util.ArrayList;
import java.util.List;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.LineaProduccion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.ProcesamientoException;

public class Fabrica implements Sincronizado{

	private List<Maquina> maquinas;
	private List<LineaProduccion> lineas;
	private List<Fuente> fuentes;
	private float costoCompra;
	private float costoAlquiler;
	private int metrosCuadrados;
	private int cantMaximaLineas;
	private Jugador jugador;
	private float costoFabricaXMes;

	public Fabrica(int metrosCuadrados, float costoCompra, float costoAlquiler, int cantMaximaLineas) {
		this.maquinas = new ArrayList<Maquina>();
		this.lineas = new ArrayList<LineaProduccion>();
		this.fuentes = new ArrayList<Fuente>();
		this.setMetrosCuadrados(metrosCuadrados);
		this.setCostoCompra(costoCompra);
		this.setCostoAlquiler(costoAlquiler);
		this.setCantMaximaLineas(cantMaximaLineas);
		this.setCostoFabricaXMes(0);
		Calendario.instancia().registrar(this);
	}

	public void agregarFuente(Fuente fuente) {
		this.fuentes.add(fuente);
	}
	
	public void agregarMaquina(Maquina maquina) {
		this.maquinas.add(maquina);
	}

	public void conectarMaquina(Fuente fuente, Maquina maquina) throws CantidadLineasMaximaException {
		if(!maquinas.contains(maquina)){
			this.agregarMaquina(maquina);
		}
		
		CintaTransportadora cinta = new CintaTransportadora();
		cinta.conectar(fuente, maquina);
		
		boolean maquinaEnLinea = false;
		for (LineaProduccion linea : lineas) {
			if(linea.contieneMaquina(maquina)){
				maquinaEnLinea = true;
			}
		}
		
		if(!maquinaEnLinea){
			LineaProduccion linea = new LineaProduccion(this.jugador.getLaboratorio());
			linea.agregarMaquina(maquina);
			agregarLinea(linea);
		}
		
	}

	/**
	 * Conecta dos máquinas dadas, verifica si alguna de las dos pertenece a una linea
	 * de producción existente, en caso negativo crea una nueva linea que las contenga.
	 * @param origen
	 * @param destino
	 * @throws CantidadLineasMaximaException
	 */
	public void conectarMaquina(Maquina origen, Maquina destino) throws CantidadLineasMaximaException {
		if(!maquinas.contains(origen)){
			this.agregarMaquina(origen);
		}
		
		if(!maquinas.contains(destino)){
			this.agregarMaquina(destino);
		}
		
		CintaTransportadora cinta = new CintaTransportadora();
		cinta.conectar(origen, destino);
		
		/*
		 * Si una linea contiene alguna de las máquinas agrego la otra a esa linea.
		 */
		boolean maquinasEnLinea = true;
		for (LineaProduccion linea : lineas) {
			if(!linea.contieneMaquina(origen)
					&& !linea.contieneMaquina(destino)){
				maquinasEnLinea = false;
			}else if(linea.contieneMaquina(origen)){
				linea.agregarMaquina(destino);
			}else if(linea.contieneMaquina(destino)){
				linea.agregarMaquina(origen);
			}
		}
		
		/*
		 * Ninguna de las dos máquinas está en alguna de las lineas, creo una 
		 * nueva linea que las contenga.
		 */
		if(!maquinasEnLinea){
			LineaProduccion linea = new LineaProduccion(this.jugador.getLaboratorio());
			linea.agregarMaquina(origen);
			linea.agregarMaquina(destino);
			agregarLinea(linea);
		}
		
	}
	
	public void agregarLinea(LineaProduccion linea) throws CantidadLineasMaximaException {
		this.verificarCantidadLineas();
		this.lineas.add(linea);
	}
	
	public void verificarCantidadLineas() throws CantidadLineasMaximaException{
		if(this.lineas.size()==this.getCantMaximaLineas())
			throw new CantidadLineasMaximaException();
		
	}
	
	public void setCostoCompra(float costoCompra) {
		this.costoCompra = costoCompra;
	}

	public float getCostoCompra() {
		return costoCompra;
	}

	public void setCostoAlquiler(float costoAlquiler) {
		this.costoAlquiler = costoAlquiler;
	}

	public float getCostoAlquiler() {
		return costoAlquiler;
	}

	public void setMetrosCuadrados(int metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	public int getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setCantMaximaLineas(int cantMaximaLineas) {
		this.cantMaximaLineas = cantMaximaLineas;
	}

	public int getCantMaximaLineas() {
		return cantMaximaLineas;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Jugador getJugador() {
		return jugador;
	}
	
	public void setCostoFabricaXMes(float costoFabricaXMes) {
		this.costoFabricaXMes = costoFabricaXMes;
	}

	public float getCostoFabricaXMes() {
		return costoFabricaXMes;
	}
	
	/**
	 * Verifica la existencia de un jugador poseyendo la fábrica.
	 * @throws FabricaOcupadaException
	 */
	public void verificarJugadorAsignado() throws FabricaOcupadaException{
		if(this.getJugador()!=null)
			throw new FabricaOcupadaException();
	}
	
	/**
	 * La fábrica se intenta comprar, previamente verificando que se cumplan las condiciones de
	 * dinero suficiente.
	 * @param jugador
	 * @throws DineroInsuficienteException
	 * @throws FabricaOcupadaException
	 * @throws JugadorConFabricaException
	 */
	public void comprar(Jugador jugador) throws DineroInsuficienteException, FabricaOcupadaException, JugadorConFabricaException{
		jugador.verificarDineroSuficiente(this.getCostoCompra());
		this.realizarCompraOAlquiler(jugador,0);
		jugador.comprarFabrica(this);
	}
	
	/**
	 * La fábrica se intenta alquilar.
	 * @param jugador
	 * @throws FabricaOcupadaException
	 * @throws JugadorConFabricaException
	 */
	public void alquilar(Jugador jugador) throws FabricaOcupadaException, JugadorConFabricaException{
		this.realizarCompraOAlquiler(jugador,this.getCostoAlquiler());
		jugador.alquilarFabrica(this);
	}
	
	/**
	 * Realiza la compra o alquiler de la fábrica verificando que el jugador no tenga
	 * ya una fábrica asignada y que la fábrica no este asignada a un jugador.
	 * @param jugador
	 * @param costoXMes
	 * @throws FabricaOcupadaException
	 * @throws JugadorConFabricaException
	 */
	public void realizarCompraOAlquiler(Jugador jugador, float costoXMes) throws FabricaOcupadaException, JugadorConFabricaException{
		jugador.verificarFabricaAsignada();
		this.verificarJugadorAsignado();
		this.lineas = new ArrayList<LineaProduccion>();
		this.setJugador(jugador);
		this.setCostoFabricaXMes(costoXMes);
	}

	/**
	 * La fábrica deja de tener un jugador asignado.
	 */
	public void vender(){
		try{
			this.verificarJugadorAsignado();
		}
		catch(FabricaOcupadaException e){
			if(this.isAlquilada())
				this.getJugador().venderFabrica(0);
			this.getJugador().venderFabrica((float) (this.getCostoCompra()*0.8));
		}
		this.setCostoFabricaXMes(0);
		this.setJugador(null);
		
		/*Antes de borrar todas sus lineas de produccion y tambien pasarle el 50%
		 * del valor de cada maquina que no este rota al jugador.*/
		this.lineas = null;
		
	}
	
	/**
	 * Si esta alquilada el costo x mes es el costo de alquiler y tiene un jugador asignado.
	 * @return
	 */
	public boolean isAlquilada(){
		return (this.getCostoAlquiler()==this.getCostoFabricaXMes() && this.getJugador()!=null);
	}
	
	/**
	 * Si esta comprada el costo x mes es nulo y tiene un jugador asignado.
	 * @return
	 */
	public boolean isComprada(){
		return (this.getCostoFabricaXMes()==0 && this.getJugador()!=null);
	}

	@Override
	public void notificar(Evento evento) {
		if(evento.equals(Evento.COMIENZO_DE_MES)){
			this.asignarCostoJugador();
		}else if(evento.equals(Evento.COMIENZO_DE_DIA)){
			try {
				for (LineaProduccion linea : lineas) {
						linea.procesar();
				}
			} catch (ProcesamientoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void asignarCostoJugador(){
		
		try {
			this.verificarJugadorAsignado();
		} 
		catch (FabricaOcupadaException e) {
			//Habria que sumarle el costo de las lineas de produccion.
			this.getJugador().disminuirDinero(this.getCostoFabricaXMes());
		}
		
		
	}
	
	public String toString(){
		return "Mts2: " + this.getMetrosCuadrados() + "-Compra: " + this.getCostoCompra() + "-Alquiler: " + this.getCostoAlquiler() + "-Max LP: " + this.getCantMaximaLineas();
	}
}
