package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CintaImposibleException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Fabrica;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class CubiculoFabril {

    private Maquina maquina = null;
    private Fuente fuente = null;
    
    private boolean puedeSerFinDeCinta() {
        return (maquina != null);
    }
    
    public boolean estaOcupado() {
        return (maquina != null) || (fuente != null);
    }

    public void ubicarMaquina(Maquina maquina) throws CubiculoOcupadoExcetion {
        if (estaOcupado())
            throw new CubiculoOcupadoExcetion();
        this.maquina = maquina;
    }

    public void ubicarMateriaPrima(Fuente fuente) throws CubiculoOcupadoExcetion {
        if (estaOcupado())
            throw new CubiculoOcupadoExcetion();
        this.fuente = fuente;
    }

    public boolean puedeSerComienzoDeCinta() {
        return fuente != null || (maquina != null && !maquina.tieneCintaDeSalida());
    }

    public IFuente getFuenteConectable() throws CubiculoVacioException {
        if (fuente != null)
            return fuente;
        if (maquina == null)
            throw new CubiculoVacioException();
        if (!maquina.tieneCintaDeSalida())
            return maquina;
        throw new CubiculoVacioException();
    }

    public Maquina getMaquina() throws CubiculoVacioException {
        if (!puedeSerFinDeCinta())
            throw new CubiculoVacioException();
        return maquina;
    }

    public IFuente getFuente() throws CubiculoVacioException {
        if (fuente != null)
            return fuente;
        if (maquina != null)
            return maquina;
        throw new CubiculoVacioException();
    }
}
