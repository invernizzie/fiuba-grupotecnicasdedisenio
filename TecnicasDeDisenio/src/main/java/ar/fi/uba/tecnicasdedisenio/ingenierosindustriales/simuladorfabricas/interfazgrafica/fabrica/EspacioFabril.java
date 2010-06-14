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
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        } catch (CoordenadasIncorrectasException e) {
            //  No deberia arrojarse nunca
            e.printStackTrace();
        }
        return maquina;
    }

    public boolean puedeComenzarCintaEn(int _x, int _y) throws CoordenadasIncorrectasException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        return obtenerCubiculo(x, y) != null
                && obtenerCubiculo(x, y).puedeSerComienzoDeCinta();
    }

    public CintaTransportadora crearCinta(int _x1, int _y1, int _x2, int _y2) throws CintaImposibleException, CoordenadasIncorrectasException {
        int x1 = transformarCoordenada(_x1);
        int y1 = transformarCoordenada(_y1);
        int x2 = transformarCoordenada(_x2);
        int y2 = transformarCoordenada(_y2);

        CubiculoFabril cubiculoInicial = obtenerCubiculo(x1, y1);
        CubiculoFabril cubiculoFinal = obtenerCubiculo(x2, y2);

        if ((cubiculoInicial == null) || (cubiculoFinal == null))
            throw new CintaImposibleException();

        CintaTransportadora nuevaCinta = null;
        try {
            nuevaCinta = getFabrica().conectarMaquina(cubiculoInicial.obtenerPrincipioDeCinta(), cubiculoFinal.obtenerFinDeCinta(), new Float(10)); // TODO Calcular longitud
            cintas.put(nuevaCinta, new Integer[][] {{_x1, _y1}, {_x2, _y2}});
        } catch (CubiculoVacioException e) {
            throw new CintaImposibleException();
        }
        return nuevaCinta;
    }

    public void borrarMaquina(int _x, int _y) throws CoordenadasIncorrectasException, CubiculoVacioException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        CubiculoFabril cubiculoClickeado = obtenerCubiculo(x, y);
        if (cubiculoClickeado == null)
            throw new CubiculoVacioException();
        Maquina maquina = cubiculoClickeado.obtenerMaquina();
        getFabrica().eliminarMaquina(maquina);
        borrarAqui(maquina, x, y);
        borrarAlrededor(maquina, x, y);
        // TODO Eliminar cintas!
    }

    public void borrarFuente(int _x, int _y) throws CoordenadasIncorrectasException, CubiculoVacioException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        CubiculoFabril cubiculoClickeado = obtenerCubiculo(x, y);
        if (cubiculoClickeado == null)
            throw new CubiculoVacioException();
        Fuente fuente = cubiculoClickeado.obtenerFuente();
        // TODO Comunicar a la Fabrica??
        borrarAqui(fuente, x, y);
        borrarAlrededor(fuente, x, y);
        // TODO Eliminar cintas!
    }

    public void redibujar() {
        List<IFuente> dibujados = new ArrayList<IFuente>();
        GC gc = new GC(canvas);
        borrarCanvas(gc);

        gc.drawRectangle(0, 0, limiteCanvas.width - LONGITUD_DEL_LADO, limiteCanvas.height - LONGITUD_DEL_LADO);

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
                catch (CubiculoVacioException ignored) {}
                catch (CoordenadasIncorrectasException e) {
                    // No deberia arrojarse nunca
                    e.printStackTrace();
                }
            }

        for (CintaTransportadora cinta: cintas.keySet()) {
            Integer[][] coordenadas = cintas.get(cinta);
            dibujarCinta(coordenadas[0][0], coordenadas[0][1], coordenadas[1][0], coordenadas[1][1]);
        }
    }

    public void setFabrica(Fabrica fabrica, Canvas canvas) {
        this.fabrica = fabrica;
        setCanvas(canvas);
        GC gc = new GC(canvas);
        borrarCanvas(gc);

        superficieFabril = new CubiculoFabril[ancho][alto];
        cintas = new HashMap<CintaTransportadora, Integer[][]>();
    }

    protected Fabrica getFabrica() {
        if (fabrica == null)
            throw new FabricaAusenteException();
        return fabrica;
    }

    private void setCanvas(final Canvas canvas) {
        this.canvas = canvas;
        limiteCanvas = canvas.getBounds();
        ancho = transformarCoordenada(limiteCanvas.width);
        alto = transformarCoordenada(limiteCanvas.height);
    }

    private void borrarCanvas(GC gc) {
        gc.fillRectangle(0, 0, limiteCanvas.width, limiteCanvas.height);
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
        int x = transformarCoordenada(_x) * LONGITUD_DEL_LADO;
        int y = transformarCoordenada(_y) * LONGITUD_DEL_LADO;

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
        int x = transformarCoordenada(_x) * LONGITUD_DEL_LADO;
        int y = transformarCoordenada(_y) * LONGITUD_DEL_LADO;

        GC gc = new GC(canvas);
        Color colorAnterior = gc.getBackground();
        gc.setBackground(GeneradorDeColores.porClass(maquina.getClass()));
        int ancho = LONGITUD_DEL_LADO * _ancho;
        int alto = LONGITUD_DEL_LADO * _alto;
        gc.fillRectangle(x, y, ancho, alto);
        gc.setBackground(colorAnterior);
        gc.dispose();
    }

    private void borrarAlrededor(IFuente fuente, int x, int y) {
        boolean borradoIzquierda = borrarAqui(fuente, x - 1, y);
        boolean borradoDerecha = borrarAqui(fuente, x + 1, y);
        boolean borradoArriba = borrarAqui(fuente, x, y - 1);
        boolean borradoAbajo = borrarAqui(fuente, x, y + 1);

        if (borradoIzquierda)
            borrarAlrededor(fuente, x - 1, y);
        if (borradoDerecha)
            borrarAlrededor(fuente, x + 1, y);
        if (borradoArriba)
            borrarAlrededor(fuente, x, y - 1);
        if (borradoAbajo)
            borrarAlrededor(fuente, x, y + 1);
    }

    private boolean borrarAqui(IFuente fuente, int x, int y) {
        CubiculoFabril cubiculoFabril;
        try {
            cubiculoFabril = obtenerCubiculo(x, y);
        } catch (CoordenadasIncorrectasException e) {
            return false;
        }
        return cubiculoFabril != null && cubiculoFabril.eliminar(fuente);
    }

    private boolean estaDentroDelEspacio(int x, int y, int ancho, int alto) {
        if ((x < 0) || (y < 0))
            return false;
        return !((transformarCoordenada(x + ancho * LONGITUD_DEL_LADO) >= this.ancho)
                || (transformarCoordenada(y + alto * LONGITUD_DEL_LADO) >= this.alto));
    }

    private void ocupar(Fuente fuente, int _x, int _y, int ancho, int alto) throws CubiculoOcupadoExcetion, CoordenadasIncorrectasException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);
        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                obtenerOCrearCubiculo(x + offsetX, y + offsetY).ubicarMateriaPrima(fuente);
    }

    private void ocupar(Maquina maquina, int _x, int _y, int ancho, int alto) throws CubiculoOcupadoExcetion, CoordenadasIncorrectasException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);
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
        if ((x < 0) || (y < 0) || (x >= ancho) || (y >= alto))
            throw new CoordenadasIncorrectasException();
        return superficieFabril[x][y];
    }

    private boolean estaOcupado(int _x, int _y, int ancho, int alto) throws CoordenadasIncorrectasException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        for (int offsetX = 0; offsetX <= ancho; offsetX++)
            for (int offsetY = 0; offsetY <= alto; offsetY++)
                if ((obtenerCubiculo(x + offsetX, y + offsetY) != null)
                        && obtenerCubiculo(x + offsetX, y + offsetY).estaOcupado())
                    return true;

        return false;
    }

    private int transformarCoordenada(int coordenada) {
        return coordenada / LONGITUD_DEL_LADO;
    }
}
