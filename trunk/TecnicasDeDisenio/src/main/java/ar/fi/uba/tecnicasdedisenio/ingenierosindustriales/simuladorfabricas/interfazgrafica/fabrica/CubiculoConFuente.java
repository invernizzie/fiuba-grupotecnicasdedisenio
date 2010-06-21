package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.FuenteNoSoportadaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IFuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 21/06/2010
 */
public class CubiculoConFuente extends CubiculoFabril {

    private Fuente fuente;

    public boolean estaOcupado() {
        return fuente != null;
    }

    public void ocuparCon(final IFuente iFuente) throws CubiculoOcupadoExcetion, FuenteNoSoportadaException {
        if (fuente != null) {
            throw new CubiculoOcupadoExcetion();
        }
        if (!(iFuente instanceof Fuente)) {
            throw new FuenteNoSoportadaException();
        }
        fuente = (Fuente) iFuente;
    }

    public boolean puedeSerComienzoDeCinta() {
        return estaOcupado();
    }

    public IFuente obtenerPrincipioDeCinta() throws CubiculoVacioException {
        if (puedeSerComienzoDeCinta())
            return fuente;
        throw new CubiculoVacioException();
    }

    public Maquina obtenerFinDeCinta() throws CubiculoVacioException {
        throw new CubiculoVacioException();
    }

    @Override
    public Maquina obtenerMaquina() throws CubiculoVacioException {
        throw new CubiculoVacioException();
    }

    public Fuente obtenerFuente() throws CubiculoVacioException {
        if (!estaOcupado()) {
            throw new CubiculoVacioException();
        }
        return fuente;
    }

    public IFuente getFuente() throws CubiculoVacioException {
        return obtenerFuente();
    }

    public boolean eliminar(final IFuente iFuente) {
        if (fuente == iFuente) {
            fuente = null;
            return true;
        }
        return false;
    }
}
