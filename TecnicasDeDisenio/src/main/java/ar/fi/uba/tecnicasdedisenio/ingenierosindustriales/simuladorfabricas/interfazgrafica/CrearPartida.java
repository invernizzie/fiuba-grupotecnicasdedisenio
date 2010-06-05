package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import java.util.HashMap;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;

public class CrearPartida {

	private Shell shellPartida = null;  //  @jve:decl-index=0:visual-constraint="126,20"
	private Label lUsuario = null;
	private Text tUsuario = null;
	private Button bCreate = null;
	private Button bCancel = null;
	private Label lDineroInicial = null;
	private Spinner sDineroInicial = null;
	private Label lTipoLaboratorio = null;
	private Combo cTipoLaboratorio = null;
	private HashMap<String,Laboratorio> hashLaboratorios = null;
	private NuevoMenu menu;

	public CrearPartida(NuevoMenu menu){
		this.createShellPartida();
		this.menu = menu;
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
		shellPartida.setSize(new Point(210, 178));
		lUsuario = new Label(shellPartida, SWT.HORIZONTAL);
		lUsuario.setText("Nombre de Usuario");
		lUsuario.setLayoutData(gridData7);
		tUsuario = new Text(shellPartida, SWT.BORDER);
		tUsuario.setLayoutData(gridData);
		lDineroInicial = new Label(shellPartida, SWT.HORIZONTAL);
		lDineroInicial.setText("Monto Inicial");
		lDineroInicial.setLayoutData(gridData6);
		sDineroInicial = new Spinner(shellPartida, SWT.BORDER);
		sDineroInicial.setSelection(1000);
		sDineroInicial.setEnabled(true);
		sDineroInicial.setPageIncrement(1000);
		sDineroInicial.setLayoutData(gridData4);
		sDineroInicial.setMaximum(10000);
		lTipoLaboratorio = new Label(shellPartida, SWT.NONE);
		lTipoLaboratorio.setText("Tipo de Laboratorio");
		createCTipoLaboratorio();
		bCreate = new Button(shellPartida, SWT.NONE);
		bCreate.setText("Crear Partida");
		bCreate.setLayoutData(gridData21);
		bCreate.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				CrearJuego();
			}
		});
		bCancel = new Button(shellPartida, SWT.NONE);
		bCancel.setText("Cancelar");
		bCancel.setLayoutData(gridData31);
		bCancel.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				shellPartida.close();
			}
		});
	}
	/**
	 * This method initializes shellPartida
	 *
	 */
	
	public void CrearJuego(){
	Jugador jug = new Jugador(tUsuario.getText(),new Float(sDineroInicial.getText()));
	menu.setJugador(jug);
	menu.getJugador().addObserver(menu);
	menu.getJugador().setLaboratorio(hashLaboratorios.get(cTipoLaboratorio.getText()));
	
	shellPartida.close();
	}
	/**
	 * This method initializes cTipoFabrica
	 *
	 */
	private void createCTipoLaboratorio() {
		hashLaboratorios = new HashMap<String,Laboratorio>();
		Laboratorio labo = null;
		
		labo = new Laboratorio("Comidas");
		hashLaboratorios.put("Comidas", labo);
		
		labo = new Laboratorio("Bebidas");
		hashLaboratorios.put("Bebidas", labo);
		
		labo = new Laboratorio("Ropa");
		hashLaboratorios.put("Ropa", labo);
		
		labo = new Laboratorio("Videojuegos");
		hashLaboratorios.put("Videojuegos", labo);
		
		labo = new Laboratorio("Electrodomesticos");
		hashLaboratorios.put("Electrodomesticos", labo);
		
		
		String[] laboratorios = new String[]{"Comidas","Bebidas","Ropa","Videojuegos","Electrodomesticos"};
		
		cTipoLaboratorio = new Combo(shellPartida, SWT.NONE);
		cTipoLaboratorio.setItems(laboratorios);
		cTipoLaboratorio.setText(cTipoLaboratorio.getItem(0));
	}

	public void hacerVisible(){
		this.shellPartida.setVisible(true);
	}

}

