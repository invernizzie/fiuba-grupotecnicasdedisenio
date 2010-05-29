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
		super();
		this.alto = alto;
		this.ancho = ancho;
		this.nombre = nombre;
		this.display = new Display ();
		this.shell = new Shell (this.display);
	}


	public void dibujar () {
		shell.setSize (800, 600);
		shell.setText("TP1 Tecnicas de Diseño");
		Composite c1 = new Composite (shell, SWT.BORDER);
		c1.setSize (100, 100);
		Composite c2 = new Composite (shell, SWT.BORDER);
		c2.setBounds (100, 0, 100, 100);
		Menu menu = new Menu (shell, SWT.POP_UP);
		MenuItem item = new MenuItem (menu, SWT.PUSH);
		item.setText ("Popup");
		c1.setMenu (menu);
		c2.setMenu (menu);
		shell.setMenu (menu);
		shell.addListener (SWT.MouseEnter, new Listener () {
			public void handleEvent (Event e) {
				System.out.println ("ENTER");
			}
		});
		shell.addListener (SWT.MouseExit, new Listener () {
			public void handleEvent (Event e) {
				System.out.println ("EXIT");
			}
		});
		shell.addListener (SWT.MouseHover, new Listener () {
			public void handleEvent (Event e) {
				System.out.println ("HOVER");
			}
		});
		shell.open ();
		CoolBar bar = new CoolBar (shell, SWT.BORDER);
		for (int i=0; i<2; i++) {
			CoolItem item1 = new CoolItem (bar, SWT.NONE);
			Button button = new Button (bar, SWT.PUSH);
			button.setText ("Button " + i);
			Point size = button.computeSize (SWT.DEFAULT, SWT.DEFAULT);
			item1.setPreferredSize (item1.computeSize (size.x, size.y));
			item1.setControl (button);
		}
		bar.pack ();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

	public void dibujar2 () {
		final Image image = new Image (display, 16, 16);
		GC gc = new GC (image);
		gc.setBackground (display.getSystemColor (SWT.COLOR_RED));
		gc.fillRectangle (image.getBounds ());
		gc.dispose ();
		shell.setText ("Lazy Table");
		shell.setLayout (new FillLayout ());
		final Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
		table.setSize (200, 200);
		Thread thread = new Thread () {
			public void run () {
				for (int i=0; i<200; i++) {
					if (table.isDisposed ()) return;
					final int [] index = new int [] {i};
					display.syncExec (new Runnable () {
						public void run () {
							if (table.isDisposed ()) return;
							TableItem item = new TableItem (table, SWT.NONE);
							item.setText ("Table Item " + index [0]);
							item.setImage (image);
						}
					});
				}
			}
		};
		thread.start ();
		shell.setSize (200, 200);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		image.dispose ();
		display.dispose ();
	}
	
	public void dibujarMenu() {
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		ManejadorEventos eventM1 = new ManejadorEventos(new Event());
		ManejadorEventos eventM2 = new ManejadorEventos(new Event());
		ManejadorEventos eventM3 = new ManejadorEventos(new Event());
		MenuItem fileItemArchivo = new MenuItem (bar, SWT.CASCADE);
		fileItemArchivo.setText ("&Archivo");
		Menu submenuArchivo = new Menu (shell, SWT.DROP_DOWN);
		fileItemArchivo.setMenu (submenuArchivo);
		MenuItem itemArchivoCrearPartida= new MenuItem (submenuArchivo, SWT.PUSH);
		itemArchivoCrearPartida.addListener (SWT.Selection, eventM1);
		itemArchivoCrearPartida.setText ("Crear &Partida\tCtrl+N");
		itemArchivoCrearPartida.setAccelerator (SWT.MOD1 + 'N');
		
		MenuItem itemArchivoSalir= new MenuItem (submenuArchivo, SWT.PUSH);
		itemArchivoSalir.addListener (SWT.Selection, eventM2);
		itemArchivoSalir.setText ("Salir\tCtrl+X");
		itemArchivoSalir.setAccelerator (SWT.MOD1 + 'X');
		
		MenuItem fileItemHelp = new MenuItem (bar, SWT.CASCADE);
		fileItemHelp.setText ("&Help");
		Menu submenuHelp = new Menu (shell, SWT.DROP_DOWN);
		fileItemHelp.setMenu (submenuHelp);
		MenuItem itemHelp= new MenuItem (submenuHelp, SWT.PUSH);
		itemHelp.addListener (SWT.Selection, eventM3);
		itemHelp.setText ("Acerca &de...\t");
		
		shell.setSize (640, 480);
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

}