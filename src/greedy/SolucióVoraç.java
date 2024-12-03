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

    private boolean seleccionar(char[][] paraules, PosicioInicial pos){ //TODO


        if(!taulaempty(paraules)) {
            if(hiCapParaula(paraules, pos)) {

                int row = pos.getInitRow();
                int col = pos.getInitCol();
                int lenght = pos.getLength();
                char direccioo = pos.getDireccio();

                for (int i = 0; i < paraules.length; i++) {
                    for (int j = 0; j < paraules[i].length; j++) {
                        if(laMillorParaula(paraules,pos) != null) {
                            //char[] paraula = paraules.getItem(i);
                            //char lletra = paraula[i];
                            //paraules[i][j] = lletra;
                        }
                    }
                }
            }
        }
        return false;
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

    private char[] laMillorParaula(char[][] paraules, PosicioInicial pos){

        char[] millor = {'0'};
        int capacitat = pos.getLength();
        //List<char[]> candidats = new ArrayList<char[]>();
        int cont = 0;

        //Afegim les paraules que hi poden caber dins de la posició
        for (int i = 0; i < paraules.length; i++) {
            char[] possible = paraules[i];
            for (int j = 0; j < paraules[i].length; j++) {
                if(paraules[i][j] != ' '){
                    cont++;

                    //Ja ha arribat a formar una paraula que hi pot caber dins de l'ubicació
                    if(cont == capacitat){
                        //Mirem si és millor o no la possible candidata
                        //candidats.add(paraules[i]);
                        if(puntuacioParaula(millor)<puntuacioParaula(possible)){
                            millor = possible;
                        }

                    }
                }
                else {
                    j = paraules[i].length;
                }

            }
            cont = 0;
        }


        /*
        //Quina paraula és la millor (major puntuació)
        int puntuacio = 0;
        boolean trobat = false;
        for(int i = 0; i < candidats.size(); i++) {

            char[] best = candidats.get(i);
            if(puntuacioParaula(millor)<puntuacioParaula(best)){
                millor = best;
            }

        }

         */

        return millor;
    }

    private int puntuacioParaula(char[] paraula) {
        int total = 0;
        for (int i = 0; i < paraula.length; i++) {
            int valor = paraula[i];
            total += valor;
        }

        return total;
    }




}
