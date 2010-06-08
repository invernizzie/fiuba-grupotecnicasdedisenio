package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import java.awt.Toolkit;

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

	private Shell sShellLaboratorio = null;  //  @jve:decl-index=0:visual-constraint="89,-5"
	private CTabFolder cTabFolderLaboratorio = null;
	private Composite compositeLaboratorio = null;
	private Label labelTipoLaboratorio = null;
	private Text textTipoLaboratorio = null;
	private Label labelDineroAcumulado = null;
	private Text textDineroAcumulado = null;
	private Button buttonImagenLaboratorio = null;
	private Display display = null;
	private Image imagenLaboratorio = null;
	/**
	 * This method initializes sShellLaboratorio
	 *
	 */
	private void createSShellLaboratorio() {
		display = new Display();
		sShellLaboratorio = new Shell(display);
		imagenLaboratorio = new Image(display, "C:/diego.jpg");
		sShellLaboratorio.setVisible(true);
		sShellLaboratorio.setLayout(new GridLayout());
		sShellLaboratorio.setMaximized(false);
		sShellLaboratorio.setMinimized(false);
		createCTabFolderLaboratorio();
		sShellLaboratorio.setSize(new Point(358, 361));
		
		while (!sShellLaboratorio.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		imagenLaboratorio.dispose();
		display.dispose();
	}
	/**
	 * This method initializes cTabFolderLaboratorio
	 *
	 */
	private void createCTabFolderLaboratorio() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		cTabFolderLaboratorio = new CTabFolder(sShellLaboratorio, SWT.FLAT | SWT.BORDER | SWT.TOP);
		cTabFolderLaboratorio.setLayoutData(gridData);
		CTabItem cTabItem = new CTabItem(cTabFolderLaboratorio, SWT.NONE);
		cTabItem.setText("Laboratorio");
		createCompositeLaboratorio();
		cTabItem.setControl(compositeLaboratorio);
		
	}
	/**
	 * This method initializes compositeLaboratorio
	 *
	 */
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
		compositeLaboratorio = new Composite(cTabFolderLaboratorio, SWT.NONE);
		//compositeLaboratorio = new Composite(sShellLaboratorio, SWT.NONE);
		compositeLaboratorio.setLayout(gridLayout1);
		labelTipoLaboratorio = new Label(compositeLaboratorio, SWT.NONE);
		labelTipoLaboratorio.setText("Tipo Laboratorio");
		labelTipoLaboratorio.setVisible(true);
		labelTipoLaboratorio.setLayoutData(gridData2);
		textTipoLaboratorio = new Text(compositeLaboratorio, SWT.BORDER | SWT.READ_ONLY);
		textTipoLaboratorio.setVisible(true);
		textTipoLaboratorio.setText("<Tipo Laboratorio>");
		textTipoLaboratorio.setLayoutData(gridData5);
		labelDineroAcumulado = new Label(compositeLaboratorio, SWT.NONE);
		labelDineroAcumulado.setText("Dinero Acumulado");
		labelDineroAcumulado.setEnabled(true);
		labelDineroAcumulado.setLayoutData(gridData3);
		textDineroAcumulado = new Text(compositeLaboratorio, SWT.BORDER);
		textDineroAcumulado.setEditable(false);
		textDineroAcumulado.setText("<Dinero Acumulado>");
		textDineroAcumulado.setLayoutData(gridData4);
		buttonImagenLaboratorio = new Button(compositeLaboratorio, SWT.PUSH);
		buttonImagenLaboratorio.setImage(imagenLaboratorio);
		buttonImagenLaboratorio.setSelection(true);
		buttonImagenLaboratorio.setVisible(true);
		buttonImagenLaboratorio.setLayoutData(gridData1);
	}

    public void run() {
    	createSShellLaboratorio();
	}
}
