package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPlancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

public class AreaFabrica {

	private Shell shellAreaDibujo = null;  //  @jve:decl-index=0:visual-constraint="109,17"
	private Canvas canvas = null;  //  @jve:decl-index=0:visual-constraint="263,47"
	private Group groupControl = null;
	private Button buttonCinta = null;
	private Button buttonMaquina = null;
	private Composite compositeControles = null;
	private Boolean dibujar = false;
	private Button buttonMateriaPrima = null;
	private Combo comboMP = null;
	private Combo comboMaquina = null;
    private ConstructorDeFabricas constructorDeFabricas;
    private EspacioFabril espacioFabril;
    private ValidadorProductos validadorProd = ValidadorProductos.instancia();

	/**
	 * This method initializes comboMP
	 *
	 */
	private void createComboMP() {
		comboMP = new Combo(groupControl, SWT.NONE);
		CargarComboMateriaPrimas();
		comboMP.setEnabled(false);
		comboMP.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				System.out.println("Materia Prima Seleccionada= "+ comboMP.getText());
				constructorDeFabricas.setDibujante(new DibujanteDeMateriaPrima(espacioFabril, new Producto(validadorProd,comboMP.getText(),0F)));
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
	}

	protected void CargarComboMateriaPrimas() {
		// TODO Auto-generated method stub
		//for(int i=0; i<10; i++){
		//	comboMP.add(new String("Materia Prima  " + i));
		//}
		//comboMP.setItems(validadorProd.toString().split(",", 0));
		comboMP.setItems(validadorProd.getAll());
		//comboMP.setText(comboMP.getItem(0));
	}

	/**
	 * This method initializes comboMaquina
	 *
	 */
	private void createComboMaquina() {
		comboMaquina = new Combo(groupControl, SWT.NONE);
		CargarMaquinas();
		comboMaquina.setEnabled(false);
		comboMaquina.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				System.out.println("Maquina Seleccionada= "+ comboMaquina.getText());
				constructorDeFabricas.setDibujante(new DibujanteDeMaquinas(espacioFabril, new TipoMaquinaPlancha()));
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
	}

	private void CargarMaquinas() {
		// TODO Auto-generated method stub
		for(int i=0; i<10; i++){
			comboMaquina.add(new String("Maquina " + i));
		}
		//comboMaquina.setText(comboMaquina.getItem(0));
	}
    
    public void run() {
		createShellAreaDibujo();
	}

	/**
	 * This method initializes shellAreaDibujo
	 *
	 */
	private void createShellAreaDibujo() {

        Display display = Display.getDefault();
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellAreaDibujo = new Shell(display);
		createCompositeControles();
		createCanvas(this);
		shellAreaDibujo.setLayout(gridLayout);
		shellAreaDibujo.setSize(new Point(399, 316));
		shellAreaDibujo.setVisible(true);




		while (!shellAreaDibujo.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method initializes canvas
	 *
	 */
	private void createCanvas(final AreaFabrica theArea) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		canvas = new Canvas(shellAreaDibujo, SWT.BORDER);
		canvas.setLayout(new FillLayout());
		canvas.setSize(new Point(268, 259));
		canvas.setLayoutData(gridData);

        espacioFabril = new EspacioFabril(canvas);
        constructorDeFabricas = new ConstructorDeFabricas(new DibujanteDeCintas(espacioFabril));
		canvas.addListener (SWT.MouseDown, constructorDeFabricas);
		canvas.addListener (SWT.MouseUp, constructorDeFabricas);
		canvas.addListener (SWT.MouseMove, constructorDeFabricas);

	}

	/**
	 * This method initializes groupControl
	 *
	 */
	private void createGroupControl() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 1;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		groupControl = new Group(compositeControles, SWT.NONE);
		groupControl.setText("Controles");
		groupControl.setLayout(gridLayout1);
		buttonCinta = new Button(groupControl, SWT.TOGGLE);
		buttonCinta.setText("Linea");
		buttonCinta.setLayoutData(gridData1);
		buttonMateriaPrima = new Button(groupControl, SWT.TOGGLE);
		buttonMateriaPrima.setText("Materia Prima");
		buttonMateriaPrima.setLayoutData(gridData3);
		createComboMP();
		buttonMaquina = new Button(groupControl, SWT.TOGGLE);
		buttonMaquina.setText("Maquina");
		buttonMaquina.setLayoutData(gridData2);
		createComboMaquina();
        buttonCinta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso dibujar cintas"); // TODO Auto-generated Event stub widgetSelected()
                constructorDeFabricas.setDibujante(new DibujanteDeCintas(espacioFabril));
                System.out.println("De-selecciono los otros botones");
				buttonMaquina.setSelection(false);
				buttonMateriaPrima.setSelection(false);
				comboMaquina.setEnabled(false);
				comboMP.setEnabled(false);
				//DibujarLinea();
			}
		});
        buttonMateriaPrima
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("Paso a dibujar materia prima");
                        // TODO Pasarle una materia prima
						comboMP.setEnabled(true);
                        //constructorDeFabricas.setDibujante(new DibujanteDeMateriaPrima(espacioFabril, new Producto()));
                        System.out.println("De-selecciono los otros botones");
						buttonMaquina.setSelection(false);
						buttonCinta.setSelection(false);
						comboMaquina.setEnabled(false);
						comboMP.setEnabled(true);
					}
				});
        buttonMaquina.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso a dibujar prensas"); // TODO Auto-generated Event stub widgetSelected()
				comboMaquina.setEnabled(true);
                //constructorDeFabricas.setDibujante(new DibujanteDeMaquinas(espacioFabril, new TipoMaquinaPlancha()));
                System.out.println("De-selecciono los otros botones");
				buttonMateriaPrima.setSelection(false);
				buttonCinta.setSelection(false);
				comboMP.setEnabled(false);
			}
		});


	}

	public void DibujarLinea(){
		this.setDibujar(!this.getDibujar());
	}

	/**
	 * This method initializes compositeControles
	 *
	 */
	private void createCompositeControles() {
		compositeControles = new Composite(shellAreaDibujo, SWT.NONE);
		compositeControles.setLayout(new GridLayout());
		createGroupControl();
	}

	public void setDibujar(Boolean dibujar) {
		this.dibujar = dibujar;
	}

	public Boolean getDibujar() {
		return dibujar;
	}

    public Canvas getCanvas() {
        return canvas;
    }
}

