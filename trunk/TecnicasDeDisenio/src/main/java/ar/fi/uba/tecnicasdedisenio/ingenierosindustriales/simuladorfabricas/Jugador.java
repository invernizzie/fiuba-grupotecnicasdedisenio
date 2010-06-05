package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.Observable;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class Jugador extends Observable implements Sincronizado {
	
	
	private float dineroActual;
	private Laboratorio laboratorio;
	private Fabrica fabrica;
	private String nombre;
	
	private static float DINERO_PARA_GANAR = 1000000;
	private static final float PORCENTAJE_INVERSION_LABORATORIO = 10;
	
	public Jugador(String nombre, float dineroActual){
		this.setDineroActual(dineroActual);
		this.setNombre(nombre);
		Calendario.instancia().registrar(this);
	}
	
	public void setLaboratorio(Laboratorio laboratorio){
		this.laboratorio = laboratorio;
		this.setChanged();
		this.notifyObservers();
	}
	
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setDineroActual(float dineroActual) {
		this.dineroActual = dineroActual;
		this.setChanged();
		this.notifyObservers();
	}

	public float getDineroActual() {
		return dineroActual;
	}
	
	public void habilitarLaboratorio(){
		this.getLaboratorio().setHabilitado(true);
	}
	
	public void invertirDineroLaboratorio(float porcentaje){
		try{
			this.getLaboratorio().invertir(this.getDineroActual()*porcentaje/100);
			this.setDineroActual(this.getDineroActual()-this.getDineroActual()*porcentaje/100);
		}
		catch(LaboratorioInhabilitadoException e){
		}
	}
	
	public void agregarMaquina(Maquina maquina){
		this.getFabrica().agregarMaquina(maquina);
	}
	
	public void agregarFuente(Fuente fuente){
		this.getFabrica().agregarFuente(fuente);
	}
	
	public void conectarMaquina(Fuente fuente, Maquina maquina){
		this.getFabrica().conectarMaquina(fuente, maquina);
	}
	
	public void conectarMaquina(Maquina origen, Maquina destino){
		this.getFabrica().conectarMaquina(origen, destino);
	}

	public void setFabrica(Fabrica fabrica) {
		this.fabrica = fabrica;
		this.setChanged();
		this.notifyObservers();
	}

	public Fabrica getFabrica() {
		return fabrica;
	}
	
	public void comprarFabrica(Fabrica fabrica){
		this.disminuirDinero(fabrica.getCostoCompra());
		this.setFabrica(fabrica);
	}
	
	public void alquilarFabrica(Fabrica fabrica){
		this.setFabrica(fabrica);
		this.setChanged();
	}
	
	/**
	 * Verifica la existencia de una fábrica asignada a un jugador.
	 * @throws JugadorConFabricaException
	 */
	public void verificarFabricaAsignada() throws JugadorConFabricaException{
		if(this.getFabrica()!=null)
			throw new JugadorConFabricaException();
	}
	
	public void verificarDineroSuficiente(float costo) throws DineroInsuficienteException{
		if(this.getDineroActual()< costo)
			throw new DineroInsuficienteException();
	}
	
	/**
	 * El jugador deja de tener una fábrica y recupera parte del dinero que gastó.*/
	public void venderFabrica(float ganancia){
		try{
			this.verificarFabricaAsignada();
		}
		catch(JugadorConFabricaException e){
			this.aumentarDinero(ganancia); 
			this.setFabrica(null);
		}
	}
	
	public void aumentarDinero(float dinero){
		this.setDineroActual(this.getDineroActual()+dinero);
		if(this.getDineroActual()>=Jugador.DINERO_PARA_GANAR)
			System.out.println("GANO");
			//GANO!!!
	}
	
	public void disminuirDinero(float dinero){
		this.setDineroActual(this.getDineroActual()-dinero);
		if(this.getDineroActual()<0)
			System.out.println("PERDIO");
			//PERDIO!!!
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
		this.setChanged();
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public void notificar(Evento evento) {
		if(evento==Evento.COMIENZO_DE_MES)
			this.invertirDineroLaboratorio(PORCENTAJE_INVERSION_LABORATORIO);
		
	}
	
}
