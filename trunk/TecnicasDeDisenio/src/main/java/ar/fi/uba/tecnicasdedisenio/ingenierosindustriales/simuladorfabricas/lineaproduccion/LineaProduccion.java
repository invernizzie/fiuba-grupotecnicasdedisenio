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
	private static int CANTIDAD_PRODUCIDA_FIJA = 10;
	
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
	
	public LineaProduccion(Jugador jugador){
		this.maquinas = new HashSet<Maquina>();
		this.primerasMaquinas = new HashSet<Maquina>();
		this.jugador = jugador;
		this.laboratorio = jugador.getLaboratorio();
		tipoControlDeCalidad = new TipoMaquinaControlCalidad();
		this.setCiclo(false);
		
	}
	
	public void agregarMaquina(Maquina maquina) {
		
		this.setCostoLinea(costoLinea + maquina.getCostoMaquina());
		this.getMaquinas().add(maquina);
		this.actualizarLinea();
	}

	public void eliminarMaquina(Maquina maquina) {
		
		this.setCostoLinea(costoLinea - maquina.getCostoMaquina());
		this.getMaquinas().remove(maquina);
		
		this.actualizarLinea();
	}
	
	public void actualizarLinea() {
		ultimaMaquina = null;
		this.primerasMaquinas = new HashSet<Maquina>();
		for (Maquina maquina : getMaquinas()) {
			maquina.setConectadaAContenedor(false);
			if (esPrimeraMaquina(maquina)){
				this.primerasMaquinas.add(maquina);
			}
			
			if (esUltimaMaquina(maquina)){
				ultimaMaquina = maquina;
				this.contenedor = new Contenedor(maquina.getTipoProducto());
				CintaTransportadora cinta = new CintaTransportadora(new Salida(), new Entrada());
				this.contenedor.agregarCinta(cinta);
				ultimaMaquina.setCintaSalida(cinta);
				maquina.setConectadaAContenedor(true);
			}
		}
		/*Si no hay ultima maquina entonces es un ciclo.*/
		if(ultimaMaquina==null)
			this.setCiclo(true);
		else
			this.setCiclo(false);
	}

	public boolean contieneMaquina(Maquina maquina) {
		return this.getMaquinas().contains(maquina);
	}
	
	public Maquina obtenerUltimaMaquina(){
		return this.ultimaMaquina;
	}
	
	public boolean esPrimeraMaquina(Maquina maquinaAVerificar){
		boolean esPrimera = false;
		
		if (maquinaAVerificar.obtenerPrecedentesFisicos().isEmpty() &&
				!maquinaAVerificar.obtenerMateriasPrimasFisicas().isEmpty()){
			esPrimera = true;
		}
		
		return esPrimera;
	}
	
	/**
	 * Verifica si, para el laboratorio recibido, la linea produce un producto
	 * ya descubierto, de no ser as� retorna false y los producido por esta linea 
	 * debe considerarse desecho.
	 * @param laboratorio
	 * @return
	 */
	public boolean construyeProductoValido(){
		Maquina maquinaAVerificar = ultimaMaquina;
		if (this.tipoControlDeCalidad.verificarTipo(ultimaMaquina)){
			// Kinda nasty, but works
			maquinaAVerificar = ultimaMaquina.obtenerPrecedentesFisicos().get(0);
		}
		
		return this.laboratorio.existeProcesoValido(maquinaAVerificar);
	}
	
	public void procesar() throws ProcesamientoException {
		
		if (this.maquinasActuales == null || this.maquinasActuales.isEmpty()){
			this.maquinasActuales = this.primerasMaquinas;
		}
		
		Set<Maquina> siguientes = new HashSet<Maquina>();
		
		Boolean productoValido;
		
		for (Maquina maquina : maquinasActuales) {
			try {
				
				if (!maquina.getFuentes().isEmpty()){
					for (Fuente fuente : maquina.getFuentes()) {
						Float precioCompra = fuente.getTipoProducto().getPrecioCompra();
						this.jugador.disminuirDinero(precioCompra);
					}
				}
				
				if (this.esUltimaMaquina(maquina)){
					productoValido = this.construyeProductoValido();
				}else{
					productoValido = true;
				}
				
				Producto productoObtenido = maquina.procesar(productoValido);
				
				
				if (maquina.getSiguiente() != null){
					siguientes.add(maquina.getSiguiente());
				}

				if (this.esUltimaMaquina(maquina)){
					// Si se termin� la linea guardamos el producto en el contenedor
					this.getContenedor().recibirProducto(productoObtenido, CANTIDAD_PRODUCIDA_FIJA);
					// Y seteamos la siguiente m�quina en null para que vuelva a procesar desde el principio
					siguientes = null;
					
					Float ganancia = this.getContenedor().calcularGanancia();
					this.jugador.aumentarDinero(ganancia);
					this.contenedor.vaciar();
				}
				
			} catch (EntradaInvalidaException e) {
				throw new ProcesamientoException("No se pudo realizar el proceso",e);
			}
		}
		
		this.maquinasActuales = siguientes;
	}

	public void setCostoLinea(Float costoLinea) {
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
	public void validarCiclo(){
		if (this.isCiclo())
			for(Maquina maquina : getMaquinas())
				maquina.forzarRotura();
	}
	
	public Set<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setCiclo(boolean ciclo) {
		this.ciclo = ciclo;
	}

	public boolean isCiclo() {
		return ciclo;
	}
	
	/**
	 * Una m�quina es la �ltima de la linea si no figura en la lista de precedentes
	 * de otra m�quina.
	 * @param maquinaAVerificar
	 * @return
	 */
	private boolean esUltimaMaquina(Maquina maquinaAVerificar){
		
		boolean esUltima = true;
		
		for (Maquina maquina : getMaquinas()) {
			if (!maquina.equals(maquinaAVerificar) &&
					!maquina.obtenerPrecedentesFisicos().isEmpty() && 
					maquina.obtenerPrecedentesFisicos().contains(maquinaAVerificar)){
				esUltima = false;
			}
		}
		
		return esUltima;
		
	}
}
