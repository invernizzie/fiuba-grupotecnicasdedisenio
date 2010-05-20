package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.InputOutput;


/**
 * Representa la conexión entre dos máquinas {@link Maquina} o una fuente de materia prima 
 * y una máquina. Traslada los elementos ({@link Elemento})  entre ellas.
 * @author santiago
 *
 */
public class CintaTransportadora{
	
	private InputOutput extremoInicial;
	private InputOutput extremoFinal;
	
	
	public CintaTransportadora(InputOutput extremoInicial,
			InputOutput extremoFinal) {
		super();
		this.extremoInicial = extremoInicial;
		this.extremoFinal = extremoFinal;
	}

	/**
	 * Traslada todos los elementos del extremo inicial al final.
	 */
	public void trasladarElementos(){
		
		List<Elemento> elementos = this.getExtremoInicial().getElementos();
		for (Elemento elemento : elementos) {
			this.getExtremoFinal().asignarElemento(elemento);
		}
		
	}
	
	public void setExtremoInicial(InputOutput extremoInicial) {
		this.extremoInicial = extremoInicial;
	}
	public InputOutput getExtremoInicial() {
		return extremoInicial;
	}
	public void setExtremoFinal(InputOutput extremoFinal) {
		this.extremoFinal = extremoFinal;
	}
	public InputOutput getExtremoFinal() {
		return extremoFinal;
	}
	
}
