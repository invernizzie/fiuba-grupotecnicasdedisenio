package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class DibujanteDeMaquinas extends Dibujante {

    private TipoMaquina tipoMaquina;

    public DibujanteDeMaquinas(EspacioFabril espacioFabril, TipoMaquina tipoMaquina) {
        super(espacioFabril);
        this.tipoMaquina = tipoMaquina;
    }

    @Override
    public void mouseMove(int x, int y) {}

    @Override
    public void mouseDown(int x, int y) {
        try {
            getEspacioFabril().crearMaquina(x, y, tipoMaquina);
        } catch (EspacioOcupadoException e) {
            // Simplemente no creo la maquina
        }

        /*GC gc = new GC(getCanvas());
        Color colorAnterior = gc.getBackground();
        // TODO Cambiar el color segun el tipo de maquina
        gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE)); 
        gc.fillRectangle(x, y, 10, 10);
        gc.setBackground(colorAnterior);
        gc.dispose();*/
    }

    @Override
    public void mouseUp(int x, int y) {}
}
