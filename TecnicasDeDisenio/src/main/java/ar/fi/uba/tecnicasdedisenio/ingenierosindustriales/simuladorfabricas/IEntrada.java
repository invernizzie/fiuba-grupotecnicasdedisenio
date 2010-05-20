package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.List;



public interface IEntrada {
	public void agregarElemento(Elemento e);
	public List<Elemento> getElementos();
}
