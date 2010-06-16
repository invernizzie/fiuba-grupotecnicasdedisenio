package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

@Deprecated
public class Ventana {
	private Display display;
	private Shell shell;
	
	public Ventana(Integer alto, Integer ancho, String nombre) {
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
	public void dibujar () {
		Slider slider = new Slider (shell, SWT.HORIZONTAL);
		slider.setBounds (10, 10, 200, 32);
		slider.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				String string = "SWT.NONE";
				switch (event.detail) {
					case SWT.DRAG: string = "SWT.DRAG"; break;
					case SWT.HOME: string = "SWT.HOME"; break;
					case SWT.END: string = "SWT.END"; break;
					case SWT.ARROW_DOWN: string = "SWT.ARROW_DOWN"; break;
					case SWT.ARROW_UP: string = "SWT.ARROW_UP"; break;
					case SWT.PAGE_DOWN: string = "SWT.PAGE_DOWN"; break;
					case SWT.PAGE_UP: string = "SWT.PAGE_UP"; break;
				}
				System.out.println ("Scroll detail -> " + string);
			}
		});
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
	public void dibujar2(){
		Shell parentShell = new Shell(display);
		parentShell.setBounds(10,10,100,100);
		parentShell.open();
	
		Shell childShell = new Shell(parentShell);
		childShell.setLayout(new GridLayout());
		TabFolder folder = new TabFolder(childShell, SWT.NONE);
		folder.setLayout(new FillLayout());
		TabItem tab1 = new TabItem(folder, SWT.NONE);
		tab1.setText("Tab &1");
		new TabItem(folder, SWT.NONE).setText("Tab &2");
		Composite composite = new Composite(folder, SWT.NONE);
		composite.setLayout(new GridLayout());
		tab1.setControl(composite);
		Text text1 = new Text(composite, SWT.SINGLE);
	
		/* canvas represents a custom control */
		final Canvas canvas = new Canvas(composite, SWT.BORDER);
		canvas.setLayoutData(new GridData(300,200));
		canvas.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				if (canvas.isFocusControl()) {
					event.gc.drawText("focus is here, custom traverse keys:\n\tN: Tab next\n\tP: Tab previous\n\tR: Return\n\tE: Esc\n\tT: Tab Folder next page", 0, 0);
				} else {
					event.gc.drawString("focus is not in this control", 0, 0);
				}
			}
		});
		canvas.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				int traversal = SWT.NONE;
				switch (event.keyCode) {
					case 'n': traversal = SWT.TRAVERSE_TAB_NEXT; break;
					case 'p': traversal = SWT.TRAVERSE_TAB_PREVIOUS; break;
					case 'r': traversal = SWT.TRAVERSE_RETURN; break;
					case 'e': traversal = SWT.TRAVERSE_ESCAPE; break;
					case 't': traversal = SWT.TRAVERSE_PAGE_NEXT; break;
				}
				if (traversal != SWT.NONE) {
					event.doit = true; /* this will be the Traverse event's initial doit value */
					canvas.traverse(traversal);
				}
			}
		});
		canvas.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				canvas.redraw();
			}
			public void focusGained(FocusEvent e) {
				canvas.redraw();
			}
		});
	
		Text text2 = new Text(composite, SWT.SINGLE);
		Button button = new Button(childShell, SWT.PUSH);
		button.setText("Default &Button");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				System.out.println("Default button selected");
			}
		});
		childShell.setDefaultButton(button);
	
		Listener printTraverseListener = new Listener() {
			public void handleEvent(Event event) {
				StringBuffer buffer = new StringBuffer("Traverse ");
				buffer.append(event.widget);
				buffer.append(" type=");
				switch (event.detail) {
					case SWT.TRAVERSE_ARROW_NEXT: buffer.append("TRAVERSE_ARROW_NEXT"); break;
					case SWT.TRAVERSE_ARROW_PREVIOUS: buffer.append("TRAVERSE_ARROW_NEXT"); break;
					case SWT.TRAVERSE_ESCAPE: buffer.append("TRAVERSE_ESCAPE"); break;
					case SWT.TRAVERSE_MNEMONIC: buffer.append("TRAVERSE_MNEMONIC"); break;
					case SWT.TRAVERSE_PAGE_NEXT: buffer.append("TRAVERSE_PAGE_NEXT"); break;
					case SWT.TRAVERSE_PAGE_PREVIOUS: buffer.append("TRAVERSE_PAGE_PREVIOUS"); break;
					case SWT.TRAVERSE_RETURN: buffer.append("TRAVERSE_RETURN"); break;
					case SWT.TRAVERSE_TAB_NEXT: buffer.append("TRAVERSE_TAB_NEXT"); break;
					case SWT.TRAVERSE_TAB_PREVIOUS: buffer.append("TRAVERSE_TAB_PREVIOUS"); break;
				}
				buffer.append(" doit=" + event.doit);
				buffer.append(" keycode=" + event.keyCode);
				buffer.append(" char=" + event.character);
				buffer.append(" stateMask=" + event.stateMask);
				System.out.println(buffer.toString());
			}
		};
		childShell.addListener(SWT.Traverse, printTraverseListener);
		folder.addListener(SWT.Traverse, printTraverseListener);
		composite.addListener(SWT.Traverse, printTraverseListener);
		canvas.addListener(SWT.Traverse, printTraverseListener);
		button.addListener(SWT.Traverse, printTraverseListener);
		text1.addListener(SWT.Traverse, printTraverseListener);
		text2.addListener(SWT.Traverse, printTraverseListener);
	
		childShell.pack();
		childShell.open();
		text1.setFocus();
		while (!parentShell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	
	}
	
	public void dibujar3(){
		
		shell.setLayout(new GridLayout());
		final ScrolledComposite sc = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		Composite c = new Composite(sc, SWT.NONE);
		GridLayout gridLayout1 = new GridLayout(32, true);
		gridLayout1.numColumns = 32;
		gridLayout1.verticalSpacing = 0;
		gridLayout1.horizontalSpacing = 0;
		c.setLayout(gridLayout1);
		Button[][] botones = new Button[32][24];
		for (int i = 0 ; i < 32; i++) {
			for (int j = 0 ; j < 24; j++) {
				botones[i][j] = new Button(c, SWT.PUSH);
				botones[i][j].setText("");
			}
		}
		//for (int i = 0 ; i < 32*24; i++) {
		//	Button b = new Button(c, SWT.PUSH);
		//}
		sc.setContent(c);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setShowFocusedControl(true);
		
		//shell.setSize(300, 500);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
	public void dibujar4 () {
		shell.setLayout (new RowLayout (SWT.VERTICAL));
		Composite c1 = new Composite (shell, SWT.BORDER | SWT.NO_RADIO_GROUP);
		c1.setLayout (new RowLayout ());
		Composite c2 = new Composite (shell, SWT.BORDER | SWT.NO_RADIO_GROUP);
		c2.setLayout (new RowLayout ());
		final Composite [] composites = new Composite [] {c1, c2};
		Listener radioGroup = new Listener () {
			public void handleEvent (Event event) {
				for (int i=0; i<composites.length; i++) {
					Composite composite = composites [i];
					Control [] children = composite.getChildren ();
					for (int j=0; j<children.length; j++) {
						Control child = children [j];
						if (child instanceof Button) {
							Button button = (Button) child;
							if ((button.getStyle () & SWT.RADIO) != 0) button.setSelection (false);
						}
					}
				}
				Button button = (Button) event.widget;
				button.setSelection (true);
			}
		};
		for (int i=0; i<4; i++) {
			Button button = new Button (c1, SWT.RADIO);
			button.setText ("Button " + i);
			button.addListener (SWT.Selection, radioGroup);
		}
		for (int i=0; i<4; i++) {
			Button button = new Button (c2, SWT.RADIO);
			button.setText ("Button " + (i + 4));
			button.addListener (SWT.Selection, radioGroup);
		}
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
	public void dibujar5 () {
		Listener listener = new Listener () {
			int lastX = 0, lastY = 0;
			public void handleEvent (Event event) {
				switch (event.type) {
					case SWT.MouseMove:
						if ((event.stateMask & SWT.BUTTON1) == 0) break;
						GC gc = new GC (shell);
						gc.drawLine (lastX, lastY, event.x, event.y);
						gc.dispose ();
						//FALL THROUGH
					case SWT.MouseDown:
						lastX = event.x;
						lastY = event.y;
						break;
				}
			}
		};
		shell.addListener (SWT.MouseDown, listener);
		shell.addListener (SWT.MouseMove, listener);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}


}
	