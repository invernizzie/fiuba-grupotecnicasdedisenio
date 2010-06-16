package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.ControlCalidad;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class TipoMaquinaControlCalidad extends TipoMaquina {

	private ControlCalidad controlCalidad;
	
	public TipoMaquinaControlCalidad(ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		controlCalidad = new ControlCalidad(0F, 0F);
	}
	
	public TipoMaquinaControlCalidad() {
		this(new ComparadorDeMaquinasSimple());
	}
	

	@Override
	public Maquina getInstancia() {
		ControlCalidad nuevoControlCalidad = controlCalidad.clone();
		return nuevoControlCalidad;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (this.getComparador().compare(maquina, controlCalidad) == 0);
	}
}
