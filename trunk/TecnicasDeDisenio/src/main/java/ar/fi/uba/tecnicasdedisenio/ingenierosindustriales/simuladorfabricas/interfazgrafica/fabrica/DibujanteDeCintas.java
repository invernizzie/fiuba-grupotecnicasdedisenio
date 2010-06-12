package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CintaImposibleException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasNoPertenecenAlEspacioException;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class DibujanteDeCintas extends Dibujante {

    private boolean dibujando = false;
    private int primerX, primerY;

    public DibujanteDeCintas(EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    public void mouseMove(int x, int y) { }

    @Override
    public void mouseDown(int x, int y) {
        try {
            if (getEspacioFabril().puedeComenzarCintaEn(x, y)) {
                dibujando = true;
                primerX = x;
                primerY = y;
            }
        } catch (CoordenadasNoPertenecenAlEspacioException e) {
            dibujando = false;
        }
    }

    @Override
    public void mouseUp(int x, int y) {

        if (!dibujando)
            return;

        try {
            getEspacioFabril().crearCinta(primerX, primerY, x, y);
        } catch (CintaImposibleException e) {
            // Simplemente no se crea la cinta
        } catch (CoordenadasNoPertenecenAlEspacioException e) {
            // Simplemente no se crea la cinta
        } finally {
            dibujando = false;
        }
    }
}
