package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.old;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.DialogoNuevaPartida;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

@Deprecated
public class MainWindow implements Sincronizado {

	private Shell sShell = null;
    private ScrolledComposite scAreaTrabajo = null;
	private CTabFolder tabWorkingControls = null;
	private Composite composite = null;
	private Group gUserProperties = null;
    private Canvas canvasFabrica = null;
    private ToolItem botonControlDeTiempo;
    private Label labelFecha;
    private boolean actualizado = false;

    /**
	 * This method initializes scAreaTrabajo
	 *
	 */
	private void createScAreaTrabajo() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		scAreaTrabajo = new ScrolledComposite(sShell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scAreaTrabajo.setAlwaysShowScrollBars(true);
		scAreaTrabajo.setExpandHorizontal(true);
		scAreaTrabajo.setExpandVertical(true);
		scAreaTrabajo.setMinSize(new Point(280, 210));
		scAreaTrabajo.setLayoutData(gridData);
		createTabWorkingControls();
		scAreaTrabajo.setLayout(new FillLayout());
		createCanvasFabrica();
		scAreaTrabajo.setContent(canvasFabrica);
	}

	/**
	 * This method initializes tabWorkingControls
	 *
	 */
	private void createTabWorkingControls() {
		tabWorkingControls = new CTabFolder(scAreaTrabajo, SWT.H_SCROLL | SWT.BORDER | SWT.V_SCROLL);
		tabWorkingControls.setToolTipText("Mapa");
		tabWorkingControls.setMaximizeVisible(true);
		createComposite();
		tabWorkingControls.setMaximized(true);
		CTabItem tabItem2 = new CTabItem(tabWorkingControls, SWT.NONE);
		tabItem2.setText("Fabrica");
		CTabItem tabItem3 = new CTabItem(tabWorkingControls, SWT.NONE);
		tabItem3.setText("Laboratorio");

		tabItem2.setControl(composite);
		tabWorkingControls.setEnabled(true);
		tabWorkingControls.setVisible(true);
		tabWorkingControls.pack();
	}

