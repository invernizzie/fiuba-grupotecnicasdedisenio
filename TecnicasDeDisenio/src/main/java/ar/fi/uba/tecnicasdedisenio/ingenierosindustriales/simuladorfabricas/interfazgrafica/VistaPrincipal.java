package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import java.text.NumberFormat;
import java.util.*;


import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.DineroInsuficienteException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.FabricaOcupadaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.excepciones.JugadorConFabricaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.RecursosAplicacion;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

public class VistaPrincipal implements Sincronizado, Observer {

	private static final int DINERO_PARA_GANAR = 50000;
	public static final int SEGUNDOS_POR_DIA = 1;
    private static final int CANTIDAD_FABRICAS = 5;
    private static final int PASO_SUPERFICIE = 15000;
    private static final int PASO_PRECIO_COMPRA = 1000;
    private static final int PASO_PRECIO_ALQUILER = 150;
    private static final int DEFAULT_ANCHO = 792;
    private static final int DEFAULT_ALTO = 459;
    private static final String PATRON_FECHA_ESPANIOL = "d 'de' MMMM 'de' yyyy ";

	private boolean actualizado = false;
	private Shell shellPrincipal = null;
	private MenuBar menuPrincipal = null;
	private Group groupJugador = null;
	private CTabFolder tabFolderFabrica = null;
    private Button buttonTimer = null;
    private Text textTime = null;
	private Text textJugador = null;
    private Text textDineroAcum = null;
	private Button checkBoxInvertirLabo = null;
	private Combo comboFabrica = null;
	private AreaFabrica areaFabrica = null;
	private Jugador jugador = null;
	private Button buttonComprar = null;
	private Button buttonAlquilar = null;
	private Button buttonVender = null;
	private HashMap<String, Fabrica> fabricas;
    private NumberFormat formateador = NumberFormat.getInstance();
    private Set<Widget> botonesPartida = new HashSet<Widget>();
	private Composite compositeLaboratorio = null;
    private Text textTipoLabo = null;
    private Text textDineroAcumLabo = null;
	private Button buttonImagenLabo = null;
    private Display display = null;

	public Shell getShellPrincipal() {
		return shellPrincipal;
	}


	public void setShellPrincipal(Shell shellPrincipal) {
		this.shellPrincipal = shellPrincipal;
	}
    
    
    /**
	 * Carga las distintas fabricas standard.
	 */
	public void cargarOpcionesFabrica(){
		int i;
		String[] fab = new String[CANTIDAD_FABRICAS];
		
		fabricas = new HashMap<String, Fabrica>();
		Fabrica fabrica = null;
		for (i = 0; i < CANTIDAD_FABRICAS; i++){
			fabrica = new Fabrica((i + 1) * PASO_SUPERFICIE, (i + 1) * PASO_PRECIO_COMPRA, (i + 1) * PASO_PRECIO_ALQUILER);
			fabricas.put(fabrica.toString(), fabrica);
			fab[i] = fabrica.toString();
		}
		comboFabrica.removeAll();
		comboFabrica.setItems(fab);
		comboFabrica.setText(comboFabrica.getItem(0));
		checkBoxInvertirLabo.setSelection(false);
	}

	
    @Override
    public void notificar(final Evento evento) {
        String textoControlDeTiempo = null;

        if (shellPrincipal.isDisposed()) {
            return;
        }
        
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

        if (textoControlDeTiempo != null) {
        	buttonTimer.setText(textoControlDeTiempo);
        }
    }    
	
