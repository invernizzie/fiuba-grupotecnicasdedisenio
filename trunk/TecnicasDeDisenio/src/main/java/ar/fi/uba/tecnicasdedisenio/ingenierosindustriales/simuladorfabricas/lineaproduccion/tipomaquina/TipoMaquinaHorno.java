package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Horno;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class TipoMaquinaHorno extends TipoMaquina {

	private Horno horno;
	
	public TipoMaquinaHorno(ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		horno = new Horno(0.2F, 0.01F);
	}
	
	public TipoMaquinaHorno() {
		this(new ComparadorDeMaquinasSimple());
	}
	

	@Override
	public Maquina getInstancia() {
		Horno nuevoHorno = horno.clone();
		return nuevoHorno;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (this.getComparador().compare(maquina, horno) == 0);
	}

}
