package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;

public class MenuBar {
	private Menu menuBar;
	private VistaPrincipal vista; 

	
	public Menu getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(Menu menuBar) {
		this.menuBar = menuBar;
	}
	
	public MenuBar(final VistaPrincipal menu) {
		vista = menu;
		menuBar = new Menu(vista.getShellPrincipal(), SWT.BAR);
		menuBar.setEnabled(true);
        MenuItem submenuItemJuego = crearSubmenu(menuBar, "Juego");
        MenuItem submenuItemAyuda = crearSubmenu(menuBar, "Ayuda");
        Menu submenuAyuda = new Menu(submenuItemAyuda);
        MenuItem pushContenido = crearItemDeMenu(submenuAyuda, "Contenido");
		@SuppressWarnings("unused")
		MenuItem separatorAyuda = new MenuItem(submenuAyuda, SWT.SEPARATOR);
        MenuItem pushAcercaDe = crearItemDeMenu(submenuAyuda, "Acerca de...");
		submenuItemAyuda.setMenu(submenuAyuda);
        Menu submenuJuego = new Menu(submenuItemJuego);
		submenuJuego.setVisible(true);
		submenuJuego.setEnabled(true);
		submenuItemJuego.setMenu(submenuJuego);
        MenuItem pushJuegoNuevo = crearItemDeMenu(submenuJuego, "Juego Nuevo");
		pushJuegoNuevo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(final SelectionEvent selectionEvent) {
			}
			public void widgetSelected(final SelectionEvent selectionEvent) {
				System.out.println("widgetDefaultSelected()");
				vista.juegoNuevo();
			}
		});
        MenuItem pushAbrir = crearItemDeMenu(submenuJuego, "Abrir");
		pushAbrir.setEnabled(false);
        MenuItem pushGuardar = crearItemDeMenu(submenuJuego, "Guardar");
		pushGuardar.setEnabled(false);
        MenuItem pushGuardarComo = crearItemDeMenu(submenuJuego, "Guardar Como");
		pushGuardarComo.setEnabled(false);
		new MenuItem(submenuJuego, SWT.SEPARATOR);
        MenuItem pushSalir = crearItemDeMenu(submenuJuego, "Salir");
		pushSalir.addSelectionListener(new SelectionListener() {
			public void widgetSelected(final SelectionEvent e) {
				Calendario.instancia().detener();
				vista.getShellPrincipal().close();
			}
			public void widgetDefaultSelected(final SelectionEvent e) {
			}
		});
		pushContenido.addSelectionListener(new SelectionListener() {
			public void widgetSelected(final SelectionEvent e) {
				 MessageBox messageBox = new MessageBox(vista.getShellPrincipal(), SWT.OK | SWT.ICON_INFORMATION);
				 String mensaje = "Contenido:\n";
				 mensaje += "TP Tecnicas de Diseno 2010 - Simulador de Fabricas";
				 messageBox.setMessage(mensaje);
				 messageBox.open();
			}
			public void widgetDefaultSelected(SelectionEvent selectionEvent) {
			}
		});
		pushAcercaDe.addSelectionListener(new SelectionListener() {
			public void widgetSelected(final SelectionEvent selectionEvent) {
				 MessageBox messageBox = new MessageBox(vista.getShellPrincipal(), SWT.OK | SWT.ICON_INFORMATION);
				 String mensaje = "Creditos:\n";
				 mensaje += "Esteban Invernizzi\n"; 
				 mensaje += "Gustavo Meller\n"; 
				 mensaje += "Santiago Risaro\n"; 
				 mensaje += "Diego Garcia Jaime\n"; 
				 messageBox.setMessage(mensaje);
				 messageBox.open();
			}
			public void widgetDefaultSelected(final SelectionEvent selectionEvent) {
			}
		});

	}

    private MenuItem crearItemDeMenu(final Menu padre, final String texto) {
        MenuItem itemDeMenu = new MenuItem(padre, SWT.PUSH);
        itemDeMenu.setText(texto);
        return itemDeMenu;
    }
    
    private MenuItem crearSubmenu(final Menu padre, final String texto) {
        MenuItem submenu = new MenuItem(padre, SWT.CASCADE);
        submenu.setText(texto);
        return submenu;
    }
}