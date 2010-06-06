package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.ConstructorDeFabricas;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.DibujanteDeCintas;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.DibujanteDeMaquinas;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.DibujanteDeMateriaPrima;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

public class AreaFabrica {

	private Shell shellAreaDibujo = null;  //  @jve:decl-index=0:visual-constraint="109,17"
	private Canvas canvas = null;  //  @jve:decl-index=0:visual-constraint="263,47"
	private Group groupControl = null;
	private Button buttonCinta = null;
	private Button buttonMaq1 = null;
	private Button buttonMaq2 = null;
	private Button buttonMaq3 = null;
	private Composite compositeControles = null;
	private Boolean dibujar = false;
	private Button buttonMateriaPrima = null;
    private ConstructorDeFabricas constructorDeFabricas;

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

        constructorDeFabricas = new ConstructorDeFabricas(new DibujanteDeCintas(canvas));
		canvas.addListener (SWT.MouseDown, constructorDeFabricas);
		canvas.addListener (SWT.MouseUp, constructorDeFabricas);
		canvas.addListener (SWT.MouseMove, constructorDeFabricas);

	}

	/**
	 * This method initializes groupControl
	 *
	 */
	private void createGroupControl() {
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
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
		buttonMateriaPrima
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("Paso a dibujar materia prima");
                        constructorDeFabricas.setDibujante(new DibujanteDeMateriaPrima(canvas));
					}
				});
		buttonMaq1 = new Button(groupControl, SWT.TOGGLE);
		buttonMaq1.setText("Maquina1");
		buttonMaq1.setLayoutData(gridData2);
		buttonCinta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso dibujar cintas"); // TODO Auto-generated Event stub widgetSelected()
                constructorDeFabricas.setDibujante(new DibujanteDeCintas(canvas));
				//DibujarLinea();
			}
		});
		buttonMaq1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso a dibujar maquina 1"); // TODO Auto-generated Event stub widgetSelected()
                constructorDeFabricas.setDibujante(new DibujanteDeMaquinas(canvas));
			}
		});
		buttonMaq2 = new Button(groupControl, SWT.TOGGLE);
		buttonMaq2.setText("Maquina2");
		buttonMaq2.setLayoutData(gridData3);
		buttonMaq2.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso a dibujar maquina 2"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		buttonMaq3 = new Button(groupControl, SWT.TOGGLE);
		buttonMaq3.setText("Maquina3");
		buttonMaq3.setLayoutData(gridData4);
		buttonMaq3.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso a dibujar maquina 3"); // TODO Auto-generated Event stub widgetSelected()
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

