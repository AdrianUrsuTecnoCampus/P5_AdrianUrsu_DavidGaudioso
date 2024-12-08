package backtracking;
import estructura.Encreuades;
import estructura.PosicioInicial;



import java.util.Arrays;
import java.util.List;

public class SolucioBacktracking {

	/* TODO
	 * cal definir els atributs necessaris
	 */

	private char[][] taulellSol;
	private char[][] taulellMillor;
	private boolean[] marcades;

	private final Encreuades repte;


	public SolucioBacktracking(Encreuades repte) {
		this.repte = repte;
	}

	public char[][] getMillorSolucio() {
		return this.taulellMillor;
	}

	public Runnable start(boolean optim)
	{
		/* TODO
		 * cal inicialitzar els atributs necessaris
		 */

		taulellSol = repte.getPuzzle();
		this.taulellMillor = new char[taulellSol.length][taulellSol[0].length];
		this.marcades = new boolean[repte.getItemsSize()];

		guardarMillorSolucio();

		if(!optim) {
			if (!this.backUnaSolucio(0))
				throw new RuntimeException("solució no trobada");
			guardarMillorSolucio();
		}else
			this.backMillorSolucio(0);
		return null;
	}

	/* esquema recursiu que troba una solució
	 * utilitzem una variable booleana (que retornem)
	 * per aturar el recorregut quan haguem trobat una solució
	 */

	private boolean backUnaSolucio(int indexUbicacio) {
		boolean trobada = false;
		// iterem sobre els possibles elements
		for(int indexItem = 0; indexItem < this.repte.getItemsSize() && !trobada; indexItem++) {
			//mirem si l'element es pot posar a la ubicació actual
			if(acceptable( indexUbicacio, indexItem)) {
				//posem l'element a la solució actual
				anotarASolucio(indexUbicacio, indexItem);

				if(esSolucio(indexUbicacio)) { // és solució si totes les ubicacions estan plenes
					return true;
				} else
					trobada = this.backUnaSolucio(indexUbicacio+1); //inserim la següent paraula
				if(!trobada)
					// esborrem la paraula actual, per després posar-la a una altra ubicació
					desanotarDeSolucio(indexUbicacio, indexItem);
			}
		}
		return trobada;
	}

	private void backMillorSolucio(int indexUbicacio) {

		int indexItem = 0;

		while (quedin_valors()) {
			if (indexItem < repte.getItemsSize()) {
				if (acceptable(indexUbicacio, indexItem)) {
					anotarASolucio(indexUbicacio, indexItem);
					if (esSolucio(0)) {
						if(calcularFuncioObjectiu(this.taulellSol) > calcularFuncioObjectiu(this.taulellMillor))
							guardarMillorSolucio();
					} else if (completable()) {
						backMillorSolucio(indexUbicacio + 1);
					}
					desanotarDeSolucio(indexUbicacio, indexItem);
				}
			} else {
				break;
			}
			indexItem++;
		}
	}

	private boolean quedin_valors() {
		// minetras que alguna palabra marcada este false
		// se podria ir haciendo el bucle
		for (int i = 0; i< marcades.length; i++){
			if(!marcades[i]){
				return true;
			}
		}
		return false;
	}

	private boolean acceptable(int indexUbicacio, int indexItem) {

		/* Quan la palabra a posar no estigui posada en la taula i es pugui posar
		, la llargada i l’espai per posar-la són iguals.
		 */

		PosicioInicial ubi = this.repte.getEspaisDisponibles().get(indexUbicacio);
		char[] paraula = this.repte.getItem(indexItem);
		int llargada = ubi.getLength();
		char direccio = ubi.getDireccio();
		int fila = ubi.getInitRow();
		int col = ubi.getInitCol();

		if(llargada == paraula.length) {

			if(direccio == 'V')
				return esPotPosar(fila,col,llargada,'V',paraula);
			else{
				return esPotPosar(fila,col,llargada,'H',paraula);
			}
		}
		return false;
	}

	//Mira si no hi ca cap altre lletra en la direcció donada
	// ( això vol dir que hi ha una posicióInicial que comença en la paraula donada i per tant aquesta no s'esborra)
	private boolean esPotPosar(int fila, int col, int len, char dir, char[] paraula) {

		boolean espotposar = true;

		switch (dir) {
			case 'V':
				for (int i = 0; (len-i)!=0 && espotposar; i++) {
					if(taulellSol[fila+i][col] != ' ' && taulellSol[fila+i][col] != '▪'){
						if(taulellSol[fila+i][col] != paraula[i])
							espotposar = false;
					}
				}
				return espotposar;
			default:
				for (int j = 0; (len-j)!=0 && espotposar; j++) {
					if (taulellSol[fila][col + j] != ' ' && taulellSol[fila][col+j] != '▪') {
						if(taulellSol[fila][col+j] != paraula[j])
							espotposar = false;
					}
				}
				return espotposar;
		}
	}

