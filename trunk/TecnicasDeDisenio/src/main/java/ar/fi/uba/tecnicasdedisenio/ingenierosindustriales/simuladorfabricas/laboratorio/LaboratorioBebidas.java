package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPlancha;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaPrensa;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.ValidadorProductos;

public class LaboratorioBebidas extends Laboratorio {

	@Override
	public void cargarProcesos() {
		TipoMaquina maq = null;
		Proceso proceso = null;
		ValidadorProductos val = new ValidadorProductos();
		val.Cargar();
		
		proceso = new Proceso(1000);
		maq = new TipoMaquinaPrensa();
		maq.getPrecedentes().add(new TipoMaquinaPlancha());
		maq.getPrecedentes().add(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "azucar", 0));
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addMateriaPrima(new Producto(val, "edulcorante", 0));
		proceso.setMaquinaFinal(maq);
		this.getProcesosHabilitados().add(proceso);
		
		proceso = new Proceso(1500);
		maq = new TipoMaquinaPrensa();
		maq.addPrecedente(new TipoMaquinaPlancha());
		maq.addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPrensa());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addPrecedente(new TipoMaquinaPlancha());
		maq.getPrecedentes().get(0).addMateriaPrima(new Producto(val, "agua", 0));
		proceso.setMaquinaFinal(maq);
		this.getProcesosHabilitados().add(proceso);

	}

}
