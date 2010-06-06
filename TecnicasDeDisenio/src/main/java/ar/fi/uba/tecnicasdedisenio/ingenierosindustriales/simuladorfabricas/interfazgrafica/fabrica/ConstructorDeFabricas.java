package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.Dibujante;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class ConstructorDeFabricas implements Listener {

    private Dibujante dibujante;
    
    public ConstructorDeFabricas(Dibujante dibujanteInicial) {
        dibujante = dibujanteInicial;
    }

    public void setDibujante(Dibujante dibujante) {
        this.dibujante = dibujante;
    }

    public void handleEvent (Event event) {

        switch (event.type) {
            case SWT.MouseMove:
                dibujante.mouseMove(event.x, event.y);
                break;

            case SWT.MouseDown:
                dibujante.mouseDown(event.x, event.y);
                break;

            case SWT.MouseUp:
                if ((event.stateMask & SWT.BUTTON1) == 0)
                    return;
                dibujante.mouseUp(event.x, event.y);
                break;
        }
    }
}