	private void anotarASolucio(int indexUbicacio, int indexItem) {

		PosicioInicial ubi = this.repte.getEspaisDisponibles().get(indexUbicacio);
		char[] palabra = this.repte.getItem(indexItem);

        if (ubi.getDireccio() == 'V') {
			for (int i = 0; (ubi.getLength()-i)!=0; i++) {
				taulellSol[ubi.getInitRow() + i][ubi.getInitCol()] = palabra[i];
			}
        } else {
			for (int j = 0;(ubi.getLength()-j)!=0; j++) {
				taulellSol[ubi.getInitRow()][ubi.getInitCol() + j] = palabra[j];
			}
		}

		marcades[indexItem] = true;
	}

	private void desanotarDeSolucio(int indexUbicacio, int indexItem) {

        PosicioInicial ubi = this.repte.getEspaisDisponibles().get(indexUbicacio);
        char[] paraula = this.repte.getItem(indexItem);
        int fila = ubi.getInitRow();
        int col = ubi.getInitCol();

        for(int i = 0; i<paraula.length; i++) {
            if(ubi.getDireccio() == 'V'){
                if(taulellSol[fila+i][col] == paraula[i]){
                    if(esPotEliminar(ubi,i)) {
						taulellSol[fila + i][col] = ' ';
					}

                }
            }
            else{
                if(taulellSol[fila][col+i] == paraula[i]){
                    if(esPotEliminar(ubi,i)){
                        taulellSol[fila][col+i] = ' ';
					}
                }
            }
        }

        marcades[indexItem] = false;


    }

	private boolean esPotEliminar(PosicioInicial ubi,int count) {

		if (ubi.getDireccio() == 'V') {
			for (int i = 0; i < ubi.getLength(); i++) {
				int fila = ubi.getInitRow() + count;
				if (ubi.getInitCol() - 1 >= 0) {
					if (taulellSol[fila][ubi.getInitCol() - 1] != ' ' && taulellSol[fila][ubi.getInitCol() - 1] != '▪') {
						return false;
					}
				}
				if (ubi.getInitCol() + 1 < taulellSol[0].length) {
					if (taulellSol[fila][ubi.getInitCol() + 1] != ' ' && taulellSol[fila][ubi.getInitCol() + 1] != '▪') {
						return false;
					}
				}
			}
		} else {
			for (int j = 0; j < ubi.getLength(); j++) {
				int col = ubi.getInitCol() + count;
				if (ubi.getInitRow() - 1 >= 0) {
					if (taulellSol[ubi.getInitRow() - 1][col] != ' ' && taulellSol[ubi.getInitRow() - 1][col] != '▪') {
						return false;
					}
				}
				if (ubi.getInitRow() + 1 < taulellSol.length) {
					if (taulellSol[ubi.getInitRow() + 1][col] != ' ' && taulellSol[ubi.getInitRow() + 1][col] != '▪') {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean esSolucio(int index) {
		/*
		És solució quan totes les ubicacions estiguin plenes i
		no hi hagin paraules per agafar.
		(Hem arribat a una fulla de l’espai de cerca.)
		 */

		for(int i = 0; i< taulellSol.length; i++) {
			for(int j = 0; j<taulellSol[i].length; j++) {
				if(taulellSol[i][j] == ' '){
					return false;
				}
			}
		}
		return true;
	}

	private boolean completable() {
		for(int i = 0; i< taulellSol.length; i++) {
			for(int j = 0; j<taulellSol[i].length; j++) {
				if(taulellSol[i][j] == ' '){
					return true;
				}
			}
		}
		return false;
	}

	private int calcularFuncioObjectiu(char[][] matriu) {

		int total = 0;

		for(int i = 0; i< matriu.length; i++) {
			for(int j = 0; j<matriu[i].length; j++) {
				if(matriu[i][j] != '▪'){
					char a = matriu[i][j];
                    total += a;
				}
			}
		}

		return total;
	}

	private void guardarMillorSolucio() {

		for(int i = 0; i<this.taulellSol.length; i++) {
			this.taulellMillor[i] = this.taulellSol[i].clone();
		}

	}

	public String toString() {
		String resultat = "";

		for(int i = 0; i<this.taulellSol.length; i++) {
			System.out.println("");
			for(int j = 0; j<this.taulellSol[i].length; j++) {
				resultat += this.taulellSol[i][j] + "	";
			}
		}
		return resultat;
	}

}
