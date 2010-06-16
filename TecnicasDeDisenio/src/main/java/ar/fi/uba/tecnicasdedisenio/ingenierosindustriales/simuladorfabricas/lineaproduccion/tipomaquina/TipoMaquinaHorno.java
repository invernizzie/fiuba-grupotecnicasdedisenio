package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Horno;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class TipoMaquinaHorno extends TipoMaquina {
	
	private static final float VEINTE_PORCIENTO = 0.2F;
    private static final float UNO_PORCIENTO = 0.01F;
	
	private Horno horno;
	
	public TipoMaquinaHorno(final ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		horno = new Horno(VEINTE_PORCIENTO, UNO_PORCIENTO);
	}
	
	public TipoMaquinaHorno() {
		this(new ComparadorDeMaquinasSimple());
	}
	

	@Override
	public Maquina getInstancia() {
        return horno.clone();
	}

	@Override
	public Boolean verificarTipo(final Maquina maquina) {
		return (this.getComparador().compare(maquina, horno) == 0);
	}
}
