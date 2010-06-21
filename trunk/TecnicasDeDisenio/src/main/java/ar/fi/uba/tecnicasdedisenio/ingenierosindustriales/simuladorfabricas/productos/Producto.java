package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos;


/**
 * Clase que representa a cualquier Producto
 * @author Diego
 *
 */


public class Producto implements Cloneable {
	public static final int DESECHO = 0;
	public static final int DEFECTUOSO = 1;
	public static final int PRODUCTO_OK = 2;
	
	public enum Estado {DESECHO,DEFECTUOSO, PRODUCTO_OK};
	
	private Estado estado = null;
	
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	private String tipoProducto;

	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(final String tipo) {
		if (!this.estado.equals(DEFECTUOSO) || !this.estado.equals(DESECHO)) {
			this.tipoProducto = tipo;
        }
	}

	public Producto(final String tipo, final double tasa_falla) {
		if (ValidadorProductos.instancia().existe(tipo)) {
			double proba = Math.random();
			if (tasa_falla < proba) {
				this.tipoProducto = tipo;
				estado = Estado.PRODUCTO_OK;
			} else {
				estado = Estado.DEFECTUOSO;
			}
		} else {
			estado = Estado.DESECHO;
		}
	}

    @Deprecated
    public Producto() { }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipoProducto == null) ? 0 : tipoProducto.hashCode());
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
		if (tipoProducto == null) {
			if (other.tipoProducto != null) {
				return false;
            }
		} else if (!tipoProducto.equals(other.tipoProducto))
			return false;
		return true;
	}

	public Producto clone() {
		return new Producto(tipoProducto, 0F);
	}

	public Float getPrecioMercado() {
		return ValidadorProductos.instancia().obtenerPrecioMercado(this.tipoProducto);
	}

	public Float getPrecioCompra() {
		return ValidadorProductos.instancia().obtenerPrecioCompra(this.tipoProducto);		
	}
}
