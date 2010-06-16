package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class CubiculoFabril {

    private Maquina maquina = null;
    private Fuente fuente = null;
    private List<CintaTransportadora> cintasIncidentes = new ArrayList<CintaTransportadora>();
    
    private boolean puedeSerFinDeCinta() {
        return (maquina != null);
    }
    
    public boolean estaOcupado() {
        return (maquina != null) || (fuente != null);
    }

    public void ubicarMaquina(final Maquina maquina) throws CubiculoOcupadoExcetion {
        if (estaOcupado()) {
            throw new CubiculoOcupadoExcetion();
        }
        this.maquina = maquina;
    }

    public void ubicarMateriaPrima(final Fuente fuente) throws CubiculoOcupadoExcetion {
        if (estaOcupado())
            throw new CubiculoOcupadoExcetion();
        this.fuente = fuente;
    }

    public void conectarCinta(final CintaTransportadora cinta) {
        cintasIncidentes.add(cinta);
    }

    public boolean puedeSerComienzoDeCinta() {
        return fuente != null || (maquina != null && !maquina.tieneCintaDeSalida());
    }

    public IFuente obtenerPrincipioDeCinta() throws CubiculoVacioException {
        if (fuente != null) {
            return fuente;
        }
        if (maquina == null) {
            throw new CubiculoVacioException();
        }
        if (!maquina.tieneCintaDeSalida()) {
            return maquina;
        }
        throw new CubiculoVacioException();
    }

    public Maquina obtenerFinDeCinta() throws CubiculoVacioException {
        if (!puedeSerFinDeCinta())
            throw new CubiculoVacioException();
        return maquina;
    }

    public Maquina obtenerMaquina() throws CubiculoVacioException {
        if (maquina == null)
            throw new CubiculoVacioException();
        return maquina;
    }

    public Fuente obtenerFuente() throws CubiculoVacioException {
        if (fuente == null)
            throw new CubiculoVacioException();
        return fuente;
    }

    public IFuente getFuente() throws CubiculoVacioException {
        if (fuente != null)
            return fuente;
        if (maquina != null)
            return maquina;
        throw new CubiculoVacioException();
    }

    public List<CintaTransportadora> obtenerCintas() {
        return cintasIncidentes;
    }

    public void eliminarCintasIncidentes() {
        cintasIncidentes = new ArrayList<CintaTransportadora>();
    }

    public boolean eliminar(IFuente fuente) {
        if (fuente == null)
            return false;
        if ((maquina == fuente) || this.fuente == fuente) {
            maquina = null;
            this.fuente = null;
            return true;
        }
        return false;
    }
}
