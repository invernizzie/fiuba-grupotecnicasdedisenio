package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public interface TipoMaquina {
	
	
	public void setEntrada(IEntrada entrada);
	
	public IEntrada getEntrada();
	
	public void setSalida(ISalida salida);
	
	public ISalida getSalida();
	
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
