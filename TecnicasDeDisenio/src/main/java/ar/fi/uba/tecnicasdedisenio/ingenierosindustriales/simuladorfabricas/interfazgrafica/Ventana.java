package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


public class Ventana {
	private Integer alto;
	private Integer ancho;
	private String nombre;
	private Display display;
	private Shell shell;
	
	public Ventana(Integer alto, Integer ancho, String nombre) {
		this.alto = alto;
		this.ancho = ancho;
		this.nombre = nombre;
		this.display = new Display ();
		this.shell = new Shell (this.display);
		shell.setSize (alto,ancho);
		shell.setText(nombre);
	}

	public void dibujarMenu() {
		Menu menu = new Menu(shell, SWT.BAR);
	    MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
	    fileItem.setText("Archivo");
	    MenuItem editItem = new MenuItem(menu, SWT.CASCADE);
	    editItem.setText("Edicion");
	    MenuItem viewItem = new MenuItem(menu, SWT.CASCADE);
	    viewItem.setText("Ver");
	    MenuItem helpItem = new MenuItem(menu, SWT.CASCADE);
	    helpItem.setText("Ayuda");

	    Menu fileMenu = new Menu(menu);
	    fileItem.setMenu(fileMenu);
	    MenuItem newItem = new MenuItem(fileMenu, SWT.NONE);
	    newItem.setText("Juego Nuevo\tCtrl+N");
	    newItem.setAccelerator (SWT.MOD1 + 'N');
	    MenuItem openItem = new MenuItem(fileMenu, SWT.NONE);
	    openItem.setText("Abrir...\tCtrl+O");
	    openItem.setAccelerator (SWT.MOD1 + 'O');
	    MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
	    saveItem.setText("Guardar\tCtrl+S");
	    saveItem.setAccelerator (SWT.MOD1 + 'S');
	    MenuItem saveAsItem = new MenuItem(fileMenu, SWT.NONE);
	    saveAsItem.setText("Guardar como...\tCtrl+G");
	    saveAsItem.setAccelerator (SWT.MOD1 + 'G');
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
	    exitItem.setText("Salir\tCtrl+X");
	    exitItem.setAccelerator (SWT.MOD1 + 'X');

	    Menu editMenu = new Menu(menu);
	    editItem.setMenu(editMenu);
	    MenuItem cutItem = new MenuItem(editMenu, SWT.NONE);
	    cutItem.setText("Copiar\tCtrl+C");
	    cutItem.setAccelerator (SWT.MOD1 + 'C');
	    MenuItem pasteItem = new MenuItem(editMenu, SWT.NONE);
	    pasteItem.setText("Pegar\tCtrl+V");
	    pasteItem.setAccelerator (SWT.MOD1 + 'V');

	    Menu viewMenu = new Menu(menu);
	    viewItem.setMenu(viewMenu);
	    MenuItem toolItem = new MenuItem(viewMenu, SWT.NONE);
	    toolItem.setText("Barra de Herramientas");
	    MenuItem fontItem = new MenuItem(viewMenu, SWT.NONE);
	    fontItem.setText("Tipos de Letras");
	    
	    Menu helpMenu = new Menu(menu);
	    helpItem.setMenu(helpMenu);
	    MenuItem HelpItem = new MenuItem(helpMenu, SWT.NONE);
	    HelpItem.setText("Contenido de Ayuda...");
	    MenuItem AboutItem = new MenuItem(helpMenu, SWT.NONE);
	    AboutItem.setText("Acerca de...");
	    
	    exitItem.addSelectionListener(new MenuItemListener(this.shell));
	  
	    shell.setMenuBar(menu);
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	   }
}
	