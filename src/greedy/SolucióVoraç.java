package greedy;

import estructura.Encreuades;
import estructura.PosicioInicial;
import java.util.Arrays;

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
        this.sol = repte.getPuzzle();
        this.sol = greedy();

    }

    public char[][] getSolucio() { return sol; }

    private char[][] greedy(){
        // mireu l'esquema utilitzat per la professora Lina
        // si voleu podeu modificar la capçalera d'aquest mètode
        // si voleu podeu utilitzar recursivita

        char[][] paraules = getItems();

        for (int k = 0; k < repte.getEspaisDisponibles().size(); k++) {
            seleccionar(paraules, repte.getEspaisDisponibles().get(k));

        }
        return sol;
    }

    private void seleccionar(char[][] paraules, PosicioInicial pos){ //TODO

        if(!esSolucio()) {
            int row = pos.getInitRow();
            int col = pos.getInitCol();
            char direccioo = pos.getDireccio();
            char[] p = laMillorParaula(paraules, pos); //Miro la millor paraula que es pogui posar segons la PosicioInicial que li paso

            if (!Arrays.equals(p, new char[]{'0'})) { //Si ha agafat una paraula...

                //Anotem segons la direcció
                if(direccioo == 'V') {
                    for (int j = 0; j < p.length; j++) {
                        sol[row + j][col] = p[j];
                    }

                } else {
                    for (int j = 0; j < p.length; j++) {
                        sol[row][col + j] = p[j];
                    }
                }

                //Eliminem el primer caràcter de la taula de paraules per a no contar-la després
                boolean trobat = false;
                for (int i = 0; i < paraules.length && !trobat; i++) {
                    if (paraules[i] == p) {
                        trobat = true;
                        paraules[i][0] = ' ';
                    }
                }
            }
        }
    }

    private char[][] getItems() {
        char[][] items = new char[repte.getItemsSize()][];
        for (int i = 0; i < repte.getItemsSize(); i++) {
            items[i] = repte.getItem(i);
        }
        return items;
    }

    private boolean esPotPosar(PosicioInicial pos, char[] p) {

        char dir = pos.getDireccio();
        int fila = pos.getInitRow();
        int col = pos.getInitCol();

        switch (dir) {
            case 'V':
                for(int i = 0;i<p.length;i++) {
                    if (col + 1 < sol[fila].length) {
                        if (sol[fila+i][col + 1] != ' ' && sol[fila+i][col + 1] != '▪')
                            if(sol[fila+i][col] != p[i]) //Si coincideixen les lletres es pot posar de moment
                                return false; //Si no no
                    }
                    if (col - 1 >= 0) {
                        if (sol[fila+i][col - 1] != ' ' && sol[fila+i][col - 1] != '▪') //Si és una lletra
                            if(sol[fila+i][col] != p[i]) //Si coincideixen les lletres es pot posar de moment
                                return false; //Si no no
                    }
                }
                break;
            default:

                for(int i = 0;i<p.length;i++) {
                    if (fila + 1 < sol.length) {
                        if (sol[fila + 1][col + i] != ' ' && sol[fila+1][col +i] != '▪')
                            if(sol[fila][col+i] != p[i]) //Si coincideixen les lletres es pot posar de moment
                                return false; //Si no no
                    }
                    if (fila - 1 >= 0) {
                        if (sol[fila - 1][col + i] != ' ' && sol[fila-1][col +i] != '▪') //Si és una lletra
                            if(sol[fila][col+i] != p[i]) //Si coincideixen les lletres es pot posar de moment
                                return false; //Si no no
                    }
                }
        }
        return true;
    }

    private boolean esSolucio() {
        for (int i = 0; i < sol.length; i++) {
            for (int j = 0; j < sol[i].length; j++) {
                if (sol[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Dóna la millor paraula segons la posició ( si n'hi ha ) i si es pot posar o no
    private char[] laMillorParaula(char[][] paraules, PosicioInicial pos){

        char[] millor = {'0'};
        int capacitat = pos.getLength();

        //Mirem les paraules que hi poden caber dins de la posició
        for (int i = 0; i < paraules.length; i++) {
            char[] possible = paraules[i];
            if(paraules[i].length == capacitat && paraules[i][0] != ' ') {

                //Mirem si és millor o no la possible candidata i si es pot posar
                if (puntuacioParaula(millor) < puntuacioParaula(possible) && esPotPosar(pos,possible)) {
                    millor = possible;
                }
            }
        }
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
