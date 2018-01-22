public class det {
    public static int nrOfMult;

    private static int findNextpossibleLine(int searchColumn, double[][] arr) {
        for (int z = searchColumn; z < arr.length; z++) {
            if (arr[z][searchColumn] != 0) {
                return z;
            } else {
                throw new ArithmeticException("Seems to be impossible to resolve");
            }
        }
        return -1;
        // bei nutzung checken ob resultat -1 falls dann return NaN
        // in use case check if result is -1 than return NaN
    }

    //Tausche Zeile mit einer anderer Zeile und gebe das resultierende Array
    //Swap one line with another and return the array
    private static double[][] swapLines(int lineSwap1, int lineSwap2, double[][] arr) {
        double[] tempSwap = arr[lineSwap2];
        arr[lineSwap2] = arr[lineSwap1];
        arr[lineSwap1] = tempSwap;
        return arr;
    }

    //Berechnung mit 1. Normalform
    //Compute the first normal form
    public static double calcDet(double[][] arr) {

        // nrOfMult = n+n*n+SUMME((n-1) * n)

        nrOfMult = 0;
        int nCount = arr.length;
        //multiplier which needs to be applied to the priciple diagonal
        //multiplikator der am ende mit der Hauptdiagonelen multipliziert wird
        double curMultiplier = 1;
        double gDet = 1;

        //Iterate over all lines
        for (int curGaussStep = 0; curGaussStep < nCount; curGaussStep++) {

            // find next possible row
            // finde die nächste mögliche reihe
            int nextLine = findNextpossibleLine(curGaussStep, arr);

            //catch the possibility that there is no possible line and return NaN
            //fange die Möglichkeit ab das keine mögliche Zeile existiert und gebe NaN zurueck
            if (nextLine == -1) {
                //herzlichen glueckwunsch das ist ein deadend!
                System.out.println("Geht nicht, weil gibts nicht! (konnte keine passende zeile finden)");
                return Double.NaN;
            }

            // if the next line isn't possible swap it
            // falls mit der nächsten nicht weitergearbeitet werden kann tausche sie aus
            else if (nextLine != curGaussStep) {
                arr = swapLines(curGaussStep, nextLine, arr);
            }

            //so ab hier muss es jetzt einen validen wert haben
            //at this point value needs to be valid


            //set currently operating line in currently operating column to 1 for easier operation
            //Setze das aktuelle element auf der Hauptdiagonalen auf 1 und wende den multi auf die Komplette Zeile an

            curMultiplier = curMultiplier * arr[curGaussStep][curGaussStep]; //apply cur Value to multiplier
            nrOfMult++; // n mal audgefuehrt

            double localMulti = arr[curGaussStep][curGaussStep]; //set localMultiplier to make the first element ( !=0 ) -> 1
            for (int s = 0; s < nCount; s++) {
                arr[curGaussStep][s] = arr[curGaussStep][s] / localMulti;
                //apply the multiplier to the whole line
                nrOfMult++; // n*n
            }

            // setting every line with a higher index than curGaussStep to zero in currently operating column by line wise subtraction
            for (int z = curGaussStep + 1; z < nCount; z++) {
                double locMultiplier = arr[z][curGaussStep];
                for (int s = curGaussStep; s < nCount; s++) {
                    arr[z][s] = arr[z][s] - locMultiplier * arr[curGaussStep][s];
                    nrOfMult++; //  Summe(n-1 * n)
                }
            }
        }
        /* Printe das array
        for (int x=0; x < nCount; x++){
            String pS = "";
            for (int y=0; y < nCount; y++){
                pS = pS + arr[x][y] + " ";
            }
            System.out.println(pS);
        }
        */
        //since principal diagonal should be 1 in all values
        //da die Hauptdiagonale an allen stellen eins ist kann direkt der Multiplikator als Wert zurueck gegeben werden
        return curMultiplier;
    }

    //Regel von Sarrus fuer 2x2 Matrix
    private static double sarrusDet2x2(double[][] arr) {

        nrOfMult=nrOfMult+2;
        return ((arr[0][0] * arr[1][1]) - (arr[1][0] * arr[0][1]));
    }

    //erstellt eine untermatrix ohne col und row
    private static double[][] erstelleUntermatrix(double[][] arr, int col, int row)
    {
        //loesung inspiriert durch: https://dotnet-snippets.de/snippet/determinante-berechnen/1055

        //erstelle 2-Dimensionales Array mit einer zeile und spalte weniger als das urbergebene
        //create 2-dimensional Array with one row and col less then the overhanded array
        double[][] uMatrix = new double[arr.length-1][arr.length-1];

        //Da man jeweils eine bestimmte Zeile und spalte ausschließt, besteht die neue Matrix aus maximal 4 Bereichen
        //der alten Matrix. Diese vier Bereiche werden jetzt zusammengesetzt

        //Since one row and col get removed the new matrix can be created from the 4 resulting sectors, which will be merged here

        //Erstes Segment
        //First Sector

        //obere linke Ecke der Matrix
        //upper left corner of the matrix
        for (int y = 0; y < col; y++)
        {
            for (int x = 0; x < row; x++)
                uMatrix[x][y] = arr[x][y];
        }

        //Zweites Segment
        //Second Sector

        //obere rechte Ecke der Matrix
        //upper right corner of the matrix
        for (int y = (col+1); y < arr.length; y++)
        {
            for (int x = 0; x < row; x++)
                uMatrix[x][y - 1] = arr[x][y];
        }

        //Drittes Segment
        //Third Sector

        //untere linke Ecke der Matrix
        //lower left corner of the matrix
        for (int y = 0; y < col; y++)
        {
            for (int x = (row + 1); x < arr.length; x++)
                uMatrix[x - 1][y] = arr[x][y];
        }

        //Viertes Segment
        //Fourth Sector

        //untere rechte Ecke der Matrix
        //lower right corner of the matrix
        for (int y = (col + 1); y < arr.length; y++)
        {
            for (int x = (row+1); x < arr.length; x++)
                uMatrix[x - 1][y - 1] = arr[x][y];
        }

        return uMatrix;
    }

    // only used to set the nrOfMult only once to 0
    // holder dessen nutzen es ist die calcDetRec Methode aufzurufen und vorher die nrOfMult auf null zu setzten
    public static double calcDetRecHolder ( double[][] arrRec){
        nrOfMult = 0;
        return calcDetRec(arrRec);
    }

    //Rekursive Berechnung mit Def. L.4.1.1 Skript
    public static double calcDetRec ( double[][] arrRec){
        // anwenden von Sarrus im falle das 2x2 Matrix vorliegt
        if (arrRec.length==2)
        {
            return sarrusDet2x2(arrRec);
        }
        // Hier alles was passiert falls arrRec groesser als 2x2
        double ret_det = 0;
        //Entwickeln nach Zeile 0
        for (int i = 0; i < arrRec.length; i++)
        //n-neue Matritzen -> (n-1) Ordnung
        {
            //Rekrusiver Aufruf mit übergabe neuer Matrix ohne oberste (0te) Zeile und i-ter Spalte
            nrOfMult=nrOfMult+2;
            ret_det += Math.pow(-1,i) * arrRec[0][i] * calcDetRec(erstelleUntermatrix(arrRec, i, 0));

        }
        return ret_det;
    }
}
