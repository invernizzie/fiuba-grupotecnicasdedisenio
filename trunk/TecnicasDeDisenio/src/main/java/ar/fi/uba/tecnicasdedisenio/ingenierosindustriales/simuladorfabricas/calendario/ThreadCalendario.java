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

    /**
     * Bucle de ejecucion del hilo.
     * Debe dejar pasar tantos segundos como devuelva getSegundosPorDia(),
     * y luego notificar un evento del tipo Evento.COMIENZO_DE_DIA.
     * Cada siete eventos de ese tipo, debe notificar un evento del tipo
     * Evento.COMIENZO_DE_SEMANA.
     */
    @Override
    public void run() {
        int i = DIAS_POR_SEMANA;
        while (calendario.esValido()) {
            try {
                while (calendario.estaPausado()) {
                    sleep(1);
                    if (!calendario.esValido()) {
                        break;
                    }
                }
                Date inicio = new Date();
                while ((new Date().getTime() - inicio.getTime() < MIL * calendario.getSegundosPorDia())
                        && calendario.esValido() && !calendario.estaPausado()) {
                    sleep(PAUSA_MINIMA);
                }

                if (!calendario.esValido() || calendario.estaPausado())
                    continue;

                int mesAnterior = calendario.getVirtualCalendar().get(Calendar.MONTH);
                calendario.getVirtualCalendar().add(Calendar.DAY_OF_WEEK, 1);

                calendario.notificar(Evento.COMIENZO_DE_DIA);

                if ((i % DIAS_POR_SEMANA) == 0) {
                    calendario.notificar(Evento.COMIENZO_DE_SEMANA);
                    i -= DIAS_POR_SEMANA;
                }
                i++;

                if (mesAnterior != calendario.getVirtualCalendar().get(Calendar.MONTH)) {
                    calendario.notificar(Evento.COMIENZO_DE_MES);
                }
            } catch (InterruptedException ignored) { }
        }
    }
    
    ThreadCalendario(final Calendario calendario) {
        this.calendario = calendario;
    }
}