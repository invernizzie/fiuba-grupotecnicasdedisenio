package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Licuadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class TipoMaquinaLicuadora extends TipoMaquina {

	private Licuadora licuadora;
	
	public TipoMaquinaLicuadora(ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		licuadora = new Licuadora(0.4F, 0.01F);
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
	public Boolean verificarTipo(Maquina maquina) {
		return (this.getComparador().compare(maquina, licuadora) == 0);
	}

}
