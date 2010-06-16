package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasIncorrectasException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;

/**
 * @author Esteban I. Invernizzi
 *         Date 14/06/2010
 */
public class ReparadorDeMaquinas extends Instalador {

    public ReparadorDeMaquinas(final EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    public void doMouseMove(final int x, final int y) { }

    @Override
    public void doMouseDown(final int x, final int y) {
        try {
            getEspacioFabril().repararMaquina(x, y);
        } catch (CoordenadasIncorrectasException ignored) {
            // Simplemente no se repara nada
        } catch (CubiculoVacioException ignored) {
            // Simplemente no se repara nada
        }
    }

    @Override
    public void doMouseUp(final int x, final int y) { }
}
