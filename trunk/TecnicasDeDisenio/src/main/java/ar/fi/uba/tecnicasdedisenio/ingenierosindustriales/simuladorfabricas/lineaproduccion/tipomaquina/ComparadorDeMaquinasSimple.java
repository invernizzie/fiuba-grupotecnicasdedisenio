package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

public class ComparadorDeMaquinasSimple implements ComparadorDeMaquinas {

	@Override
	public int compare(final Maquina maquina1, final Maquina maquina2) {
		int resultadoComparacion = -1;
		if (maquina1.getClass().equals(maquina2.getClass())) {
			resultadoComparacion = 0;
		}
		return resultadoComparacion;
	}

}
