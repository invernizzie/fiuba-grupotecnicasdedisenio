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
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 * @created 24/05/2010
 */
public class Calendario {

    private static final int ANIO_INICIAL = 2000;
    private static final int MES_INICIAL = 1;
    private static final int DIA_INICIAL = 1;

    private static Calendario instancia = new Calendario();

    private int segundosPorDia = 5;
    private List<Sincronizado> sincronizados = new ArrayList<Sincronizado>();
    private boolean detenido = false;
    private boolean pausado = false;
    private Calendar virtualCalendar = new GregorianCalendar(ANIO_INICIAL, MES_INICIAL, DIA_INICIAL);
    private Thread threadCalendario = new ThreadCalendario();

    /**
     * Devuelve la unica instancia valida del Calendario
     * @return instancia valida actual del Calendario
     */
    public static Calendario instancia() {
        return instancia;
    }

    /**
     * Restaura el calendario virtual a la fecha inicial.
     * Debe ser iniciado para comenzar el paso del tiempo.
     */
    public void restaurar() {
        threadCalendario.interrupt();
        sincronizados = new ArrayList<Sincronizado>();
        virtualCalendar = new GregorianCalendar(ANIO_INICIAL, MES_INICIAL, DIA_INICIAL);
        detenido = false;
        pausado = false;
        threadCalendario = new Calendario.ThreadCalendario();
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
    public synchronized void detener() {
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
    public boolean isActivo() {
        return !detenido;
    }

    /**
     * Informa si el Calendario se encuentra interrumpido.
     *
     * @return true si el Calendario se encuentra pausado.
     */
    public boolean isPausado() {
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
     * Configura la cnatidad de segundos del sistema que deben
     * transcurrir para que ocurra un evento del tipo Evento.COMIENZO_DE_DIA.
     * 
     * @param segundosPorDia
     */
    public synchronized void setSegundosPorDia(int segundosPorDia) {
        this.segundosPorDia = segundosPorDia;
    }

    private synchronized List<Sincronizado> getSincronizados() {
        return sincronizados;
    }

    private synchronized Calendar getVirtualCalendar() {
        return virtualCalendar;
    }

    private void notificar(Evento evento) {
        for (Sincronizado sincronizado: getSincronizados())
            sincronizado.notificar(evento);
    }

    /**
     * Constructor privado para evitar instanciacion directa.
     */
    private Calendario() {}

    /**
     * Representa el hilo de ejecucion que lleva cuenta del paso
     * del tiempo, y lanza las notificaciones correspondientes.
     */
    private class ThreadCalendario extends Thread {

        /**
         * Bucle de ejecucion del hilo.
         * Debe dejar pasar tantos segundos como devuelva getSegundosPorDia(),
         * y luego notificar un evento del tipo Evento.COMIENZO_DE_DIA.
         * Cada siete eventos de ese tipo, debe notificar un evento del tipo
         * Evento.COMIENZO_DE_SEMANA.
         */
        @Override
        public void run() {
            int i = 7;
            while (isActivo()) {
                try {
                    if (isPausado()) {
                        sleep(100);
                        continue;
                    }
                    Date inicio = new Date();
                    while (new Date().getTime() - inicio.getTime() < 1000 * getSegundosPorDia())
                        sleep(1000);

                    getVirtualCalendar().add(GregorianCalendar.DAY_OF_MONTH, 1);
                    notificar(Evento.COMIENZO_DE_DIA);
                    if ((i % 7) == 0) {
                        notificar(Evento.COMIENZO_DE_SEMANA);
                        i -= 7;
                    }
                    i++;
                } catch (InterruptedException e) { /* ?? */ }
            }
        }
    }

}
