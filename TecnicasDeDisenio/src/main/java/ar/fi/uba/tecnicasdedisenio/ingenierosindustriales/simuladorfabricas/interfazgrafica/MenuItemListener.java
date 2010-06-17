package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class MenuItemListener extends SelectionAdapter {
	private Shell shell;

    public MenuItemListener(final Shell shell) {
		this.shell = shell;
	}

	public void widgetSelected(final SelectionEvent event) {
        if (((MenuItem) event.widget).getText().equals("Salir\tCtrl+X")) {
          shell.close();
        }
     }


}