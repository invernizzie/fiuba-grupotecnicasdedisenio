package IG;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.custom.CTabFolder;

public class MainWindow {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="106,8"
	private Menu menuBar = null;
	private Menu FileItem = null;
	private Menu HelpItem = null;
	private Menu EditItem = null;
	private ScrolledComposite scAreaTrabajo = null;
	private CTabFolder tabWorkingControls = null;
	private Table tMapa = null;
	private ScrolledComposite scControl = null;
	private Tree tree1 = null;
	/**
	 * This method initializes scAreaTrabajo
	 *
	 */
	private void createScAreaTrabajo() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = GridData.CENTER;
		scAreaTrabajo = new ScrolledComposite(sShell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scAreaTrabajo.setAlwaysShowScrollBars(true);
		scAreaTrabajo.setExpandHorizontal(true);
		scAreaTrabajo.setExpandVertical(true);
		scAreaTrabajo.setMinSize(new Point(320, 240));
		scAreaTrabajo.setLayoutData(gridData);
		createTabWorkingControls();
		scAreaTrabajo.setLayout(new FillLayout());
	}

	/**
	 * This method initializes tabWorkingControls
	 *
	 */
	private void createTabWorkingControls() {
		tabWorkingControls = new CTabFolder(scAreaTrabajo, SWT.H_SCROLL | SWT.BORDER | SWT.V_SCROLL);
		tabWorkingControls.setToolTipText("Mapa");
		tabWorkingControls.setMaximizeVisible(true);
		tabWorkingControls.setMaximized(true);
		CTabItem tabItem1 = new CTabItem(tabWorkingControls, SWT.NONE);
		tabItem1.setText("Mapa");
		CTabItem tabItem3 = new CTabItem(tabWorkingControls, SWT.NONE);
		tabItem3.setText("Laboratorio");
		tMapa = new Table(tabWorkingControls, SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		tMapa.setHeaderVisible(true);
		tMapa.setLinesVisible(true);
		tabItem1.setControl(tMapa);
		tabWorkingControls.pack();
	}

	/**
	 * This method initializes scControl
	 *
	 */
	private void createScControl() {
		scControl = new ScrolledComposite(sShell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scControl.setAlwaysShowScrollBars(true);
		scControl.setMinSize(new Point(20, 60));
		scControl.setExpandHorizontal(true);
		tree1 = new Tree(scControl, SWT.BORDER);
		tree1.setHeaderVisible(true);
		scControl.setContent(tree1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
		 * for the correct SWT library path in order to run with the SWT dlls.
		 * The dlls are located in the SWT plugin jar.
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		MainWindow thisClass = new MainWindow();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;

		gridLayout.horizontalSpacing = 1;
		sShell = new Shell(SWT.V_SCROLL | SWT.SHELL_TRIM | SWT.H_SCROLL);
		sShell.setText("Tecnincas de Diseño");
		sShell.setMaximized(false);
		sShell.setVisible(true);
		sShell.setLayout(gridLayout);
		createScControl();
		createScAreaTrabajo();
		sShell.setSize(new Point(481, 382));
		menuBar = new Menu(sShell, SWT.BAR);

		MenuItem newItem = new MenuItem(menuBar, SWT.CASCADE);
		newItem.setText("Archivo");
		FileItem = new Menu(newItem);
		FileItem.setVisible(true);
		MenuItem newItem4 = new MenuItem(FileItem, SWT.PUSH);
		newItem4.setText("Nuevo");
		newItem4.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetDefaultSelected()");
				juegoNuevo();
			}
		});
		MenuItem openItem = new MenuItem(FileItem, SWT.PUSH);
		openItem.setText("Abrir");
		MenuItem saveItem = new MenuItem(FileItem, SWT.PUSH);
		saveItem.setText("Guardar");
		MenuItem saveAllItem = new MenuItem(FileItem, SWT.PUSH);
		saveAllItem.setText("Guardar todo");
		MenuItem separatorFile = new MenuItem(FileItem, SWT.SEPARATOR);
		newItem.setMenu(FileItem);
		MenuItem exitItem = new MenuItem(FileItem, SWT.PUSH);
		exitItem.setText("Salir");
		MenuItem Edit = new MenuItem(menuBar, SWT.CASCADE);
		Edit.setText("Edicion");
		EditItem = new Menu(Edit);
		MenuItem copy = new MenuItem(EditItem, SWT.PUSH);
		copy.setText("Copiar");
		Edit.setMenu(EditItem);
		exitItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell.close();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem helpItem = new MenuItem(menuBar, SWT.CASCADE);
		helpItem.setText("Ayuda");
		HelpItem = new Menu(helpItem);
		MenuItem about = new MenuItem(HelpItem, SWT.PUSH);
		about.setText("Acerca de...");
		helpItem.setMenu(HelpItem);
		sShell.setMenuBar(menuBar);
	}

	public void juegoNuevo(){
		CrearPartida partida= new CrearPartida();
		partida.hacerVisible();
		System.out.println("Se Invoca la pantalla de Creacion");
	}
}
