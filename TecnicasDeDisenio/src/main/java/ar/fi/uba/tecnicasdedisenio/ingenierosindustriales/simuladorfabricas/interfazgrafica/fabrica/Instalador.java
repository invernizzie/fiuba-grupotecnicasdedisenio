package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public abstract class Instalador {

    private EspacioFabril espacioFabril;

    public Instalador(final EspacioFabril espacioFabril) {
        this.espacioFabril = espacioFabril;
    }

    public final void mouseMove(final int x, final int y) {
        doMouseMove(x, y);
    }

    public final void mouseDown(final int x, final int y) {
        doMouseDown(x, y);
        getEspacioFabril().redibujar();
    }

    public final void mouseUp(final int x, final int y) {
        doMouseUp(x, y);
        getEspacioFabril().redibujar();
    }

    public abstract void doMouseMove(int x, int y);

    public abstract void doMouseDown(int x, int y);

    public abstract void doMouseUp(int x, int y);
    
    protected EspacioFabril getEspacioFabril() {
        return espacioFabril;
    }
}
