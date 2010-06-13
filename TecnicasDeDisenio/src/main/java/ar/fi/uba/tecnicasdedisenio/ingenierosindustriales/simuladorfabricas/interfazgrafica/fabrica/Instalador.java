package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public abstract class Instalador {

    private EspacioFabril espacioFabril;

    public Instalador(EspacioFabril espacioFabril) {
        this.espacioFabril = espacioFabril;
    }

    protected EspacioFabril getEspacioFabril() {
        return espacioFabril;
    }

    public abstract void mouseMove(int x, int y) ;

    public abstract void mouseDown(int x, int y);

    public abstract void mouseUp(int x, int y);
}
