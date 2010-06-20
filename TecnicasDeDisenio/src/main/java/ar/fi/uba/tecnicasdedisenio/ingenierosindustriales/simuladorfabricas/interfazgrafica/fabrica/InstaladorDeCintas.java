package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CintaImposibleException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasIncorrectasException;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class InstaladorDeCintas extends InstaladorDragAndDrop {

    public InstaladorDeCintas(final EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    protected boolean puedeDraggearDesdeAqui(int x, int y) {
        try {
            return getEspacioFabril().puedeComenzarCintaEn(x, y);
        } catch (CoordenadasIncorrectasException e) {
            return false;
        }
    }

    @Override
    protected void droppear(int xInicial, int yInicial, int xFinal, int yFinal) {
        try {
            getEspacioFabril().crearCinta(xInicial, yInicial, xFinal, yFinal);
        } catch (CintaImposibleException e) {
            // Simplemente no se crea la cinta
        } catch (CoordenadasIncorrectasException e) {
            // Simplemente no se crea la cinta
        }
    }

    @Override
    protected void previsualizar(int xInicial, int yInicial, int x, int y) { }
}
