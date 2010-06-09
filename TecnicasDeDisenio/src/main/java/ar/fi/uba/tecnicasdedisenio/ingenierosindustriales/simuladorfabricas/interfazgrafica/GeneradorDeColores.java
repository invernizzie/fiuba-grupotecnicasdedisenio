package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.interfazgrafica;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que asocia colores a Strings u objetos Class
 *
 * @author Esteban I. Invernizzi (invernizzie@gmail.com)
 *         Date: 08/06/2010
 */
public class GeneradorDeColores {

    private static List<Color> colores = new ArrayList<Color>();
    private static int cantidad;
    static {
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_CYAN));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_MAGENTA));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_YELLOW));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
        colores.add(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

        cantidad = colores.size();
    }

    private static Map<String, Color> claveString = new HashMap<String, Color>();
    private static Map<Class, Color> claveClass = new HashMap<Class, Color>();

    public static Color porString(String clave) {
        if (!claveString.containsKey(clave))
            claveString.put(clave, siguienteColor());

        return claveString.get(clave);
    }

    public static Color porClass(Class clave) {
        if (!claveClass.containsKey(clave))
            claveClass.put(clave, siguienteColor());

        return claveClass.get(clave);
    }

    private static int indice = 0;
    private static Color siguienteColor() {
        indice %= cantidad;
        return colores.get(indice++);
    }
}
