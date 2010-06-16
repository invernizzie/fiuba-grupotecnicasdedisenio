package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Plancha;

public class TipoMaquinaPlancha extends TipoMaquina {

	private static final float TREINTA_PORCIENTO = 0.3F;
    private static final float UNO_PORCIENTO = 0.01F;
	
    private Plancha plancha;
    
	public TipoMaquinaPlancha(ComparadorDeMaquinas comparador) {
		super();
		this.setComparador(comparador);
		plancha = new Plancha(TREINTA_PORCIENTO, UNO_PORCIENTO);
		
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
