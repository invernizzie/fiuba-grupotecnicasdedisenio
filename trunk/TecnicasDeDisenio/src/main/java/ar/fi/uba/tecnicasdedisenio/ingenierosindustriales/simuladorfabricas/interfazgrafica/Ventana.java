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
		//itemArchivoSalir.setText ("Salir\tCtrl+X");
		//itemArchivoSalir.setAccelerator (SWT.MOD1 + 'X');
		Menu menu = new Menu(shell, SWT.BAR);
	    MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
	    fileItem.setText("File");
	    MenuItem editItem = new MenuItem(menu, SWT.CASCADE);
	    editItem.setText("Edit");
	    MenuItem viewItem = new MenuItem(menu, SWT.CASCADE);
	    viewItem.setText("View");
	    MenuItem helpItem = new MenuItem(menu, SWT.CASCADE);
	    helpItem.setText("Help");

	    Menu fileMenu = new Menu(menu);
	    fileItem.setMenu(fileMenu);
	    MenuItem newItem = new MenuItem(fileMenu, SWT.NONE);
	    newItem.setText("New");
	    MenuItem openItem = new MenuItem(fileMenu, SWT.NONE);
	    openItem.setText("Open...");
	    MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
	    saveItem.setText("Save");
	    MenuItem saveAsItem = new MenuItem(fileMenu, SWT.NONE);
	    saveAsItem.setText("Save As...");
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    MenuItem pageSetupItem = new MenuItem(fileMenu, SWT.NONE);
	    pageSetupItem.setText("Page Setup...");
	    MenuItem printItem = new MenuItem(fileMenu, SWT.NONE);
	    printItem.setText("Print...");
	    new MenuItem(fileMenu, SWT.SEPARATOR);
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
	    exitItem.setText("Exit");

	    Menu editMenu = new Menu(menu);
	    editItem.setMenu(editMenu);
	    MenuItem cutItem = new MenuItem(editMenu, SWT.NONE);
	    cutItem.setText("Cut");
	    MenuItem pasteItem = new MenuItem(editMenu, SWT.NONE);
	    pasteItem.setText("Paste");

	    Menu viewMenu = new Menu(menu);
	    viewItem.setMenu(viewMenu);
	    MenuItem toolItem = new MenuItem(viewMenu, SWT.NONE);
	    toolItem.setText("ToolBars");
	    MenuItem fontItem = new MenuItem(viewMenu, SWT.NONE);
	    fontItem.setText("Font");

	    exitItem.addSelectionListener(new MenuItemListener(shell));
	  
	    shell.setMenuBar(menu);
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	   }
}
	