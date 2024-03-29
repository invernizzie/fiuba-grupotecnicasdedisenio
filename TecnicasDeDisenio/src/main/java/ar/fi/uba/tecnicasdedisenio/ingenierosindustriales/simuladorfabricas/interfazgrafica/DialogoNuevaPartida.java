package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import java.util.HashMap;


import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;



public class DialogoNuevaPartida {

    private static final int ANCHO = 300;
    private static final int ALTO = 200;
    private static final int DINERO_INICIAL = 1000;
    private static final int DINERO_MAXIMO = 10000;
    private static final int DINERO_MINIMO = 100;
    
    private Shell shellPartida = null;
    private Text tUsuario = null;
    private Spinner sDineroInicial = null;
    private Combo cTipoLaboratorio = null;
	private HashMap<String, Laboratorio> hashLaboratorios = null;
	private VistaPrincipal menu;

    public DialogoNuevaPartida(final VistaPrincipal menu) {
		this.createShellPartida();
		this.menu = menu;
	}
	
	public void crearJuego(){
		menu.resetearCalendario();
		Jugador jug = new Jugador(tUsuario.getText(),new Float(sDineroInicial.getText()));
		menu.setJugador(jug);
		menu.getJugador().addObserver(menu);
		menu.getJugador().setLaboratorio(hashLaboratorios.get(cTipoLaboratorio.getText()));
		menu.cargarOpcionesFabrica();
		shellPartida.close();
	}
	
	public void hacerVisible() {
		this.shellPartida.setVisible(true);
	}
	
	 /**
	 * This method initializes shellPartida
	 *
	 */
	private void createShellPartida() {
		GridData gridData7 = new GridData();
		gridData7.horizontalAlignment = GridData.FILL;
		gridData7.verticalAlignment = GridData.CENTER;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalAlignment = GridData.CENTER;
		gridData5.horizontalAlignment = GridData.FILL;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData31 = new GridData();
		gridData31.horizontalAlignment = GridData.FILL;
		gridData31.grabExcessHorizontalSpace = true;
		gridData31.verticalAlignment = GridData.CENTER;
		GridData gridData21 = new GridData();
		gridData21.grabExcessHorizontalSpace = true;
		gridData21.verticalAlignment = GridData.CENTER;
		gridData21.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.verticalSpan = 2;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellPartida = new Shell();
		shellPartida.setText("Crear Partida");
		shellPartida.setLayout(gridLayout);
		shellPartida.setSize(new Point(ANCHO, ALTO));
         Label lUsuario = new Label(shellPartida, SWT.HORIZONTAL);
		lUsuario.setText("Nombre de Usuario");
		lUsuario.setLayoutData(gridData7);
		tUsuario = new Text(shellPartida, SWT.BORDER);
		tUsuario.setLayoutData(gridData);
         Label lDineroInicial = new Label(shellPartida, SWT.HORIZONTAL);
		lDineroInicial.setText("Monto Inicial");
		lDineroInicial.setLayoutData(gridData6);
		sDineroInicial = new Spinner(shellPartida, SWT.BORDER);
		sDineroInicial.setSelection(DINERO_INICIAL);
		sDineroInicial.setEnabled(true);
		sDineroInicial.setPageIncrement(DINERO_INICIAL);
		sDineroInicial.setLayoutData(gridData4);
		sDineroInicial.setMaximum(DINERO_MAXIMO);
         Label lTipoLaboratorio = new Label(shellPartida, SWT.NONE);
		lTipoLaboratorio.setText("Tipo de Laboratorio");
		createCTipoLaboratorio();
         Button bCreate = new Button(shellPartida, SWT.NONE);
		bCreate.setText("Crear Partida");
		bCreate.setLayoutData(gridData21);
		bCreate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
                String mensaje = "";
                if (tUsuario.getText().length() < 1) {
                    mensaje += "Debe ingresar su nombre\n";
                }
                if (new Float(sDineroInicial.getText()) < DINERO_MINIMO) {
                    mensaje += "El dinero inicial debe ser al menos "+ DINERO_MINIMO +"\n";
                }
                
                if (mensaje.length() > 0) {
                    new DialogoMensaje(mensaje);
                } else {
				    crearJuego();
                }
			}
		});
        Button bCancel = new Button(shellPartida, SWT.NONE);
		bCancel.setText("Cancelar");
		bCancel.setLayoutData(gridData31);
		bCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				shellPartida.close();
			}
		});
	}
	
	/**
	 * This method initializes cTipoFabrica
	 *
	 */
	private void createCTipoLaboratorio() {
		hashLaboratorios = new HashMap<String, Laboratorio>();
		Laboratorio labo = null;
		
		labo = new Laboratorio("Comidas", "comida.jpg");
		hashLaboratorios.put("Comidas", labo);
		
		labo = new Laboratorio("Bebidas", "bebida.jpg");
		hashLaboratorios.put("Bebidas", labo);
		
		labo = new Laboratorio("Ropa", "ropa.jpg");
		hashLaboratorios.put("Ropa", labo);
		
		labo = new Laboratorio("Videojuegos", "videojuegos.jpg");
		hashLaboratorios.put("Videojuegos", labo);
		
		labo = new Laboratorio("Electrodomesticos", "electrodomesticos.jpg");
		hashLaboratorios.put("Electrodomesticos", labo);
		
		
		String[] laboratorios = new String[] { "Comidas", "Bebidas", "Ropa", "Videojuegos", "Electrodomesticos"};
		
		cTipoLaboratorio = new Combo(shellPartida, SWT.NONE);
		cTipoLaboratorio.setItems(laboratorios);
		cTipoLaboratorio.setText(cTipoLaboratorio.getItem(0));
	}
}

