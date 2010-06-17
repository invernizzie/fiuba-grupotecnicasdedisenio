package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica.excepciones.EspacioOcupadoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

/**
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 06/06/2010
 */
public class InstaladorDeFuentes extends Instalador {

    private Producto materiaPrima;
    private String nombre;

    public InstaladorDeFuentes(final EspacioFabril espacioFabril, final Producto materiaPrima, final String nombre) {
        super(espacioFabril);
        this.materiaPrima = materiaPrima;
        this.nombre = nombre; 
    }

    @Override
    public void doMouseMove(final int x, final int y) { }

    @Override
    public void doMouseDown(final int x, final int y) {
        try {
            getEspacioFabril().crearMateriaPrima(x, y, materiaPrima, nombre);
        } catch (EspacioOcupadoException e) {
            // Simplemente no se crea la materia prima
        }
    }

    @Override
    public void doMouseUp(final int x, final int y) { }
}
