package greedy;

import estructura.Encreuades;
import estructura.PosicioInicial;
import java.util.*;

public class SolucióVoraç {
    /* TODO
     * cal definir els atributs necessaris
     */

    private char[][] sol;

    private final Encreuades repte;

    public SolucióVoraç(Encreuades repte) {
        this.repte = repte;
        /* TODO
         * cal inicialitzar els atributs necessaris
         * i invocar al mètode greedy
         */

        this.sol = greedy();

    }

    public char[][] getSolucio() { return sol; }

    private char[][] greedy(){
        // mireu l'esquema utilitzat per la professora Lina
        // si voleu podeu modificar la capçalera d'aquest mètode
        // si voleu podeu utilitzar recursivita

        return null; // TODO
    }

    /* A aquesta classe
     * podeu definir els mètodes privats que vulgueu
     */

    private boolean seleccionar(char[][] paraules, PosicioInicial pos){


        if(!taulaempty(paraules)) {
            if(hiCapParaula(paraules, pos)) {

                int row = pos.getInitRow();
                int col = pos.getInitCol();
                int lenght = pos.getLength();
                char direccioo = pos.getDireccio();

                for (int i = 0; i < paraules.length; i++) {
                    for (int j = 0; j < paraules[i].length; j++) {
                        paraules[i][j] = ;
                    }
                }

            }


        }

    }

    private boolean taulaempty(char[][] paraules) {
        for (int i = 0; i < paraules.length; i++) {
            for (int j = 0; j < paraules[i].length; j++) {
                if (paraules[i][j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hiCapParaula(char[][] paraules, PosicioInicial pos){

        int capacitat = pos.getLength();
        int cont = 0;

        for (int i = 0; i < paraules.length; i++) {
            for (int j = 0; j < paraules[i].length; j++) {
                if(paraules[i][j] != ' '){
                    cont++;
                }
                if(cont == capacitat){
                    return true;
                }
            }
            cont = 0;
        }

        return false;

    }

    private char letraitem(char item){
        
    }
}
