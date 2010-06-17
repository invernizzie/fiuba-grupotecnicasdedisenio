package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.*;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.*;

/**
 * Clase encargada de cargar procesos dado un tipo de laboratorio en particular.
 * Se encuentra harcodeada para los tipos de laboratorios que tenemos creados, pero
 * a futuro se puede implementar un cargador de un XML u otro lado.
 * 
 * @author Gustavo A. Meller (gmeller@gmail.com)
 *
 */
public class CargadorDeProcesos {
	/**
	 * Dado un tipo de laboratorio devuelve la lista de todos sus procesos.
	 * @param tipo
	 * @return
	 */
	public List<Proceso> cargarProcesos(String tipo){
		TipoMaquina maq = null;
		Proceso proceso = null;
		ValidadorProductos val = ValidadorProductos.instancia();
		
		List<Proceso> procesos = new ArrayList<Proceso>();
		
		if (tipo=="Comidas"){
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.getPrecedentes().add(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "centeno", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addMateriaPrima(new Producto(val,"centeno",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaPlancha());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "centeno", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(400);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "centeno", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(500);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "centeno", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(200);
			maq = new TipoMaquinaPlancha();
			maq.addMateriaPrima(new Producto(val,"centeno",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
		}
		if (tipo=="Bebidas"){
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.getPrecedentes().add(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addMateriaPrima(new Producto(val,"agua",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaPlancha());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(400);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(500);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(200);
			maq = new TipoMaquinaHorno();
			maq.addMateriaPrima(new Producto(val,"agua",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
		}
		if (tipo=="Ropa"){
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.getPrecedentes().add(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "trigo", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addMateriaPrima(new Producto(val,"trigo",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaPlancha());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "trigo", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(400);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "trigo", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(500);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "trigo", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(200);
			maq = new TipoMaquinaHorno();
			maq.addMateriaPrima(new Producto(val,"trigo",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
		}
		if (tipo=="Videojuegos"){
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.getPrecedentes().add(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addMateriaPrima(new Producto(val,"azucar",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaPlancha());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(400);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(500);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(200);
			maq = new TipoMaquinaHorno();
			maq.addMateriaPrima(new Producto(val,"azucar",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
		}
		if (tipo=="Electrodomesticos"){
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.getPrecedentes().add(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "edulcorante", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addMateriaPrima(new Producto(val,"edulcorante",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(0);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaPlancha());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "edulcorante", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(400);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "edulcorante", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(500);
			maq = new TipoMaquinaPrensa();
			maq.addPrecedente(new TipoMaquinaMezcladora());
			maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
			maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaHorno());
			maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "edulcorante", 0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
			
			proceso = new Proceso(200);
			maq = new TipoMaquinaHorno();
			maq.addMateriaPrima(new Producto(val,"edulcorante",0));
			proceso.setMaquinaFinal(maq);
			procesos.add(proceso);
		}
		return procesos;
	}
}
