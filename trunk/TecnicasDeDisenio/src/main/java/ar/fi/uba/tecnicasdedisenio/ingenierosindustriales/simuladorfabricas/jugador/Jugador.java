package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador;

import java.util.Observable;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.DineroInsuficienteException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.JugadorConFabricaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.excepciones.LaboratorioInhabilitadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

/**
 * Representa a un jugador.
 * Tiene un nombre y un dinero el cual va siendo actualizado.
 * Puede comprar, alquilar o vender una fábrica.
 * Puede crear líneas de producción dentro de cada fábrica que tenga.
 *
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public class Jugador extends Observable implements Sincronizado {
	
	private static final float PORCENTAJE_INVERSION_LABORATORIO = 10;
    private static final int CIEN = 100;
	
	private float dineroActual;
	private Laboratorio laboratorio;
	private Fabrica fabrica;
	private String nombre;

    public Jugador(final String nombre, final float dineroActual) {
		this.setDineroActual(dineroActual);
		this.setNombre(nombre);
		Calendario.instancia().registrar(this);
	}
	
	public void setLaboratorio(final Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
		this.setChanged();
		this.notifyObservers();
	}
	
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setDineroActual(final float dineroActual) {
		this.dineroActual = dineroActual;
		this.setChanged();
		this.notifyObservers();
	}

	public float getDineroActual() {
		return dineroActual;
	}
	
	public boolean hasFabrica(){
		return (this.getFabrica() != null);
	}
	
	public void habilitarLaboratorio() {
		this.getLaboratorio().setHabilitado(true);
	}
	
	public void deshabilitarLaboratorio() {
		this.getLaboratorio().setHabilitado(false);
	}
	
	/**
	 * Trata de invertir un porcentaje de dinero en el laboratorio.
	 * @param porcentaje Porcentaje del dinero en tenencia a invertir
	 */
	public void invertirDineroLaboratorio(float porcentaje) {
		try {
			this.getLaboratorio().invertir(this.getDineroActual() * porcentaje / CIEN);
			this.setDineroActual(this.getDineroActual() - this.getDineroActual() * porcentaje / CIEN);
		} catch(LaboratorioInhabilitadoException ignored) { }
	}
	
	public void agregarMaquina(final Maquina maquina) {
		this.getFabrica().comprarMaquina(maquina);
	}
	
	public void agregarFuente(final Fuente fuente) {
		this.getFabrica().agregarFuente(fuente);
	}
	
	public void conectarMaquina(final Fuente fuente, final Maquina maquina, final float longitud) {
		this.getFabrica().conectarMaquina(fuente, maquina, longitud);
	}
	
	public void conectarMaquina(final Maquina origen, final Maquina destino, final float longitud) {
		this.getFabrica().conectarMaquina(origen, destino, longitud);
	}

	public void setFabrica(final Fabrica fabrica) {
		this.fabrica = fabrica;
		this.setChanged();
		this.notifyObservers();
	}

	public Fabrica getFabrica() {
		return fabrica;
	}
	
	/**
	 * Compra una fábrica.
	 * @param fabrica Fábrica a comprar
	 */
	public void comprarFabrica(final Fabrica fabrica) {
		this.disminuirDinero(fabrica.getCostoCompra());
		this.setFabrica(fabrica);
	}
	
	/**
	 * Alquila una fábrica.
	 * @param fabrica Fábrica a comprar
	 */
	public void alquilarFabrica(final Fabrica fabrica) {
		this.setFabrica(fabrica);
		this.setChanged();
	}
	
	/**
	 * Verifica la existencia de una fábrica asignada a un jugador.
	 * @throws JugadorConFabricaException Si el jugador ya tiene fábrica
	 */
	public void verificarFabricaAsignada() throws JugadorConFabricaException {
		if (this.getFabrica() != null)
			throw new JugadorConFabricaException();
	}
	
	/**
	 * Verifica si el dinero que tiene es mayor a un costo.
	 * @param costo Costo contra el cual comprar el dinero del jugador
	 * @throws DineroInsuficienteException Si el dinero es insuficiente 
	 */
	public void verificarDineroSuficiente(final float costo) throws DineroInsuficienteException {
		if (this.getDineroActual() < costo) {
			throw new DineroInsuficienteException();
        }
	}
	
	/**
	 * El jugador deja de tener una fábrica y recupera parte del dinero que gastó.
	 * @param ganancia Precio de venta (dinero obtenido)
	 */
	public void venderFabrica(final float ganancia) {
		try {
			this.verificarFabricaAsignada();
		} catch (JugadorConFabricaException e) {
			this.aumentarDinero(ganancia);
			this.setFabrica(null);
		}
	}
	
	public void aumentarDinero(final float dinero) {
		this.setDineroActual(this.getDineroActual() + dinero);
	}
	
	public void disminuirDinero(final float dinero) {
		this.setDineroActual(this.getDineroActual() - dinero);
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
		this.setChanged();
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public void notificar(final Evento evento) {
		if (evento == Evento.COMIENZO_DE_MES) {
			this.invertirDineroLaboratorio(PORCENTAJE_INVERSION_LABORATORIO);
        }
	}
}
