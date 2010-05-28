package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;

public class TipoMaquinaPrensa extends TipoMaquina {


	private Prensa prensa;
	
	public TipoMaquinaPrensa(ComparadorDeMaquinas comparador) {
		this.setComparador(comparador);
		prensa = new Prensa();
	}

	public TipoMaquinaPrensa(IEntrada entrada, ISalida salida) {
		this(new ComparadorDeMaquinasSimple());
		
	}

	
	@Override
	public void procesar() {
	}

	@Override
	public Maquina getInstancia() {
		IEntrada entrada = new Entrada();
		ISalida salida = new Salida();
		Prensa nuevaPrensa = prensa.clone();
		nuevaPrensa.setEntrada(entrada);
		nuevaPrensa.setSalida(salida);
		return nuevaPrensa;
	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		return (getComparador().compare(maquina, prensa) == 0);
	}

}