	public void run() {
		display  = Display.getDefault();
		this.createShellPrincipal();
		this.shellPrincipal.open();
		resetearCalendario();
        this.cambiarHabilitacionBotonesDePartida(false);
        

        formateador.setMaximumFractionDigits(2);
        formateador.setMinimumFractionDigits(2);

		while (!this.shellPrincipal.isDisposed()) {
			try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
                if (this.necesitaActualizacion()) {
                    this.actualizarDatosTiempo();
                    this.actualizarDatosJugador();
                    this.verificarFinalJuego();
                    areaFabrica.actualizar();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		display.dispose();
	}

    public void setJugador(final Jugador jugador) {
    	this.jugador = jugador;
        areaFabrica.setFabrica(jugador.getFabrica());
        cambiarHabilitacionBotonesDePartida(true);
        
	}

	public Jugador getJugador() {
		return jugador;
	}
    
	
	@Override
	public void update(final Observable observable, final Object arg) {
		forzarActualizacion();
	}
	
	/**
    * Resetea el calendario para que vuelva a empezar.
    */
    public void resetearCalendario() {
    	Calendario.instancia().inicializar();
    	Calendario.instancia().registrar(this);
    	Calendario.instancia().registrar(ValidadorProductos.instancia());
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        buttonTimer.setText("Comenzar");
    }
	
	/**
	 * This method initializes shellPrincipal
	 *
	 */
	private void createShellPrincipal() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellPrincipal = new Shell(SWT.V_SCROLL | SWT.SHELL_TRIM | SWT.H_SCROLL);
		shellPrincipal.setText("TP Tecnicas de diseno");
		createGroupTiempo();
		createTabFolderFabrica();
		createGroupJugador();
		shellPrincipal.setLayout(gridLayout);
		shellPrincipal.setSize(new Point(DEFAULT_ANCHO, DEFAULT_ALTO));
		menuPrincipal = new MenuBar(this);
		shellPrincipal.setMenuBar(menuPrincipal.getMenuBar());
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
        Group groupTiempo = new Group(shellPrincipal, SWT.NONE);
		groupTiempo.setText("Timer");
		groupTiempo.setLayoutData(gridData1);
		groupTiempo.setLayout(gridLayout1);
		buttonTimer = new Button(groupTiempo, SWT.NONE);
		buttonTimer.setText("Comenzar");
		textTime = new Text(groupTiempo, SWT.BORDER);
		textTime.setEditable(false);
		textTime.setLayoutData(gridData3);
		textTime.setText(Calendario.instancia().fechaFormateada(PATRON_FECHA_ESPANIOL));
		buttonTimer.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(final SelectionEvent selectionEvent) {
                if (!Calendario.instancia().estaIniciado()) {
                	jugador.getFabrica().validarCiclos();
                	Calendario.instancia().iniciar();
                    cambiarHabilitacionBotonesDePartida(false);
                    /*Se hace porque sino quedan habilitados algunos botones.*/
                    buttonTimer.setEnabled(true);
                    cambiarHabilitacionControlesDeFabrica(false);
                    areaFabrica.cambiarHabilitacionBotones(false);
                } else {
                    if (Calendario.instancia().estaPausado()) {
                    	jugador.getFabrica().validarCiclos();
                    	Calendario.instancia().reanudar();
                		cambiarHabilitacionBotonesDePartida(false);
                		/*Se hace porque sino quedan habilitados algunos botones.*/
                        buttonTimer.setEnabled(true);
                        cambiarHabilitacionControlesDeFabrica(false);
                        areaFabrica.cambiarHabilitacionBotones(false);
                    } else {
                        Calendario.instancia().pausar();
                        cambiarHabilitacionBotonesDePartida(true);
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(final SelectionEvent selectionEvent) { }
        });
		
		botonesPartida.add(buttonTimer);
	}

    private void cambiarHabilitacionControlesDeFabrica(final boolean habilitados) {
        buttonVender.setEnabled(habilitados);
        buttonComprar.setEnabled(habilitados);
        buttonAlquilar.setEnabled(habilitados);
        comboFabrica.setEnabled(habilitados);
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
        Label labelJugador = new Label(groupJugador, SWT.NONE);
		labelJugador.setText("Jugador");
		textJugador = new Text(groupJugador, SWT.BORDER);
		textJugador.setEditable(false);
        Label labelDineroAcum = new Label(groupJugador, SWT.NONE);
		labelDineroAcum.setText("Dinero Acumulado");
		textDineroAcum = new Text(groupJugador, SWT.BORDER);
		textDineroAcum.setLayoutData(gridData4);
		textDineroAcum.setEditable(false);
		checkBoxInvertirLabo = new Button(groupJugador, SWT.CHECK);
		checkBoxInvertirLabo.setText("Invertir en Laboratorio");
		checkBoxInvertirLabo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent selectionEvent) {
				if (checkBoxInvertirLabo.getSelection()) {
					getJugador().habilitarLaboratorio();
                } else {
					getJugador().deshabilitarLaboratorio();
                }
			}
		});
		
