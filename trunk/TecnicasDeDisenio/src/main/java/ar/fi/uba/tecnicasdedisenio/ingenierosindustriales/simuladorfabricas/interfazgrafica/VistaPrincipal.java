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

	private boolean actualizado = false;
	private Shell shellPrincipal = null;  //  @jve:decl-index=0:visual-constraint="79,7"
	private Menu menuBar = null;
	private Group groupTiempo = null;
	private Group groupJugador = null;
	private CTabFolder tabFolderFabrica = null;
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
	private AreaFabricaAEmbeber areaFabrica = null;
	private Jugador jugador = null;
	private Button buttonComprar = null;
	private Button buttonAlquilar = null;
	private Button buttonVender = null;
	private HashMap<String, Fabrica> fabricas;
    private NumberFormat formateador = NumberFormat.getInstance();
    private Set<Widget> botonesPartida = new HashSet<Widget>();
	private Composite compositeLaboratorio = null;
	private Label labelTipoLabo = null;
	private Text textTipoLabo = null;
	private Label labelDineroAcumLabo = null;
	private Text textDineroAcumLabo = null;
	private Button buttonImagenLabo = null;
	private Image imagenLaboratorio = null;
	private Display display = null;

    /**
	 * This method initializes shellPrincipal
	 *
	 */
	private void createShellPrincipal() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellPrincipal = new Shell(SWT.V_SCROLL | SWT.SHELL_TRIM | SWT.H_SCROLL);
		shellPrincipal.setText("TP Tecnicas de dise�o");
		createGroupTiempo();
		createTabFolderFabrica();
		createGroupJugador();
		shellPrincipal.setLayout(gridLayout);
		shellPrincipal.setSize(new Point(792, 459));
		CreateMenuBar();
		shellPrincipal.setMenuBar(menuBar);
	}

	private void CreateMenuBar(){
		menuBar = new Menu(shellPrincipal, SWT.BAR);
		menuBar.setEnabled(true);
        MenuItem submenuItemJuego = crearSubmenu(menuBar, "Juego");
        MenuItem submenuItemAyuda = crearSubmenu(menuBar, "Ayuda");
		submenuAyuda = new Menu(submenuItemAyuda);
        MenuItem pushContenido = crearItemDeMenu(submenuAyuda, "Contenido");
		MenuItem separatorAyuda = new MenuItem(submenuAyuda, SWT.SEPARATOR);
        MenuItem pushAcercaDe = crearItemDeMenu(submenuAyuda, "Acerca de...");
		submenuItemAyuda.setMenu(submenuAyuda);
		submenuJuego = new Menu(submenuItemJuego);
		submenuJuego.setVisible(true);
		submenuJuego.setEnabled(true);
		submenuItemJuego.setMenu(submenuJuego);
        MenuItem pushJuegoNuevo = crearItemDeMenu(submenuJuego, "Juego Nuevo");
		pushJuegoNuevo.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetDefaultSelected()");
				juegoNuevo();
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
		pushSalir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Calendario.instancia().detener();
				shellPrincipal.close();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		pushContenido.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				 MessageBox messageBox = new MessageBox(shellPrincipal, SWT.OK|SWT.ICON_INFORMATION);	 
				 String mensaje = new String ("Contenido:\n");
				 mensaje += "TP Tecnicas de Dise�o 2010 - Simulador de F�bricas";  
				 messageBox.setMessage(mensaje);
				 messageBox.open();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		pushAcercaDe.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				 MessageBox messageBox = new MessageBox(shellPrincipal, SWT.OK|SWT.ICON_INFORMATION);	 
				 String mensaje = new String ("Creditos:\n");
				 mensaje += "Esteban Invernizzi\n"; 
				 mensaje += "Gustavo Meller\n"; 
				 mensaje += "Santiago Risaro\n"; 
				 mensaje += "Diego Garcia Jaime\n"; 
				 messageBox.setMessage(mensaje);
				 messageBox.open();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});

	}

    private MenuItem crearItemDeMenu(Menu padre, String texto) {
        MenuItem itemDeMenu = new MenuItem(padre, SWT.PUSH);
        itemDeMenu.setText(texto);
        return itemDeMenu;
    }

    private MenuItem crearSubmenu(Menu padre, String texto) {
        MenuItem submenu = new MenuItem(padre, SWT.CASCADE);
        submenu.setText(texto);
        return submenu;
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
		textTime.setText(Calendario.instancia().fechaAsString());
		buttonTimer.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (!Calendario.instancia().estaIniciado()) {
                    Calendario.instancia().iniciar();
                    cambiarHabilitacionBotonesDePartida(false);
                    /*Se hace porque sino quedan habilitados algunos botones.*/
                    buttonTimer.setEnabled(true);
                    cambiarHabilitacionControlesDeFabrica(false);
                    areaFabrica.cambiarHabilitacionBotones(false);
                }
                else{
                    if (Calendario.instancia().estaPausado()){
                        Calendario.instancia().reanudar();
                		cambiarHabilitacionBotonesDePartida(false);
                		/*Se hace porque sino quedan habilitados algunos botones.*/
                        buttonTimer.setEnabled(true);
                        cambiarHabilitacionControlesDeFabrica(false);
                        areaFabrica.cambiarHabilitacionBotones(false);
                    }
                    else{
                        Calendario.instancia().pausar();
                        cambiarHabilitacionBotonesDePartida(true);
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {}
        });
		
		botonesPartida.add(buttonTimer);
	}

    private void cambiarHabilitacionControlesDeFabrica(boolean habilitados) {
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
		checkBoxInvertirLabo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(checkBoxInvertirLabo.getSelection())
					getJugador().habilitarLaboratorio();
				else
					getJugador().deshabilitarLaboratorio();
				
			}
		});
		
		Label filler = new Label(groupJugador, SWT.NONE);
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
		buttonAlquilar.setText("Alquilar F�brica");
		buttonAlquilar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				alquilar();
			}
		});
		
		buttonComprar = new Button(groupJugador, SWT.NONE);
		buttonComprar.setText("Comprar F�brica");
		buttonComprar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				comprar();
			}
		});
		
		buttonVender = new Button(groupJugador, SWT.NONE);
		buttonVender.setText("Vender F�brica");
		buttonVender.setEnabled(false);
		buttonVender.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
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
		areaFabrica = new AreaFabricaAEmbeber();
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
	private void juegoNuevo(){
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
		labelTipoLabo = new Label(compositeLaboratorio, SWT.NONE);
		labelTipoLabo.setText("Tipo Laboratorio");
		labelTipoLabo.setVisible(true);
		labelTipoLabo.setLayoutData(gridData2);
		textTipoLabo = new Text(compositeLaboratorio, SWT.BORDER | SWT.READ_ONLY);
		textTipoLabo.setVisible(true);
		textTipoLabo.setText("<Tipo Laboratorio>");
		textTipoLabo.setLayoutData(gridData5);
		labelDineroAcumLabo = new Label(compositeLaboratorio, SWT.NONE);
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
	
    private void cambiarHabilitacionBotonesDePartida(boolean habilitados) {
        for (Widget boton: botonesPartida)
            ((Control) boton).setEnabled(habilitados);
        if(this.getJugador()!=null){
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
        if (getJugador() == null)
            return;
    	textJugador.setText(getJugador().getNombre());
		textDineroAcum.setText(formateador.format(getJugador().getDineroActual()));
		actualizarDatosLaboratorio();
	}
    
    /**
     * Verifica si termina el juego.
     */
    private void verificarFinalJuego(){
    	if (getJugador()==null)
    		return;
    	if (getJugador().getDineroActual()<0)
        	this.finalizarJuego(" PERDIO al quedarse sin dinero.");
		if (getJugador().getDineroActual()>=DINERO_PARA_GANAR)
			this.finalizarJuego(" GANO al cumplir el objetivo monetario.");
    }
    
    /**
     * Indica el final de un juego.
     * @param mensaje
     */
    private void finalizarJuego(String mensaje){
    	MessageBox messageBox =
			   new MessageBox(shellPrincipal, SWT.OK|SWT.ICON_INFORMATION);
			 messageBox.setMessage("El jugador "+this.getJugador().getNombre() + mensaje);
			 messageBox.open();
		cambiarHabilitacionBotonesDePartida(false);
		Calendario.instancia().detener();
        buttonTimer.setEnabled(false);
        cambiarHabilitacionControlesDeFabrica(false);
		this.notificarActualizacion();
    }
    
    private void actualizarDatosLaboratorio(){
    	textTipoLabo.setText(getJugador().getLaboratorio().getTipo());
    	textDineroAcumLabo.setText(Float.toString(getJugador().getLaboratorio().getDineroAcumulado()));
    	//imagenLaboratorio = new Image(display, dirImagenes+getJugador().getLaboratorio().getNombreImagen());
    	imagenLaboratorio = new Image(display, RecursosAplicacion.instance()
                .getResourceAsStream("images/" + getJugador().getLaboratorio().getNombreImagen()));
    	buttonImagenLabo.setImage(imagenLaboratorio);
    }
    
    /**
	 * Actualiza los datos del tiempo en la pantalla.
	 */
    private void actualizarDatosTiempo(){
    	this.textTime.setText(Calendario.instancia().fechaAsString());
    	notificarActualizacion();
    }
    
    /**
	 * Vende la f�brica del jugador.
	 * */
	private void vender(){
		try {
			this.getJugador().verificarFabricaAsignada();
		} 
		catch (JugadorConFabricaException e) {
			this.getJugador().getFabrica().vender();
			cambiarHabilitacionBotonesDePartida(true);
            areaFabrica.setFabrica(null);
		}
	}
	
	/**
	 * Compra la f�brica seleccionada.
	 * */
	private void comprar(){
		Fabrica fabrica = fabricas.get(comboFabrica.getText());
		try {
			fabrica.comprar(this.getJugador());
			cambiarHabilitacionBotonesDePartida(true);
            areaFabrica.setFabrica(fabrica);
		} 
		catch (DineroInsuficienteException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
				 messageBox.setMessage("No se tiene dinero suficiente.");
				 messageBox.open();
		}
		catch (FabricaOcupadaException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
				 messageBox.setMessage("La f�brica ya se encuentra comprada por otro jugador.");
				 messageBox.open();
		} 
		catch (JugadorConFabricaException e) {
			
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
				 messageBox.setMessage("El jugador ya tiene una f�brica.");
				 messageBox.open();
		}
		
	}
	
	/**
	 * Alquila la f�brica seleccionada.
	 * */
	private void alquilar(){
		Fabrica fabrica = fabricas.get(comboFabrica.getText());
		try {
			fabrica.alquilar(this.getJugador());
			cambiarHabilitacionBotonesDePartida(true);
            areaFabrica.setFabrica(fabrica);
		}
		catch (FabricaOcupadaException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
				 messageBox.setMessage("La f�brica ya se encuentra comprada por otro jugador.");
				 messageBox.open();
		} 
		catch (JugadorConFabricaException e) {
			 MessageBox messageBox =
				   new MessageBox(shellPrincipal, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
				 messageBox.setMessage("El jugador ya tiene una f�brica.");
				 messageBox.open();

		}
		
	}
    
	/**
	 * Carga las distintas fabricas standard.
	 */
	public void cargarOpcionesFabrica(){
		int i;
		String[] fab = new String[5];
		
		/*
		 * Fabrica 0: Tamanio 100, Costo Compra 1000, Costo Alquiler 150, Cantidad Lineas 1.
		 * Fabrica 1: Tamanio 200, Costo Compra 2000, Costo Alquiler 300, Cantidad Lineas 2.
		 * Fabrica 2: Tamanio 300, Costo Compra 3000, Costo Alquiler 450, Cantidad Lineas 3.
		 * Fabrica 3: Tamanio 400, Costo Compra 4000, Costo Alquiler 600, Cantidad Lineas 4.
		 * Fabrica 4: Tamanio 500, Costo Compra 5000, Costo Alquiler 750, Cantidad Lineas 5.
		 */
		fabricas = new HashMap<String,Fabrica>();
		Fabrica fabrica = null;
		for(i=0;i<5;i++){
			fabrica = new Fabrica((i+1)*15000,(i+1)*1000, (i+1)*150);
			fabricas.put(fabrica.toString(),fabrica);
			fab[i]=fabrica.toString();
		}
		//comboFabrica = new Combo(groupJugador, SWT.NONE);
		comboFabrica.removeAll();
		comboFabrica.setItems(fab);
		comboFabrica.setText(comboFabrica.getItem(0));
		checkBoxInvertirLabo.setSelection(false);
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
                if (!display.readAndDispatch())
                    display.sleep();
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

    public void setJugador(Jugador jugador) {
    	this.jugador = jugador;
        areaFabrica.setFabrica(jugador.getFabrica());
        cambiarHabilitacionBotonesDePartida(jugador != null);
        
	}

	public Jugador getJugador() {
		return jugador;
	}
    
	
	@Override
	public void update(Observable arg0, Object arg1) {
		forzarActualizacion();
	}
	
	/**
    * Resetea el calendario para que vuelva a empezar.
    */
    public void resetearCalendario(){
    	Calendario.instancia().inicializar();
    	Calendario.instancia().registrar(this);
    	Calendario.instancia().registrar(ValidadorProductos.instancia());
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        buttonTimer.setText("Comenzar");
    }

	public static void main(String [ ] args){
		VistaPrincipal ventana = new VistaPrincipal();
		ventana.run();
	}
}

