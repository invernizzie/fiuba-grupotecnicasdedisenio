package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class Fabrica implements Sincronizado {

    private static final double OCHENTA_PORCIENTO = 0.8;

	private List<Maquina> maquinas;
	private List<LineaProduccion> lineas;
	private List<Fuente> fuentes;
	private Map<CintaTransportadora, FuenteSumidero> cintasMaquinas;
	private float costoCompra;
	private float costoAlquiler;
	private int metrosCuadrados;
	private Jugador jugador;
	private float costoFabricaXMes;

    public Fabrica(final int metrosCuadrados, final float costoCompra, final float costoAlquiler) {
		this.maquinas = new ArrayList<Maquina>();
		this.lineas = new ArrayList<LineaProduccion>();
		this.fuentes = new ArrayList<Fuente>();
		this.cintasMaquinas = new HashMap<CintaTransportadora, FuenteSumidero>();
		this.setMetrosCuadrados(metrosCuadrados);
		this.setCostoCompra(costoCompra);
		this.setCostoAlquiler(costoAlquiler);
		this.setCostoFabricaXMes(0);
	}

	public void agregarFuente(final Fuente fuente) {
		this.fuentes.add(fuente);
	}
	
	public void comprarMaquina(final Maquina maquina) {
		this.getJugador().disminuirDinero(maquina.getCostoMaquina());
		agregarMaquina(maquina);
	}

    public void agregarMaquina(final Maquina maquina) {
        maquinas.add(maquina);
    }
	
	public void venderMaquina(final Maquina maquina) {
		eliminarMaquina(maquina);
		this.getJugador().aumentarDinero(maquina.obtenerCostoVenta());
	}

    public void eliminarMaquina(final Maquina maquina) {
        limpiarCintas(maquina);
        limpiarLineas(maquina);
        maquinas.remove(maquina);
        maquina.reset();
    }

    public void eliminarFuente(final Fuente fuente) {
        limpiarCintas(fuente);
        fuentes.remove(fuente);
    }
    
    public void repararMaquina(final Maquina maquina) {
        getJugador().disminuirDinero(maquina.reparar());
    }

    public CintaTransportadora conectarMaquina(final IFuente fuente, final Maquina maquina, final float longitud) {
    	CintaTransportadora cinta = null;
        if (fuente instanceof Fuente) {
        	cinta = conectarMaquina((Fuente) fuente, maquina, longitud);
        } else {
        	cinta = conectarMaquina((Maquina) fuente, maquina, longitud);
        }
        
        FuenteSumidero par = new FuenteSumidero(fuente, maquina);
        cintasMaquinas.put(cinta, par);
        
        return cinta;
    }


	public CintaTransportadora conectarMaquina(final Fuente fuente, final Maquina maquina, final float longitud) {
		adquirirMaquina(maquina);
		CintaTransportadora cinta = crearCintaTransportadora(fuente, maquina, longitud);
		actualizarLineas(maquina);
		
		return cinta;
	}

	private void actualizarLineas(final Maquina maquina) {
		boolean maquinaEnLinea = false;
		for (LineaProduccion linea : lineas) {
			if (linea.contieneMaquina(maquina)) {
				maquinaEnLinea = true;
			}
		}
		
		if (!maquinaEnLinea) {
			LineaProduccion linea = new LineaProduccion(this.jugador);
			linea.agregarMaquina(maquina);
			agregarLinea(linea);
		}
	}

	private CintaTransportadora crearCintaTransportadora(final IFuente fuente,
			final Maquina maquina, final float longitud) {
		CintaTransportadora cinta = new CintaTransportadora(longitud);
		cinta.conectar(fuente, maquina);
		this.getJugador().disminuirDinero(cinta.getCostoConectar());
		return cinta;
	}

	private void adquirirMaquina(final Maquina maquina) {
		if (!maquinas.contains(maquina)) {
			this.comprarMaquina(maquina);
		}
	}

	/**
	 * Conecta dos máquinas dadas, verifica si alguna de las dos pertenece a una linea
	 * de producción existente, en caso negativo crea una nueva linea que las contenga.
	 * @param origen
	 * @param destino
	 */
	public CintaTransportadora conectarMaquina(final Maquina origen, final Maquina destino, final float longitud) {
		adquirirMaquina(origen);
		adquirirMaquina(destino);
		
		CintaTransportadora cinta = crearCintaTransportadora(origen, destino, longitud);
		actualizarLineas(origen, destino);
		return cinta;
	}

	private void actualizarLineas(final Maquina origen, final Maquina destino) {
		/*
		 * Si una linea contiene alguna de las máquinas agrego la otra a esa linea.
		 */
		boolean maquinasEnLinea = true;
		for (LineaProduccion linea : lineas) {
			if (!linea.contieneMaquina(origen)
					&& !linea.contieneMaquina(destino)) {
				maquinasEnLinea = false;
            } else if (linea.contieneMaquina(origen)) {
				linea.agregarMaquina(destino);
				maquinasEnLinea = true;
			} else if (linea.contieneMaquina(destino)) {
				linea.agregarMaquina(origen);
				maquinasEnLinea = true;
            }
		}
		
		/*
		 * Si una de las dos esta en una linea agrego la otra a la linea.
		 */
		/*
		 * Ninguna de las dos máquinas está en alguna de las lineas, creo una
		 * nueva linea que las contenga.
		 */
		if (!maquinasEnLinea) {
			LineaProduccion linea = new LineaProduccion(this.jugador);
			linea.agregarMaquina(origen);
			linea.agregarMaquina(destino);
			agregarLinea(linea);
		}
	}
	
	public void desconectar(){
		
	}
	
	public void agregarLinea(final LineaProduccion linea) {
		this.lineas.add(linea);
	}
	
	public void eliminarLinea(final LineaProduccion linea) {
		this.lineas.remove(linea);
	}
	
	public void setCostoCompra(final float costoCompra) {
		this.costoCompra = costoCompra;
	}

	public float getCostoCompra() {
		return costoCompra;
	}

	public void setCostoAlquiler(final float costoAlquiler) {
		this.costoAlquiler = costoAlquiler;
	}

	public float getCostoAlquiler() {
		return costoAlquiler;
	}

	public void setMetrosCuadrados(final int metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	public int getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setJugador(final Jugador jugador) {
		this.jugador = jugador;
	}

	public Jugador getJugador() {
		return jugador;
	}
	
	public void setCostoFabricaXMes(final float costoFabricaXMes) {
		this.costoFabricaXMes = costoFabricaXMes;
	}

	public float getCostoFabricaXMes() {
		return costoFabricaXMes;
	}
	
	/**
	 * Verifica la existencia de un jugador poseyendo la fábrica.
	 * @throws FabricaOcupadaException
	 */
	public void verificarJugadorAsignado() throws FabricaOcupadaException {
		if (this.getJugador() != null) {
			throw new FabricaOcupadaException();
        }
	}
	
	/**
	 * La fábrica se intenta comprar, previamente verificando que se cumplan las condiciones de
	 * dinero suficiente.
	 * @param jugador
	 * @throws DineroInsuficienteException
	 * @throws FabricaOcupadaException
	 * @throws JugadorConFabricaException
	 */
	public void comprar(final Jugador jugador) throws DineroInsuficienteException, FabricaOcupadaException, JugadorConFabricaException {
		jugador.verificarDineroSuficiente(this.getCostoCompra());
		this.realizarCompraOAlquiler(jugador, 0);
		jugador.comprarFabrica(this);
	}
	
	/**
	 * La fábrica se intenta alquilar.
	 * @param jugador
	 * @throws FabricaOcupadaException
	 * @throws JugadorConFabricaException
	 */
	public void alquilar(final Jugador jugador) throws FabricaOcupadaException, JugadorConFabricaException {
		this.realizarCompraOAlquiler(jugador, this.getCostoAlquiler());
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
	public void realizarCompraOAlquiler(final Jugador jugador, final float costoXMes) throws FabricaOcupadaException, JugadorConFabricaException {
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
	public void vender() {
		try{
			this.verificarJugadorAsignado();
		} catch(FabricaOcupadaException e) {
			if (this.isAlquilada()) {
				this.getJugador().venderFabrica(0);
			} else {
				this.getJugador().venderFabrica((float) (this.getCostoCompra() * OCHENTA_PORCIENTO));
			}
			
			Float costoMaquinas = 0F;
			for (Maquina maquina : maquinas) {
				costoMaquinas += maquina.obtenerCostoVenta();
			}
			
			this.getJugador().aumentarDinero(costoMaquinas);
			
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
	public boolean isAlquilada() {
		return ((getCostoAlquiler() == getCostoFabricaXMes()) && (getJugador() != null));
	}
	
	/**
	 * Si esta comprada el costo x mes es nulo y tiene un jugador asignado.
	 * @return
	 */
	public boolean isComprada() {
		return ((getCostoFabricaXMes() == 0) && (getJugador() != null));
	}

	@Override
	public void notificar(final Evento evento) {
		if (evento.equals(Evento.COMIENZO_DE_MES)) {
			this.asignarCostoJugador();
		}
		if (evento.equals(Evento.COMIENZO_DE_DIA)) {
			try {
				for (LineaProduccion linea : lineas) {
						linea.procesar();
				}
			} catch (ProcesamientoException ignored) { }
		}
	}
	
	public void asignarCostoJugador() {
		
		try {
			this.verificarJugadorAsignado();
		} catch (FabricaOcupadaException e) {
			this.getJugador().disminuirDinero(this.getCostoFabricaXMes());
		}
	}
	
	public String toString() {
		return "Mts2: " + this.getMetrosCuadrados() + "-Compra: " + this.getCostoCompra() + "-Alquiler: " + this.getCostoAlquiler();
	}

	public List<LineaProduccion> getLineas() {
		return this.lineas;
	}
	
	/**
	 * Para cada linea de producción se verifica si se tiene un ciclo.
	 */
	public void validarCiclos() {
		for (LineaProduccion linea : this.getLineas()) {
			linea.validarCiclo();
        }
	}

	private void limpiarCintas(final Maquina maquina) {
		Set<CintaTransportadora> cintas = cintasMaquinas.keySet();
		Set<CintaTransportadora> cintasAEliminar = new HashSet<CintaTransportadora>();
		
		for (CintaTransportadora cintaTransportadora : cintas) {
			FuenteSumidero par = cintasMaquinas.get(cintaTransportadora);
			if (par.contieneMaquina(maquina)) {
				cintaTransportadora.desconectar(par.getOrigen(), par.getDestino());
				cintasAEliminar.add(cintaTransportadora);
			}
		}
		
		for (CintaTransportadora cintaAEliminar : cintasAEliminar) {
			cintasMaquinas.remove(cintaAEliminar);
		}
	}

    private void limpiarCintas(final Fuente fuente) {
		Set<CintaTransportadora> cintas = cintasMaquinas.keySet();
		Set<CintaTransportadora> cintasAEliminar = new HashSet<CintaTransportadora>();

		for (CintaTransportadora cintaTransportadora : cintas) {
			FuenteSumidero par = cintasMaquinas.get(cintaTransportadora);
			if (par.getOrigen() == fuente) {
				cintaTransportadora.desconectar(par.getOrigen(), par.getDestino());
				cintasAEliminar.add(cintaTransportadora);
			}
		}

		for (CintaTransportadora cintaAEliminar : cintasAEliminar) {
			cintasMaquinas.remove(cintaAEliminar);
		}
	}

	private void limpiarLineas(final Maquina maquina) {
		List<LineaProduccion> lineasAEliminar = new ArrayList<LineaProduccion>();
		for (LineaProduccion linea : this.getLineas()) {
			if (linea.contieneMaquina(maquina)) {
				linea.eliminarMaquina(maquina);
				for (Maquina maq : linea.getMaquinas()) {
					if ((maq.getPrecedentes().size() == 0) && (maq.getSiguiente() == null)) {
						linea.eliminarMaquina(maq);
					}
				}
				if (linea.estaVacia()) {
					lineasAEliminar.add(linea);
				}
			}
		}
		
		for (LineaProduccion lineaProduccion : lineasAEliminar) {
			this.eliminarLinea(lineaProduccion);
		}
	}
}
