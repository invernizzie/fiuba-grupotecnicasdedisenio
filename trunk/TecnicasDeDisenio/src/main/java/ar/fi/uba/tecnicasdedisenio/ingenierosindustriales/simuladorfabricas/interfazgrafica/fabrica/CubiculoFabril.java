package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CintaImposibleException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.CubiculoOcupadoExcetion;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.CintaTransportadora;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Entrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IEntrada;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.ISalida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Salida;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class CubiculoFabril {

    private Maquina maquina = null;
    private Producto materiaPrima = null;
    private List<CintaTransportadora> cintasSalientes = new ArrayList<CintaTransportadora>();
    private List<CintaTransportadora> cintasEntrantes = new ArrayList<CintaTransportadora>();

    public boolean estaOcupado() {
        return (maquina != null) || (materiaPrima != null);
    }

    public void ubicarMaquina(Maquina maquina) throws CubiculoOcupadoExcetion {
        if (estaOcupado())
            throw new CubiculoOcupadoExcetion();
        this.maquina = maquina;
    }

    public void ubicarMateriaPrima(Producto materiaPrima) throws CubiculoOcupadoExcetion {
        if (estaOcupado())
            throw new CubiculoOcupadoExcetion();
        this.materiaPrima = materiaPrima;
    }

    public CintaTransportadora conectarConCintaHacia(CubiculoFabril cubiculoFinal) throws CintaImposibleException {
        if (!this.puedeSerComienzoDeCinta()
                || !cubiculoFinal.puedeSerFinDeCinta())
            throw new CintaImposibleException();

        ISalida extremoInicial;
        if (maquina != null)
            extremoInicial = maquina.getSalida();
        else {
            extremoInicial = new Salida();
            extremoInicial.asignarProducto(materiaPrima);
        }

        /**** Esto podria encapsularse como servicio del modelo ****/
        CintaTransportadora cinta = new CintaTransportadora(extremoInicial, cubiculoFinal.finDeCinta());
        if (maquina != null)
            maquina.setCintaSalida(cinta);
        cubiculoFinal.maquina.addCintaEntrada(cinta);
        /***********************************************************/
        this.cintasSalientes.add(cinta);
        cubiculoFinal.cintasEntrantes.add(cinta);
        return cinta;
    }

    public boolean puedeSerComienzoDeCinta() {
        if (materiaPrima != null)
            return true;
        if (maquina == null)
            return false;
        if (maquina.getCintaSalida() != null)
            return false;
        return true;
    }

    private boolean puedeSerFinDeCinta() {
        return (maquina != null);
    }

    private IEntrada finDeCinta() {
        if (maquina != null)
            return maquina.getEntrada();
        if (materiaPrima != null) {
            IEntrada entrada = new Entrada();
            entrada.agregarProducto(materiaPrima);
            return entrada;
        }
        return null;
    }
}
