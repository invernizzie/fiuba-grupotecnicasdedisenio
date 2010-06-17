package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;

/**
 * Representa a un proceso en particular.
 * Un un conjunto ordenado de tipos de máquinas y materias primas, simboliza
 * a un proceso de producción, una receta para producir un producto.
 * Tiene un costo de descubrimiento.
 *
 * @author Gustavo A. Meller (gmeller@gmail.com)
 */

public class Proceso {
	
	private TipoMaquina tipoMaquinaFinal;
	private float costo;
	
	public Proceso(final float costo) {
		this.setCosto(costo);
	}

	public void setMaquinaFinal(final TipoMaquina maquina) {
		this.tipoMaquinaFinal = maquina;
	}

	public TipoMaquina getMaquinaFinal() {
		return tipoMaquinaFinal;
	}
	
	public void setCosto(final float costo) {
		this.costo = costo;
	}

	public float getCosto() {
		return costo;
	}
	
	/**
	 * Si la oferta es mayor o igual al costo del proceso indica que lo puede habilitar.
	 * @param oferta Valor de la oferta
	 * @return Verdadero si la oferta es suficiente
	 */
	public boolean sePuedeHabilitar(final float oferta) {
		return this.getCosto() <= oferta;
	}
	
	/**
	 * Dada la maquina final de una linea de producci�n verifica si un proceso (receta)
	 * es igual a una l�nea de producci�n.
	 * @param maquinaFinalLinea
	 * @return
	 */
	public boolean esProcesoIgualALinea(final Maquina maquinaFinalLinea) {
		return this.getMaquinaFinal().equals(maquinaFinalLinea);
	}
	
}
