package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;

public class TipoMaquinaPrensa extends TipoMaquina {


	private Prensa prensa;
	
	public TipoMaquinaPrensa(ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		prensa = new Prensa(0.2F, 0.01F);
	}
	
	public TipoMaquinaPrensa() {
		this(new ComparadorDeMaquinasSimple());
	}
	

	@Override
	public Maquina getInstancia() {
		Prensa nuevaPrensa = prensa.clone();
		return nuevaPrensa;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (this.getComparador().compare(maquina, prensa) == 0);
	}

}
