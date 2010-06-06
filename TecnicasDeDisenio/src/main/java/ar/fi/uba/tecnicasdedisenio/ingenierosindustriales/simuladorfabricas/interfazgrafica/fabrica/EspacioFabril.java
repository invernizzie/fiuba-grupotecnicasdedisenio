package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

/**
 * Espacio de dibujo de una fabrica cuadriculado.
 * Maneja colisiones y determina si una cinta puede
 * construirse o no entre dos puntos.
 *
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class EspacioFabril {

    public static final int LONGITUD_DEL_LADO = 10;
    public static final int ANCHO_MATERIA_PRIMA = 2;

    private int ancho, alto;
    private Canvas canvas;
    private CubiculoFabril[][] superficieFabril;

    public EspacioFabril(Canvas canvas) {
        this.canvas = canvas;
        Rectangle limites = canvas.getBounds();
        ancho = alto = convertirCoordenada(limites.width);
        superficieFabril = new CubiculoFabril[ancho][alto];
    }

    public void crearMateriaPrima(int x, int y, Producto materiaPrima) throws EspacioOcupadoException {
        if (!estaDentroDelEspacio(x, y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA)
                || estaOcupado(x, y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA))
            throw new EspacioOcupadoException();
        try {
            ocupar(materiaPrima, x, y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        }
    }

    public void crearMaquina(int x, int y, TipoMaquina tipoMaquina) {

    }

    public boolean puedeComenzarCintaEn(int x, int y) {
        return true;
    }

    public void crearCinta(int x1, int y1, int x2, int y2) {

    }

    public void borrar(int x, int y) {

    }

    private boolean estaDentroDelEspacio(int x, int y, int ancho, int alto) {
        if ((x < 0) || (y < 0))
            return false;
        if ((convertirCoordenada(x) + ancho > this.ancho)
            || (convertirCoordenada(y) + alto > this.alto))
            return false;
        return true;
    }

    private void ocupar(Producto materiaPrima, int _x, int _y, int ancho, int alto) throws CubiculoOcupadoExcetion {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        for (int offsetX = 0; offsetX < ancho; offsetX++)
            for (int offsetY = 0; offsetY < alto; offsetY++)
                obtenerOCrearCubiculo(x + offsetX, y + offsetY).ubicarMateriaPrima(materiaPrima);
    }

    private CubiculoFabril obtenerOCrearCubiculo(int x, int y) {
        CubiculoFabril cubiculo = superficieFabril[x][y];
        if (cubiculo == null) {
            cubiculo = new CubiculoFabril();
            superficieFabril[x][y] = cubiculo;
        }
        return cubiculo;
    }

    private boolean estaOcupado(int _x, int _y, int ancho, int alto) {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);

        for (int offsetX = 0; offsetX < ancho; offsetX++)
            for (int offsetY = 0; offsetY < alto; offsetY++)
                if ((superficieFabril[x + offsetX][y + offsetY] == null)
                        || superficieFabril[x + offsetX][y + offsetY].estaOcupado())
                    return true;

        return false;
    }

    private int convertirCoordenada(int coordenada) {
        return coordenada / LONGITUD_DEL_LADO;
    }
}
