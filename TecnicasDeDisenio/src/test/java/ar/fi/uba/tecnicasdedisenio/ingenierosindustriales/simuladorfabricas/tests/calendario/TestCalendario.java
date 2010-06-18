package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.calendario;

import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Calendario;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 * @created 24/05/2010
 */
public class TestCalendario {

    private static final int SEGUNDOS_POR_DIA = 1;
    private static final int DIAS_POR_SEMANA = 7;
    private static final int OFFSET_MILLIS = 250;

    @After
    public void tearDown() {
        Calendario.instancia().detener();
    }

    @Test
    public void obtenerInstanciaSiempreIgualYNoNula() {
        Calendario instanciaObtenidaLaPrimeraVez = Calendario.instancia();
        Assert.assertNotNull("La instancia del calendario no debiera ser nula", instanciaObtenidaLaPrimeraVez);
        Assert.assertEquals("La instancia del calendario debería ser siempre la misma",
                instanciaObtenidaLaPrimeraVez, Calendario.instancia());
    }

    @Test
    public void registrarYDesregistrarSincronizado() {
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(final Evento evento) {}
        };
        Calendario.instancia().inicializar();

        Calendario.instancia().registrar(sincronizado);
        boolean resultadoDesregistracion = Calendario.instancia().desregistrar(sincronizado);

