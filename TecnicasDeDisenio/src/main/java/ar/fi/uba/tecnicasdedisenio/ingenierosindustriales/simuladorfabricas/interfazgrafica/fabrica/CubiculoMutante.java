package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoVacioException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.FuenteNoSoportadaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class CubiculoMutante extends CubiculoFabril {

    private CubiculoFabril cubiculoDecorado;

    public CubiculoMutante(final IFuente fuente) throws FuenteNoSoportadaException {
        cubiculoDecorado = crearCubiculoCon(fuente);
    }

    @Override
    public boolean estaOcupado() {
        return cubiculoDecorado.estaOcupado();
    }

    @Override
    public void ocuparCon(final IFuente fuente) throws CubiculoOcupadoExcetion, FuenteNoSoportadaException {
        try {
            cubiculoDecorado.ocuparCon(fuente);
        } catch (FuenteNoSoportadaException e) {
            cubiculoDecorado = crearCubiculoCon(fuente);
        }
    }

    @Override
    public boolean puedeSerComienzoDeCinta() {
        return cubiculoDecorado.puedeSerComienzoDeCinta();
    }

    @Override
    public IFuente obtenerPrincipioDeCinta() throws CubiculoVacioException {
        return cubiculoDecorado.obtenerPrincipioDeCinta();
    }

    @Override
    public Maquina obtenerFinDeCinta() throws CubiculoVacioException {
        return cubiculoDecorado.obtenerFinDeCinta();
    }

    @Override
    public Maquina obtenerMaquina() throws CubiculoVacioException {
        return cubiculoDecorado.obtenerMaquina();
    }

    @Override
    public Fuente obtenerFuente() throws CubiculoVacioException {
        return cubiculoDecorado.obtenerFuente();
    }

    @Override
    public IFuente getFuente() throws CubiculoVacioException {
        return cubiculoDecorado.getFuente();
    }

    @Override
    public boolean eliminar(final IFuente fuente) {
        return cubiculoDecorado.eliminar(fuente);
    }

    public static CubiculoFabril crearCubiculoCon(final IFuente fuente) throws FuenteNoSoportadaException {
        CubiculoFabril resultado;
        if (fuente instanceof Maquina) {
            resultado = new CubiculoConMaquina();

        } else if (fuente instanceof Fuente) {
            resultado = new CubiculoConFuente();

        } else {
            throw new FuenteNoSoportadaException();
        }
        try {
            resultado.ocuparCon(fuente);
        } catch (CubiculoOcupadoExcetion cubiculoOcupadoExcetion) {
            // No deberia arrojarse nunca
            cubiculoOcupadoExcetion.printStackTrace();
        }
        return resultado;
    }

    protected CubiculoMutante() { }
}
