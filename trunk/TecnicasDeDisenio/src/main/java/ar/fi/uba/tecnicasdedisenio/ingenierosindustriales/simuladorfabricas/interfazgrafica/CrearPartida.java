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
	private Label lfabrica = null;
	private Text tFabrica = null;
	private Button bCreate = null;
	private Button bCancel = null;
	private Label lDineroInicial = null;
	private Spinner sDineroInicial = null;
	private Label lTipoLaboratorio = null;
	private Combo cTipoLaboratorio = null;
	private Jugador jugador;
	private HashMap<String,Laboratorio> hashLaboratorios = null;

	public CrearPartida(){
		this.createShellPartida();
	}

	/**
	 * This method initializes shellPartida
	 *
	 */
	private void createShellPartida() {
		GridData gridData = new GridData();
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellPartida = new Shell();
		shellPartida.setText("Crear Partida");
		shellPartida.setLayout(gridLayout);
		shellPartida.setSize(new Point(209, 157));
		lUsuario = new Label(shellPartida, SWT.HORIZONTAL);
		lUsuario.setText("Nombre de Usuario");
		tUsuario = new Text(shellPartida, SWT.BORDER);
		tUsuario.setLayoutData(gridData);
		//lfabrica = new Label(shellPartida, SWT.HORIZONTAL);
		//lfabrica.setText("Nombre Fabrica");
		//tFabrica = new Text(shellPartida, SWT.BORDER);
		lDineroInicial = new Label(shellPartida, SWT.HORIZONTAL);
		lDineroInicial.setText("Monto Inicial");
		sDineroInicial = new Spinner(shellPartida, SWT.NONE);
		sDineroInicial.setMaximum(10000000);
		sDineroInicial.setSelection(500000);
		sDineroInicial.setEnabled(true);
		sDineroInicial.setPageIncrement(10000);
		lTipoLaboratorio = new Label(shellPartida, SWT.NONE);
		lTipoLaboratorio.setText("Tipo de Laboratorio");
		createCTipoLaboratorio();
		bCreate = new Button(shellPartida, SWT.NONE);
		bCreate.setText("Crear Partida");
		bCreate.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()");
				// TODO Auto-generated Event stub widgetSelected()
				jugador = new Jugador(tUsuario.getText(),new Float(sDineroInicial.getText()));
				jugador.setLaboratorio(hashLaboratorios.get(cTipoLaboratorio.getText()));
				System.out.println(jugador.getLaboratorio().getTipo());
				System.out.println("Ahora se deben cargar los valores para iniciar la partida");
				shellPartida.close();
			}
		});
		bCancel = new Button(shellPartida, SWT.NONE);
		bCancel.setText("Cancelar");
		bCancel.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				shellPartida.close();
			}
		});
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

