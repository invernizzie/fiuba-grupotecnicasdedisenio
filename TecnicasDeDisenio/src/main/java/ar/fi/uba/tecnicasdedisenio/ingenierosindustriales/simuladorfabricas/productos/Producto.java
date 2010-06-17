package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;


/**
 * Clase que representa a cualquier Producto
 * @author Diego
 *
 */
public class Producto implements Cloneable {
	private String estado;
	private ValidadorProductos validador;

	public String getEstado() {
		return estado;
	}

	public void setEstado(final String estado) {
		if (!this.estado.equals("Defectuoso") || !this.estado.equals("Desecho")) {
			this.estado = estado;
        }
	}

	public Producto(final ValidadorProductos val, final String estado, final double tasa_falla) {
		super();
		this.validador = val;
		if (val.existe(estado)) {
			double proba = Math.random();
			if (tasa_falla < proba) {
				this.estado = estado;
			} else {
				this.estado = "Defectuoso";
			}
		} else {
			this.estado = "Desecho";
		}
	}

    @Deprecated
    public Producto() { }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
        }
		if ((obj == null) || !(obj instanceof Producto)) {
			return false;
        }
		Producto other = (Producto) obj;
		if (estado == null) {
			if (other.estado != null) {
				return false;
            }
		} else if (!estado.equals(other.estado))
			return false;
		return true;
	}

	public Producto clone() {
		return new Producto(this.validador, estado, 0F);
	}

	public Float getPrecioMercado() {
		return this.validador.obtenerPrecioMercado(this.estado);
	}

	public Float getPrecioCompra() {
		return this.validador.obtenerPrecioCompra(this.estado);		
	}
}
