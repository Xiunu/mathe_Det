public class det {
    public static int nrOfMult;

    private static int findNextpossibleLine(int searchColumn , double[][] arr){
        for (int z = searchColumn; z < arr.length; z++) {
            if(arr[z][searchColumn]!= 0) {
                return z;
            }
            else{
                throw new ArithmeticException("Seems to be impossible to resolve");
            }
        }
        return -1;
        // bei nutzung checken ob resultat -1 falls dann return NaN
    }

    //Swap one line with another and return the array
    private static double[][] swapLines (int lineSwap1 , int lineSwap2, double[][] arr){
        double[] tempSwap = arr[lineSwap2];
        arr[lineSwap2] = arr[lineSwap1];
        arr[lineSwap1] = tempSwap;
        return arr;
    }

    //Berechnung mit 1. Normalform
    public static double calcDet(double[][] arr) {
        int nCount= arr.length;
        //multiplier which needs to be applied to the det;
        double curMultiplier=1;
        double gDet=0;
        //Iterate over all lines
        for(int curGaussStep=0; curGaussStep<nCount;curGaussStep++) {
            //set currently operating line in currently operating column to 1 for easier operation

            // hier eigentlich erst mal checken was denn so eine possible line ist -- und noch ein bissel error catching (ist nextLine !=-1 )
            int nextLine = findNextpossibleLine(curGaussStep,arr);

            if(nextLine == -1){
                //herzlichen glueckwunsch das ist ein deadend!
                System.out.println("Geht nicht, weil gibts nicht! (konnte keine passende zeile finden)");
                return Double.NaN;
            }
            else if(nextLine!=curGaussStep){
                arr = swapLines(curGaussStep, nextLine, arr);
            }
            
            if(arr[curGaussStep][curGaussStep]!= 0){ //Check if Value isn't 0 since multiplication with 0 makes everything 0...
                curMultiplier = curMultiplier * arr[curGaussStep][curGaussStep]; //apply cur Value to multiplier
                double localMulti = arr[curGaussStep][curGaussStep]; //set localMultiplier to make the first element ( !=0 ) -> 1
                for (int s = 0; s < nCount; s++) {
                    arr[curGaussStep][s] = arr[curGaussStep][s] / localMulti; //apply the multiplier to the whole line
                }
            }
            // Was ist zu tun falls das element schon 0 ist -> line swap? (und mit welcher -> einfach mit der naechsten bis es nicht mehr weiter geht) / vlt irgendwas besseres...
            else if((curGaussStep + 1) < nCount){
                // swapping with next line, not pretty good since it would now loop...
                // need to iterate over the whole array and find a line fitting the purpose
                double[] tempSwap = arr[curGaussStep+1];
                arr[curGaussStep+1] = arr[curGaussStep];
                arr[curGaussStep] = tempSwap;
            }
            //wieder nach oben, da der obere Prozess dann ja auch ausgefuehrt werden muss oder funktion ausloesen und einfach nochmal aufrufen...
            System.out.println("New arr (curGaussStep="+ curGaussStep +")!");
            // setting every line with a higher index than curGaussStep to zero in currently operating column by line wise subtraction
                for (int z = curGaussStep + 1; z < nCount; z++) {
                    if(arr[z][curGaussStep]!= 0) {
                        double locMultiplier = arr[z][curGaussStep];
                        for (int s = curGaussStep; s < nCount; s++) {
                            arr[z][s] = arr[z][s] - locMultiplier * arr[curGaussStep][s];
                            System.out.println("[" + z + "] [" + s + "]: " + arr[z][s]);
                        }
                    }
                    else
                        System.out.println("value at this point is 0");
                        //zu spaet... Ansatz muss nun eine gleichung mit z>curGaussStep+1 waehlen und das dingsi dann als loop bis ein match gefunden wurde
                }

        }
        for(int i=0; i<nCount;i++) {
            gDet+=arr[i][i];
        }
        System.out.println(gDet*curMultiplier);
        return gDet*curMultiplier;
    }

    //Rekursive Berechnung mit Def. L.4.1.1 Skript
    public static double calcDetRec(double[][] A){
        return Double.NaN; // Durch Ihren Code ersetzen!
    }
}