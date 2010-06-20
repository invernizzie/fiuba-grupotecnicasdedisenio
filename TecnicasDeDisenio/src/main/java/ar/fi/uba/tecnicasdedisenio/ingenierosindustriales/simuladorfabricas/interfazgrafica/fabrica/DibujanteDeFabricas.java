package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.GeneradorDeColores;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IFuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class DibujanteDeFabricas {
    private Canvas canvas;
    private Rectangle limite;
    
    public void setCanvas(final Canvas canvas) {
        this.canvas = canvas;
        limite = canvas.getBounds();
    }

    public void dibujarLimiteDeFabrica(GC gc) {
        gc.drawRectangle(0, 0, limite.width,
                limite.height);
    }

    void dibujarFuente(final IFuente iFuente, final int x, final int y) {
        if (canvas == null) {
            return;
        }

        if (iFuente instanceof Maquina) {
            Maquina maquina = (Maquina) iFuente;
            dibujarMaquina(maquina, x, y, EspacioFabril.LADO_MAQUINA, EspacioFabril.LADO_MAQUINA);
        } else if (iFuente instanceof Fuente) {
            Fuente fuente = (Fuente) iFuente;
            dibujarMateriaPrima(fuente.getNombreProducto(), x, y, EspacioFabril.LADO_MATERIA_PRIMA, EspacioFabril.LADO_MATERIA_PRIMA);
        }
    }

    public void dibujarMateriaPrima(final String nombre, final int x, final int y, final int _ancho, final int _alto) {
        if (canvas == null) {
            return;
        }

        GC gc = new GC(canvas);
        Color backgroundAnterior = gc.getBackground();
        gc.setBackground(GeneradorDeColores.porString(nombre));
        int ancho = EspacioFabril.LONGITUD_DEL_LADO * (_ancho + 1);
        int alto = EspacioFabril.LONGITUD_DEL_LADO * (_alto + 1);
        gc.fillOval(x, y, ancho, alto);
        gc.drawOval(x, y, ancho - 1, alto - 1);
        gc.setBackground(backgroundAnterior);
        gc.dispose();
    }

    public void dibujarMaquina(final Maquina maquina, final int x, final int y, final int _ancho, final int _alto) {
        if (canvas == null) {
            return;
        }

        GC gc = new GC(canvas);
        Color backgroundAnterior = gc.getBackground();
        gc.setBackground(GeneradorDeColores.porClass(maquina.getClass()));
        int ancho = EspacioFabril.LONGITUD_DEL_LADO * (_ancho + 1);
        int alto = EspacioFabril.LONGITUD_DEL_LADO * (_alto + 1);
        gc.fillRectangle(x, y, ancho, alto);
        gc.drawRectangle(x, y, ancho, alto);
        gc.setBackground(backgroundAnterior);

        if (maquina.estaRota()) {
            backgroundAnterior = gc.getForeground();
            gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
            gc.drawLine(x, y, x + ancho, y + alto);
            gc.drawLine(x, y + alto, x + ancho, y);
            gc.setForeground(backgroundAnterior);
        }
        gc.dispose();
    }

    public void dibujarCinta(final int x1, final int y1, final int x2, final int y2) {
        if (canvas == null) {
            return;
        }

        GC gc = new GC(canvas);
        gc.drawLine(x1, y1, x2, y2);
        gc.dispose();
    }
}