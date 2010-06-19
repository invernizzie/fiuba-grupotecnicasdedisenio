package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario;

//import com.sun.corba.se.spi.activation._ActivatorImplBase;

import java.util.Calendar;
import java.util.Date;

/**
 * Representa el hilo de ejecucion que lleva cuenta del paso
 * del tiempo, y lanza las notificaciones correspondientes.
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 29/05/2010
 */
// Acceso de paquete
class ThreadCalendario extends Thread {

    private static final int DIAS_POR_SEMANA = 7;
    private static final int MIL = 1000;
    private static final int PAUSA_MINIMA = 10;

    private Calendario calendario;
    private int diaDeLaSemana;
    private int mesVigente;

    /**
     * Bucle de ejecucion del hilo.
     * Debe dejar pasar tantos segundos como devuelva getSegundosPorDia(),
     * y luego notificar un evento del tipo Evento.COMIENZO_DE_DIA.
     * Cada siete eventos de ese tipo, debe notificar un evento del tipo
     * Evento.COMIENZO_DE_SEMANA.
     */
    @Override
    public void run() {
        diaDeLaSemana = DIAS_POR_SEMANA;
        while (!calendario.estaDetenido()) {
            try {
                esperarReanudacionSiEstaPausado();

                if (esDetenidoOPausadoAlEsperarUnDia())
                    continue;

                avanzarUnDia();

            } catch (InterruptedException ignored) { }
        }
    }

    private void esperarReanudacionSiEstaPausado() throws InterruptedException {
        while (calendario.estaPausado() && ! calendario.estaDetenido()) {
            sleep(1);
        }
    }

    private boolean esDetenidoOPausadoAlEsperarUnDia() throws InterruptedException {
        Date inicio = new Date();
        while (noTranscurrioUnDia(inicio)
                && !calendario.estaDetenido() && !calendario.estaPausado()) {
            sleep(PAUSA_MINIMA);
        }
        return calendario.estaDetenido() || calendario.estaPausado();
    }

    private boolean noTranscurrioUnDia(Date inicio) {
        return (new Date().getTime() - inicio.getTime() < MIL * calendario.getSegundosPorDia());
    }

    private void avanzarUnDia() {
        // Se registra el mes corriente para verificar cuando cambia
        mesVigente = calendario.getVirtualCalendar().get(Calendar.MONTH);
        calendario.getVirtualCalendar().add(Calendar.DAY_OF_WEEK, 1);

        calendario.notificar(Evento.COMIENZO_DE_DIA);

        detectarYNotificarComienzoDeSemana();
        diaDeLaSemana++;

        detectarYNotificarComienzoDeMes();
    }

    private void detectarYNotificarComienzoDeSemana() {
        if ((diaDeLaSemana % DIAS_POR_SEMANA) == 0) {
            calendario.notificar(Evento.COMIENZO_DE_SEMANA);
            diaDeLaSemana -= DIAS_POR_SEMANA;
        }
    }

    private void detectarYNotificarComienzoDeMes() {
        if (mesVigente != calendario.getVirtualCalendar().get(Calendar.MONTH)) {
            calendario.notificar(Evento.COMIENZO_DE_MES);
        }
    }

    ThreadCalendario(final Calendario calendario) {
        this.calendario = calendario;
    }
}