package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

/**
 * Container class que almacena dos máquinas, o una máquina y una fuente, que se 
 * encuentran conectadas por una cinta
 * @author santiago
 */
public class FuenteSumidero {
	private IFuente origen;
	private Maquina destino;
	
	public FuenteSumidero(final IFuente origen, final Maquina destino) {
		super();
		this.setOrigen(origen);
		this.setDestino(destino);
	}

	public void setOrigen(final IFuente origen) {
		this.origen = origen;
	}

	public IFuente getOrigen() {
		return origen;
	}

	public void setDestino(final Maquina destino) {
		this.destino = destino;
	}

	public Maquina getDestino() {
		return destino;
	}
	
	public Boolean contieneMaquina(final Maquina maquina){
		return origen.equals(maquina) || destino.equals(maquina);
	}
	
}
