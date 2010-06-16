package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Mezcladora;

public class TipoMaquinaMezcladora extends TipoMaquina {
	
	private static final float DIEZ_PORCIENTO = 0.1F;
    private static final float UNO_PORCIENTO = 0.01F;
	
	private Mezcladora mezcladora;
	
	public TipoMaquinaMezcladora(final ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		mezcladora = new Mezcladora(DIEZ_PORCIENTO, UNO_PORCIENTO);
	}
	
	public TipoMaquinaMezcladora() {
		this(new ComparadorDeMaquinasSimple());
	}

	@Override
	public Maquina getInstancia() {
        return mezcladora.clone();
	}

	@Override
	public Boolean verificarTipo(final Maquina maquina) {
		return (this.getComparador().compare(maquina, mezcladora) == 0);
	}
}
