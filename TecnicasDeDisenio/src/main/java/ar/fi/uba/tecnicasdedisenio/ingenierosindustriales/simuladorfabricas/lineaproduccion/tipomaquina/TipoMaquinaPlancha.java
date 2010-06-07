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
	
	public TipoMaquinaPlancha(){
		this(new ComparadorDeMaquinasSimple());
	}

	@Override
	public Maquina getInstancia() {
		Plancha nuevaPlancha = plancha.clone();
		return nuevaPlancha;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (this.getComparador().compare(maquina, plancha) == 0);
	}

}