        Assert.assertTrue("Debería haberse podido desregistrar el Sincronizado", resultadoDesregistracion);
    }

    private boolean notificado;
    @Test
    public void registrarSincronizadoYNotificarlo() {
        notificado = false;
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(final Evento evento) {
                notificado = true;
            }
        };
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);

        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(2 * SEGUNDOS_POR_DIA);

        if (!notificado) {
            Assert.fail("Debería haberse notificado algún evento");
        }
    }

    @Test
    public void desregistrarSincronizadoRegistradoYQueNoSeaNotificado() {
        notificado = false;
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(final Evento evento) {
                notificado = true;
            }
        };
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);

        Calendario.instancia().registrar(sincronizado);
        Calendario.instancia().desregistrar(sincronizado);
        Calendario.instancia().iniciar();
        esperar(2 * SEGUNDOS_POR_DIA);

        Assert.assertFalse("No deberían haberse notificado eventos", notificado);
    }

    private boolean diaNotificado;
    @Test
    public void notificarComienzoDeDia() {
        diaNotificado = false;
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(final Evento evento) {
                if (evento == Evento.COMIENZO_DE_DIA) {
                    diaNotificado = true;
                }
            }
        };
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);

        Calendario.instancia().iniciar();
        esperar(1 * SEGUNDOS_POR_DIA);
        Calendario.instancia().detener();

        Assert.assertTrue("Debería haberse notificado el comienzo de un día", diaNotificado);
    }

    private boolean semanaNotificada;
    @Test
    public void notificarComienzoDeSemana() {
        semanaNotificada = false;
        Sincronizado sincronizado = new Sincronizado() {
            @Override
            public void notificar(final Evento evento) {
                if (evento == Evento.COMIENZO_DE_SEMANA) {
                    semanaNotificada = true;
                }
            }
        };
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);

        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * DIAS_POR_SEMANA);
        Calendario.instancia().detener();

        Assert.assertTrue("Debería haberse notificado el comienzo de alguna semana", semanaNotificada);
    }

    @Test
    public void notificarEventosEnLaCantidadCorrecta() {
        ContadorSincronizado sincronizado = new ContadorSincronizado();
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(sincronizado);

        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 17);
        Calendario.instancia().detener();

        Assert.assertEquals("Cantidad de dias incorrecta", 17, sincronizado.getNotificacionesDiarias());
        Assert.assertEquals("Cantidad de semanas incorrecta", 3, sincronizado.getNotificacionesSemanales());
    }

    @Test
    public void pausarLuegoReanudarYNotificarEventosEnCantidadCorrecta() {
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

        Assert.assertEquals("Cantidad de dias incorrecta", 12, sincronizado.getNotificacionesDiarias());
        Assert.assertEquals("Cantidad de semanas incorrecta", 2, sincronizado.getNotificacionesSemanales());
    }

    @Test
    public void desregistrarSincronizadosYQueYaNoSeanNotificados() {
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
                3, s1.getNotificacionesDiarias());
        Assert.assertEquals("Cantidad de semanas incorrecta para s1",
                1, s1.getNotificacionesSemanales());
        Assert.assertEquals("Cantidad de dias incorrecta para s2",
                4, s2.getNotificacionesDiarias());
        Assert.assertEquals("Cantidad de semanas incorrecta para s2",
                0, s2.getNotificacionesSemanales());
        Assert.assertEquals("Cantidad de dias incorrecta para s3",
                DIAS_POR_SEMANA, s3.getNotificacionesDiarias());
        Assert.assertEquals("Cantidad de semanas incorrecta para s3",
                1, s3.getNotificacionesSemanales());
    }

    @Test
    public void registrarVariosSincronizadosSimultaneamenteYNotificarEventosEnCantidadCorrecta() {
        ContadorSincronizado s1 = new ContadorSincronizado();
        ContadorSincronizado s2 = new ContadorSincronizado();

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(s1);
        Calendario.instancia().registrar(s2);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 4);
        Calendario.instancia().detener();

        Assert.assertEquals("Ambos sincronizados debieron recibir la misma cantidad de notificaciones",
                s1.getNotificacionesDiarias(), s2.getNotificacionesDiarias());
        Assert.assertEquals("Deberían haberse recibido cuatro notificaciones diarias",
                4, s1.getNotificacionesDiarias());
    }

    @Test
    public void registrarVariosSincronizadosIntercalandoPausaYNotificarEventosEnCantidadCorrecta() {
        ContadorSincronizado s1 = new ContadorSincronizado();
        ContadorSincronizado s2 = new ContadorSincronizado();

        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(s1);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 2);
        Calendario.instancia().pausar();

        Assert.assertEquals("Dias incorrectos", 2, s1.getNotificacionesDiarias());

        Calendario.instancia().registrar(s2);
        Calendario.instancia().reanudar();
        esperar(SEGUNDOS_POR_DIA * 2);
        Calendario.instancia().detener();

        Assert.assertEquals("Dias incorrectos", 6, s1.getNotificacionesDiarias() + s2.getNotificacionesDiarias());
    }

    @Test
    public void obtenerFechaVirtualAvanzadaCorrectamente() {
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);

        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 5);
        Calendario.instancia().pausar();

        Assert.assertEquals("Fecha incorrecta: deberían haber pasado 5 días",
                new GregorianCalendar(Calendario.ANIO_INICIAL, Calendario.MES_INICIAL, Calendario.DIA_INICIAL + 5).getTime(),
                Calendario.instancia().getFechaActual());
    }

    public void obtenerFechaVirtualAvanzadaCorrectamenteIntercalandoPausa() {
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);

        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * 5);
        Calendario.instancia().pausar();
        esperar(SEGUNDOS_POR_DIA * 2);
        Calendario.instancia().reanudar();
        esperar(SEGUNDOS_POR_DIA * 10);
        Calendario.instancia().detener();

        Assert.assertEquals("Fecha incorrecta: deberían haber pasado 15 días",
                new GregorianCalendar(Calendario.ANIO_INICIAL, Calendario.MES_INICIAL, Calendario.DIA_INICIAL + 15).getTime(),
                Calendario.instancia().getFechaActual());
    }

    @Test
    public void restaurarValoresInicialesDelCalendario() {
        ContadorSincronizado s1 = new ContadorSincronizado();
        ContadorSincronizado s2 = new ContadorSincronizado();
        Calendario.instancia().inicializar();
        Calendario.instancia().setSegundosPorDia(SEGUNDOS_POR_DIA);
        Calendario.instancia().registrar(s1);
        Calendario.instancia().iniciar();
        esperar(SEGUNDOS_POR_DIA * DIAS_POR_SEMANA);
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
    }

    private void esperar(final int segundos) {

    	try {
        	/* Se agrega un offset de 250ms para evitar el problema
        	 * de terminar la espera antes de concluir la actividad
        	 * de los suscriptores al calendario. Eso probablemente
        	 * sucede porque este thread es muy activo y se prioriza
        	 * ante el del calendario. */
            Thread.sleep((1000 * segundos) + OFFSET_MILLIS);
        } catch (InterruptedException ignored) { }

    }
}
