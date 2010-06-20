package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.util.RecursosAplicacion;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;


public class AreaLaboratorio {

    private static final int DEFAULT_ANCHO = 358;
    private static final int DEFAULT_ALTO = 361;

	//private Shell sShellLaboratorio = null;
	//private CTabFolder cTabFolderLaboratorio = null;
	private Composite compositeLaboratorio = null;
	private Text textTipoLaboratorio = null;
    //private Image imagenLaboratorio = null;
	private Text textDineroAcumulado = null;
	private Button buttonImagenLaboratorio = null;

    public Composite getCompositeLaboratorio() {
		return compositeLaboratorio;
	}

	public AreaLaboratorio(final CTabFolder cTabFolder) {
		createCompositeLaboratorio(cTabFolder);
	}

	public void run() {
    	Display display = new Display();
		Shell shell = new Shell(display);
		CTabFolder cTabFolder = new CTabFolder(shell, SWT.FLAT | SWT.BORDER | SWT.TOP);
    	createSShellLaboratorio(shell, display, cTabFolder);
	}
    
    /**
	 * This method initializes sShellLaboratorio
     * @param sShellLaboratorio 
     * @param display 
     * @param path 
	 *
	 */
	private void createSShellLaboratorio(Shell sShellLaboratorio, Display display, CTabFolder cTabFolder) {
        //Display display = new Display();
		//sShellLaboratorio = new Shell(display);
		String path = new String("lab.jpg");
		Image imagenLaboratorio = new Image(display, RecursosAplicacion.instance().getResourceAsStream(path));
		sShellLaboratorio.setVisible(true);
		sShellLaboratorio.setLayout(new GridLayout());
		sShellLaboratorio.setMaximized(false);
		sShellLaboratorio.setMinimized(false);
		createCTabFolderLaboratorio(cTabFolder);
		sShellLaboratorio.setSize(new Point(DEFAULT_ANCHO, DEFAULT_ALTO));
		
		while (!sShellLaboratorio.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
            }
		}
		imagenLaboratorio.dispose();
		display.dispose();
	}
	
	/**
	 * This method initializes cTabFolderLaboratorio
	 * @param cTabFolderLaboratorio 
	 * @param imagenLaboratorio 
	 * @param sShellLaboratorio 
	 *
	 */
	private void createCTabFolderLaboratorio(CTabFolder cTabFolderLaboratorio) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		//cTabFolderLaboratorio = new CTabFolder(sShellLaboratorio, SWT.FLAT | SWT.BORDER | SWT.TOP);
		cTabFolderLaboratorio.setLayoutData(gridData);
		CTabItem cTabItem = new CTabItem(cTabFolderLaboratorio, SWT.NONE);
		cTabItem.setText("Laboratorio");
		createCompositeLaboratorio(cTabFolderLaboratorio);
		cTabItem.setControl(compositeLaboratorio);
		
	}
	
	/**
	 * This method initializes compositeLaboratorio
	 * @param cTabFolderLaboratorio 
	 * @param imagenLaboratorio 
	 * @param cTabFolderLaboratorio 
	 *
	 */
	private void createCompositeLaboratorio(CTabFolder cTabFolderLaboratorio) {
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
		compositeLaboratorio = new Composite(cTabFolderLaboratorio, SWT.NONE);
		compositeLaboratorio.setLayout(gridLayout1);
        Label labelTipoLaboratorio = new Label(compositeLaboratorio, SWT.NONE);
		labelTipoLaboratorio.setText("Tipo Laboratorio");
		labelTipoLaboratorio.setVisible(true);
		labelTipoLaboratorio.setLayoutData(gridData2);
        textTipoLaboratorio = new Text(compositeLaboratorio, SWT.BORDER | SWT.READ_ONLY);
		textTipoLaboratorio.setVisible(true);
		textTipoLaboratorio.setText("<Tipo Laboratorio>");
		textTipoLaboratorio.setLayoutData(gridData5);
        Label labelDineroAcumulado = new Label(compositeLaboratorio, SWT.NONE);
		labelDineroAcumulado.setText("Dinero Acumulado");
		labelDineroAcumulado.setEnabled(true);
		labelDineroAcumulado.setLayoutData(gridData3);
        textDineroAcumulado = new Text(compositeLaboratorio, SWT.BORDER);
		textDineroAcumulado.setEditable(false);
		textDineroAcumulado.setText("<Dinero Acumulado>");
		textDineroAcumulado.setLayoutData(gridData4);
        buttonImagenLaboratorio = new Button(compositeLaboratorio, SWT.PUSH);
		//buttonImagenLaboratorio.setImage(imagenLaboratorio);
		buttonImagenLaboratorio.setSelection(true);
		buttonImagenLaboratorio.setVisible(true);
		buttonImagenLaboratorio.setLayoutData(gridData1);
	}
	
	public void actualizarDatosLaboratorio(Display display, String nombreImagen, String tipoLabo, String dineroAcumulado) {
    	textTipoLaboratorio.setText(tipoLabo);
    	textDineroAcumulado.setText(dineroAcumulado);
        Image imagenLaboratorio = new Image(display, RecursosAplicacion.instance().getResourceAsStream("images/" + nombreImagen));
    	buttonImagenLaboratorio.setImage(imagenLaboratorio);
    }

}
