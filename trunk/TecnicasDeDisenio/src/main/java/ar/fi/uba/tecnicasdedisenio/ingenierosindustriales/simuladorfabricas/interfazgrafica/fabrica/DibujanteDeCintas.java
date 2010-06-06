package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class DibujanteDeCintas extends Dibujante {

    private boolean dibujando = false;
    private int primerX, primerY;
    

    /*public DibujanteDeCintas(EspacioFabril espacioFabril) {
        super(espacioFabril);
    }*/

    public DibujanteDeCintas(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void mouseMove(int x, int y) { }

    @Override
    public void mouseDown(int x, int y) {
        /*if (getEspacioFabril().puedeComenzarCintaEn(x, y)) {
            dibujando = true;
            primerX = x;
            primerY = y;
        }*/
        primerX = x;
        primerY = y;
    }

    @Override
    public void mouseUp(int x, int y) {

        dibujando = false;
        //getEspacioFabril().crearCinta(primerX, primerY, x, y);

        GC gc = new GC(getCanvas());
        gc.drawLine (primerX, primerY, x, y);
        gc.dispose ();
    }
}
