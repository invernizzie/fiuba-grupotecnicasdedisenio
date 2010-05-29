package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;


/**
 * Representa la conexión entre dos máquinas {@link Maquina} o una fuente de materia prima 
 * y una máquina. Traslada los elementos ({@link Producto})  entre ellas.
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
		Producto producto = this.getExtremoInicial().obtenerProducto();
		this.getExtremoFinal().agregarProducto(producto);
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
	
	public void conectar(Maquina origen, Maquina destino){
		this.extremoInicial = origen.getSalida();
		this.extremoFinal = destino.getEntrada();
		destino.addPrecedente(origen);
	}

	public void conectar(Fuente origen, Maquina destino){
		this.extremoInicial = origen.getSalida();
		this.extremoFinal = destino.getEntrada();
		destino.addMateriaPrima(origen.getTipoProducto());
	}
	
	public void desconectar(Maquina origen, Maquina destino){
		this.extremoInicial = null;
		this.extremoFinal = null;
		destino.removePrecedente(origen);
	}

	public void desconectar(Fuente origen, Maquina destino){
		this.extremoInicial = null;
		this.extremoFinal = null;
		destino.removeMateriaPrima(origen.getTipoProducto());
	}
}
