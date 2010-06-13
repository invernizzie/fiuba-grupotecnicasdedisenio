package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.excepciones.LaboratorioInhabilitadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

/**
 * Representa a un laboratorio.
 * Tiene un tipo y un nombre de una imagen que lo caracteriza.
 * Tiene una lista de procesos habilitados y otros inhabilitados los cuales
 * puede ir habilitandolos a medida de que acumule dinero.
 *
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public class Laboratorio{
	
	private float dineroAcumulado;
	private boolean habilitado;
	private ArrayList<Proceso> procesosHabilitados;
	private ArrayList<Proceso> procesosInhabilitados;
	private String tipo;
	private String nombreImagen;
	
	public Laboratorio(String tipo, String nombreImagen){
		this.setProcesosHabilitados(new ArrayList<Proceso>());
		this.setProcesosInhabilitados(new ArrayList<Proceso>());
		this.setDineroAcumulado(0);
		this.setHabilitado(false);
		this.setTipo(tipo);
		this.setNombreImagen(nombreImagen);
		this.cargarProcesos();
	}
	
	public void setProcesosHabilitados(ArrayList<Proceso> procesosHabilitados) {
		this.procesosHabilitados = procesosHabilitados;
	}

	public ArrayList<Proceso> getProcesosHabilitados() {
		return procesosHabilitados;
	}
	
	
	public void cargarProcesos(){
		TipoMaquina maq = null;
		Proceso proceso = null;
		ValidadorProductos val = ValidadorProductos.instancia();
		
		proceso = new Proceso(1000);
		maq = new TipoMaquinaPrensa();
		maq.getPrecedentes().add(new TipoMaquinaHorno());
		maq.getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaMezcladora());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
		maq.getPrecedentes().get(1).addMateriaPrima(new Producto(val, "agua", 0));
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "edulcorante", 0));
		maq.getPrecedentes().get(0).getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "centeno", 0));
		proceso.setMaquinaFinal(maq);
		this.getProcesosHabilitados().add(proceso);
		
		proceso = new Proceso(1500);
		maq = new TipoMaquinaPrensa();
		maq.addMateriaPrima(new Producto(val, "agua", 0));
		maq.addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		proceso.setMaquinaFinal(maq);
		this.getProcesosHabilitados().add(proceso);
		
		proceso = new Proceso(500);
		maq = new TipoMaquinaPrensa();
		maq.addPrecedente(new TipoMaquinaMezcladora());
		maq.addPrecedente(new TipoMaquinaHorno());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		proceso.setMaquinaFinal(maq);
		this.getProcesosInhabilitados().add(proceso);
		
		proceso = new Proceso(500);
		maq = new TipoMaquinaPrensa();
		maq.addPrecedente(new TipoMaquinaMezcladora());
		maq.addPrecedente(new TipoMaquinaHorno());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
		proceso.setMaquinaFinal(maq);
		this.getProcesosInhabilitados().add(proceso);
		
	}
	

	public void setProcesosInhabilitados(ArrayList<Proceso> procesosInhabilitados) {
		this.procesosInhabilitados = procesosInhabilitados;
	}

	public ArrayList<Proceso> getProcesosInhabilitados() {
		return procesosInhabilitados;
	}
	
	public void invertir(float cantidad) throws LaboratorioInhabilitadoException{
		if(this.isHabilitado()){
			this.setDineroAcumulado(this.getDineroAcumulado()+cantidad);
			this.habilitarProcesos();
		}
		else{
			throw new LaboratorioInhabilitadoException();
		}
	}

	public void setDineroAcumulado(float dineroAcumulado) {
		this.dineroAcumulado = dineroAcumulado;
	}

	public float getDineroAcumulado() {
		return dineroAcumulado;
	}
	
	public Iterator<Proceso> iteratorProcesosHabilitados(){
		return new IteradorProcesos(this.getProcesosHabilitados());
	}
	
	public Iterator<Proceso> iteratorProcesosInhabilitados(){
		return new IteradorProcesos(this.getProcesosInhabilitados());
	}
	
	public void habilitarProcesos(){
		Iterator<Proceso> it = this.iteratorProcesosInhabilitados();
		Proceso proc = null;
		while(it.hasNext()){
			proc = it.next();
			if(proc.habilitar(this.getDineroAcumulado())){
				this.getProcesosHabilitados().add(proc);
				this.setDineroAcumulado(this.getDineroAcumulado()-proc.getCosto());
				it.remove();
				it = this.iteratorProcesosInhabilitados();
			}
		}
	}
	
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public boolean isHabilitado() {
		return habilitado;
	}
	
	/* Si existe un proceso que sea igual a la linea de producción
	 * entonces devuelve el elemento que produce*/
	public boolean existeProcesoValido(Maquina maquinaFinalLinea){
		Iterator<Proceso> itProcesos = this.iteratorProcesosHabilitados();
		boolean procesoValido;
		while(itProcesos.hasNext()){
			procesoValido = itProcesos.next().esProcesoIgualALinea(maquinaFinalLinea);
			if(procesoValido){
				return true;
			}
		}
		return false;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public class IteradorProcesos implements Iterator<Proceso>{
		private int indice;
		List<Proceso> lista;
		
		public IteradorProcesos(List<Proceso> lista){
			this.lista = lista;
			indice = 0;
		}
		
		public boolean hasNext() {
			return indice<lista.size();
		}

		public Proceso next() {
			Proceso proc = lista.get(indice);
			indice++;
			return proc; 
		}

		public void remove() {
			lista.remove(--indice);
		
		}
	}
	
	
}
