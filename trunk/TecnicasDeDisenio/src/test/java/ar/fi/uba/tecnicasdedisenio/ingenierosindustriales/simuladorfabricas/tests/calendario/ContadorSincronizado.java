package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.calendario;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;

/**
 * Sincronizado de prueba que cuenta las notificaciones
 * por día y por semana que recibe.
 *
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 29/05/2010
 */
public class ContadorSincronizado implements Sincronizado {

    private int notificacionesDiarias = 0;
    private int notificacionesSemanales = 0;

    @Override
    public void notificar(final Evento evento) {
        if (evento == Evento.COMIENZO_DE_DIA) {
            notificacionesDiarias = getNotificacionesDiarias() + 1;
            
        } else if (evento == Evento.COMIENZO_DE_SEMANA) {
            notificacionesSemanales = getNotificacionesSemanales() + 1;
        }
    }

    public int getNotificacionesDiarias() {
        return notificacionesDiarias;
    }

    public int getNotificacionesSemanales() {
        return notificacionesSemanales;
    }
}
