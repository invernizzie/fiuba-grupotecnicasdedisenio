package ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.tipomaquina;

import java.util.ArrayList;

import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.MateriaPrimaDistintaException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.laboratorio.PrecedentesDistintosException;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.lineaproduccion.Maquina;
import ar.fi.uba.tecnicasdedisenio.ingenierosindustriales.simuladorfabricas.productos.Producto;

public abstract class TipoMaquina {
	
	private float costo;
	private ArrayList<Producto> materiasPrimas;
	private ArrayList<TipoMaquina> precedentes;
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
	
	public void setMateriasPrimas(ArrayList<Producto> materiasPrimas) {
		this.materiasPrimas = materiasPrimas;
	}

	public ArrayList<Producto> getMateriasPrimas() {
		return materiasPrimas;
	}

	public void setPrecedentes(ArrayList<TipoMaquina> precedentes) {
		this.precedentes = precedentes;
	}

	public ArrayList<TipoMaquina> getPrecedentes() {
		return precedentes;
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
				this.verificarMateriasPrimas(maquina);
				this.verificarPrecedencias(maquina);
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
	
	public void verificarMateriasPrimas(Maquina maquina) throws MateriaPrimaDistintaException{
		int i,j;
		
		/*Se copia a un Array para trabajar mejor.*/
		ArrayList<Producto> matPrimas = new ArrayList<Producto>(maquina.getMateriasPrimas());
		
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
	
	public void verificarPrecedencias(Maquina maquina) throws PrecedentesDistintosException{
		int i, j;
		
		/*Se copia a un Array para trabajar mejor.*/
		ArrayList<Maquina> maquinas = new ArrayList<Maquina>(maquina.getPrecedentes());
		
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
