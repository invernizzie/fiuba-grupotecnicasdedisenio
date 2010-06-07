package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Mezcladora;

public class TipoMaquinaMezcladora extends TipoMaquina {

	private Mezcladora mezcladora;
	
	public TipoMaquinaMezcladora(ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		mezcladora = new Mezcladora(0.1F, 0.01F);
	}
	
	public TipoMaquinaMezcladora() {
		this(new ComparadorDeMaquinasSimple());
	}

	@Override
	public Maquina getInstancia() {
		Mezcladora nuevaMezcladora = mezcladora.clone();
		return nuevaMezcladora;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (this.getComparador().compare(maquina, mezcladora) == 0);
	}

}
