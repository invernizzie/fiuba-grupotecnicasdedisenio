package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 20/06/2010
 */
public abstract class InstaladorDragAndDrop extends Instalador {

    private boolean draggeando;
    private int xInicial, yInicial;

    public InstaladorDragAndDrop(final EspacioFabril espacioFabril) {
        super(espacioFabril);
    }

    @Override
    public void doMouseMove(int x, int y) {
        previsualizar(xInicial, yInicial, x, y);
    }

    @Override
    public void doMouseDown(int x, int y) {
        if (puedeDraggearDesdeAqui(x, y)) {
            draggeando = true;
            xInicial = x;
            yInicial = y;
        }
    }

    @Override
    public void doMouseUp(int x, int y) {

        if (draggeando) {
            droppear(xInicial, yInicial, x, y);
            draggeando = false;
        }
    }

    protected abstract boolean puedeDraggearDesdeAqui(int x, int y);

    protected abstract void droppear(int xInicial, int yInicial, int xFinal, int yFinal);

    protected abstract void previsualizar(int xInicial, int yInicial, int x, int y);
}
