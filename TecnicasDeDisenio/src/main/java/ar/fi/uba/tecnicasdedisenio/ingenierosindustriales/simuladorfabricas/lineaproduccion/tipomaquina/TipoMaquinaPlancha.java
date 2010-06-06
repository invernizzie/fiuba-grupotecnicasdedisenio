package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Plancha;

public class TipoMaquinaPlancha extends TipoMaquina {

	private Plancha plancha;
	
	
	
	public TipoMaquinaPlancha(ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		plancha = new Plancha(0.3F, 0.01F);
		
	}

	/*public TipoMaquinaPlancha(IEntrada entrada, ISalida salida) {
		this(new ComparadorDeMaquinasSimple());
		
	}*/
	
	public TipoMaquinaPlancha(){
		this(new ComparadorDeMaquinasSimple());
	}

	@Override
	public Maquina getInstancia() {
		Plancha nuevaPrensa = plancha.clone();
		return nuevaPrensa;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (this.getComparador().compare(maquina, plancha) == 0);
	}

}
