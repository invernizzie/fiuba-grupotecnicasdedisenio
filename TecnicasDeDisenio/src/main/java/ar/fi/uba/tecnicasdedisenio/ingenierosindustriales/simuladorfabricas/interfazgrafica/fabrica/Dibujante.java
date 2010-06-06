package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import org.eclipse.swt.widgets.Canvas;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public abstract class Dibujante {

    /* TODO Cambiar por un canvas cuadriculado
     * que resuelva el dibujo y las colisiones,
     * sabiendo que hay en cada posicion de la
     * cuadricula.     */
    private Canvas canvas;

    public Dibujante(Canvas canvas) {
        this.canvas = canvas;
    }

    protected Canvas getCanvas() {
        return canvas;
    }

    public abstract void mouseMove(int x, int y) ;

    public abstract void mouseDown(int x, int y);

    public abstract void mouseUp(int x, int y);
}
