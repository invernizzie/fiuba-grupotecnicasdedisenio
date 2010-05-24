package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario;

import java.util.*;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 * @created 24/05/2010
 */
public class Calendario {

    private static Calendario instancia = new Calendario();

    // TODO Hacer configurable
    private static final int SEGUNDOS_POR_DIA = 5;

    private List<Sincronizado> sincronizados = new ArrayList<Sincronizado>();
    private boolean detenido = false;
    private boolean pausado = false;
    private Calendar virtualCalendar = new GregorianCalendar(2000, 1, 1);

    public static Calendario obtenerInstancia() {
        return instancia;
    }

    public static void reiniciar() {
        instancia = new Calendario();
    }

    public Date getFechaActual() {
        return virtualCalendar.getTime();
    }

    public void registrar(Sincronizado sincronizado) {
        getSincronizados().add(sincronizado);
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

    private int getSegundosPorDia() {
        return SEGUNDOS_POR_DIA;
    }

    private Calendario() {
        Thread thread = new ThreadCalendario();
        thread.start();
    }

    public synchronized void detener() {
        detenido = true;
    }

    public synchronized void pausar() {
        pausado = true;
    }

    public synchronized void reanudar() {
        pausado = false;
    }

    public boolean isActivo() {
        return !detenido;
    }

    public boolean isPausado() {
        return pausado;
    }

    private class ThreadCalendario extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (isActivo()) {
                try {
                    if (isPausado()) {
                        sleep(100);
                        continue;
                    }
                    sleep(1000 * getSegundosPorDia());

                    getVirtualCalendar().add(GregorianCalendar.DAY_OF_MONTH, 1);
                    notificar(Evento.COMIENZO_DE_DIA);
                    if (i++ % 7 == 0) {
                        notificar(Evento.COMIENZO_DE_SEMANA);
                        i -= 7;
                    }
                } catch (InterruptedException e) { /* ?? */ }
            }
        }
    }

}
