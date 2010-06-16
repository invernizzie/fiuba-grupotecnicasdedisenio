package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.fabrica;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica.GeneradorDeColores;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Fuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.IFuente;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class DibujanteDeFabricas {
    private Canvas canvas;
    
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    
    void dibujarFuente(IFuente _fuente, int x, int y) {
        if (canvas == null)
            return;

        if (_fuente instanceof Maquina) {
            Maquina maquina = (Maquina) _fuente;
            dibujarMaquina(maquina, x, y, EspacioFabril.ANCHO_MAQUINA, EspacioFabril.ANCHO_MAQUINA);
        } else if (_fuente instanceof Fuente) {
            Fuente fuente = (Fuente) _fuente;
            dibujarMateriaPrima(fuente.getNombreProducto(), x, y, EspacioFabril.ANCHO_MATERIA_PRIMA, EspacioFabril.ANCHO_MATERIA_PRIMA);
        }
    }

    void dibujarMateriaPrima(String nombre, int x, int y, int _ancho, int _alto) {
        if (canvas == null)
            return;

        GC gc = new GC(canvas);
        Color colorAnterior = gc.getBackground();
        gc.setBackground(GeneradorDeColores.porString(nombre));
        int ancho = EspacioFabril.LONGITUD_DEL_LADO * _ancho;
        int alto = EspacioFabril.LONGITUD_DEL_LADO * _alto;
        gc.fillOval(x, y, ancho, alto);
        gc.setBackground(colorAnterior);
        gc.dispose();
    }

    void dibujarMaquina(Maquina maquina, int x, int y, int _ancho, int _alto) {
        if (canvas == null)
            return;

        GC gc = new GC(canvas);
        Color colorAnterior = gc.getBackground();
        gc.setBackground(GeneradorDeColores.porClass(maquina.getClass()));
        int ancho = EspacioFabril.LONGITUD_DEL_LADO * _ancho;
        int alto = EspacioFabril.LONGITUD_DEL_LADO * _alto;
        gc.fillRectangle(x, y, ancho, alto);
        gc.setBackground(colorAnterior);

        if (maquina.estaRota()) {
            colorAnterior = gc.getForeground();
            gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
            gc.drawLine(x, y, x + ancho, y + alto);
            gc.drawLine(x, y + alto, x + ancho, y);
            gc.setForeground(colorAnterior);
        }
        gc.dispose();
    }

    void dibujarCinta(int _x1, int _y1, int _x2, int _y2) {
        if (canvas == null)
            return;

        GC gc = new GC(canvas);
        gc.drawLine (_x1, _y1, _x2, _y2);
        gc.dispose ();
    }
}