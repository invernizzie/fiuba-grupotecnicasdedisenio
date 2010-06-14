package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.DineroInsuficienteException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.FabricaOcupadaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.JugadorConFabricaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.ProcesamientoException;

/**
 * Representa a una fábrica en particular.
 * La misma tiene unos metros cuadrados, un costo de compra y un costo de alquiler.
 * Puede ser comprada, alquilada o vendida por un jugador.
 *
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public class Fabrica implements Sincronizado{

	private List<Maquina> maquinas;
	private List<LineaProduccion> lineas;
	private List<Fuente> fuentes;
	private float costoCompra;
	private float costoAlquiler;
	private int metrosCuadrados;
	private Jugador jugador;
	private float costoFabricaXMes;

	public Fabrica(int metrosCuadrados, float costoCompra, float costoAlquiler) {
		this.maquinas = new ArrayList<Maquina>();
		this.lineas = new ArrayList<LineaProduccion>();
		this.fuentes = new ArrayList<Fuente>();
		this.setMetrosCuadrados(metrosCuadrados);
		this.setCostoCompra(costoCompra);
		this.setCostoAlquiler(costoAlquiler);
		this.setCostoFabricaXMes(0);
	}

	public void agregarFuente(Fuente fuente) {
		this.fuentes.add(fuente);
	}
	
	public void agregarMaquina(Maquina maquina) {
		this.getJugador().disminuirDinero(maquina.getCostoMaquina());
		this.maquinas.add(maquina);
	}
	
	public void eliminarMaquina(Maquina maquina) {
		List<LineaProduccion> lineasAEliminar = new ArrayList<LineaProduccion>();
		
		for (LineaProduccion linea : this.getLineas()) {
			if(linea.contieneMaquina(maquina)){
				linea.eliminarMaquina(maquina);
				
				if(linea.estaVacia()){
					lineasAEliminar.add(linea);
				}
			}
		}
		
		for (LineaProduccion lineaProduccion : lineasAEliminar) {
			this.eliminarLinea(lineaProduccion);
		}
		this.getJugador().aumentarDinero(maquina.obtenerCostoVenta());
		this.maquinas.remove(maquina);
	}

    public CintaTransportadora conectarMaquina(IFuente fuente, Maquina maquina, float longitud) {
        if (fuente instanceof Fuente)
            return conectarMaquina((Fuente)fuente, maquina, longitud);
        return conectarMaquina((Maquina)fuente, maquina, longitud);
    }

    // TODO Eliminar repeticion de codigo con su sobrecarga para (Maquina, Maquina)
	public CintaTransportadora conectarMaquina(Fuente fuente, Maquina maquina, float longitud){
		if(!maquinas.contains(maquina)){
			this.agregarMaquina(maquina);
		}
		
		CintaTransportadora cinta = new CintaTransportadora(longitud);
		cinta.conectar(fuente, maquina);
		
		boolean maquinaEnLinea = false;
		for (LineaProduccion linea : lineas) {
			if(linea.contieneMaquina(maquina)){
				maquinaEnLinea = true;
			}
		}
		
		if(!maquinaEnLinea){
			LineaProduccion linea = new LineaProduccion(this.jugador);
			linea.agregarMaquina(maquina);
			agregarLinea(linea);
		}
		this.getJugador().disminuirDinero(cinta.getCostoConectar());
		return cinta;
	}

	/**
	 * Conecta dos máquinas dadas, verifica si alguna de las dos pertenece a una linea
	 * de producción existente, en caso negativo crea una nueva linea que las contenga.
	 * @param origen
	 * @param destino
	 */
    // TODO Dividir en metodos mas cohesivos
	public CintaTransportadora conectarMaquina(Maquina origen, Maquina destino, float longitud){
		if(!maquinas.contains(origen)){
			this.agregarMaquina(origen);
		}
		
		if(!maquinas.contains(destino)){
			this.agregarMaquina(destino);
		}
		
		CintaTransportadora cinta = new CintaTransportadora(longitud);
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
				maquinasEnLinea = true;
			}else if(linea.contieneMaquina(destino)){
				linea.agregarMaquina(origen);
				maquinasEnLinea = true;
			}
		}
		
		/*
		 * Ninguna de las dos máquinas está en alguna de las lineas, creo una 
		 * nueva linea que las contenga.
		 */
		if(!maquinasEnLinea){
			LineaProduccion linea = new LineaProduccion(this.jugador);
			linea.agregarMaquina(origen);
			linea.agregarMaquina(destino);
			agregarLinea(linea);
		}
		this.getJugador().disminuirDinero(cinta.getCostoConectar());
		return cinta;
	}
	
	public void agregarLinea(LineaProduccion linea){
		this.lineas.add(linea);
	}
	
	public void eliminarLinea(LineaProduccion linea){
		this.lineas.remove(linea);
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
		Calendario.instancia().registrar(this);
	}

	/**
	 * La fábrica deja de tener un jugador asignado.
	 */
	public void vender(){
		try{
			this.verificarJugadorAsignado();
		}
		catch(FabricaOcupadaException e){
			if(this.isAlquilada()){
				this.getJugador().venderFabrica(0);
			}else{
				this.getJugador().venderFabrica((float) (this.getCostoCompra()*0.8));
			}
			
			Float costoMaquinas = 0F;
			for (Maquina maquina : maquinas) {
				costoMaquinas += maquina.obtenerCostoVenta();
			}
			
			this.getJugador().aumentarDinero(costoMaquinas);
			
			/*Borra todo lo que tiene seteado, hay que empezar de nuevo. Verificar si esta bien esto.*/
			this.maquinas = new ArrayList<Maquina>();
			this.lineas = new ArrayList<LineaProduccion>();
			this.fuentes = new ArrayList<Fuente>();
		}
		this.setCostoFabricaXMes(0);
		this.setJugador(null);

		this.lineas = null;
		Calendario.instancia().desregistrar(this);
		
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
		}
		if(evento.equals(Evento.COMIENZO_DE_DIA)){
			try {
				for (LineaProduccion linea : lineas) {
						linea.procesar();
				}
			} catch (ProcesamientoException e) {
				// TODO ¿Qué hacemos en este caso? ¿cómo informamos al jugador?
			}
		}
	}
	
	public void asignarCostoJugador(){
		
		try {
			this.verificarJugadorAsignado();
		} 
		catch (FabricaOcupadaException e) {
			//TODO Habria que sumarle el costo de las lineas de produccion.
			this.getJugador().disminuirDinero(this.getCostoFabricaXMes());
		}
		
		
	}
	
	public String toString(){
		return "Mts2: " + this.getMetrosCuadrados() + "-Compra: " + this.getCostoCompra() + "-Alquiler: " + this.getCostoAlquiler();
	}

	public List<LineaProduccion> getLineas() {
		return this.lineas;
	}
}
