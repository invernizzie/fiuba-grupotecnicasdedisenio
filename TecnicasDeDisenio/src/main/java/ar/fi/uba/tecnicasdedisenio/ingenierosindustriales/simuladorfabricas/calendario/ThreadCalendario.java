package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario;

import com.sun.corba.se.spi.activation._ActivatorImplBase;

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

    Calendario calendario;

    // Acceso de paquete
    ThreadCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

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
        while (calendario.esValido()) {
            try {
                while (calendario.estaPausado()) {
                    sleep(1);
                    if (!calendario.esValido())
                        break;
                }
                Date inicio = new Date();
                while ((new Date().getTime() - inicio.getTime() < 1000 * calendario.getSegundosPorDia())
                        && calendario.esValido() && !calendario.estaPausado())
                    sleep(10);

                if (!calendario.esValido() || calendario.estaPausado())
                    continue;

                calendario.getVirtualCalendar().add(Calendar.DAY_OF_WEEK, 1);
                calendario.notificar(Evento.COMIENZO_DE_DIA);
                if ((i % 7) == 0) {
                    calendario.notificar(Evento.COMIENZO_DE_SEMANA);
                    i -= 7;
                }
                i++;
            } catch (InterruptedException e) { /* ?? */ }
        }
    }
}