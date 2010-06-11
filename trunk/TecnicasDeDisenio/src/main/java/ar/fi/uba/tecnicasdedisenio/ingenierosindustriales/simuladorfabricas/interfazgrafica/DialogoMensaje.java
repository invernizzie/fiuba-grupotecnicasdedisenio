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
   
	public DialogoMensaje(String mensaje) {
        Display display = Display.getCurrent();
        final Shell dialog = new Shell (SWT.DIALOG_TRIM);
        Label label = new Label (dialog, SWT.NONE);
        label.setText (mensaje);
        Button okButton = new Button (dialog, SWT.PUSH);
        okButton.setText ("&Ok");

        FormLayout form = new FormLayout ();
        form.marginWidth = form.marginHeight = 8;
        dialog.setLayout (form);
        FormData okData = new FormData ();
        okData.top = new FormAttachment(label, 8);
        okButton.setLayoutData (okData);
        okButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                dialog.close();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {}
        });

        dialog.setDefaultButton (okButton);
        dialog.pack ();
        dialog.open ();

        while (!dialog.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
    }
}
