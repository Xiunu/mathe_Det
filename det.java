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
    }

    //Swap one line with another and return the array
    private static double[][] swapLines(int lineSwap1, int lineSwap2, double[][] arr) {
        double[] tempSwap = arr[lineSwap2];
        arr[lineSwap2] = arr[lineSwap1];
        arr[lineSwap1] = tempSwap;
        return arr;
    }

    //Berechnung mit 1. Normalform
    public static double calcDet(double[][] arr) {
        int nCount = arr.length;
        //multiplier which needs to be applied to the det;
        double curMultiplier = 1;
        double gDet = 1;
        //Iterate over all lines
        for (int curGaussStep = 0; curGaussStep < nCount; curGaussStep++) {

            // hier eigentlich erst mal checken was denn so eine possible line ist -- und noch ein bissel error catching (ist nextLine !=-1 )
            int nextLine = findNextpossibleLine(curGaussStep, arr);

            if (nextLine == -1) {
                //herzlichen glueckwunsch das ist ein deadend!
                System.out.println("Geht nicht, weil gibts nicht! (konnte keine passende zeile finden)");
                return Double.NaN;
            } else if (nextLine != curGaussStep) {
                arr = swapLines(curGaussStep, nextLine, arr);
            }

            //so hier muss es jetzt einen validen wert haben!
            //also jetzt:
            //set currently operating line in currently operating column to 1 for easier operation

            curMultiplier = curMultiplier * arr[curGaussStep][curGaussStep]; //apply cur Value to multiplier
            double localMulti = arr[curGaussStep][curGaussStep]; //set localMultiplier to make the first element ( !=0 ) -> 1
            for (int s = 0; s < nCount; s++) {
                arr[curGaussStep][s] = arr[curGaussStep][s] / localMulti; //apply the multiplier to the whole line
            }

            // setting every line with a higher index than curGaussStep to zero in currently operating column by line wise subtraction
            for (int z = curGaussStep + 1; z < nCount; z++) {
                double locMultiplier = arr[z][curGaussStep];
                for (int s = curGaussStep; s < nCount; s++) {
                    arr[z][s] = arr[z][s] - locMultiplier * arr[curGaussStep][s];
                }
            }
        }
        for (int i = 0; i < nCount; i++) {
            gDet *= arr[i][i];
        }
        System.out.println(gDet * curMultiplier);
        /* Printe das array
        for (int x=0; x < nCount; x++){
            String pS = "";
            for (int y=0; y < nCount; y++){
                pS = pS + arr[x][y] + " ";
            }
            System.out.println(pS);
        }
        */
        return gDet * curMultiplier;
    }

    private static double sarrusDet2x2(double[][] arr) {

        return ((arr[0][0] * arr[1][1]) - (arr[1][0] * arr[0][1]));
    }

    //erstellt eine untermatrix ohne col und row
    private static double[][] erstelleUntermatrix(double[][] arr, int col, int row)
    {
        //loesung inspiriert durch: https://dotnet-snippets.de/snippet/determinante-berechnen/1055
        // erstelle
        double[][] uMatrix = new double[arr.length-1][arr.length-1];

        //Da man jeweils eine bestimmte Zeile und spalte ausschließt, besteht die neue Matrix aus maximal 4 Bereichen
        // der alten Matrix. Diese vier Bereiche werden jetzt zusammengesetzt

        //Erstes Segment
        for (int y = 0; y < col; y++)
        {
            for (int x = 0; x < row; x++)
                //Quasi die obere linke Ecke der Matrix
                uMatrix[x][y] = arr[x][y];
        }
        //Zweites Segment
        for (int y = (col+1); y < arr.length; y++)
        {
            for (int x = 0; x < row; x++)
                //obere rechte Ecke der Matrix
                uMatrix[x][y - 1] = arr[x][y];
        }
        //Drittes Segment
        for (int y = 0; y < col; y++)
        {
            for (int x = (row + 1); x < arr.length; x++)
                //untere linke Ecke der Matrix
                uMatrix[x - 1][y] = arr[x][y];
        }
        //Viertes Segment
        for (int y = (col + 1); y < arr.length; y++)
        {
            for (int x = (row+1); x < arr.length; x++)
                //untere rechte Ecke der Matrix
                uMatrix[x - 1][y - 1] = arr[x][y];
        }

        return uMatrix;
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
            {
                //Rekrusiver Aufruf mit übergabe neuer Matrix ohne oberste (0te) Zeile und i-ter Spalte
                ret_det += Math.pow(-1,i) * arrRec[0][i] * calcDetRec(erstelleUntermatrix(arrRec, i, 0));
            }

            return ret_det;
        }
    }
