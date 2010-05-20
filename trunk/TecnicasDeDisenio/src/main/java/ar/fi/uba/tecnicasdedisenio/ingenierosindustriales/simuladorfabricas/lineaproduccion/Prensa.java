package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Elemento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Prensado;

/**
 * Maquina ({@link Maquina}) encargada de prensar un elemento ({@link Elemento}).
 * @author santiago
 *
 */
public class Prensa extends Maquina {

	@Override
	protected Elemento realizarProceso() {
// TODO Definir como vamos a manejar la diferencia entre procesos que 
// afectan a un elemento y procesos que afectan a varios.
// ¿Cómo sabemos de que tipo es cada elemento de una manera elegante?
		return new Prensado(this.getElementos().get(0));
	}

}
