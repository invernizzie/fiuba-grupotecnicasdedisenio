package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.MateriaPrimaDistintaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.PrecedentesDistintosException;
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
	
	public abstract Maquina getInstancia();

	public abstract Boolean verificarTipo(Maquina maquina);

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public float getCosto() {
		return costo;
	}
	
	public void setMateriasPrimas(List<Producto> materiasPrimas) {
		this.materiasPrimas = materiasPrimas;
	}

	public List<Producto> getMateriasPrimas() {
		return materiasPrimas;
	}

	public void setPrecedentes(List<TipoMaquina> precedentes) {
		this.precedentes = precedentes;
	}

	public List<TipoMaquina> getPrecedentes() {
		return precedentes;
	}

	public void addPrecedente(TipoMaquina precedente) {
		if(this.precedentes == null){
			this.precedentes = new ArrayList<TipoMaquina>();
		}
		this.precedentes.add(precedente);
		
	}

	public void addMateriaPrima(Producto tipoProducto) {
		if(this.materiasPrimas == null){
			this.materiasPrimas = new ArrayList<Producto>();
		}
		this.materiasPrimas.add(tipoProducto);
		
	}
	
	public void removePrecedente(TipoMaquina precedente) {
		this.precedentes.remove(precedente);
		
	}

	public void removeMateriaPrima(Producto tipoProducto) {
		this.materiasPrimas.remove(tipoProducto);
		
	}

	public void setComparador(ComparadorDeMaquinas comparador) {
		this.comparador = comparador;
	}

	public ComparadorDeMaquinas getComparador() {
		return comparador;
	}
	
	public boolean equals(Maquina maquina){
		
		if(this.verificarTipo(maquina)){
			try{
				this.verificarMateriasPrimas(maquina.getMateriasPrimas());
				this.verificarPrecedencias(maquina.getPrecedentes());
			}
			catch(MateriaPrimaDistintaException e){
				return false;

			}
			catch(PrecedentesDistintosException e){
				return false;

			}
		}
		else{
			return false;
		}
			
		return true;
	}
	
	
	public void verificarMateriasPrimas(List<Producto> list) throws MateriaPrimaDistintaException{
		int i,j;
		
		/*Se copia a un Array para trabajar mejor.*/
		ArrayList<Producto> matPrimas = new ArrayList<Producto>(list);
		
		/*Si las cantidades son distintas no estan bien las materias primas.*/
		if(this.getMateriasPrimas().size()!=matPrimas.size()){
			throw new MateriaPrimaDistintaException();
		}
		
		/*Se comparan todas las materias primas.*/
		for(i=0;i<this.getMateriasPrimas().size();i++){
			for(j=0;j<matPrimas.size();j++){
				if(this.getMateriasPrimas().get(i).equals(matPrimas.get(j))){
					matPrimas.remove(j);
					
				}
			}
		}
		
		/*Si todas tuvieron equivalencia no deberia quedar ninguna en maquina.*/
		if(matPrimas.size()!=0){
			throw new MateriaPrimaDistintaException();
		}
	}
	
	public void verificarPrecedencias(List<Maquina> list) throws PrecedentesDistintosException{
		int i, j;
		
		/*Se copia a un Array para trabajar mejor.*/
		ArrayList<Maquina> maquinas = new ArrayList<Maquina>(list);
		
		/*Si las cantidades son distintas no estan bien las precedencias.*/
		if(this.getPrecedentes().size()!=maquinas.size()){
			throw new PrecedentesDistintosException();
		}
		
		/*Se comparan todas los precedencias.*/
		for(i=0;i<this.getPrecedentes().size();i++){
			for(j=0;j<maquinas.size();j++){
				if(this.getPrecedentes().get(i).equals(maquinas.get(j))){
					maquinas.remove(j);	
				}
			}
		}
		
		/*Si todas tuvieron equivalencia no deberia quedar ninguna en maquina.*/
		if(maquinas.size()!=0){
			throw new PrecedentesDistintosException();
		}
		
	}
	
}
