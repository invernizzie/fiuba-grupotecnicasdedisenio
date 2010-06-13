package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class InstaladorDeMaquinas extends Instalador {

    private TipoMaquina tipoMaquina;

    public InstaladorDeMaquinas(EspacioFabril espacioFabril, TipoMaquina tipoMaquina) {
        super(espacioFabril);
        this.tipoMaquina = tipoMaquina;
    }

    @Override
    public void mouseMove(int x, int y) {}

    @Override
    public void mouseDown(int x, int y) {
        try {
            getEspacioFabril().crearMaquina(x, y, tipoMaquina);
        } catch (EspacioOcupadoException e) {
            // Simplemente no creo la maquina
        }
    }

    @Override
    public void mouseUp(int x, int y) {}
}
