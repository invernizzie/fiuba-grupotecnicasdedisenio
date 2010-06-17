package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.tests.calendario;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Evento;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.calendario.Sincronizado;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 29/05/2010
 */
public class ContadorSincronizado implements Sincronizado {

    public int notificacionesDiarias = 0;
    public int notificacionesSemanales = 0;

    @Override
    public void notificar(final Evento evento) {
        if (evento == Evento.COMIENZO_DE_DIA) {
            notificacionesDiarias++;
            
        } else if (evento == Evento.COMIENZO_DE_SEMANA) {
            notificacionesSemanales++;
        }
    }
}
