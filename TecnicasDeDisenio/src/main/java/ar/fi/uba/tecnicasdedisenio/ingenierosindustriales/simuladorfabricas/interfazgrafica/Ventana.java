package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Point;
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

}