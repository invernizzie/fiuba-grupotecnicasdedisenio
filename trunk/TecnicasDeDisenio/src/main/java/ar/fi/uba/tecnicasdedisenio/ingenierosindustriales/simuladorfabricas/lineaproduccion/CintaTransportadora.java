package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;


/**
 * Representa la conexión entre dos máquinas {@link Maquina} o una fuente de materia prima
 * y una máquina. Traslada los elementos ({@link Producto})  entre ellas.
 * @author santiago
 *
 */
public class CintaTransportadora {
	
	private static final float COSTOXMETRO = 1;
	
	private ISalida extremoInicial;
	private IEntrada extremoFinal;
	private float longitud;
	
	public CintaTransportadora(final float longitud) {
		this.longitud = longitud;
	}
	
	public CintaTransportadora(final ISalida extremoInicial, final IEntrada extremoFinal) {
		this.extremoInicial = extremoInicial;
		this.extremoFinal = extremoFinal;
		this.longitud = 0;
	}

	/**
	 * Traslada un elemento del extremo inicial al final.
	 */
	public void trasladarElementos() {
		Producto producto = this.getExtremoInicial().obtenerProducto();
		this.getExtremoFinal().agregarProducto(producto);
	}
	
	public void setExtremoInicial(final ISalida extremoInicial) {
		this.extremoInicial = extremoInicial;
	}
	
	public ISalida getExtremoInicial() {
		return extremoInicial;
	}
	
	public void setExtremoFinal(final IEntrada extremoFinal) {
		this.extremoFinal = extremoFinal;
	}
	
	public IEntrada getExtremoFinal() {
		return extremoFinal;
	}
	
	public void conectar(final Maquina origen, final Maquina destino) {
		this.extremoInicial = origen.getSalida();
		this.extremoFinal = destino.getEntrada();
		
		origen.setCintaSalida(this);
		destino.addCintaEntrada(this);
		
		origen.setSiguiente(destino);
		destino.addPrecedente(origen);
		
	}

	public void conectar(final Fuente origen, final Maquina destino) {
		this.extremoInicial = origen.getSalida();
		this.extremoFinal = destino.getEntrada();
		
		origen.agregarCinta(this);
		destino.addCintaEntrada(this);
		
		destino.addMateriaPrima(origen.getTipoProducto());
		destino.addFuente(origen);
	}
	
	public void desconectar(final Maquina origen, final Maquina destino) {
		this.extremoInicial = null;
		this.extremoFinal = null;
		
		origen.setCintaSalida(null);
		destino.removeCintaEntrada(this);
		
		destino.removePrecedente(origen);
	}

	public void desconectar(final Fuente origen, final Maquina destino) {
		this.extremoInicial = null;
		this.extremoFinal = null;
		origen.removerCinta(this);
		destino.removeMateriaPrima(origen.getTipoProducto());
		destino.removeFuente(origen);
		destino.removeCintaEntrada(this);
	}
	
	public float getCostoConectar() {
		return COSTOXMETRO * longitud;
	}

	public void desconectar(final IFuente fuente, final Maquina maquina) {
		if (fuente instanceof Fuente) {
			desconectar((Fuente) fuente, maquina);
        } else {
        	desconectar((Maquina) fuente, maquina);
        }
	}
	
}
