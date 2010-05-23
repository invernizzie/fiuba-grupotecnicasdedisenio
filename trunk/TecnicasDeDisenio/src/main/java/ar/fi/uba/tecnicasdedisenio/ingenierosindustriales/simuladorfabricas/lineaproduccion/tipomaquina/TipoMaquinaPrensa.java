package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Prensa;

public class TipoMaquinaPrensa implements TipoMaquina {

	private IEntrada entrada;
	private ISalida salida;
	private Prensa prensa;
	private ComparadorDeMaquinas comparador;
	private float costo;
	
	public TipoMaquinaPrensa(ComparadorDeMaquinas comparador) {
		this.comparador = comparador;
		prensa = new Prensa();
	}

	public TipoMaquinaPrensa(IEntrada entrada, ISalida salida) {
		this(new ComparadorDeMaquinasSimple());
		this.setEntrada(entrada);
		this.setSalida(salida);
	}

	@Override
	public IEntrada getEntrada() {
		return entrada;
	}

	@Override
	public ISalida getSalida() {
		return salida;
	}

	@Override
	public void setEntrada(IEntrada entrada) {
		this.entrada = entrada;
	}
	
	@Override
	public void setSalida(ISalida salida) {
		this.salida = salida;
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
		return (comparador.compare(maquina, prensa) == 0);
	}

	@Override
	public float getCosto() {
		return this.costo;
	}

	@Override
	public void setCosto(float costo) {
		this.costo = costo;	
	}

}
