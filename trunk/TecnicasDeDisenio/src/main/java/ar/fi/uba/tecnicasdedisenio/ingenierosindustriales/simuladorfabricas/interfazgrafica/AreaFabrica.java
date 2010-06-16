package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class AreaFabrica {

    //private Shell shellAreaDibujo = null;  //  @jve:decl-index=0:visual-constraint="109,17"
	private Canvas canvas = null;  //  @jve:decl-index=0:visual-constraint="263,47"
	private Group grupoConstruccion = null;
	private Button buttonCinta = null;
	private Button buttonMaquina = null;
    private Button buttonBorrarMaquina = null;
    private Button buttonBorrarMP = null;
    private Button buttonRepararMaquina = null;
    private Composite compositeControles = null;
    private Button buttonMoverMaquina = null;
    private Boolean dibujar = false;
    private Button buttonMateriaPrima = null;
    private Combo comboMP = null;
    private Combo comboMaquina = null;
    private ConstructorDeFabricas constructorDeFabricas;
    private EspacioFabril espacioFabril;
    private ValidadorProductos validadorProd = ValidadorProductos.instancia();
    private HashMap<String,TipoMaquina> hashTipoMaquinas = null;
    private List<Button> botones = new ArrayList<Button>();
    private List<Combo> combos = new ArrayList<Combo>();
    private Fabrica fabrica;

    /**
	 * This method initializes comboMP
	 *
	 */
	private void createComboMP() {
		comboMP = new Combo(grupoConstruccion, SWT.NONE);
		cargarComboMateriaPrimas();
		comboMP.setEnabled(false);
		comboMP.select(0);
		comboMP.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Materia Prima Seleccionada= "+ comboMP.getText());
				elegirMP();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
        combos.add(comboMP);
	}
	
	/**
	 * This method initializes comboMaquina
	 *
	 */
	private void createComboMaquina() {
		hashTipoMaquinas = new HashMap<String,TipoMaquina>();
		comboMaquina = new Combo(grupoConstruccion, SWT.NONE);
		cargarMaquinas();
		comboMaquina.select(0);
		comboMaquina.setEnabled(false);
		comboMaquina.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Maquina Seleccionada= "+ comboMaquina.getText());
				elegirMaquina();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
        combos.add(comboMaquina);
	}

	private void cargarMaquinas() {
		
		comboMaquina.add("Horno");
		hashTipoMaquinas.put("Horno", new TipoMaquinaHorno());
		comboMaquina.add("Plancha");
		hashTipoMaquinas.put("Plancha", new TipoMaquinaPlancha());
		comboMaquina.add("Prensa");
		hashTipoMaquinas.put("Prensa", new TipoMaquinaPrensa());
		comboMaquina.add("Licuadora");
		hashTipoMaquinas.put("Licuadora", new TipoMaquinaLicuadora());
		comboMaquina.add("Mezcladora");
		hashTipoMaquinas.put("Mezcladora", new TipoMaquinaMezcladora());
		comboMaquina.add("ControlCalidad");
		hashTipoMaquinas.put("ControlCalidad", new TipoMaquinaControlCalidad());
		
		comboMaquina.setText(comboMaquina.getItem(0));
	}

	private void elegirMP() {
		constructorDeFabricas.setInstalador(
				new InstaladorDeFuentes(
						espacioFabril,
						new Producto(validadorProd, comboMP.getText(), 0F), comboMP.getText()));
	}

	private void elegirMaquina() {
		constructorDeFabricas.setInstalador(
				new InstaladorDeMaquinas(espacioFabril, hashTipoMaquinas.get(comboMaquina.getText())));
	}
	
	/**
	 * This method initializes shellAreaDibujo
     * 
	 * @param cTabFolder
	 */
	private void createShellAreaDibujo(CTabFolder cTabFolder) {
		createCompositeControles(cTabFolder);
		createCanvas(compositeControles);
	}

	/**
	 * This method initializes canvas
	 * @param shellAreaDibujo
	 *
	 */
	private void createCanvas(Composite shellAreaDibujo) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
        canvas = new Canvas(shellAreaDibujo, SWT.BORDER);
        canvas.addPaintListener(new org.eclipse.swt.events.PaintListener() {
            @Override
            public void paintControl(PaintEvent event) {
                espacioFabril.redibujar();
            }
        });
        resizeCanvas();
		canvas.setLayout(new FillLayout());
		canvas.setLayoutData(gridData);
		espacioFabril = new EspacioFabril(canvas);
		constructorDeFabricas = new ConstructorDeFabricas(new InstaladorDeCintas(espacioFabril));
		canvas.addListener (SWT.MouseDown, constructorDeFabricas);
		canvas.addListener (SWT.MouseUp, constructorDeFabricas);
		canvas.addListener (SWT.MouseMove, constructorDeFabricas);
	}

    private void resizeCanvas() {
        int longLadoCanvas = 240;
        if (fabrica != null)
            longLadoCanvas = (int) Math.sqrt(fabrica.getMetrosCuadrados());

        canvas.setSize(new Point(longLadoCanvas, longLadoCanvas));
    }

    /**
	 * This method initializes grupoConstruccion
	 *
	 */
	private void createGroupControl() {
		GridData layoutBotonSimple = new GridData();
		layoutBotonSimple.horizontalAlignment = GridData.FILL;
		layoutBotonSimple.verticalAlignment = GridData.CENTER;
		GridLayout layoutGrupoConstruccion = new GridLayout();
		layoutGrupoConstruccion.numColumns = 2;
		GridData layoutBotonDoble = new GridData();
		layoutBotonDoble.horizontalAlignment = GridData.FILL;
		layoutBotonDoble.verticalAlignment = GridData.CENTER;
        layoutBotonDoble.horizontalSpan = 2;
		grupoConstruccion = new Group(compositeControles, SWT.NONE);
		grupoConstruccion.setText("Controles");
		grupoConstruccion.setLayout(layoutGrupoConstruccion);
        buttonCinta = crearBoton(grupoConstruccion, "Cinta Transportadora", layoutBotonDoble);
        buttonMateriaPrima = crearBoton(grupoConstruccion, "Materia Prima", layoutBotonSimple);
		createComboMP();
        buttonMaquina = crearBoton(grupoConstruccion, "Maquina", layoutBotonSimple);
		createComboMaquina();
        buttonBorrarMaquina = crearBoton(grupoConstruccion, "Vender Maquina", layoutBotonSimple);
        buttonBorrarMP = crearBoton(grupoConstruccion, "Borrar M.P.", layoutBotonSimple);
        buttonRepararMaquina = crearBoton(grupoConstruccion, "Reparar Maquina", layoutBotonSimple);
        buttonMoverMaquina = crearBoton(grupoConstruccion, "Mover Maquina", layoutBotonSimple);

		cambiarHabilitacionBotones(false);
		buttonCinta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso a dibujar cintas");
				constructorDeFabricas.setInstalador(new InstaladorDeCintas(espacioFabril));
				deseleccionarControles();
                buttonCinta.setSelection(true);
			}
		});
		buttonMateriaPrima
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso a dibujar materia prima");
				deseleccionarControles();
                buttonMateriaPrima.setSelection(true);
				comboMP.setEnabled(true);
				elegirMP();
			}
		});
		buttonMaquina.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("Paso a dibujar maquinas");
				deseleccionarControles();
                buttonMaquina.setSelection(true);
				comboMaquina.setEnabled(true);
				elegirMaquina();
			}
		});
        buttonBorrarMaquina.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                deseleccionarControles();
                buttonBorrarMaquina.setSelection(true);
                constructorDeFabricas.setInstalador(new DesinstaladorDeMaquinas(espacioFabril));
            }
        });
        buttonBorrarMP.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                deseleccionarControles();
                buttonBorrarMP.setSelection(true);
                constructorDeFabricas.setInstalador(new DesinstaladorDeFuentes(espacioFabril));
            }
        });
        buttonRepararMaquina.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                deseleccionarControles();
                buttonRepararMaquina.setSelection(true);
                constructorDeFabricas.setInstalador(new ReparadorDeMaquinas(espacioFabril));
            }
        });
        buttonMoverMaquina.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                deseleccionarControles();
                buttonMoverMaquina.setSelection(true);
                constructorDeFabricas.setInstalador(new TrasladadorDeMaquinas(espacioFabril));
            }
        });
	}

    private Button crearBoton(Composite padre, String texto, Object layoutData) {
        Button nuevoBoton = new Button(padre, SWT.TOGGLE);
		nuevoBoton.setText(texto);
		nuevoBoton.setLayoutData(layoutData);
        botones.add(nuevoBoton);
        return nuevoBoton;
    }

	/**
	 * This method initializes compositeControles
	 * @param cTabFolder
	 *
	 */
	private void createCompositeControles(CTabFolder cTabFolder) {
		compositeControles = new Composite(cTabFolder, SWT.NONE);
		compositeControles.setLayout(new GridLayout());
		createGroupControl();
	}
	
	private void deseleccionarControles() {
        for (Button boton: botones)
            boton.setSelection(false);
        for (Combo combo: combos)
            combo.setEnabled(false);
	}
	
	protected void cargarComboMateriaPrimas() {
		comboMP.setItems(validadorProd.toString().split(",", 0));
		comboMP.setItems(validadorProd.getMateriasPrimas());
		comboMP.setText(comboMP.getItem(0));
	}

    public void load(CTabFolder cTabFolder) {
		createShellAreaDibujo(cTabFolder);
	}
	
	public void run() {
		Display display =new Display();
		Shell shell = new Shell(display);
		GridData gridData = new GridData();
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessVerticalSpace = true;
		CTabFolder cTabFolder = new CTabFolder(shell, SWT.NONE);
		cTabFolder.setLayoutData(gridData);
		CTabItem cTabItem = new CTabItem(cTabFolder, SWT.NONE);
		cTabItem.setText("Fabrica");
		createShellAreaDibujo(cTabFolder);
		cTabItem.setControl(compositeControles);

		shell.setLayout(gridLayout);
		shell.setSize(new Point(399, 316));
		shell.setVisible(true);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	
	public void DibujarLinea(){
		this.setDibujar(!this.getDibujar());
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

	public Composite getCompositeControles() {
		return compositeControles;
	}

    public void setFabrica(Fabrica fabrica) {
        this.fabrica = fabrica;
        resizeCanvas();
        espacioFabril.setFabrica(fabrica, canvas);
    }
    
    public void cambiarHabilitacionBotones(boolean estado){
        for (Button boton: botones)
            boton.setEnabled(estado);
        for (Combo combo: combos)
            combo.setEnabled(estado);
    }

    public void actualizar() {
        espacioFabril.redibujar();
    }
}


