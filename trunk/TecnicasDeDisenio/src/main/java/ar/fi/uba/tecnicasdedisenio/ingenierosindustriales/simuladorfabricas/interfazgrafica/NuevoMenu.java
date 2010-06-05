package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import java.util.Observable;
import java.util.Observer;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.graphics.Rectangle;

public class NuevoMenu implements Sincronizado, Observer {

	private boolean actualizado = false;
	private Shell shellPrincipal = null;  //  @jve:decl-index=0:visual-constraint="79,7"
	private Menu menuBar = null;
	private Group groupTiempo = null;
	private Group groupJugador = null;
	private TabFolder tabFolderFabrica = null;
	private Menu submenuJuego = null;
	private Menu submenuAyuda = null;
	private Button buttonTimer = null;
	private Label labelJugador = null;
	private Text textTime = null;
	private Text textJugador = null;
	private Label labelDineroAcum = null;
	private Text textDineroAcum = null;
	private Button checkBoxInvertirLabo = null;
	private Combo comboFabrica = null;
	private Button buttonFabrica = null;
	private Canvas canvasFabrica = null;
	private Menu submenuFabrica = null;
	private Jugador jugador = null;
	
	/**
	 * This method initializes shellPrincipal
	 *
	 */
	private void createShellPrincipal() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellPrincipal = new Shell(SWT.V_SCROLL | SWT.SHELL_TRIM | SWT.H_SCROLL);
		shellPrincipal.setText("TP Tecnicas de diseño");
		createGroupTiempo();
		createTabFolderFabrica();
		createGroupJugador();
		shellPrincipal.setLayout(gridLayout);
		shellPrincipal.setSize(new Point(792, 459));
		menuBar = new Menu(shellPrincipal, SWT.BAR);
		menuBar.setEnabled(true);
		MenuItem submenuItemJuego = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemJuego.setText("Juego");
		MenuItem submenuItemFabrica = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemFabrica.setText("Fabrica");
		MenuItem submenuItemAyuda = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemAyuda.setText("Ayuda");
		submenuFabrica = new Menu(submenuItemFabrica);
		MenuItem pushVender = new MenuItem(submenuFabrica, SWT.PUSH);
		pushVender.setText("Vender");
		MenuItem pushComprar = new MenuItem(submenuFabrica, SWT.PUSH);
		pushComprar.setText("Comprar");
		MenuItem pushAlquilar = new MenuItem(submenuFabrica, SWT.PUSH);
		pushAlquilar.setText("Alquilar");
		MenuItem separatorFabrica = new MenuItem(submenuFabrica, SWT.SEPARATOR);
		MenuItem pushLaboratorio = new MenuItem(submenuFabrica, SWT.PUSH);
		pushLaboratorio.setText("Laboratorio");
		submenuItemFabrica.setMenu(submenuFabrica);
		submenuAyuda = new Menu(submenuItemAyuda);
		MenuItem pushContenido = new MenuItem(submenuAyuda, SWT.PUSH);
		pushContenido.setText("Contenido");
		MenuItem separatorAyuda = new MenuItem(submenuAyuda, SWT.SEPARATOR);
		MenuItem pushAcercaDe = new MenuItem(submenuAyuda, SWT.PUSH);
		pushAcercaDe.setText("Acerca de...");
		submenuItemAyuda.setMenu(submenuAyuda);
		submenuJuego = new Menu(submenuItemJuego);
		submenuJuego.setVisible(true);
		submenuJuego.setEnabled(true);
		submenuItemJuego.setMenu(submenuJuego);
		MenuItem pushJuegoNuevo = new MenuItem(submenuJuego, SWT.PUSH);
		pushJuegoNuevo.setText("Juego Nuevo");
		pushJuegoNuevo.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetDefaultSelected()");
				juegoNuevo();
				//openItem.setEnabled(true);
				//saveItem.setEnabled(true);
				//saveAllItem.setEnabled(true);
			}
		});
		MenuItem pushAbrir = new MenuItem(submenuJuego, SWT.PUSH);
		pushAbrir.setText("Abrir");
		pushAbrir.setEnabled(false);
		MenuItem pushGuardar = new MenuItem(submenuJuego, SWT.PUSH);
		pushGuardar.setText("Guardar");
		pushGuardar.setEnabled(false);
		MenuItem pushGuardarComo = new MenuItem(submenuJuego, SWT.PUSH);
		pushGuardarComo.setText("Guardar Como");
		pushGuardarComo.setEnabled(false);
		MenuItem pushSeparador = new MenuItem(submenuJuego, SWT.SEPARATOR);
		MenuItem pushSalir = new MenuItem(submenuJuego, SWT.PUSH);
		pushSalir.setText("Salir");
		pushSalir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				shellPrincipal.close();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		shellPrincipal.setMenuBar(menuBar);
	}

	/**
	 * This method initializes groupTiempo
	 *
	 */
	private void createGroupTiempo() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.grabExcessVerticalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		groupTiempo = new Group(shellPrincipal, SWT.NONE);
		groupTiempo.setText("Timer");
		groupTiempo.setLayoutData(gridData1);
		groupTiempo.setLayout(gridLayout1);
		buttonTimer = new Button(groupTiempo, SWT.NONE);
		buttonTimer.setText("Comenzar");
		textTime = new Text(groupTiempo, SWT.BORDER);
		textTime.setEditable(false);
		textTime.setLayoutData(gridData3);
		textTime.setText(Calendario.instancia().getFechaActual().toString());
		buttonTimer.addSelectionListener(new SelectionListener() {
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
	}

	/**
	 * This method initializes groupJugador
	 *
	 */
	private void createGroupJugador() {
		GridData gridData5 = new GridData();
		gridData5.grabExcessVerticalSpace = false;
		gridData5.verticalAlignment = GridData.BEGINNING;
		gridData5.horizontalAlignment = GridData.FILL;
		GridData gridData4 = new GridData();
		gridData4.grabExcessHorizontalSpace = false;
		gridData4.verticalAlignment = GridData.CENTER;
		gridData4.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.grabExcessVerticalSpace = true;
		gridData2.verticalAlignment = GridData.FILL;
		gridData2.horizontalAlignment = GridData.BEGINNING;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		groupJugador = new Group(shellPrincipal, SWT.NONE);
		groupJugador.setText("Datos del Jugador");
		groupJugador.setLayoutData(gridData2);
		groupJugador.setLayout(gridLayout2);
		labelJugador = new Label(groupJugador, SWT.NONE);
		labelJugador.setText("Jugador");
		textJugador = new Text(groupJugador, SWT.BORDER);
		textJugador.setEditable(false);
		labelDineroAcum = new Label(groupJugador, SWT.NONE);
		labelDineroAcum.setText("Dinero Acumulado");
		textDineroAcum = new Text(groupJugador, SWT.BORDER);
		textDineroAcum.setLayoutData(gridData4);
		textDineroAcum.setEditable(false);
		checkBoxInvertirLabo = new Button(groupJugador, SWT.CHECK);
		checkBoxInvertirLabo.setText("Invertir en Laboratorio");
		Label filler = new Label(groupJugador, SWT.NONE);
		createComboFabrica();
		buttonFabrica = new Button(groupJugador, SWT.NONE);
		buttonFabrica.setText("Fabrica");
		buttonFabrica.setLayoutData(gridData5);
	}

	/**
	 * This method initializes tabFolderFabrica
	 *
	 */
	private void createTabFolderFabrica() {
		GridData gridData = new GridData();
		gridData.verticalSpan = 2;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tabFolderFabrica = new TabFolder(shellPrincipal, SWT.BORDER);
		tabFolderFabrica.setLayoutData(gridData);
		createCanvasFabrica();
		TabItem tabItemFabrica = new TabItem(tabFolderFabrica, SWT.NONE);
		tabItemFabrica.setText("Fabrica");
		TabItem tabItemLaboratorio = new TabItem(tabFolderFabrica, SWT.NONE);
		tabItemLaboratorio.setText("Laboratorio");

		tabItemFabrica.setControl(canvasFabrica);
	}

	/**
	 * This method initializes comboFabrica
	 *
	 */
	private void createComboFabrica() {
		GridData gridData6 = new GridData();
		gridData6.grabExcessHorizontalSpace = false;
		gridData6.verticalAlignment = GridData.BEGINNING;
		gridData6.grabExcessVerticalSpace = false;
		gridData6.horizontalAlignment = GridData.FILL;
		comboFabrica = new Combo(groupJugador, SWT.NONE);
		comboFabrica.setLayoutData(gridData6);
	}

	/**
	 * This method initializes canvasFabrica
	 *
	 */
	private void createCanvasFabrica() {
		canvasFabrica = new Canvas(tabFolderFabrica, SWT.NONE);
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

	public void juegoNuevo(){
		CrearPartida partida= new CrearPartida(this);
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
            	buttonTimer.setEnabled(false);
                break;
            case COMIENZO_DE_DIA:
            	forzarActualizacion();
                break;
        }

        if (textoControlDeTiempo != null)
        	buttonTimer.setText(textoControlDeTiempo);
    }    
	
    /**
	 * @param args
	 */
	public void run() {
		// TODO Auto-generated method stub
		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
		 * for the correct SWT library path in order to run with the SWT dlls.
		 * The dlls are located in the SWT plugin jar.
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		NuevoMenu thisClass = new NuevoMenu();
		thisClass.createShellPrincipal();
		thisClass.shellPrincipal.open();

        Calendario.instancia().registrar(thisClass);
        Calendario.instancia().setSegundosPorDia(1);

		while (!thisClass.shellPrincipal.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
            if (thisClass.necesitaActualizacion())
                thisClass.actualizarDatosTiempo();
		}
		display.dispose();
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Jugador getJugador() {
		return jugador;
	}
    
    private void actualizarDatosJugador() {
    	textJugador.setText(getJugador().getNombre());
		textDineroAcum.setText(Float.toString(getJugador().getDineroActual()));
	}
    
    private void actualizarDatosTiempo(){
    	this.textTime.setText(Calendario.instancia().getFechaActual().toString());
    	notificarActualizacion();
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		actualizarDatosJugador();
	}
}