	/**
	 * This method initializes composite
	 *
	 */
	private void createComposite() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 32;
		gridLayout1.verticalSpacing = 0;
		gridLayout1.horizontalSpacing = 0;
		composite = new Composite(tabWorkingControls, SWT.BORDER);
		composite.setLayout(gridLayout1);
	}

	/**
	 * This method initializes gUserProperties
	 *
	 */
	@SuppressWarnings("unused")
	private void createGUserProperties() {
		GridData gridData2 = new GridData();
		gridData2.verticalSpan = 3;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		gUserProperties = new Group(sShell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		gUserProperties.setText("Propiedades del Jugador");
		gUserProperties.setLayout(gridLayout2);
        Label lJugador = new Label(gUserProperties, SWT.NONE);
		lJugador.setText("Jugador");
        Text tJugador = new Text(gUserProperties, SWT.BORDER);
		tJugador.setEditable(false);
		tJugador.setText("Nombre Jugador");
        Button checkInvertirLab = new Button(gUserProperties, SWT.CHECK);
		checkInvertirLab.setText("Invertir en Laboratorio");
		checkInvertirLab.setLayoutData(gridData1);
		createComboFabrica();
        Button bFabrica = new Button(gUserProperties, SWT.NONE);
		bFabrica.setText("Fabrica");
		bFabrica.setLayoutData(gridData2);
		checkInvertirLab
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()");
					}
				});
	}

	/**
	 * This method initializes canvasFabrica
	 *
	 */
	private void createCanvasFabrica() {
		canvasFabrica = new Canvas(scAreaTrabajo, SWT.BORDER);
		canvasFabrica.setLayout(null);
	}

	/**
	 * This method initializes comboFabrica
	 *
	 */
	private void createComboFabrica() {
		new Combo(gUserProperties, SWT.NONE);
	}

	/**
     * Crea la barra de herramientas del tiempo y su contenido
     */
    private void crearToolbarCalendario() {
        ToolBar toolbarCalendario = new ToolBar(sShell, SWT.BORDER);

        labelFecha = new Label(sShell, SWT.PUSH | SWT.BORDER);
        
        labelFecha.setText("Fecha");

        botonControlDeTiempo = new ToolItem(toolbarCalendario, SWT.PUSH);
        botonControlDeTiempo.setText("Comenzar");
        botonControlDeTiempo.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (!Calendario.instancia().estaIniciado()) {
                    Calendario.instancia().iniciar();
                }
                else
                    if (Calendario.instancia().estaPausado())
                        Calendario.instancia().reanudar();
                    else
                        Calendario.instancia().pausar();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {}
        });

        toolbarCalendario.pack();
    }

    private synchronized boolean necesitaActualizacion() {
        return !actualizado;
    }

    private synchronized void notificarActualizacion() {
        actualizado = true;
    }

    private synchronized void forzarActualizacion() {
        actualizado = false;
    }

    private void actualizar() {
        labelFecha.setText(Calendario.instancia().getFechaActual().toString());
        notificarActualizacion();
    }

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;

		gridLayout.horizontalSpacing = 1;
		sShell = new Shell(SWT.V_SCROLL | SWT.SHELL_TRIM | SWT.H_SCROLL);
		sShell.setText("Tecnincas de Diseño");
		sShell.setMaximized(false);
		sShell.setVisible(true);
		sShell.setLayout(gridLayout);
        crearToolbarCalendario();
		createScAreaTrabajo();
		sShell.setSize(new Point(481, 382));
        Menu menuBar = new Menu(sShell, SWT.BAR);

		MenuItem newItem = new MenuItem(menuBar, SWT.CASCADE);
		newItem.setText("Archivo");
        Menu fileItem = new Menu(newItem);
		fileItem.setVisible(true);
		MenuItem newItem4 = new MenuItem(fileItem, SWT.PUSH);
		newItem4.setText("Nuevo");
		newItem4.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetDefaultSelected()");
				juegoNuevo();
			}
		});
		MenuItem openItem = new MenuItem(fileItem, SWT.PUSH);
		openItem.setText("Abrir");
		openItem.setEnabled(false);
		MenuItem saveItem = new MenuItem(fileItem, SWT.PUSH);
		saveItem.setText("Guardar");
		saveItem.setEnabled(false);
		MenuItem saveAllItem = new MenuItem(fileItem, SWT.PUSH);
		saveAllItem.setText("Guardar todo");
		saveAllItem.setEnabled(false);
		@SuppressWarnings("unused")
		MenuItem separatorFile = new MenuItem(fileItem, SWT.SEPARATOR);
		newItem.setMenu(fileItem);
		MenuItem exitItem = new MenuItem(fileItem, SWT.PUSH);
		exitItem.setText("Salir");
		MenuItem Edit = new MenuItem(menuBar, SWT.CASCADE);
		Edit.setText("Edicion");
        Menu editItem = new Menu(Edit);
		MenuItem copy = new MenuItem(editItem, SWT.PUSH);
		copy.setText("Copiar");
		copy.setEnabled(false);
		MenuItem paste = new MenuItem(editItem, SWT.PUSH);
		paste.setText("Pegar");
		paste.setEnabled(false);
		Edit.setMenu(editItem);
		exitItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell.close();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem helpItem = new MenuItem(menuBar, SWT.CASCADE);
		helpItem.setText("Ayuda");
        Menu helpItem1 = new Menu(helpItem);
		MenuItem about = new MenuItem(helpItem1, SWT.PUSH);
		about.setText("Acerca de...");
		helpItem.setMenu(helpItem1);
		sShell.setMenuBar(menuBar);
	}

	public void juegoNuevo(){
		DialogoNuevaPartida partida= new DialogoNuevaPartida(null);
		partida.hacerVisible();
		System.out.println("Se Invoca la pantalla de Creacion");
	}

    @Override
    public void notificar(Evento evento) {
        String textoControlDeTiempo = null;
        switch (evento) {
            case INICIO_TIEMPO:
            case FIN_PAUSA:
                textoControlDeTiempo = "Pausar";
                break;
            case INICIO_PAUSA:
                textoControlDeTiempo = "Reanudar";
                break;
            case FIN_TIEMPO:
                botonControlDeTiempo.setEnabled(false);
                break;
            case COMIENZO_DE_DIA:
                forzarActualizacion();
                break;
        }

        if (textoControlDeTiempo != null)
            botonControlDeTiempo.setText(textoControlDeTiempo);
    }

	public void run() {
		// TODO Auto-generated method stub
		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
		 * for the correct SWT library path in order to run with the SWT dlls.
		 * The dlls are located in the SWT plugin jar.
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		MainWindow thisClass = new MainWindow();
		thisClass.createSShell();
		thisClass.sShell.open();

        Calendario.instancia().registrar(thisClass);
        Calendario.instancia().setSegundosPorDia(1);

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
            if (thisClass.necesitaActualizacion())
                thisClass.actualizar();
		}
		display.dispose();
	}

}
