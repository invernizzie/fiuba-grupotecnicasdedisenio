package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 05/06/2010
 */
public class DialogoMensaje {
    private static final int MARGEN = 8;
    private Shell dialogo;
    private Label campoDelMensaje;
    private Button botonOk;

    public DialogoMensaje(final String mensaje) {
        crearCuadroDeDialogo();
        crearCampoDelMensaje(mensaje);
        crearBotonOk();
        configurarYMostrarCuadroDeDialogo();
        esperarASerCerrado();
    }

    private void crearCuadroDeDialogo() {
        dialogo = new Shell(SWT.DIALOG_TRIM);
        FormLayout layoutParaElDialogo = new FormLayout();
        layoutParaElDialogo.marginWidth = MARGEN;
        layoutParaElDialogo.marginHeight = MARGEN;
        dialogo.setLayout(layoutParaElDialogo);
    }

    private void crearCampoDelMensaje(String mensaje) {
        campoDelMensaje = new Label(dialogo, SWT.NONE);
        campoDelMensaje.setText(mensaje);
    }

    private void crearBotonOk() {
        FormData layoutParaElBotonOk = new FormData();
        layoutParaElBotonOk.top = new FormAttachment(campoDelMensaje, MARGEN);
        botonOk = new Button(dialogo, SWT.PUSH);
        botonOk.setText("&Ok");
        botonOk.setLayoutData(layoutParaElBotonOk);

        botonOk.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(final SelectionEvent selectionEvent) {
                dialogo.close();
            }
            @Override
            public void widgetDefaultSelected(final SelectionEvent selectionEvent) { }
        });
    }

    private void configurarYMostrarCuadroDeDialogo() {
        dialogo.setDefaultButton(botonOk);
        dialogo.pack();
        dialogo.open();
    }

    private void esperarASerCerrado() {
        while (!dialogo.isDisposed()) {
            if (!Display.getCurrent().readAndDispatch()) { Display.getCurrent().sleep(); }
        }
    }
}
