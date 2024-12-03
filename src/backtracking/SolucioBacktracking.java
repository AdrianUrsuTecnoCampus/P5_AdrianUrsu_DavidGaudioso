package backtracking;
import estructura.Encreuades;
import estructura.PosicioInicial;

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
		marcades = new boolean[repte.getItemsSize()];



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
		/* TODO
		 * Esquema recursiu que busca totes les solucions
		 * no cal utilitzar una variable booleana per aturar perquè busquem totes les solucions
		 * cal guardar una COPIA de la millor solució a una variable
		 */
		/*
		inicialitzem_valors_domini_decisio_nivell_k();
		agafar_el_primer_valor();
		while (quedin_valors()) { // Recorregut de tot l’espai de cerca
			if (valor_acceptable()) { // No viola les restriccions
				anotar_el_valor_a_la_solucio();
				if (solucio_final()) {
					if (millor_solucio())
						Millor = TS;
				} else if (solucio_completable()) {
					BackMillorSolucio(TS, k + 1, Millor);
				}
				desanotar_el_valor();
			} // fi if
			agafar_seguent_valor();
			// Passem al següent germà a la dreta
			} // fi while
		} // fi procediment
		 */

		int indexItem = 0;

		while (quedin_valors()) { // Recorregut de tot l’espai de cerca
			if (acceptable(indexUbicacio,indexItem)) { // No viola les restriccions
				anotarASolucio(indexUbicacio,indexItem);
				if (esSolucio(0)) {
					if(calcularFuncioObjectiu(this.taulellSol) > calcularFuncioObjectiu(this.taulellMillor))
						guardarMillorSolucio();
				} else if (completable()) {
					backMillorSolucio(indexUbicacio + 1);
				}
				desanotarDeSolucio(indexUbicacio, indexItem);
			} // fi if
			indexItem++;
		} // fi while
	} // fi procediment


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


	private boolean paraulaUtilitzada(int indexItem) {return marcades[indexItem];}

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
		return false; //TODO
	}

	//Mira si no hi ca cap altre lletra en la direcció donada
	// ( això vol dir que hi ha una posicióInicial que comença en la paraula donada i per tant aquesta no s'esborra)
	private boolean esPotPosar(int fila, int col, int len, char dir, char[] paraula) {

		boolean espotposar = true;

		switch (dir) {
			case 'V':
				for (int i = 0; (len-i)!=0 && espotposar; i++) {
					if(taulellSol[fila+i][col] != ' '){
						//char lletra = paraula[i];
						if(taulellSol[fila+i][col] != paraula[i])
							espotposar = false;
					}
				}
				return espotposar;
			default:
				for (int j = 0; (len-j)!=0 && espotposar; j++) {
					if (taulellSol[fila][col + j] != ' ') {
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
	}

	private void desanotarDeSolucio(int indexUbicacio, int indexItem) {

		PosicioInicial ubi = this.repte.getEspaisDisponibles().get(indexUbicacio);
		char[] paraula = this.repte.getItem(indexItem);
		int fila = ubi.getInitRow();
		int col = ubi.getInitCol();

		for(int i = 0; i<paraula.length; i++) {
			if(ubi.getDireccio() == 'V'){
				if(taulellSol[fila+i][col] == paraula[i]){
					if(esPotEliminar(ubi))
						taulellSol[fila+i][col] = ' ';
				}
			}
			else{
				if(taulellSol[fila][col+i] == paraula[i]){
					if(esPotEliminar(ubi))
						taulellSol[fila][col+i] = ' ';
				}
			}
		}


	}

	private boolean esPotEliminar(PosicioInicial ubi) {

		boolean espot = false;

		if(ubi.getDireccio()=='V') {

			for(int i = 0; (ubi.getLength()-i)!=0; i++) {
				int fila = ubi.getInitRow() + i;
				if(ubi.getInitCol() - 1 > 0) {
					if(taulellSol[fila][ubi.getInitCol() - 1 ] != ' ' && taulellSol[fila][ubi.getInitCol() - 1 ] != '▪') {
						espot = false;
					}
				} else if (ubi.getInitCol() + 1 < taulellSol[0].length) {
					if(taulellSol[fila][ubi.getInitCol() + 1] != ' ' && taulellSol[fila][ubi.getInitCol() + 1] != '▪' ) {
						espot = false;
					}
				} else {
					espot = true;
				}
			}
		} else {
			for(int j = 0; (ubi.getLength()-j)!=0; j++) {
				int col = ubi.getInitCol() + j;
				if(ubi.getInitRow() - 1 > 0) {
					if(taulellSol[ubi.getInitRow() - 1][col] != ' ' && taulellSol[ubi.getInitRow()-1][col] != '▪') {
						espot = false;
					}
				} else if (ubi.getInitCol() + 1 < taulellSol[0].length) {
					if(taulellSol[ubi.getInitRow() + 1][col] != ' ' && taulellSol[ubi.getInitRow() + 1][col] != '▪' ) {
						espot = false;
					}
				} else
					espot = true;
			}
		}

		return espot;

	}

	private boolean esSolucio(int index) {
		/*
		És solució quan totes les ubicacions estiguin plenes i
		no hi hagin paraules per agafar.
		(Hem arribat a una fulla de l’espai de cerca.)
		 */

		for(int i = 0; i< taulellSol.length; i++) {
			for(int j = 0; j<taulellSol[i].length; j++) {
				if(taulellSol[i][j] != ' '){
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
