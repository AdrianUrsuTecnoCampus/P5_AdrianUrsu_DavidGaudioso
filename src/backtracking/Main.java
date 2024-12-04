package backtracking;

import estructura.Encreuades;

public class Main {
    public static void main(String[] args) {
        char[][] puzzle = {
                {' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	'▪',	' ',	' ',	' ',	' ',	' '},
                {'▪',	'▪',	' ',	'▪',	'▪',	' ',	'▪',	' ',	'▪',	' ',	'▪',	'▪',	'▪',	' '},
                {' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	'▪',	'▪',	'▪',	' '},
                {' ',	'▪',	' ',	'▪',	'▪',	' ',	'▪',	' ',	'▪',	' ',	' ',	' ',	' ',	' '},
                {' ',	' ',	' ',	'▪',	'▪',	' ',	'▪',	' ',	'▪',	' ',	'▪',	' ',	'▪',	' '},
                {' ',	'▪',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	'▪',	' '},
                {' ',	'▪',	'▪',	' ',	'▪',	' ',	'▪',	'▪',	'▪',	' ',	'▪',	' ',	'▪',	' '},
                {' ',	' ',	' ',	' ',	'▪',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	' '},
                {' ',	'▪',	'▪',	' ',	'▪',	'▪',	'▪',	' ',	'▪',	' ',	'▪',	' ',	'▪',	' '},
                {' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	'▪',	' ',	'▪',	' ',	'▪',	'▪'},
                {' ',	'▪',	'▪',	' ',	'▪',	'▪',	'▪',	' ',	' ',	' ',	' ',	' ',	' ',	' '},
                {' ',	' ',	' ',	' ',	' ',	' ',	' ',	' ',	'▪',	'▪',	'▪',	' ',	'▪',	' '},
                {' ',	'▪',	'▪',	' ',	'▪',	'▪',	' ',	'▪',	'▪',	'▪',	'▪',	' ',	' ',	' '},
                {' ',	'▪',	'▪',	' ',	'▪',	'▪',	' ',	' ',	' ',	' ',	' ',	' ',	'▪',	' '},
                {' ',	' ',	' ',	' ',	' ',	' ',	' ',	'▪',	'▪',	'▪',	'▪',	' ',	' ',	' '}
        };
        char[][] valors = {
                "CONTEMPLAREIS".toCharArray(),
                "ORDENANCISMO".toCharArray(),
                "DESVINCULAR".toCharArray(),
                "APELOTONAD".toCharArray(),
                "COMPETIAIS".toCharArray(),
                "PANTOGRAFO".toCharArray(),
                "AUTOCTONO".toCharArray(),
                "MEZQUINEN".toCharArray(),
                "LINOLEUM".toCharArray(),
                "PATAPLUM".toCharArray(),
                "RETRANCA".toCharArray(),
                "ZOLLIPAS".toCharArray(),
                "PERECIA".toCharArray(),
                "SACONEA".toCharArray(),
                "CUORUM".toCharArray(),
                "LIMOSA".toCharArray(),
                "STABAT".toCharArray(),
                "ACLLA".toCharArray(),
                "DALLA".toCharArray(),
                "VIOLO".toCharArray(),
                "ZAMPA".toCharArray(),
                "CUCA".toCharArray(),
                "MEAN".toCharArray(),
                "NOS".toCharArray(),
                "ODA".toCharArray(),
                "SOL".toCharArray()
        };
        Encreuades exemple = new Encreuades(puzzle,valors);

        SolucioBacktracking sol = new SolucioBacktracking(exemple);
        try {
            sol.start(false);
        }catch (Exception e){
            System.out.println("ERROR ERROR EN LA 57 MAIN: ");
            e.printStackTrace();
        }

        char [][] trobat = sol.getMillorSolucio();

        char[][] solucio = {
                {	'Z',	'O',	'L',	'L',	'I',	'P',	'A',	'S',	'▪',	'D',	'A',	'L',	'L',	'A',},
                {	'▪',	'▪',	'I',	'▪',	'▪',	'A',	'▪',	'T',	'▪',	'E',	'▪',	'▪',	'▪',	'U',},
                {	'C',	'O',	'M',	'P',	'E',	'T',	'I',	'A',	'I',	'S',	'▪',	'▪',	'▪',	'T',},
                {	'O',	'▪',	'O',	'▪',	'▪',	'A',	'▪',	'B',	'▪',	'V',	'I',	'O',	'L',	'O',},
                {	'N',	'O',	'S',	'▪',	'▪',	'P',	'▪',	'A',	'▪',	'I',	'▪',	'R',	'▪',	'C',},
                {	'T',	'▪',	'A',	'P',	'E',	'L',	'O',	'T',	'O',	'N',	'A',	'D',	'▪',	'T',},
                {	'E',	'▪',	'▪',	'A',	'▪',	'U',	'▪',	'▪',	'▪',	'C',	'▪',	'E',	'▪',	'O',},
                {	'M',	'E',	'A',	'N',	'▪',	'M',	'E',	'Z',	'Q',	'U',	'I',	'N',	'E',	'N',},
                {	'P',	'▪',	'▪',	'T',	'▪',	'▪',	'▪',	'A',	'▪',	'L',	'▪',	'A',	'▪',	'O',},
                {	'L',	'I',	'N',	'O',	'L',	'E',	'U',	'M',	'▪',	'A',	'▪',	'N',	'▪',	'▪',},
                {	'A',	'▪',	'▪',	'G',	'▪',	'▪',	'▪',	'P',	'E',	'R',	'E',	'C',	'I',	'A',},
                {	'R',	'E',	'T',	'R',	'A',	'N',	'C',	'A',	'▪',	'▪',	'▪',	'I',	'▪',	'C',},
                {	'E',	'▪',	'▪',	'A',	'▪',	'▪',	'U',	'▪',	'▪',	'▪',	'▪',	'S',	'O',	'L',},
                {	'I',	'▪',	'▪',	'F',	'▪',	'▪',	'C',	'U',	'O',	'R',	'U',	'M',	'▪',	'L',},
                {	'S',	'A',	'C',	'O',	'N',	'E',	'A',	'▪',	'▪',	'▪',	'▪',	'O',	'D',	'A',}
        };

        System.out.println(solucio[0][0] == trobat[0][0]);
    }
}
