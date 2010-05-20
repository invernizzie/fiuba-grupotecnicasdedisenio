package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas;

import java.util.List;

public interface InputOutput {
	public void asignarElemento(Elemento e);

	public List<Elemento> getElementos();
}
