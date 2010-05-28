package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import java.util.ArrayList;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

public abstract class TipoMaquina implements ITipoMaquina {
	
	private float costo;
	private ArrayList<Producto> materiasPrimas;
	private ArrayList<TipoMaquina> precedentes;
	private ComparadorDeMaquinas comparador;
	
	@Override
	public Maquina getInstancia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void procesar() {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean verificarTipo(Maquina maquina) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public float getCosto() {
		return costo;
	}
	
	public void setMateriasPrimas(ArrayList<Producto> materiasPrimas) {
		this.materiasPrimas = materiasPrimas;
	}

	public ArrayList<Producto> getMateriasPrimas() {
		return materiasPrimas;
	}

	public void setPrecedentes(ArrayList<TipoMaquina> precedentes) {
		this.precedentes = precedentes;
	}

	public ArrayList<TipoMaquina> getPrecedentes() {
		return precedentes;
	}

	public void setComparador(ComparadorDeMaquinas comparador) {
		this.comparador = comparador;
	}

	public ComparadorDeMaquinas getComparador() {
		return comparador;
	}
	
	public boolean comparar(Maquina maquinaFinalLinea){
		if(this.verificarTipo(maquinaFinalLinea))
			return true;
		return false;
	}
	
}
