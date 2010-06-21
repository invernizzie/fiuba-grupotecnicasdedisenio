package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IFuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
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
    public static final int LADO_MATERIA_PRIMA = 2;
    // TODO Reemplazar por propiedad de maquina?
    public static final int LADO_MAQUINA = 2;
    public static final int CANTIDAD_MATERIAPRIMA_DEFAULT = 100;

    private int ancho, alto;
    private Canvas canvas;
    private CubiculoFabril[][] superficieFabril;
    private Fabrica fabrica;
    private Rectangle limiteCanvas;

    private Map<CintaTransportadora, Integer[][]> cintas = new HashMap<CintaTransportadora, Integer[][]>();
    private final DibujanteDeFabricas dibujanteDeFabricas = new DibujanteDeFabricas();

    public EspacioFabril(final Canvas canvas) {
        setCanvas(canvas);
        
        superficieFabril = new CubiculoFabril[ancho][alto];
    }

    public void crearMateriaPrima(final int _x, final int _y, final Producto materiaPrima, final String nombre) throws EspacioOcupadoException {
        comprobarQueEntre(_x, _y, LADO_MATERIA_PRIMA, LADO_MATERIA_PRIMA);

        try {
            Fuente nuevaFuente = new Fuente(nombre, CANTIDAD_MATERIAPRIMA_DEFAULT, materiaPrima);
            getFabrica().agregarFuente(nuevaFuente);
            ocupar(nuevaFuente, _x, _y, LADO_MATERIA_PRIMA, LADO_MATERIA_PRIMA);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        } catch (CoordenadasIncorrectasException e) {
            // No deberia arrojarse nunca
            e.printStackTrace();
        }
    }

    public Maquina crearMaquina(final int _x, final int _y, final TipoMaquina tipoMaquina) throws EspacioOcupadoException {
        comprobarQueEntre(_x, _y, LADO_MAQUINA, LADO_MAQUINA);

        Maquina maquina = tipoMaquina.getInstancia();
        getFabrica().comprarMaquina(maquina);
        try {
            ocupar(maquina, _x, _y, LADO_MAQUINA, LADO_MAQUINA);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            throw new EspacioOcupadoException();
        } catch (CoordenadasIncorrectasException e) {
            //  No deberia arrojarse nunca
            e.printStackTrace();
        }
        return maquina;
    }

    public boolean puedeComenzarCintaEn(final int _x, final int _y) throws CoordenadasIncorrectasException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        try {
            return obtenerCubiculo(x, y).puedeSerComienzoDeCinta();
        } catch (CubiculoVacioException e) {
            return false;
        }
    }

    public CintaTransportadora crearCinta(final int _x1, final int _y1, final int _x2, final int _y2)
            throws CintaImposibleException, CoordenadasIncorrectasException {
        
        int x1 = transformarCoordenada(_x1);
        int y1 = transformarCoordenada(_y1);
        int x2 = transformarCoordenada(_x2);
        int y2 = transformarCoordenada(_y2);

        try {
            CubiculoFabril cubiculoInicial = obtenerCubiculo(x1, y1);
            CubiculoFabril cubiculoFinal = obtenerCubiculo(x2, y2);

            float longitud = (float) Math.sqrt((_x1 - _x2) * (_x1 - _x2) + (_y1 - _y2) * (_y1 - _y2));
            
            CintaTransportadora nuevaCinta = getFabrica().conectarMaquina(
                    cubiculoInicial.obtenerPrincipioDeCinta(), cubiculoFinal.obtenerFinDeCinta(), longitud);
            
            cintas.put(nuevaCinta, new Integer[][] {{_x1, _y1}, {_x2, _y2}});
            cubiculoInicial.conectarCinta(nuevaCinta);
            cubiculoFinal.conectarCinta(nuevaCinta);
            return nuevaCinta;
        } catch (CubiculoVacioException e) {
            throw new CintaImposibleException();
        }
    }

    public void borrarMaquina(final int _x, final int _y) throws CoordenadasIncorrectasException, CubiculoVacioException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        Maquina maquina = obtenerCubiculo(x, y).obtenerMaquina();
        getFabrica().venderMaquina(maquina);
        borrar(maquina, x, y);
    }

    public void borrarFuente(final int _x, final int _y) throws CoordenadasIncorrectasException, CubiculoVacioException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        Fuente fuente = obtenerCubiculo(x, y).obtenerFuente();
        getFabrica().eliminarFuente(fuente);
        borrar(fuente, x, y);
    }

    public void repararMaquina(final int _x, final int _y) throws CoordenadasIncorrectasException, CubiculoVacioException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        Maquina maquina = obtenerCubiculo(x, y).obtenerMaquina();
        if (maquina.estaRota()) {
            getFabrica().repararMaquina(maquina);
        }
    }

    public void moverMaquina(final int _xInicial, final int _yInicial, final int _xFinal, final int _yFinal)
            throws CoordenadasIncorrectasException, CubiculoVacioException, EspacioOcupadoException {

        if (!estaDentroDelEspacio(_xFinal, _yFinal, LADO_MAQUINA, LADO_MAQUINA)
                    || estaOcupado(_xFinal, _yFinal, LADO_MAQUINA, LADO_MAQUINA)) {
                throw new EspacioOcupadoException();
        }

        int xInicial = transformarCoordenada(_xInicial);
        int yInicial = transformarCoordenada(_yInicial);

        Maquina maquina = obtenerCubiculo(xInicial, yInicial).obtenerMaquina();
        borrar(maquina, xInicial, yInicial);
        getFabrica().eliminarMaquina(maquina);
        getFabrica().agregarMaquina(maquina);

        try {
            ocupar(maquina, _xFinal, _yFinal, LADO_MAQUINA, LADO_MAQUINA);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            // No deberia arrojarse nunca
            throw new EspacioOcupadoException();
        }
    }

    public void redibujar() {
        GC gc = new GC(canvas);
        borrarCanvas(gc);

        dibujanteDeFabricas.dibujarLimiteDeFabrica(gc);

        dibujarFuentes();

        dibujarCintas();
    }

    private void dibujarFuentes() {
        List<IFuente> dibujados = new ArrayList<IFuente>();
        
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                try {
                    IFuente fuente = obtenerCubiculo(x, y).getFuente();
                    if (!dibujados.contains(fuente)) {
                        dibujados.add(fuente);
                        dibujanteDeFabricas.dibujarFuente(fuente, antitransformarCoordenada(x), antitransformarCoordenada(y));
                    }
                }
                catch (CubiculoVacioException ignored) { }
                catch (CoordenadasIncorrectasException e) {
                    // No deberia arrojarse nunca
                    e.printStackTrace();
                }
            }
        }
    }

    private void dibujarCintas() {
        for (CintaTransportadora cinta : cintas.keySet()) {
            Integer[][] coordenadas = cintas.get(cinta);
            dibujanteDeFabricas.dibujarCinta(coordenadas[0][0], coordenadas[0][1], coordenadas[1][0], coordenadas[1][1]);
        }
    }

    public void setFabrica(final Fabrica fabrica, final Canvas canvas) {
        this.fabrica = fabrica;
        setCanvas(canvas);
        GC gc = new GC(canvas);
        borrarCanvas(gc);

        superficieFabril = new CubiculoFabril[ancho][alto];
        cintas = new HashMap<CintaTransportadora, Integer[][]>();
    }

    public boolean hayMaquinaEn(final int _x, final int _y) {
        try {
            obtenerCubiculo((transformarCoordenada(_x)), transformarCoordenada(_y))
                    .obtenerMaquina();
        } catch (CubiculoVacioException e) {
            return false;
        } catch (CoordenadasIncorrectasException e) {
            return false;
        }
        return true;
    }

    int transformarCoordenada(final int coordenada) {
        return coordenada / LONGITUD_DEL_LADO;
    }

    int antitransformarCoordenada(final int coordenada) {
        return coordenada * LONGITUD_DEL_LADO;
    }

    protected Fabrica getFabrica() {
        if (fabrica == null) {
            throw new FabricaAusenteException();
        }
        return fabrica;
    }

    private void comprobarQueEntre(int _x, int _y, int ancho, int alto) throws EspacioOcupadoException {
        try {
            if (!estaDentroDelEspacio(_x, _y, ancho, alto)
                    || estaOcupado(_x, _y, ancho, alto)) {
                throw new EspacioOcupadoException();
            }
        } catch (CoordenadasIncorrectasException e) {
            throw new EspacioOcupadoException();
        }
    }

    private void setCanvas(final Canvas canvas) {
        this.canvas = canvas;
        limiteCanvas = canvas.getBounds();
        ancho = transformarCoordenada(limiteCanvas.width);
        alto = transformarCoordenada(limiteCanvas.height);
        dibujanteDeFabricas.setCanvas(canvas);
    }

    private void borrarCanvas(final GC gc) {
        gc.fillRectangle(0, 0, limiteCanvas.width, limiteCanvas.height);
    }

    private void borrar(IFuente fuente, int x, int y) {
        if (fuente != null) {
            intentarBorrarFuenteEn(fuente, x, y);
            borrarFuenteAlrededorDe(fuente, x, y);
        }
    }

    private void borrarFuenteAlrededorDe(final IFuente fuente, final int x, final int y) {
        boolean borradoIzquierda = intentarBorrarFuenteEn(fuente, x - 1, y);
        boolean borradoDerecha = intentarBorrarFuenteEn(fuente, x + 1, y);
        boolean borradoArriba = intentarBorrarFuenteEn(fuente, x, y - 1);
        boolean borradoAbajo = intentarBorrarFuenteEn(fuente, x, y + 1);

        if (borradoIzquierda) {
            borrarFuenteAlrededorDe(fuente, x - 1, y);
        }
        if (borradoDerecha) {
            borrarFuenteAlrededorDe(fuente, x + 1, y);
        }
        if (borradoArriba) {
            borrarFuenteAlrededorDe(fuente, x, y - 1);
        }
        if (borradoAbajo) {
            borrarFuenteAlrededorDe(fuente, x, y + 1);
        }
    }

    /**
     * De haber un cubiculo en la posicion (x, y), y de tener
     * asignada la fuente indicada, la borra.
     *
     * @param fuente Fuente a borrar
     * @param x Coordenada x de la posicion del cubiculo apuntado
     * @param y Coordenada y de la posicion del cubiculo apuntado
     * @return Devuelve true si la fuente se encontraba en la posicion
     * (x, y) y pudo ser borrada; false en caso contrario.
     */
    private boolean intentarBorrarFuenteEn(final IFuente fuente, final int x, final int y) {
        try {
            CubiculoFabril cubiculoFabril = obtenerCubiculo(x, y);
            if (cubiculoFabril.eliminar(fuente)) {
                for (CintaTransportadora cinta: cubiculoFabril.obtenerCintas()) {
                    cintas.remove(cinta);
                }
                cubiculoFabril.eliminarCintasIncidentes();
                return true;
            }
        } catch (CubiculoVacioException ignored) {
        } catch (CoordenadasIncorrectasException ignored) { }
        return false;
    }

    private boolean estaDentroDelEspacio(final int x, final int y, final int ancho, final int alto) {
        return (x >= 0) && (y >= 0)
                && (transformarCoordenada(x + antitransformarCoordenada(ancho)) < this.ancho)
                && (transformarCoordenada(y + antitransformarCoordenada(alto)) < this.alto);
    }

    private void ocupar(final IFuente fuente, final int _x, final int _y, final int ancho, final int alto) 
            throws CubiculoOcupadoExcetion, CoordenadasIncorrectasException {

        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);
        for (int offsetX = 0; offsetX <= ancho; offsetX++) {
            for (int offsetY = 0; offsetY <= alto; offsetY++) {
                ocuparCubiculo(x + offsetX, y + offsetY, fuente);
            }
        }
    }

    private void ocuparCubiculo(final int x, final int y, IFuente fuente) throws CoordenadasIncorrectasException, CubiculoOcupadoExcetion {
        CubiculoFabril cubiculo = buscarCubiculo(x, y);
        if (cubiculo == null) {
            try {
                cubiculo = new CubiculoMutante(fuente);
            } catch (FuenteNoSoportadaException e) {
                // No deberia arrojarse nunca
                e.printStackTrace();
            }
            superficieFabril[x][y] = cubiculo;

        } else {
            try {
                cubiculo.ocuparCon(fuente);
            } catch (FuenteNoSoportadaException e) {
                // No deberia arrojarse nunca
                e.printStackTrace();
            }
        }
    }

    private CubiculoFabril buscarCubiculo(final int x, final int y) throws CoordenadasIncorrectasException {
        if ((x < 0) || (y < 0) || (x >= ancho) || (y >= alto)) {
            throw new CoordenadasIncorrectasException();
        }
        return superficieFabril[x][y];
    }

    private CubiculoFabril obtenerCubiculo(int x, int y)
            throws CoordenadasIncorrectasException, CubiculoVacioException {
        
        CubiculoFabril cubiculo = buscarCubiculo(x, y);
        if (cubiculo == null) {
            throw new CubiculoVacioException();
        }
        return cubiculo;
    }

    private boolean estaOcupado(final int _x, final int _y, final int ancho, final int alto) throws CoordenadasIncorrectasException {
        int x = transformarCoordenada(_x);
        int y = transformarCoordenada(_y);

        for (int offsetX = 0; offsetX <= ancho; offsetX++) {
            for (int offsetY = 0; offsetY <= alto; offsetY++) {
                try {
                    if (obtenerCubiculo(x + offsetX, y + offsetY).estaOcupado())
                        return true;
                } catch (CubiculoVacioException e) {
                    // No esta ocupado, continua el bucle
                }
            }
        }
        return false;
    }
}
