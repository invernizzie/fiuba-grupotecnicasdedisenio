package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;

public class TipoMaquinaPrensa extends TipoMaquina {

	private static final float VEINTE_PORCIENTO = 0.2F;
    private static final float UNO_PORCIENTO = 0.01F;
    
	private Prensa prensa;

    public TipoMaquinaPrensa(final ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		prensa = new Prensa(VEINTE_PORCIENTO, UNO_PORCIENTO);
	}
	
	public TipoMaquinaPrensa() {
		this(new ComparadorDeMaquinasSimple());
	}
	

	@Override
	public Maquina getInstancia() {
        return prensa.clone();
	}

	@Override
	public Boolean verificarTipo(final Maquina maquina) {
		return (this.getComparador().compare(maquina, prensa) == 0);
	}
}
