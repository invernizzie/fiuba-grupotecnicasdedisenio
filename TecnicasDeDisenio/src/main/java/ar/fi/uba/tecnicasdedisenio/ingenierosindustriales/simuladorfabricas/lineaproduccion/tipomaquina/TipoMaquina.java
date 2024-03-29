package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.excepciones.MateriaPrimaDistintaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.excepciones.PrecedentesDistintosException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

public abstract class TipoMaquina {
	
	private float costo;
	private List<Producto> materiasPrimas;
	private List<TipoMaquina> precedentes;
	private ComparadorDeMaquinas comparador;
	
	public TipoMaquina(){
		this.setMateriasPrimas(new ArrayList<Producto>());
		this.setPrecedentes(new ArrayList<TipoMaquina>());
	}
	
	public void setCosto(final float costo) {
		this.costo = costo;
	}

	public float getCosto() {
		return costo;
	}
	
	public void setMateriasPrimas(final List<Producto> materiasPrimas) {
		this.materiasPrimas = materiasPrimas;
	}

	public List<Producto> getMateriasPrimas() {
		return materiasPrimas;
	}

	public void setPrecedentes(final List<TipoMaquina> precedentes) {
		this.precedentes = precedentes;
	}

	public List<TipoMaquina> getPrecedentes() {
		return precedentes;
	}

	public void addPrecedente(final TipoMaquina precedente) {
		if (this.precedentes == null) {
			this.precedentes = new ArrayList<TipoMaquina>();
		}
		this.precedentes.add(precedente);
		
	}

	public void addMateriaPrima(final Producto tipoProducto) {
		if (this.materiasPrimas == null) {
			this.materiasPrimas = new ArrayList<Producto>();
		}
		this.materiasPrimas.add(tipoProducto);
		
	}
	
	public void removePrecedente(final TipoMaquina precedente) {
		this.precedentes.remove(precedente);
		
	}

	public void removeMateriaPrima(final Producto tipoProducto) {
		this.materiasPrimas.remove(tipoProducto);
		
	}

	public void setComparador(final ComparadorDeMaquinas comparador) {
		this.comparador = comparador;
	}

	public ComparadorDeMaquinas getComparador() {
		return comparador;
	}
	
	public boolean equals(final Maquina maquina){
		Boolean iguales = false;
		/*Se copian las listas a auxiliares para poder trabajar mejor.*/
		List<Producto> listMatPrimasAux = new ArrayList<Producto>(maquina.getMateriasPrimas());
		List<Maquina> listPrecedentesAux = new ArrayList<Maquina>(maquina.getPrecedentes());
		
		if (this.verificarTipo(maquina)){
			try{
				this.verificarMismasMateriasPrimas(listMatPrimasAux);
				this.verificarMismosPrecedencias(listPrecedentesAux);
				iguales = true;
			} catch(MateriaPrimaDistintaException e){
			} catch(PrecedentesDistintosException e){
			}
		}
		return iguales;
	}
	
	public void verificarMismasMateriasPrimas(List<Producto> matPrimas) throws MateriaPrimaDistintaException{
		int i,j;
		
		/*Si las cantidades son distintas no estan bien las materias primas.*/
		if (this.getMateriasPrimas().size()!=matPrimas.size()){
			throw new MateriaPrimaDistintaException();
		}
		
		/*Se comparan todas las materias primas.*/
		for (i=0;i<this.getMateriasPrimas().size();i++){
			for (j=0;j<matPrimas.size();j++){
				if (this.getMateriasPrimas().get(i).equals(matPrimas.get(j))){
					matPrimas.remove(j);
					
				}
			}
		}
		
		/*Si todas tuvieron equivalencia no deberia quedar ninguna en maquina.*/
		if (matPrimas.size()!=0){
			throw new MateriaPrimaDistintaException();
		}
	}
	
	public void verificarMismosPrecedencias(List<Maquina> maquinas) throws PrecedentesDistintosException{
		int i, j;
		
		/*Si las cantidades son distintas no estan bien las precedencias.*/
		if (this.getPrecedentes().size()!=maquinas.size()){
			throw new PrecedentesDistintosException();
		}
		
		/*Se comparan todas los precedencias.*/
		for (i=0;i<this.getPrecedentes().size();i++){
			for (j=0;j<maquinas.size();j++){
				if (this.getPrecedentes().get(i).equals(maquinas.get(j))){
					maquinas.remove(j);	
				}
			}
		}
		
		/*Si todas tuvieron equivalencia no deberia quedar ninguna en maquina.*/
		if (maquinas.size()!=0){
			throw new PrecedentesDistintosException();
		}
		
	}
	
	public abstract Maquina getInstancia();

	public abstract Boolean verificarTipo(Maquina maquina);	
}
