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
public class CubiculoConMaquina extends CubiculoFabril {

    private Maquina maquina;

    public boolean estaOcupado() {
        return maquina != null;
    }

    public void ocuparCon(final IFuente fuente) throws CubiculoOcupadoExcetion, FuenteNoSoportadaException {
        if (maquina != null) {
            throw new CubiculoOcupadoExcetion();
        }
        if (!(fuente instanceof Maquina)) {
            throw new FuenteNoSoportadaException();
        }
        maquina = (Maquina) fuente;
    }

    public boolean puedeSerComienzoDeCinta() {
        return estaOcupado() && !maquina.tieneCintaDeSalida();
    }

    public IFuente obtenerPrincipioDeCinta() throws CubiculoVacioException {
        if (puedeSerComienzoDeCinta())
            return maquina;
        throw new CubiculoVacioException();
    }

    public Maquina obtenerFinDeCinta() throws CubiculoVacioException {
        if (!puedeSerFinDeCinta()) {
            throw new CubiculoVacioException();
        }
        return maquina;
    }

    public Maquina obtenerMaquina() throws CubiculoVacioException {
        if (!estaOcupado()) {
            throw new CubiculoVacioException();
        }
        return maquina;
    }

    @Override
    public Fuente obtenerFuente() throws CubiculoVacioException {
        throw new CubiculoVacioException();
    }

    public IFuente getFuente() throws CubiculoVacioException {
        return obtenerMaquina();
    }

    public boolean eliminar(final IFuente fuente) {
        if (maquina == fuente) {
            maquina = null;
            return true;
        }
        return false;
    }

    private boolean puedeSerFinDeCinta() {
        return (maquina != null);
    }
}
