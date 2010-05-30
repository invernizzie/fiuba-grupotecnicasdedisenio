package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Representa el Calendario virtual. Determina el paso del tiempo,
 * el cual puede ser pausado y reanudado las veces que sea necesario,
 * detenido definitivamente, y reiniciado para recomenzar.
 *
 * Debe siempre accederse a la instancia valida mediante el metodo
 * de clase instancia(). En la implementacion actual, si se guarda
 * la referencia devuelta por este metodo sera siempre valida, pero
 * esto no es un invariante, se recomienda siempre usar instancia().
 *
 * Para iniciarse el paso del tiempo, debe invocarse el metodo
 * iniciar(). Para pausarlo pausar() y para reanudarlo reanudar().
 * Si se intenta pausar al Calendario ya pausado, o reanudar al
 * Calendario no pausado, no habra efectos secundarios. El metodo
 * detener() interrumpe permanentemente el paso del tiempo, solo
 * podra ser reiniciado invocando iniciar() luego de invocar a
 * inicializar().
 *
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 * @created 24/05/2010
 */
public class Calendario {

    public static final int ANIO_INICIAL = 2000;
    public static final int MES_INICIAL = 0;
    public static final int DIA_INICIAL = 1;
    public static final int DEFAULT_SEGUNDOS_POR_DIA = 5;

    private static Calendario instancia = new Calendario();

    private int segundosPorDia = DEFAULT_SEGUNDOS_POR_DIA;
    private List<Sincronizado> sincronizados = new ArrayList<Sincronizado>();
    private boolean detenido = false;
    private boolean pausado = false;
    private Calendar virtualCalendar = new GregorianCalendar(ANIO_INICIAL, MES_INICIAL, DIA_INICIAL);
    private Thread threadCalendario = new ThreadCalendario(this);

    /**
     * Devuelve la unica instancia valida del Calendario
     * @return instancia valida actual del Calendario
     */
    public static Calendario instancia() {
        return instancia;
    }

    /**
     * Restaura el calendario virtual a la fecha inicial.
     * Luego debe ser iniciado para comenzar el paso del tiempo.
     */
    public void inicializar() {
        detener();
        try {
        threadCalendario.join(100);
        } catch (InterruptedException e) { /* ?? */ }
        sincronizados = new ArrayList<Sincronizado>();
        virtualCalendar = new GregorianCalendar(ANIO_INICIAL, MES_INICIAL, DIA_INICIAL);
        detenido = false;
        pausado = false;
        threadCalendario = new ThreadCalendario(this);
    }

    /**
     * Devuelve la fecha vigente en el Calendario virtual.
     * @return fecha del Calendario virtual.
     */
    public Date getFechaActual() {
        return virtualCalendar.getTime();
    }

    /**
     * Registra un Sincronizado que sera notificado de
     * todos los eventos
     *
     * @param sincronizado Nuevo suscriptor a las notificaciones
     */
    public synchronized void registrar(Sincronizado sincronizado) {
        getSincronizados().add(sincronizado);
    }

    /**
     * Desregistra un Sincronizado previamente registrado.
     *
     * @param sincronizado El Sincronizado a desregistrar.
     * @return true si estaba registrado y se puedo desregistrar.
     * false si no estaba registrado, o bien no puedo ser desregistrado.
     */
    public synchronized boolean desregistrar(Sincronizado sincronizado) {
        return getSincronizados().remove(sincronizado);
    }

    /**
     * Iniciar el paso del tiempo en el Calendario virtual.
     */
    public synchronized void iniciar() {
        threadCalendario.start();
    }

    /**
     * Detiene el paso del tiempo en el Calendario virtual.
     * Solo podra ser reiniciado si es restaurado.
     */
    public void detener() {
        detenido = true;
    }

    /**
     * Interrumpe el paso del tiempo en el Calendario hasta
     * que se produzca una llamada a reanudar().
     */
    public synchronized void pausar() {
        pausado = true;
    }

    /**
     * Reanuda el Calendario virtual, de haber estado
     * pausado. Si no lo estaba, no produce ningun efecto.
     */
    public synchronized void reanudar() {
        pausado = false;
    }

    /**
     * Informa si el Calendario ha sido detenido definitivamente.
     *
     * @return true si el Calendario ha sido detenido.
     */
    public boolean esValido() {
        return !detenido;
    }

    /**
     * Informa si el Calendario se encuentra interrumpido.
     *
     * @return true si el Calendario se encuentra pausado.
     */
    public boolean estaPausado() {
        return pausado;
    }

    /**
     * Devuelve la cantidad de segundos del sistema que deben
     * transcurrir para que ocurra un evento del tipo Evento.COMIENZO_DE_DIA.
     *
     * @return Cantidad de segundos reales que transcurren
     * entre notificaciones del evento Evento.COMIENZO_DE_DIA.
     */
    public synchronized int getSegundosPorDia() {
        return segundosPorDia;
    }

    /**
     * Configura la cantidad de segundos del sistema que deben
     * transcurrir para que ocurra un evento del tipo Evento.COMIENZO_DE_DIA.
     * 
     * @param segundosPorDia Nueva cantidad de segundos por dia
     */
    public synchronized void setSegundosPorDia(int segundosPorDia) {
        this.segundosPorDia = segundosPorDia;
    }

    private synchronized List<Sincronizado> getSincronizados() {
        return sincronizados;
    }

    // Acceso de paquete
    synchronized Calendar getVirtualCalendar() {
        return virtualCalendar;
    }

    // Acceso de paquete
    void notificar(Evento evento) {
        for (Sincronizado sincronizado: getSincronizados())
            sincronizado.notificar(evento);
    }

    /**
     * Constructor privado para evitar instanciacion externa.
     */
    private Calendario() {}
}
