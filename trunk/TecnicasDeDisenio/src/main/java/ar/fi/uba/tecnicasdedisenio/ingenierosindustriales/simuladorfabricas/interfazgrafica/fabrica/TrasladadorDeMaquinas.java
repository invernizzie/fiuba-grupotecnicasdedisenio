package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasIncorrectasException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 14/06/2010
 */
public class TrasladadorDeMaquinas extends InstaladorDragAndDrop {

    public TrasladadorDeMaquinas(final EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    protected boolean puedeDraggearDesdeAqui(int x, int y) {
        return getEspacioFabril().hayMaquinaEn(x, y);
    }

    @Override
    protected void droppear(int xInicial, int yInicial, int xFinal, int yFinal) {
        try {
            getEspacioFabril().moverMaquina(xInicial, yInicial, xFinal, yFinal);
        } catch (CoordenadasIncorrectasException e) {
            // Simplemente no se mueve
        } catch (CubiculoVacioException e) {
            // Simplemente no se mueve
        } catch (EspacioOcupadoException e) {
            // Simplemente no se mueve
        }
    }

    @Override
    protected void previsualizar(int xInicial, int yInicial, int x, int y) { }
}
