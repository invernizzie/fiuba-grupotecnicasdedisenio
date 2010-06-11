package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class DibujanteDeMateriaPrima extends Dibujante {

    private Producto materiaPrima;
    private String nombre;

    public DibujanteDeMateriaPrima(EspacioFabril espacioFabril, Producto materiaPrima, String nombre) {
        super(espacioFabril);
        this.materiaPrima = materiaPrima;
        this.nombre = nombre; 
    }

    @Override
    public void mouseMove(int x, int y) {}

    @Override
    public void mouseDown(int x, int y) {
        try {
            getEspacioFabril().crearMateriaPrima(x, y, materiaPrima, nombre);
        } catch (EspacioOcupadoException e)
        {
            // Simplemente no se crea la materia prima
        }

        /*GC gc = new GC(getCanvas());
        Color colorAnterior = gc.getBackground();
        // TODO Cambiar el color segun el tipo de materia prima
        gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE)); 
        gc.fillOval(x, y, 10, 10);
        gc.setBackground(colorAnterior);
        gc.dispose(); */
    }

    @Override
    public void mouseUp(int x, int y) {}
}
