package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CintaImposibleException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

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
    // Cambiar por tipoMaquina.getAncho()
    public static final int ANCHO_MAQUINA = 2;

    private int ancho, alto;
    private Canvas canvas;
    private CubiculoFabril[][] superficieFabril;

    public EspacioFabril(Canvas canvas) {
        this.canvas = canvas;
        Rectangle limites = canvas.getBounds();
        ancho = alto = convertirCoordenada(limites.width);
        superficieFabril = new CubiculoFabril[ancho][alto];
    }

    public void crearMateriaPrima(int _x, int _y, Producto materiaPrima) throws EspacioOcupadoException {
        if (!estaDentroDelEspacio(_x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA)
                || estaOcupado(_x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA))
            throw new EspacioOcupadoException();
        try {
            ocupar(materiaPrima, _x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
            dibujarMateriaPrima(materiaPrima, _x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        }
    }

    public Maquina crearMaquina(int _x, int _y, TipoMaquina tipoMaquina) throws EspacioOcupadoException {
        if (!estaDentroDelEspacio(_x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA)
                || estaOcupado(_x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA))
            throw new EspacioOcupadoException();

        Maquina maquina = tipoMaquina.getInstancia();
        try {
            ocupar(maquina, _x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA);
            dibujarMaquina(tipoMaquina, _x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA);
            return maquina;
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        }

    }

    public boolean puedeComenzarCintaEn(int _x, int _y) {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        if (superficieFabril[x][y] == null)
            return false;
        return superficieFabril[x][y].puedeSerComienzoDeCinta();
    }

    public CintaTransportadora crearCinta(int _x1, int _y1, int _x2, int _y2) throws CintaImposibleException {
        int x1 = convertirCoordenada(_x1);
        int y1 = convertirCoordenada(_y1);
        int x2 = convertirCoordenada(_x2);
        int y2 = convertirCoordenada(_y2);

        CubiculoFabril cubiculoInicial = superficieFabril[x1][y1];
        CubiculoFabril cubiculoFinal = superficieFabril[x2][y2];

        if ((cubiculoInicial == null) || (cubiculoFinal == null))
            throw new CintaImposibleException();

        CintaTransportadora cinta = cubiculoInicial.conectarConCintaHacia(cubiculoFinal);
        dibujarCinta(_x1, _y1, _x2, _y2);
        return cinta;
    }

    private void dibujarCinta(int _x1, int _y1, int _x2, int _y2) {
        GC gc = new GC(canvas);
        gc.drawLine (_x1, _y1, _x2, _y2);
        gc.dispose ();
    }

    public void borrar(int x, int y) {

    }

    private void dibujarMateriaPrima(Producto materiaPrima, int _x, int _y, int _ancho, int _alto) {
        int x = convertirCoordenada(_x) * LONGITUD_DEL_LADO;
        int y = convertirCoordenada(_y) * LONGITUD_DEL_LADO;

        GC gc = new GC(canvas);
        Color colorAnterior = gc.getBackground();
        // TODO Cambiar el color segun el tipo de materia prima
        gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
        int ancho = LONGITUD_DEL_LADO * _ancho;
        int alto = LONGITUD_DEL_LADO * _alto;
        gc.fillOval(x, y, ancho, alto);
        gc.setBackground(colorAnterior);
        gc.dispose();
    }

    private void dibujarMaquina(TipoMaquina tipoMaquina, int _x, int _y, int _ancho, int _alto) {
        int x = convertirCoordenada(_x) * LONGITUD_DEL_LADO;
        int y = convertirCoordenada(_y) * LONGITUD_DEL_LADO;

        GC gc = new GC(canvas);
        Color colorAnterior = gc.getBackground();
        // TODO Cambiar el color segun el tipo de materia prima
        gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
        int ancho = LONGITUD_DEL_LADO * _ancho;
        int alto = LONGITUD_DEL_LADO * _alto;
        gc.fillRectangle(x, y, ancho, alto);
        gc.setBackground(colorAnterior);
        gc.dispose();
    }

    private boolean estaDentroDelEspacio(int x, int y, int ancho, int alto) {
        if ((x < 0) || (y < 0))
            return false;
        if ((convertirCoordenada(x + ancho * LONGITUD_DEL_LADO) > this.ancho - 1)
            || (convertirCoordenada(y + alto * LONGITUD_DEL_LADO) > this.alto - 1))
            return false;
        return true;
    }

    private void ocupar(Producto materiaPrima, int _x, int _y, int ancho, int alto) throws CubiculoOcupadoExcetion {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                obtenerOCrearCubiculo(x + offsetX, y + offsetY).ubicarMateriaPrima(materiaPrima);
    }

    private void ocupar(Maquina maquina, int _x, int _y, int ancho, int alto) throws CubiculoOcupadoExcetion {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                obtenerOCrearCubiculo(x + offsetX, y + offsetY).ubicarMaquina(maquina);
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

        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                if ((superficieFabril[x + offsetX][y + offsetY] != null)
                        && superficieFabril[x + offsetX][y + offsetY].estaOcupado())
                    return true;

        return false;
    }

    private int convertirCoordenada(int coordenada) {
        return coordenada / LONGITUD_DEL_LADO;
    }
}
