package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class ConstructorDeFabricas implements Listener {

    private Instalador instalador;

    public ConstructorDeFabricas(final Instalador instaladorInicial) {
        instalador = instaladorInicial;
    }

    public void setInstalador(final Instalador instalador) {
        this.instalador = instalador;
    }

    public void handleEvent(final Event event) {

        switch (event.type) {
            case SWT.MouseMove:
                instalador.mouseMove(event.x, event.y);
                break;

            case SWT.MouseDown:
                instalador.mouseDown(event.x, event.y);
                break;

            case SWT.MouseUp:
                if ((event.stateMask & SWT.BUTTON1) == 0) {
                    return;
                }
                instalador.mouseUp(event.x, event.y);
                break;
        }
    }
}
