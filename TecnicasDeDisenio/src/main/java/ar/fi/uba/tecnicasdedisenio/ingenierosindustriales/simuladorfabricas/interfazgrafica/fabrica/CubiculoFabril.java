package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.FuenteNoSoportadaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IFuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 21/06/2010
 */
public abstract class CubiculoFabril {

    private List<CintaTransportadora> cintasIncidentes = new ArrayList<CintaTransportadora>();

    void conectarCinta(CintaTransportadora cinta) {
        cintasIncidentes.add(cinta);
    }

    List<CintaTransportadora> obtenerCintas() {
        return cintasIncidentes;
    }

    void eliminarCintasIncidentes() {
        cintasIncidentes = new ArrayList<CintaTransportadora>();
    }

    public abstract boolean estaOcupado();

    public abstract void ocuparCon(IFuente fuente) throws CubiculoOcupadoExcetion, FuenteNoSoportadaException;

    public abstract boolean puedeSerComienzoDeCinta();

    public abstract IFuente obtenerPrincipioDeCinta() throws CubiculoVacioException;

    public abstract Maquina obtenerFinDeCinta() throws CubiculoVacioException;

    public abstract Maquina obtenerMaquina() throws CubiculoVacioException;

    public abstract Fuente obtenerFuente() throws CubiculoVacioException;

    public abstract IFuente getFuente() throws CubiculoVacioException;

    public abstract boolean eliminar(IFuente fuente);
}
