package backtracking;
import estructura.Encreuades;
import estructura.PosicioInicial;

public class SolucioBacktracking {

	/* TODO
	 * cal definir els atributs necessaris
	 */

	private char[][] taulellSol;
	private char[][] taulell;

	private final Encreuades repte;

	
	public SolucioBacktracking(Encreuades repte) {
		this.repte = repte;
	}

	public char[][] getMillorSolucio() {
		return null; //TODO
	}

	public Runnable start(boolean optim)
	{
		/* TODO
		 * cal inicialitzar els atributs necessaris
		 */

		taulell = repte.getPuzzle();



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
	/* TODO
	 * Esquema recursiu que busca totes les solucions
	 * no cal utilitzar una variable booleana per aturar perquè busquem totes les solucions
	 * cal guardar una COPIA de la millor solució a una variable
	 */
	private void backMillorSolucio(int indexUbicacio) {

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
		return false; //TODO
	}

	//Mira si no hi ca cap
	private boolean esPotPosar(int fila, int col, int len, char dir, char[] paraula) {

		boolean espotposar = true;

		switch (dir) {
			case 'V':
				for (int i = 0; (len-i)!=0 && espotposar; i++) {
					if(taulell[fila+i][col] != ' '){
						//char lletra = paraula[i];
						if(taulell[fila+i][col] != paraula[i])
							espotposar = false;
					}
				}
				return espotposar;
			default:
				for (int j = 0; (len-j)!=0 && espotposar; j++) {
					if (taulell[fila][col + j] != ' ') {
						if(taulell[fila][col+j] != paraula[j])
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
		return false; //TODO
	}
	
	private int calcularFuncioObjectiu(char[][] matriu) {
		return 0; //TODO
	}
	
	private void guardarMillorSolucio() {
		// TODO - cal guardar un clone
	}
	
	public String toString() {
		String resultat = "";
		//TODO
		return resultat;
	}

}
