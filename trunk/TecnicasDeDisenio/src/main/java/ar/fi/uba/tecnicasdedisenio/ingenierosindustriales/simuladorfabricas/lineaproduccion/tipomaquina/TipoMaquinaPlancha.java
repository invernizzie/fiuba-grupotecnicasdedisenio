package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Plancha;

public class TipoMaquinaPlancha extends TipoMaquina {

	private Plancha plancha;
	private ComparadorDeMaquinas comparador;
	
	
	
	public TipoMaquinaPlancha(ComparadorDeMaquinas comparador) {
		this.setComparador(comparador);
		plancha = new Plancha();
	}

	public TipoMaquinaPlancha(IEntrada entrada, ISalida salida) {
		this(new ComparadorDeMaquinasSimple());
		
	}

	@Override
	public void procesar() {
	}

	@Override
	public Maquina getInstancia() {
		IEntrada entrada = new Entrada();
		ISalida salida = new Salida();
		Plancha nuevaPrensa = plancha.clone();
		nuevaPrensa.setEntrada(entrada);
		nuevaPrensa.setSalida(salida);
		return nuevaPrensa;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (comparador.compare(maquina, plancha) == 0);
	}

}
