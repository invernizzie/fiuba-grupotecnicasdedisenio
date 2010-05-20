package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;


/**
 * Representa la conexión entre dos máquinas {@link Maquina} o una fuente de materia prima 
 * y una máquina. Traslada los elementos ({@link Elemento})  entre ellas.
 * @author santiago
 *
 */
public class CintaTransportadora{
	
	private ISalida extremoInicial;
	private IEntrada extremoFinal;
	
	
	public CintaTransportadora(ISalida extremoInicial,
			IEntrada extremoFinal) {
		super();
		this.extremoInicial = extremoInicial;
		this.extremoFinal = extremoFinal;
	}

	/**
	 * Traslada un elemento del extremo inicial al final.
	 */
	public void trasladarElementos(){
		Elemento elemento = this.getExtremoInicial().obtenerElemento();
		this.getExtremoFinal().agregarElemento(elemento);
	}
	
	public void setExtremoInicial(ISalida extremoInicial) {
		this.extremoInicial = extremoInicial;
	}
	public ISalida getExtremoInicial() {
		return extremoInicial;
	}
	public void setExtremoFinal(IEntrada extremoFinal) {
		this.extremoFinal = extremoFinal;
	}
	public IEntrada getExtremoFinal() {
		return extremoFinal;
	}
	
}
