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

        /*
        funció voraç (conjunt_candidats C ) retorna
        conjunt_solució S
            S= null
            mentre (!solucio(S) && C!= null)
                X=seleccio_candidat_optim
                C=C-{X}
                si factible(S,X)
                    llavors S=SU{X}
                fsi
            fimentre
            si solucio(S) retorna S
            sino no hi ha solucio fsi

         */
            PosicioInicial[] posicions = repte.getEspaisDisponibles().toArray(new PosicioInicial[0]);
            char[][] paraules = getItems();

            for (PosicioInicial pos : posicions) {
                for (int i = 0; i < repte.getItemsSize(); i++) {
                    System.out.println("Trying to select word at position: " + pos + " AT I: "+i);
                    seleccionar(paraules, pos);
                }
            }
            return sol;


    }

    private void printTable(char[][] table) {
        for (char[] row : table) {
            System.out.println(Arrays.toString(row));
        }
    }

    /* A aquesta classe
     * podeu definir els mètodes privats que vulgueu
     */

    private void seleccionar(char[][] paraules, PosicioInicial pos){ //TODO

        if(!esSolucio()) {
            int row = pos.getInitRow();
            int col = pos.getInitCol();
            //int length = pos.getLength();
            char direccioo = pos.getDireccio();
            char[] p = laMillorParaula(paraules, pos);




            if (!Arrays.equals(p, new char[]{'0'})) { //Si ha agafat una paraula...
                System.out.println("ESTEM POSANT LA PARAULA: "+ Arrays.toString(p).replace("[","").replace("]","").replace(",",""));

                boolean noespot = false;

                //Anotem segons la direcció
                if(direccioo == 'V') {
                    for (int j = 0; j < p.length && !noespot; j++) {
                        if (esPotPosar(row+j,col,direccioo)) {
                            sol[row+j][col] = p[j];
                        } else
                            noespot = true;
                    }
                } else {
                    for (int j = 0; j < p.length && !noespot; j++) {
                        if (esPotPosar(row,col+j,direccioo)) {
                            sol[row][col+j] = p[j];
                        } else
                            noespot = true;
                    }

                }

                //ELIMINEM LA PARAULA SI S'HA POSAT TODO
                if(!noespot) {
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
        printTable(sol);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        //return false;
    }

    private char[][] getItems() {
        char[][] items = new char[repte.getItemsSize()][];
        for (int i = 0; i < repte.getItemsSize(); i++) {
            items[i] = repte.getItem(i);
        }
        return items;
    }

    private boolean esPotPosar(int fila, int col, char dir) {

        switch (dir) {
            case 'V':
                System.out.println("Es vertical");
                if(col+1 < sol[fila].length) {
                    if (sol[fila][col + 1] != ' ' && sol[fila][col + 1] != '▪')
                        return false;
                }
                if(col-1 >= 0) {
                    if(sol[fila][col-1] != ' ' && sol[fila][col-1] != '▪')
                        return false;
                }
                break;
            default:
                if(fila+1 < sol.length) {
                    if (sol[fila + 1][col] != ' ' && sol[fila + 1][col] != '▪')
                        return false;
                }
                if(fila-1 >= 0) {
                    if(sol[fila-1][col] != ' ' && sol[fila-1][col] != '▪')
                        return false;
                }

                break;
        }
        return true;
    }

    /*
    private boolean esPotPosar(int fila, int col, int len, char dir, char[] paraula) {
    switch (dir) {
        case 'H':
            if (col + len > sol[0].length) return false; // Check horizontal bounds
            for (int i = 0; i < len; i++) {
                if (sol[fila][col + i] != ' ' && sol[fila][col + i] != '▪') {
                    if (sol[fila][col + i] != paraula[i]) {
                        return false;
                    }
                }
            }
            break;
        case 'V':
            if (fila + len > sol.length) return false; // Check vertical bounds
            for (int i = 0; i < len; i++) {
                if (sol[fila + i][col] != ' ' && sol[fila + i][col] != '▪') {
                    if (sol[fila + i][col] != paraula[i]) {
                        return false;
                    }
                }
            }
            break;
        default:
            return false;
    }
    return true;
}
     */

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



    // Dóna la millor paraula segons la posició ( si n'hi ha )
    private char[] laMillorParaula(char[][] paraules, PosicioInicial pos){

        System.out.println("ENTREM AMB UNA POS = ROW: "+pos.getInitRow()+", COL: "+pos.getInitCol()+" LENGTH: "+pos.getLength());

        System.out.println("Taula de paraules actual: ");
        for (char[] paraule : paraules) {
            System.out.println(Arrays.toString(paraule) + " length: "+paraule.length);
        }
        System.out.println("");

        char[] millor = {'0'};
        int capacitat = pos.getLength();
        int cont = 0;

        //Mirem les paraules que hi poden caber dins de la posició
        for (int i = 0; i < paraules.length; i++) {
            char[] possible = paraules[i];

            for (int j = 0; j < paraules[i].length; j++) {
                if(paraules[i][j] != ' '){
                    cont++;
                } else
                    j = paraules[i].length;

                //Ja ha arribat a formar una paraula que hi pot caber dins de l'ubicació
                System.out.println("MIREM SI HEM TROBAT UNA PARAULA AMB LA CAPACITAT CORRECTE AMB J = "+j);
                System.out.println("COUNT = "+cont+", CAPACITAT = "+capacitat);//+", Y paraules[i].length-1 = "+(paraules[i].length-1));
                if(cont == capacitat){ //&& capacitat == paraules[i].length-1){
                    //System.out.println("COUNT = "+cont+", CAPACITAT = "+capacitat+", Y paraules[i].length-1 = "+(paraules[i].length-1));
                    System.out.println("MIREM SI LA MILLOR QUE TENIM: "+ Arrays.toString(millor) +" ÉS MILLOR QUE LA CANDIDATA: "+ Arrays.toString(possible));

                    //Mirem si és millor o no la possible candidata
                    if(puntuacioParaula(millor)<puntuacioParaula(possible)){
                        millor = possible;
                    }

                }
            }
            cont = 0;
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
