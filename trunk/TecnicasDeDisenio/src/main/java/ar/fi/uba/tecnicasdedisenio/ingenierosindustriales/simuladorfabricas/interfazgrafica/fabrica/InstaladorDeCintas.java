package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CintaImposibleException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasIncorrectasException;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class InstaladorDeCintas extends Instalador {

    private boolean dibujando = false;
    private int primerX, primerY;

    public InstaladorDeCintas(EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    public void doMouseMove(int x, int y) { }

    @Override
    public void doMouseDown(int x, int y) {
        try {
            if (getEspacioFabril().puedeComenzarCintaEn(x, y)) {
                dibujando = true;
                primerX = x;
                primerY = y;
            }
        } catch (CoordenadasIncorrectasException e) {
            dibujando = false;
        }
    }

    @Override
    public void doMouseUp(int x, int y) {

        if (!dibujando)
            return;

        try {
            getEspacioFabril().crearCinta(primerX, primerY, x, y);
        } catch (CintaImposibleException e) {
            // Simplemente no se crea la cinta
        } catch (CoordenadasIncorrectasException e) {
            // Simplemente no se crea la cinta
        } finally {
            dibujando = false;
        }
    }
}
