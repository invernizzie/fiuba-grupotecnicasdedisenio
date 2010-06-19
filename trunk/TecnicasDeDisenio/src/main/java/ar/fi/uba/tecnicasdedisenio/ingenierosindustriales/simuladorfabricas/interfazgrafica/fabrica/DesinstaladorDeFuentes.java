package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasIncorrectasException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 * @created 13/06/2010
 */
public class DesinstaladorDeFuentes extends Instalador {

    public DesinstaladorDeFuentes(final EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    public void doMouseMove(final int x, final int y) {
    }

    @Override
    public void doMouseDown(final int x, final int y) {
        try {
            getEspacioFabril().borrarFuente(x, y);
        } catch (CoordenadasIncorrectasException ignored) {
        } catch (CubiculoVacioException ignored) { }
    }

    @Override
    public void doMouseUp(final int x, final int y) {
    }
}
