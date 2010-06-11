package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.GeneradorDeColores;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CintaImposibleException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CoordenadasNoPertenecenAlEspacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
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
    // Cambiar por tipoMaquina.getAncho()
    public static final int ANCHO_MAQUINA = 2;

    private int ancho, alto;
    private Canvas canvas;
    private CubiculoFabril[][] superficieFabril;

    private void testEsAdentro(int x, int y) throws CoordenadasNoPertenecenAlEspacioException {
        if ((x < 0) || (y < 0) || (x >= ancho) || (y >= alto))
            throw new CoordenadasNoPertenecenAlEspacioException();
    }

    private void dibujarCinta(int _x1, int _y1, int _x2, int _y2) {
        GC gc = new GC(canvas);
        gc.drawLine (_x1, _y1, _x2, _y2);
        gc.dispose ();
    }

    private void dibujarMateriaPrima(Producto materiaPrima, String nombre, int _x, int _y, int _ancho, int _alto) {
        int x = convertirCoordenada(_x) * LONGITUD_DEL_LADO;
        int y = convertirCoordenada(_y) * LONGITUD_DEL_LADO;

        GC gc = new GC(canvas);
        Color colorAnterior = gc.getBackground();
        gc.setBackground(GeneradorDeColores.porString(nombre));
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
        gc.setBackground(GeneradorDeColores.porClass(tipoMaquina.getClass()));
        int ancho = LONGITUD_DEL_LADO * _ancho;
        int alto = LONGITUD_DEL_LADO * _alto;
        gc.fillRectangle(x, y, ancho, alto);
        gc.setBackground(colorAnterior);
        gc.dispose();
    }

    private boolean estaDentroDelEspacio(int x, int y, int ancho, int alto) {
        if ((x < 0) || (y < 0))
            return false;
        if ((convertirCoordenada(x + ancho * LONGITUD_DEL_LADO) >= this.ancho)
            || (convertirCoordenada(y + alto * LONGITUD_DEL_LADO) >= this.alto))
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
    
    public EspacioFabril(Canvas canvas) {
        this.canvas = canvas;
        Rectangle limites = canvas.getBounds();
        ancho = convertirCoordenada(limites.width);
        alto = convertirCoordenada(limites.height);
        superficieFabril = new CubiculoFabril[ancho][alto];
    }

    public void crearMateriaPrima(int _x, int _y, Producto materiaPrima, String nombre) throws EspacioOcupadoException {
        if (!estaDentroDelEspacio(_x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA)
                || estaOcupado(_x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA))
            throw new EspacioOcupadoException();
        try {
            ocupar(materiaPrima, _x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
            dibujarMateriaPrima(materiaPrima, nombre, _x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
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

    public boolean puedeComenzarCintaEn(int _x, int _y) throws CoordenadasNoPertenecenAlEspacioException {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        testEsAdentro(x, y);

        return superficieFabril[x][y] != null
                && superficieFabril[x][y].puedeSerComienzoDeCinta();
    }

    public CintaTransportadora crearCinta(int _x1, int _y1, int _x2, int _y2) throws CintaImposibleException, CoordenadasNoPertenecenAlEspacioException {
        int x1 = convertirCoordenada(_x1);
        int y1 = convertirCoordenada(_y1);
        int x2 = convertirCoordenada(_x2);
        int y2 = convertirCoordenada(_y2);
        testEsAdentro(x1, y1);
        testEsAdentro(x2, y2);

        CubiculoFabril cubiculoInicial = superficieFabril[x1][y1];
        CubiculoFabril cubiculoFinal = superficieFabril[x2][y2];

        if ((cubiculoInicial == null) || (cubiculoFinal == null))
            throw new CintaImposibleException();

        CintaTransportadora cinta = cubiculoInicial.conectarConCintaHacia(cubiculoFinal);
        dibujarCinta(_x1, _y1, _x2, _y2);
        return cinta;
    }
    
    public void borrar(int x, int y) {
        // TODO
    }
    
}
