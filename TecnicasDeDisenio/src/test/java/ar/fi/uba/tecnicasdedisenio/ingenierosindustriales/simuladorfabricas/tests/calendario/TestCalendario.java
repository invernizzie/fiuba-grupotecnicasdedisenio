package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.calendario;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;
import org.junit.After;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 * @created 24/05/2010
 */
public class TestCalendario {

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

        Calendario.instancia().inicializar();
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

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 7);
        Calendario.instancia().detener();
        if (!notificadoDia || ! notificadoSemana)
            Assert.fail("No se recibieron ambas notificaciones");
    }

    @Test
    public void testConteoDeNotificaciones() {
        ContadorSincronizado sincronizado = new ContadorSincronizado();

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 17);
        Calendario.instancia().detener();

        Assert.assertEquals("Cantidad de dias incorrecta", 17, sincronizado.notificacionesDiarias);
        Assert.assertEquals("Cantidad de semanas incorrecta", 3, sincronizado.notificacionesSemanales);
    }

    @Test
    public void testPausaYReanudacion() {
        ContadorSincronizado sincronizado = new ContadorSincronizado();

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 6);
        Calendario.instancia().pausar();
        esperar(SEGUNDOS_POR_DIA * 2);
        Calendario.instancia().reanudar();
        esperar(SEGUNDOS_POR_DIA * 6);
        Calendario.instancia().detener();

        Assert.assertEquals("Cantidad de dias incorrecta", 12, sincronizado.notificacionesDiarias);
        Assert.assertEquals("Cantidad de semanas incorrecta", 2, sincronizado.notificacionesSemanales);
    }

    @Test
    public void testDesregistracionDeSincronizados() {
        ContadorSincronizado s1 = new ContadorSincronizado();
        ContadorSincronizado s2 = new ContadorSincronizado();
        ContadorSincronizado s3 = new ContadorSincronizado();

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(s1);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 3);
        Calendario.instancia().pausar();
        Calendario.instancia().desregistrar(s1);
        Calendario.instancia().registrar(s2);
        Calendario.instancia().registrar(s3);
        Calendario.instancia().reanudar();
        esperar(SEGUNDOS_POR_DIA * 4);
        Calendario.instancia().pausar();
        Calendario.instancia().desregistrar(s2);
        Calendario.instancia().reanudar();
        esperar(SEGUNDOS_POR_DIA * 3);
        Calendario.instancia().detener();

        Assert.assertEquals("Cantidad de dias incorrecta para s1",
                3, s1.notificacionesDiarias);
        Assert.assertEquals("Cantidad de semanas incorrecta para s1",
                1, s1.notificacionesSemanales);
        Assert.assertEquals("Cantidad de dias incorrecta para s2",
                4, s2.notificacionesDiarias);
        Assert.assertEquals("Cantidad de semanas incorrecta para s2",
                0, s2.notificacionesSemanales);
        Assert.assertEquals("Cantidad de dias incorrecta para s3",
                7, s3.notificacionesDiarias);
        Assert.assertEquals("Cantidad de semanas incorrecta para s3",
                1, s3.notificacionesSemanales);
    }

    @Test
    public void testVariosSincronizados() {
        ContadorSincronizado s1 = new ContadorSincronizado();
        ContadorSincronizado s2 = new ContadorSincronizado();

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(s1);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 2);
        Calendario.instancia().pausar();
        Assert.assertEquals("Dias incorrectos", 2, s1.notificacionesDiarias);

        Calendario.instancia().registrar(s2);
        Calendario.instancia().reanudar();
        esperar(SEGUNDOS_POR_DIA * 2);
        Calendario.instancia().detener();
        Assert.assertEquals("Dias incorrectos", 6, s1.notificacionesDiarias + s2.notificacionesDiarias);
    }

    @Test
    public void testFechaVirtual() {
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 5);
        Calendario.instancia().pausar();

        Assert.assertEquals("Fecha incorrecta",
                new GregorianCalendar(Calendario.ANIO_INICIAL, Calendario.MES_INICIAL, Calendario.DIA_INICIAL + 5).getTime(),
                Calendario.instancia().getFechaActual());

        Calendario.instancia().reanudar();
        esperar(SEGUNDOS_POR_DIA * 10);
        Calendario.instancia().detener();

        Assert.assertEquals("Fecha incorrecta",
                new GregorianCalendar(Calendario.ANIO_INICIAL, Calendario.MES_INICIAL, Calendario.DIA_INICIAL + 15).getTime(),
                Calendario.instancia().getFechaActual());
    }

    @Test
    public void testDetencionYRestauracion() {
        ContadorSincronizado s1 = new ContadorSincronizado();
        ContadorSincronizado s2 = new ContadorSincronizado();

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(s1);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 7);
        Calendario.instancia().detener();
        Calendario.instancia().inicializar();

        Assert.assertEquals("No se restaura la fecha inicial",
                new GregorianCalendar(Calendario.ANIO_INICIAL, Calendario.MES_INICIAL, Calendario.DIA_INICIAL).getTime(),
                Calendario.instancia().getFechaActual());
        Assert.assertTrue("Se encuentra detenido tras inicializar", Calendario.instancia().esValido());
        Assert.assertFalse("Se encuentra pausado tras reanudar", Calendario.instancia().estaPausado());
        Assert.assertEquals("No se mantiene la configuracion de segundos por dia tras inicializar",
                SEGUNDOS_POR_DIA,
                Calendario.instancia().getSegundosPorDia());

        Calendario.instancia().registrar(s2);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 9);
        Calendario.instancia().detener();

        Assert.assertEquals("Cantidad de dias incorrecta",
                16, s1.notificacionesDiarias+ s2.notificacionesDiarias);
        Assert.assertEquals("Cantidad de semanas incorrecta",
                3, s1.notificacionesSemanales + s2.notificacionesSemanales);
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
        long diferencia = 0;
        /* Se agrega un offset de 250ms para evitar el problema
         * de terminar la espera antes de concluir la actividad
         * de los suscriptores al calendario. Eso probablemente
         * sucede porque este thread es muy activo y se prioriza
         * ante el del calendario. */
        while (diferencia < 1000 * segundos + 250) {
            diferencia = new Date().getTime() - inicio.getTime();
        }
    }
}
