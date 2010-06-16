package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Licuadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class TipoMaquinaLicuadora extends TipoMaquina {
	
	private static final float CUARENTA_PORCIENTO = 0.4F;
    private static final float UNO_PORCIENTO = 0.01F;
    
	private Licuadora licuadora;
	
	public TipoMaquinaLicuadora(final ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		licuadora = new Licuadora(CUARENTA_PORCIENTO, UNO_PORCIENTO);
	}
	
	public TipoMaquinaLicuadora() {
		this(new ComparadorDeMaquinasSimple());
	}
	

	@Override
	public Maquina getInstancia() {
		Licuadora nuevaLicuadora = licuadora.clone();
		return nuevaLicuadora;
	}

	@Override
	public Boolean verificarTipo(final Maquina maquina) {
		return (this.getComparador().compare(maquina, licuadora) == 0);
	}
}
