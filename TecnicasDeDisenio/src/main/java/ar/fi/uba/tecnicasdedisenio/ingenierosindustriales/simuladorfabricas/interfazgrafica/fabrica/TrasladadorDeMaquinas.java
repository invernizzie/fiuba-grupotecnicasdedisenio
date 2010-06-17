package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasIncorrectasException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 14/06/2010
 */
public class TrasladadorDeMaquinas extends Instalador {
    private boolean trasladando;
    private int xOriginal;
    private int yOriginal;

    public TrasladadorDeMaquinas(final EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    public void doMouseMove(final int x, final int y) {
        // TODO Previsualizar
    }

    @Override
    public void doMouseDown(final int x, final int y) {
        if (getEspacioFabril().hayMaquinaEn(x, y)) {
            trasladando = true;
            xOriginal = x;
            yOriginal = y;
        }
    }

    @Override
    public void doMouseUp(int x, int y) {

        if (trasladando) {
            try {
                getEspacioFabril().moverMaquina(xOriginal, yOriginal, x, y);
            } catch (CoordenadasIncorrectasException e) {
                // Simplemente no se mueve
            } catch (CubiculoVacioException e) {
                // Simplemente no se mueve
            } catch (EspacioOcupadoException e) {
                // Simplemente no se mueve
            }
        }

        trasladando = false;
    }
}
