package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class MenuItemListener extends SelectionAdapter {
	private Shell shell;

    public void widgetSelected(SelectionEvent event) {
        if (((MenuItem) event.widget).getText().equals("Exit")) {
          shell.close();
        }
     }

}