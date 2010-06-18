package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.excepciones.LaboratorioInhabilitadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

/**
 * Representa a un laboratorio.
 * Tiene un tipo y un nombre de una imagen que lo caracteriza.
 * Tiene una lista de procesos habilitados y otros inhabilitados los cuales
 * puede ir habilitandolos a medida de que acumule dinero.
 *
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public class Laboratorio {
	
	private float dineroAcumulado;
	private boolean habilitado;
	private List<Proceso> procesosHabilitados;
	private List<Proceso> procesosInhabilitados;
	private String tipo;
	private String nombreImagen;
	private CargadorDeProcesos cargador;
	
	public Laboratorio(final String tipo, final String nombreImagen) {
		this.setProcesosHabilitados(new ArrayList<Proceso>());
		this.setProcesosInhabilitados(new ArrayList<Proceso>());
		this.setCargador(new CargadorDeProcesos());
		this.setDineroAcumulado(0);
		this.setHabilitado(false);
		this.setTipo(tipo);
		this.setNombreImagen(nombreImagen);
		this.cargarProcesos();
	}
	
	public void setProcesosHabilitados(final List<Proceso> procesosHabilitados) {
		this.procesosHabilitados = procesosHabilitados;
	}

	public List<Proceso> getProcesosHabilitados() {
		return procesosHabilitados;
	}
	
	/**
	 * Llama al cargador para que cargue los procesos y habilita los de costo 0.
	 */
	public void cargarProcesos(){
		this.setProcesosInhabilitados(this.getCargador().cargarProcesos(this.getTipo()));
		this.habilitarProcesos();
	}
	

	public void setProcesosInhabilitados(final List<Proceso> procesosInhabilitados) {
		this.procesosInhabilitados = procesosInhabilitados;
	}

	public List<Proceso> getProcesosInhabilitados() {
		return procesosInhabilitados;
	}
	
	/**
	 * Invierte una cantidad de dinero en el laboratorio y llama a la habilitación de
	 * procesos.
	 * @param cantidad
	 * @throws LaboratorioInhabilitadoException
	 */
	public void invertir(final float cantidad) throws LaboratorioInhabilitadoException {
		if (this.isHabilitado()) {
			this.aumentarDinero(cantidad);
			this.habilitarProcesos();
		} else {
			throw new LaboratorioInhabilitadoException();
		}
	}

	public void setDineroAcumulado(final float dineroAcumulado) {
		this.dineroAcumulado = dineroAcumulado;
	}

	public float getDineroAcumulado() {
		return dineroAcumulado;
	}
	
	public Iterator<Proceso> iteratorProcesosHabilitados() {
		return new IteradorProcesos(this.getProcesosHabilitados());
	}
	
	public Iterator<Proceso> iteratorProcesosInhabilitados() {
		return new IteradorProcesos(this.getProcesosInhabilitados());
	}
	
	/**
	 * Recorre cada proceso inhabilitado y verifica si se puede habilitar. Si es así
	 * lo habilita y debita el dinero.
	 */
	public void habilitarProcesos() {
		Iterator<Proceso> it = this.iteratorProcesosInhabilitados();
		Proceso proc = null;
		while (it.hasNext()) {
			proc = it.next();
			if (proc.sePuedeHabilitar(this.getDineroAcumulado())) {
				this.getProcesosHabilitados().add(proc);
				this.disminuirDinero(proc.getCosto());
				it.remove();
				it = this.iteratorProcesosInhabilitados();
			}
		}
	}
	
	public void setHabilitado(final boolean habilitado) {
		this.habilitado = habilitado;
	}

	public boolean isHabilitado() {
		return habilitado;
	}
	
	/**
	 * Si existe un proceso que sea igual a la linea de producción
	 * entonces devuelve el elemento que produce.
	 * @param maquinaFinalLinea
	 * @return
	 **/
	public boolean existeProcesoValido(final Maquina maquinaFinalLinea) {
		Iterator<Proceso> itProcesos = this.iteratorProcesosHabilitados();
		boolean procesoValido;
		while (itProcesos.hasNext()) {
			procesoValido = itProcesos.next().esProcesoIgualALinea(maquinaFinalLinea);
			if (procesoValido) {
				return true;
			}
		}
		return false;
	}
	
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setNombreImagen(final String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setCargador(final CargadorDeProcesos cargador) {
		this.cargador = cargador;
	}

	public CargadorDeProcesos getCargador() {
		return cargador;
	}
	
	public void aumentarDinero(final float cantidad) {
		this.setDineroAcumulado(this.getDineroAcumulado() + cantidad);
	}
	
	public void disminuirDinero(final float cantidad) {
		this.setDineroAcumulado(this.getDineroAcumulado() - cantidad);
	}
	
	public class IteradorProcesos implements Iterator<Proceso> {
		private int indice;
		private List<Proceso> lista;
		
		public IteradorProcesos(final List<Proceso> lista) {
			this.lista = lista;
			indice = 0;
		}
		
		public boolean hasNext() {
			return indice < lista.size();
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
