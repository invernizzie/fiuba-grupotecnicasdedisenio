package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.calendario;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import org.junit.After;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 * @created 24/05/2010
 */
public class TestCalendario {

    /* TODO Testear:
     *  Pausa y reanudacion
     *  Detencion y restauracion
     *  Obtencion de la fecha esperadada
     *  Registracion de varios Sincronizados
     *  Desregistracion de Sincronizados
     */

    private static final int SEGUNDOS_POR_DIA = 1;

    @After
    public void tearDown() {
        Calendario.instancia().detener();
    }

    @Test
    public void testObtenerInstancia() {
        Calendario calendario = Calendario.instancia();
        Assert.assertNotNull(calendario);
    }

    private boolean notificado;
    @Test
    public void testRegistrarseYSerNotificado() {
        notificado = false;
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(Evento evento) {
                notificado = true;
            }
        };

        Calendario.instancia().restaurar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(2 * SEGUNDOS_POR_DIA);
        if (!notificado)
            Assert.fail("No se ha notificado ningun evento");
    }

    private boolean notificadoDia;
    private boolean notificadoSemana;
    @Test
    public void testNotificacionDiaYSemana() {
        notificadoDia = notificadoSemana = false;
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(Evento evento) {
                if (evento == Evento.COMIENZO_DE_DIA)
                    notificadoDia = true;
                else if (evento == Evento.COMIENZO_DE_SEMANA)
                    notificadoSemana = true;
            }
        };

        Calendario.instancia().restaurar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 7);
        Calendario.instancia().detener();
        if (!notificadoDia || ! notificadoSemana)
            Assert.fail("No se recibieron ambas notificaciones");
    }

    private int notificacionesDiarias;
    private int notificacionesSemanales;
    @Test
    public void testConteoDeNotificaciones() {
        notificacionesDiarias = notificacionesSemanales = 0;
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(Evento evento) {
                if (evento == Evento.COMIENZO_DE_DIA)
                    notificacionesDiarias++;
                else if (evento == Evento.COMIENZO_DE_SEMANA)
                    notificacionesSemanales++;
            }
        };

        Calendario.instancia().restaurar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA); 
        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 17);
        Calendario.instancia().detener();

        if (notificacionesDiarias < 17)
            Assert.fail("Se notificaron menos dias de los transcurridos");
        if (notificacionesSemanales < 3)
            Assert.fail("Se notificaron menos semanas de las ocurridas");
    }

    private void esperar(int segundos) {
        // El siguiente bloque comentado se reemplaza por
        // el ciclo vacio de mas abajo, pues no funciona
        /*
        try {
            Thread.currentThread().wait(1000 * segundos);
        } catch (InterruptedException e) {}
        */

        Date inicio = new Date();
        /* Se agrega un offset de 100ms para evitar el problema
         * de terminar la espera antes de concluir la actividad
         * de los suscriptores al calendario.
         */
        long diferencia = -100;
        while (diferencia < 1000 * segundos) {
            diferencia = new Date().getTime() - inicio.getTime();
        }
    }
}
