package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion;

import java.util.HashSet;
import java.util.Set;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.jugador.Jugador;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.Laboratorio;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.EntradaInvalidaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.excepciones.ProcesamientoException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina.TipoMaquinaControlCalidad;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

public class LineaProduccion {
	private static final int CANTIDAD_PRODUCIDA_FIJA = 10;
	
	private Set<Maquina> maquinas;
	private Set<Maquina> primerasMaquinas;
	private Set<Maquina> maquinasActuales;
	private Maquina ultimaMaquina;
	private Contenedor contenedor;
	private Laboratorio laboratorio;
	private Float costoLinea = 0F;
	private Jugador jugador;
	private TipoMaquinaControlCalidad tipoControlDeCalidad;
	private boolean ciclo;
	
	public LineaProduccion(final Jugador jugador) {
		this.maquinas = new HashSet<Maquina>();
		this.primerasMaquinas = new HashSet<Maquina>();
		this.jugador = jugador;
		this.laboratorio = jugador.getLaboratorio();
		tipoControlDeCalidad = new TipoMaquinaControlCalidad();
		this.setCiclo(false);
		
	}
	
	public void agregarMaquina(final Maquina maquina) {
		this.setCostoLinea(costoLinea + maquina.getCostoMaquina());
		this.getMaquinas().add(maquina);
		this.actualizarLinea();
	}

	public void eliminarMaquina(final Maquina maquina) {
		
		this.setCostoLinea(costoLinea - maquina.getCostoMaquina());
		this.getMaquinas().remove(maquina);
		
		this.actualizarLinea();
	}
	
	public void actualizarLinea() {
		ultimaMaquina = null;
		this.primerasMaquinas = new HashSet<Maquina>();
		for (Maquina maquina : getMaquinas()) {
			maquina.setConectadaAContenedor(false);
			if (esPrimeraMaquina(maquina)) {
				actualizarPrimeraMaquina(maquina);
			}
			
			if (esUltimaMaquina(maquina)) {
				actualizarUltimaMaquina(maquina);
			}
		}
		/*Si no hay ultima maquina entonces es un ciclo.*/
        setCiclo(ultimaMaquina == null);
	}

	private void actualizarPrimeraMaquina(Maquina maquina) {
		this.primerasMaquinas.add(maquina);
	}

	private void actualizarUltimaMaquina(Maquina maquina) {
		ultimaMaquina = maquina;
		this.contenedor = new Contenedor(maquina.getTipoProducto());
		CintaTransportadora cinta = new CintaTransportadora(new Salida(), new Entrada());
		this.contenedor.agregarCinta(cinta);
		ultimaMaquina.setCintaSalida(cinta);
		maquina.setConectadaAContenedor(true);
	}

	public boolean contieneMaquina(final Maquina maquina) {
		return this.getMaquinas().contains(maquina);
	}
	
	public Maquina obtenerUltimaMaquina() {
		return this.ultimaMaquina;
	}
	
	public boolean esPrimeraMaquina(final Maquina maquinaAVerificar) {
		boolean esPrimera = false;
		
		if (maquinaAVerificar.obtenerPrecedentesFisicos().isEmpty()
				&& !maquinaAVerificar.obtenerMateriasPrimasFisicas().isEmpty()) {
			esPrimera = true;
		}
		else {
			if (maquinaAVerificar.obtenerPrecedentesFisicos().isEmpty()) {
				esPrimera = true;
			}
		}
		
		return esPrimera;
	}
	
	/**
	 * Verifica si, para el laboratorio recibido, la linea produce un producto
	 * ya descubierto, de no ser así retorna false y los producido por esta linea 
	 * debe considerarse desecho.
	 * @param laboratorio
	 * @return
	 */
	public boolean construyeProductoValido() {
		Maquina maquinaAVerificar = ultimaMaquina;
		if (this.tipoControlDeCalidad.verificarTipo(ultimaMaquina)) {
			// Kinda nasty, but works
			maquinaAVerificar = ultimaMaquina.obtenerPrecedentesFisicos().get(0);
		}
		
		return this.laboratorio.existeProcesoValido(maquinaAVerificar);
	}
	
	public void procesar() throws ProcesamientoException {
		
		if (this.maquinasActuales == null || this.maquinasActuales.isEmpty()) {
			this.maquinasActuales = this.primerasMaquinas;
		}
		
		Set<Maquina> siguientes = new HashSet<Maquina>();
		
		for (Maquina maquina : maquinasActuales) {
			try {
				
				consumirMateriasPrimas(maquina);
				
				Producto productoObtenido = maquina.procesar
												((!this.esUltimaMaquina(maquina) 
														|| this.construyeProductoValido()));
				
				actualizarSiguientes(siguientes, maquina);

				siguientes = almacenarProducto(siguientes, maquina, productoObtenido);
				
			} catch (EntradaInvalidaException e) {
				throw new ProcesamientoException("No se pudo realizar el proceso", e);
			}
		}
		
		this.maquinasActuales = siguientes;
	}

	private Set<Maquina> almacenarProducto(Set<Maquina> siguientes,
			Maquina maquina, Producto productoObtenido) {
		if (this.esUltimaMaquina(maquina)) {
			// Si se terminó la linea guardamos el producto en el contenedor
			this.getContenedor().recibirProducto(productoObtenido, CANTIDAD_PRODUCIDA_FIJA);
			// Y seteamos la siguiente máquina en null para que vuelva a procesar desde el principio
			siguientes = null;
			
			Float ganancia = this.getContenedor().calcularGanancia();
			this.jugador.aumentarDinero(ganancia);
			this.contenedor.vaciar();
		}
		return siguientes;
	}

	private void actualizarSiguientes(Set<Maquina> siguientes, Maquina maquina) {
		if (maquina.getSiguiente() != null) {
			siguientes.add(maquina.getSiguiente());
		}
	}

	private void consumirMateriasPrimas(Maquina maquina) {
		if (!maquina.getFuentes().isEmpty()) {
			for (Fuente fuente : maquina.getFuentes()) {
				Float precioCompra = fuente.getTipoProducto().getPrecioCompra();
				this.jugador.disminuirDinero(precioCompra);
			}
		}
	}

	public void setCostoLinea(final Float costoLinea) {
		this.costoLinea = costoLinea;
	}

	public Float getCostoLinea() {
		return costoLinea;
	}

	public Contenedor getContenedor() {
		return contenedor;
	}
	
	public boolean estaVacia() {
		return this.getMaquinas().isEmpty();
	}
	
	/**
	 * Verifica si se tiene un ciclo en esa linea y si es asi rompe todas las maquinas.
	 * */
	public void validarCiclo() {
		if (this.isCiclo()) {
			for (Maquina maquina : getMaquinas()) {
				maquina.forzarRotura();
            }
        }
	}
	
	public Set<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setCiclo(final boolean ciclo) {
		this.ciclo = ciclo;
	}

	public boolean isCiclo() {
		return ciclo;
	}
	
	/**
	 * Una máquina es la última de la linea si no figura en la lista de precedentes
	 * de otra máquina.
	 * @param maquinaAVerificar
	 * @return
	 */
	private boolean esUltimaMaquina(final Maquina maquinaAVerificar) {
		
		boolean esUltima = true;
		
		for (Maquina maquina : getMaquinas()) {
			if (!maquina.equals(maquinaAVerificar)
					&& !maquina.obtenerPrecedentesFisicos().isEmpty()
					&& maquina.obtenerPrecedentesFisicos().contains(maquinaAVerificar)) {
				esUltima = false;
			}
		}
		
		return esUltima;
		
	}
}
