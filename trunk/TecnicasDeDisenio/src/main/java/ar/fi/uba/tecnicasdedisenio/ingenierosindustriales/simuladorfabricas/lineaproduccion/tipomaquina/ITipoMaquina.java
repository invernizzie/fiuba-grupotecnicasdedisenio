package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public interface ITipoMaquina {
	
	
	public void procesar();
	
	/**
	 * Construye una nueva instancia del tipo de máquina al que se hace
	 * referencia.
	 * @return
	 */
	public Maquina getInstancia();
	
	/**
	 * Comprueba si la máquina recibida por parámetro es del tipo correspondiente.
	 * @param maquina
	 * @return
	 */
	public Boolean verificarTipo(Maquina maquina);
}
