package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.GeneradorDeColores;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IFuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // TODO Cambiar por tipoMaquina.getAncho()
    public static final int ANCHO_MAQUINA = 2;

    public static final int CANTIDAD_MATERIAPRIMA_DEFAULT = 100;

    private int ancho, alto;
    private Canvas canvas;
    private CubiculoFabril[][] superficieFabril;
    private Fabrica fabrica;
    private Rectangle limiteCanvas;

    private Map<CintaTransportadora, Integer[][]> cintas = new HashMap<CintaTransportadora, Integer[][]>();

    public EspacioFabril(Canvas canvas) {
        setCanvas(canvas);
        
        superficieFabril = new CubiculoFabril[ancho][alto];
    }

    public void crearMateriaPrima(int _x, int _y, Producto materiaPrima, String nombre) throws EspacioOcupadoException {
        try {
            if (!estaDentroDelEspacio(_x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA)
                    || estaOcupado(_x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA))
                throw new EspacioOcupadoException();
        } catch (CoordenadasIncorrectasException e) {
            throw new EspacioOcupadoException();
        }

        try {
            Fuente nuevaFuente = new Fuente(nombre, CANTIDAD_MATERIAPRIMA_DEFAULT, materiaPrima);
            getFabrica().agregarFuente(nuevaFuente);
            ocupar(nuevaFuente, _x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
            dibujarMateriaPrima(nombre, _x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        } catch (CoordenadasIncorrectasException e) {
            // No deberia arrojarse nunca
            e.printStackTrace();
        }
    }

    public Maquina crearMaquina(int _x, int _y, TipoMaquina tipoMaquina) throws EspacioOcupadoException {
        try {
            if (!estaDentroDelEspacio(_x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA)
                    || estaOcupado(_x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA))
                throw new EspacioOcupadoException();
        } catch (CoordenadasIncorrectasException e) {
            throw new EspacioOcupadoException();
        }

        Maquina maquina = tipoMaquina.getInstancia();
        getFabrica().agregarMaquina(maquina);
        try {
            ocupar(maquina, _x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA);
            dibujarMaquina(maquina, _x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        } catch (CoordenadasIncorrectasException e) {
            //  No deberia arrojarse nunca
            e.printStackTrace();
        }
        return maquina;
    }

    public boolean puedeComenzarCintaEn(int _x, int _y) throws CoordenadasIncorrectasException {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        testearQueEsteAdentro(x, y);

        return obtenerCubiculo(x, y) != null
                && obtenerCubiculo(x, y).puedeSerComienzoDeCinta();
    }

    public CintaTransportadora crearCinta(int _x1, int _y1, int _x2, int _y2) throws CintaImposibleException, CoordenadasIncorrectasException {
        int x1 = convertirCoordenada(_x1);
        int y1 = convertirCoordenada(_y1);
        int x2 = convertirCoordenada(_x2);
        int y2 = convertirCoordenada(_y2);
        testearQueEsteAdentro(x1, y1);
        testearQueEsteAdentro(x2, y2);

        CubiculoFabril cubiculoInicial = obtenerCubiculo(x1, y1);
        CubiculoFabril cubiculoFinal = obtenerCubiculo(x2, y2);

        if ((cubiculoInicial == null) || (cubiculoFinal == null))
            throw new CintaImposibleException();

        CintaTransportadora nuevaCinta = null;
        try {
            nuevaCinta = getFabrica().conectarMaquina(cubiculoInicial.getFuenteConectable(), cubiculoFinal.getMaquina());
            cubiculoInicial.conectarConCintaHacia(cubiculoFinal, nuevaCinta);
        } catch (CubiculoVacioException e) {
            throw new CintaImposibleException();
        }
        dibujarCinta(_x1, _y1, _x2, _y2);
        return nuevaCinta;
    }

    public void borrar(int x, int y) {
        // TODO
    }

    public void redibujar() {
        List<IFuente> dibujados = new ArrayList<IFuente>();
        GC gc = new GC(canvas);
        borrarCanvas(gc);

        for (int x = 0; x < ancho; x++)
            for (int y = 0; y < alto; y++) {
                try {
                    if (obtenerCubiculo(x, y) == null)
                        continue;
                    IFuente fuente = obtenerCubiculo(x, y).getFuente();
                    if (!dibujados.contains(fuente)) {
                        dibujados.add(fuente);
                        dibujarFuente(fuente, x * LONGITUD_DEL_LADO, y * LONGITUD_DEL_LADO);
                    }
                }
                catch (CubiculoVacioException e) {}
                catch (CoordenadasIncorrectasException e) {
                    // No deberia arrojarse nunca
                    e.printStackTrace();
                }
            }

        // TODO Dibujar las cintas
    }

    protected Fabrica getFabrica() {
        if (fabrica == null)
            throw new FabricaAusenteException();
        return fabrica;
    }

    public void setFabrica(Fabrica fabrica, Canvas canvas) {
        this.fabrica = fabrica;
        setCanvas(canvas);
        GC gc = new GC(canvas);
        borrarCanvas(gc);

        superficieFabril = new CubiculoFabril[ancho][alto];
    }

    private void setCanvas(final Canvas canvas) {
        this.canvas = canvas;
        limiteCanvas = canvas.getBounds();
        ancho = convertirCoordenada(limiteCanvas.width);
        alto = convertirCoordenada(limiteCanvas.height);
    }

    private void borrarCanvas(GC gc) {
        gc.fillRectangle(0, 0, limiteCanvas.width, limiteCanvas.height);
    }

    private void testearQueEsteAdentro(int x, int y) throws CoordenadasIncorrectasException {
        if ((x < 0) || (y < 0) || (x >= ancho) || (y >= alto))
            throw new CoordenadasIncorrectasException();
    }

    private void dibujarCinta(int _x1, int _y1, int _x2, int _y2) {
        GC gc = new GC(canvas);
        gc.drawLine (_x1, _y1, _x2, _y2);
        gc.dispose ();
    }

    private void dibujarFuente(IFuente _fuente, int _x, int _y) {
        if (_fuente instanceof Maquina) {
            Maquina maquina = (Maquina)_fuente;
            dibujarMaquina(maquina, _x, _y, ANCHO_MAQUINA, ANCHO_MAQUINA);
        } else if (_fuente instanceof Fuente) {
            Fuente fuente = (Fuente)_fuente;
            dibujarMateriaPrima(fuente.getNombreProducto(), _x, _y, ANCHO_MATERIA_PRIMA, ANCHO_MATERIA_PRIMA);
        }
    }

    private void dibujarMateriaPrima(String nombre, int _x, int _y, int _ancho, int _alto) {
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

    private void dibujarMaquina(Maquina maquina, int _x, int _y, int _ancho, int _alto) {
        int x = convertirCoordenada(_x) * LONGITUD_DEL_LADO;
        int y = convertirCoordenada(_y) * LONGITUD_DEL_LADO;

        GC gc = new GC(canvas);
        Color colorAnterior = gc.getBackground();
        gc.setBackground(GeneradorDeColores.porClass(maquina.getClass()));
        int ancho = LONGITUD_DEL_LADO * _ancho;
        int alto = LONGITUD_DEL_LADO * _alto;
        gc.fillRectangle(x, y, ancho, alto);
        gc.setBackground(colorAnterior);
        gc.dispose();
    }

    private boolean estaDentroDelEspacio(int x, int y, int ancho, int alto) {
        if ((x < 0) || (y < 0))
            return false;
        return !((convertirCoordenada(x + ancho * LONGITUD_DEL_LADO) >= this.ancho)
                || (convertirCoordenada(y + alto * LONGITUD_DEL_LADO) >= this.alto));
    }

    private void ocupar(Fuente fuente, int _x, int _y, int ancho, int alto) throws CubiculoOcupadoExcetion, CoordenadasIncorrectasException {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                obtenerOCrearCubiculo(x + offsetX, y + offsetY).ubicarMateriaPrima(fuente);
    }

    private void ocupar(Maquina maquina, int _x, int _y, int ancho, int alto) throws CubiculoOcupadoExcetion, CoordenadasIncorrectasException {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);
        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                obtenerOCrearCubiculo(x + offsetX, y + offsetY).ubicarMaquina(maquina);
    }

    private CubiculoFabril obtenerOCrearCubiculo(int x, int y) throws CoordenadasIncorrectasException {
        CubiculoFabril cubiculo = obtenerCubiculo(x, y);
        if (cubiculo == null) {
            cubiculo = new CubiculoFabril();
            superficieFabril[x][y] = cubiculo;
        }
        return cubiculo;
    }

    private CubiculoFabril obtenerCubiculo(int x, int y) throws CoordenadasIncorrectasException {
        testearQueEsteAdentro(x, y);
        return superficieFabril[x][y];
    }

    private boolean estaOcupado(int _x, int _y, int ancho, int alto) throws CoordenadasIncorrectasException {
        int x = convertirCoordenada(_x);
        int y = convertirCoordenada(_y);

        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                if ((obtenerCubiculo(x + offsetX, y + offsetY) != null)
                        && obtenerCubiculo(x + offsetX, y + offsetY).estaOcupado())
                    return true;

        return false;
    }

    private int convertirCoordenada(int coordenada) {
        return coordenada / LONGITUD_DEL_LADO;
    }
}
