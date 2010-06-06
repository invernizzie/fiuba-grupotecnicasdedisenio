package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;

public class AreaFabrica {

	private Shell sShellAreaDibujo = null;  //  @jve:decl-index=0:visual-constraint="109,17"
	private Canvas canvas = null;  //  @jve:decl-index=0:visual-constraint="263,47"
	private Group groupControl = null;
	private Button buttonCinta = null;
	private Button buttonMaq1 = null;
	private Button buttonMaq2 = null;
	private Button buttonMaq3 = null;
	private Composite compositeControles = null;
	private Boolean dibujar = false;
	private Button buttonMateriaPrima = null;

	public void run() {
		AreaFabrica area = new AreaFabrica();
		area.createSShellAreaDibujo();
	}

	/**
	 * This method initializes sShellAreaDibujo
	 *
	 */
	private void createSShellAreaDibujo() {
		Display display = Display.getDefault();
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShellAreaDibujo = new Shell(display);
		createCompositeControles();
		createCanvas(this);
		sShellAreaDibujo.setLayout(gridLayout);
		sShellAreaDibujo.setSize(new Point(399, 316));
		this.sShellAreaDibujo.setVisible(true);




		while (!sShellAreaDibujo.isDisposed()) {
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
		canvas = new Canvas(sShellAreaDibujo, SWT.BORDER);
		canvas.setLayout(new FillLayout());
		canvas.setSize(new Point(268, 259));
		canvas.setLayoutData(gridData);

		Listener listener = new Listener () {
//			AreaFabrica area = theArea;
			int lastX = 0, lastY = 0;

			public void handleEvent (Event event) {
				switch (event.type) {
					case SWT.MouseMove:
						if (((event.stateMask & SWT.BUTTON1) == 0)
								|| !theArea.getDibujar()){
							break;
						}
					case SWT.MouseDown:
						lastX = event.x;
						lastY = event.y;
						break;

					case SWT.MouseUp:
						// Ahora uno las líneas pero no queda como quiero
						// No tiene PREVIEW, asique no sabés como queda :(
						GC gc = new GC (canvas);
						// TODO discriminar de quien viene el evento para dibujar linea o rect.
						gc.drawLine (lastX, lastY, event.x, event.y);
						gc.dispose ();
						// TODO validar que no haya colisiones y meterlas en contenedor para que Santi lo pueda crear

						break;
				}
			}
		};
		canvas.addListener (SWT.MouseDown, listener);
		canvas.addListener (SWT.MouseUp, listener);
		canvas.addListener (SWT.MouseMove, listener);

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
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
					}
				});
		buttonMaq1 = new Button(groupControl, SWT.TOGGLE);
		buttonMaq1.setText("Maquina1");
		buttonMaq1.setLayoutData(gridData2);
		buttonCinta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				//DibujarLinea();
			}
		});
		buttonMaq1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		buttonMaq2 = new Button(groupControl, SWT.TOGGLE);
		buttonMaq2.setText("Maquina2");
		buttonMaq2.setLayoutData(gridData3);
		buttonMaq2.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		buttonMaq3 = new Button(groupControl, SWT.TOGGLE);
		buttonMaq3.setText("Maquina3");
		buttonMaq3.setLayoutData(gridData4);
		buttonMaq3.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
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
		compositeControles = new Composite(sShellAreaDibujo, SWT.NONE);
		compositeControles.setLayout(new GridLayout());
		createGroupControl();
	}

	public void setDibujar(Boolean dibujar) {
		this.dibujar = dibujar;
	}

	public Boolean getDibujar() {
		return dibujar;
	}

}

