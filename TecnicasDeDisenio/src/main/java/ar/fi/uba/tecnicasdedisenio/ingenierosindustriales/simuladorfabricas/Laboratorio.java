package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class Laboratorio {
	
	private float dineroAcumulado;
	private boolean habilitado;
	private ArrayList<Proceso> procesosHabilitados;
	private ArrayList<Proceso> procesosInhabilitados;
	private static Laboratorio laboratorio = new Laboratorio();
	
	private Laboratorio(){
		this.setProcesosHabilitados(new ArrayList<Proceso>());
		this.setProcesosInhabilitados(new ArrayList<Proceso>());
		this.setDineroAcumulado(0);
		this.setHabilitado(false);
	}
	
	public static Laboratorio getInstance(){
		return laboratorio;
	}

	public void setProcesosHabilitados(ArrayList<Proceso> procesosHabilitados) {
		this.procesosHabilitados = procesosHabilitados;
	}

	public ArrayList<Proceso> getProcesosHabilitados() {
		return procesosHabilitados;
	}
	
	public void cargarProcesos(){
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
	public Elemento procesoValido(ArrayList<Maquina> maquinas){
		Iterator<Proceso> itProcesos = this.iteratorProcesosHabilitados();
		Elemento e;
		while(itProcesos.hasNext()){
			e = itProcesos.next().validarLinea(maquinas);
			if (e!=null){
				return e;
			}
		}
		return null;
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