		createOpcionesFabrica();
        botonesPartida.add(checkBoxInvertirLabo);
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
		tabFolderFabrica = new CTabFolder(shellPrincipal, SWT.BORDER);
		tabFolderFabrica.setLayoutData(gridData);
		createCanvasFabrica();
		CTabItem tabItemFabrica = new CTabItem(tabFolderFabrica, SWT.NONE);
		tabItemFabrica.setText("Fabrica");
		CTabItem tabItemLaboratorio = new CTabItem(tabFolderFabrica, SWT.NONE);
		tabItemLaboratorio.setText("Laboratorio");
		tabItemFabrica.setControl(areaFabrica.getCompositeControles());
		createCompositeLaboratorio();
		tabItemLaboratorio.setControl(compositeLaboratorio);
	}

	/**
	 * This method initializes comboFabrica
	 *
	 */
	private void createOpcionesFabrica() {
		
		GridData gridData6 = new GridData();
		gridData6.grabExcessHorizontalSpace = false;
		gridData6.verticalAlignment = GridData.BEGINNING;
		gridData6.grabExcessVerticalSpace = false;
		gridData6.horizontalAlignment = GridData.FILL;
		
		buttonAlquilar = new Button(groupJugador, SWT.NONE);
		buttonAlquilar.setText("Alquilar Fabrica");
		buttonAlquilar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				alquilar();
			}
		});
		
		buttonComprar = new Button(groupJugador, SWT.NONE);
		buttonComprar.setText("Comprar Fabrica");
		buttonComprar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				comprar();
			}
		});
		
		buttonVender = new Button(groupJugador, SWT.NONE);
		buttonVender.setText("Vender Fabrica");
		buttonVender.setEnabled(false);
		buttonVender.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				vender();
			}
		});
		
		comboFabrica = new Combo(groupJugador, SWT.NONE);
		comboFabrica.setLayoutData(gridData6);
		
        //botonesPartida.add(buttonFabrica);
        botonesPartida.add(buttonAlquilar);
        botonesPartida.add(buttonComprar);
        botonesPartida.add(buttonVender);
        botonesPartida.add(comboFabrica);
	}
	
	/**
	 * This method initializes areaFabrica
	 *
	 */
	private void createCanvasFabrica() {
		areaFabrica = new AreaFabrica();
		areaFabrica.load(tabFolderFabrica);
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

    /**
     * Crea un juego nuevo.
     */
	public void juegoNuevo() {
		DialogoNuevaPartida partida = new DialogoNuevaPartida(this);
		partida.hacerVisible();
		System.out.println("Se Invoca la pantalla de Creacion");
	}
	
	private void createCompositeLaboratorio() {
		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalAlignment = GridData.CENTER;
		gridData5.horizontalAlignment = GridData.FILL;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		compositeLaboratorio  = new Composite(tabFolderFabrica, SWT.NONE);
		compositeLaboratorio.setLayout(gridLayout1);
        Label labelTipoLabo = new Label(compositeLaboratorio, SWT.NONE);
		labelTipoLabo.setText("Tipo Laboratorio");
		labelTipoLabo.setVisible(true);
		labelTipoLabo.setLayoutData(gridData2);
		textTipoLabo = new Text(compositeLaboratorio, SWT.BORDER | SWT.READ_ONLY);
		textTipoLabo.setVisible(true);
		textTipoLabo.setText("<Tipo Laboratorio>");
		textTipoLabo.setLayoutData(gridData5);
        Label labelDineroAcumLabo = new Label(compositeLaboratorio, SWT.NONE);
		labelDineroAcumLabo.setText("Dinero Acumulado");
		labelDineroAcumLabo.setEnabled(true);
		labelDineroAcumLabo.setLayoutData(gridData3);
		textDineroAcumLabo = new Text(compositeLaboratorio, SWT.BORDER);
		textDineroAcumLabo.setEditable(false);
		textDineroAcumLabo.setText("<Dinero Acumulado>");
		textDineroAcumLabo.setLayoutData(gridData4);
		buttonImagenLabo = new Button(compositeLaboratorio, SWT.PUSH);
		buttonImagenLabo.setSelection(true);
		buttonImagenLabo.setVisible(true);
		buttonImagenLabo.setLayoutData(gridData1);
	}
	
    private void cambiarHabilitacionBotonesDePartida(final boolean habilitados) {
        for (Widget boton : botonesPartida) {
            ((Control) boton).setEnabled(habilitados);
        }
        if (this.getJugador() != null) {
        	this.buttonAlquilar.setEnabled(!this.getJugador().hasFabrica());
        	this.buttonComprar.setEnabled(!this.getJugador().hasFabrica());
        	this.comboFabrica.setEnabled(!this.getJugador().hasFabrica());
        	this.buttonVender.setEnabled(this.getJugador().hasFabrica());
        	this.buttonTimer.setEnabled(this.getJugador().hasFabrica());
        	this.areaFabrica.cambiarHabilitacionBotones(this.getJugador().hasFabrica());
        }
        this.areaFabrica.getCanvas().setEnabled(habilitados);
    }
	
    /**
	 * Actualiza los datos de un jugador en la pantalla.
	 */
    private void actualizarDatosJugador() {
        if (getJugador() == null) {
            return;
        }
    	textJugador.setText(getJugador().getNombre());
		textDineroAcum.setText(formateador.format(getJugador().getDineroActual()));
		actualizarDatosLaboratorio();
	}
    
    /**
     * Verifica si termina el juego.
     */
    private void verificarFinalJuego() {
    	if (getJugador() == null) {
    		return;
        }
    	if (getJugador().getDineroActual() < 0) {
        	this.finalizarJuego(" PERDIO al quedarse sin dinero.");
        }
		if (getJugador().getDineroActual() >= DINERO_PARA_GANAR) {
			this.finalizarJuego(" GANO al cumplir el objetivo monetario.");
        }
    }
    
    /**
     * Indica el final de un juego.
     * @param mensaje
     */
    private void finalizarJuego(final String mensaje) {
    	MessageBox messageBox =
			   new MessageBox(shellPrincipal, SWT.OK | SWT.ICON_INFORMATION);
			 messageBox.setMessage("El jugador " + this.getJugador().getNombre() + mensaje);
			 messageBox.open();
		cambiarHabilitacionBotonesDePartida(false);
		Calendario.instancia().detener();
        buttonTimer.setEnabled(false);
        cambiarHabilitacionControlesDeFabrica(false);
		this.notificarActualizacion();
    }
    
    private void actualizarDatosLaboratorio() {
    	textTipoLabo.setText(getJugador().getLaboratorio().getTipo());
    	textDineroAcumLabo.setText(Float.toString(getJugador().getLaboratorio().getDineroAcumulado()));
        Image imagenLaboratorio = new Image(display, RecursosAplicacion.instance()
                .getResourceAsStream("images/" + getJugador().getLaboratorio().getNombreImagen()));
    	buttonImagenLabo.setImage(imagenLaboratorio);
    }
    
    /**
	 * Actualiza los datos del tiempo en la pantalla.
	 */
    private void actualizarDatosTiempo() {
    	this.textTime.setText(Calendario.instancia().fechaFormateada(PATRON_FECHA_ESPANIOL));
    	notificarActualizacion();
    }
    
    /**
	 * Vende la fábrica del jugador.
	 */
	private void vender() {
		try {
			this.getJugador().verificarFabricaAsignada();
		} catch (JugadorConFabricaException e) {
			this.getJugador().getFabrica().vender();
			cambiarHabilitacionBotonesDePartida(true);
            areaFabrica.setFabrica(null);
		}
	}
	
	/**
	 * Compra la fábrica seleccionada.
	 */
	private void comprar() {
		Fabrica fabrica = fabricas.get(comboFabrica.getText());
		try {
			fabrica.comprar(this.getJugador());
			cambiarHabilitacionBotonesDePartida(true);
            areaFabrica.setFabrica(fabrica);
		} catch (DineroInsuficienteException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK | SWT.CANCEL | SWT.ICON_ERROR);
				 messageBox.setMessage("No se tiene dinero suficiente.");
				 messageBox.open();
		} catch (FabricaOcupadaException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK | SWT.CANCEL | SWT.ICON_ERROR);
				 messageBox.setMessage("La fábrica ya se encuentra comprada por otro jugador.");
				 messageBox.open();
		} catch (JugadorConFabricaException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK | SWT.CANCEL | SWT.ICON_ERROR);
				 messageBox.setMessage("El jugador ya tiene una fábrica.");
				 messageBox.open();
		}
		
	}
	
	/**
	 * Alquila la fábrica seleccionada.
	 */
	private void alquilar() {
		Fabrica fabrica = fabricas.get(comboFabrica.getText());
		try {
			fabrica.alquilar(this.getJugador());
			cambiarHabilitacionBotonesDePartida(true);
            areaFabrica.setFabrica(fabrica);
		} catch (FabricaOcupadaException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK | SWT.CANCEL | SWT.ICON_ERROR);
				 messageBox.setMessage("La fábrica ya se encuentra comprada por otro jugador.");
				 messageBox.open();
		} catch (JugadorConFabricaException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK | SWT.CANCEL | SWT.ICON_ERROR);
				 messageBox.setMessage("El jugador ya tiene una fábrica.");
				 messageBox.open();
		}
		
	}
    
	public static void main(final String[] args) {
		VistaPrincipal ventana = new VistaPrincipal();
		ventana.run();
	}
}
