//Øving 2 - Sortering - ALTERNATIV 1

import java.util.Random;

public class QuickSortDualPivot {

    public static void main(String[] args) {
        int n = 1000;
        int[] arr = new int[n];

        System.out.println("Sum før: " + sumArray(fyllArray(arr, n)));
        //printArray(arr);

        //dualPivotQuickSort(arr, 0, arr.length-1); //Sorterer arrayet før tidtakingen

        long startTid = System.nanoTime();
        dualPivotQuickSort(arr, 0, arr.length-1);
        long sluttTid = System.nanoTime();
        long tidNano = sluttTid - startTid;
        double tidMS = (double) tidNano/1000000;
        System.out.println("Tid i ms: " + tidMS);

        System.out.println("Sum etter: " + sumArray(arr));
        //printArray(arr);

        System.out.println("0 Hvis den besto rekkefølgetesten (-1 hvis ikke): " + rekkefølgeSjekk(arr));
    }

    //Sjekker om arrayet er sortert
    private static int rekkefølgeSjekk(int[] arr){
        for(int i = 0; i < arr.length-1; i++){
            if(arr[i]>arr[i+1]){
                return -1;
            }
        }
        return 0;
    }

    private static int[] fyllArray(int[] arr, int n) {
        Random random = new Random();
        for(int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(n*10); //Endrer uttrykket inne i nextInt(...) utifra om vi vil teste mange duplikater osv
        }
        return arr;
    }

    static void bytte(int[] arr, int i, int j) {
        int midlertidig = arr[i];
        arr[i] = arr[j];
        arr[j] = midlertidig;
    }

    static void dualPivotQuickSort(int[] arr, int minst, int storst) {
        if (minst < storst) {
            int[] pivotArray; //pivotArray holder på minste pivot og største pivot
            pivotArray = oppdeling(arr, minst, storst);

            dualPivotQuickSort(arr, minst, pivotArray[0] - 1);
            dualPivotQuickSort(arr, pivotArray[0] + 1, pivotArray[1] - 1);
            dualPivotQuickSort(arr, pivotArray[1] + 1, storst);
        }
    }

    static int[] oppdeling(int[] arr, int minst, int storst) {
        if (arr[minst] > arr[storst]) bytte(arr, minst, storst);

        int j = minst + 1;
        int g = storst - 1;
        int k = minst + 1;
        int p = arr[minst]; //minste pivot
        int q = arr[storst]; //største pivot

        while (k <= g) { // Hvis elementet er mindre enn minste pivot
            if (arr[k] < p) {
                bytte(arr, k, j);
                j++;
            }

            // Hvis elementet er større eller like stor som største pivot
            else if (arr[k] >= q) {
                while (arr[g] > q && k < g)
                    g--;

                bytte(arr, k, g);
                g--;

                if (arr[k] < p) {
                    bytte(arr, k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;

        // Flytter pivotene til en rett plass
        bytte(arr, minst, j);
        bytte(arr, storst, g);

        return new int[] { j, g };
    }

    private static int sumArray(int[] arr) { //Finner summen av alle elementene i arrayen
        int sum = 0;
        for(int verdi : arr) sum += verdi;
        return sum;
    }

    private static void printArray(int[] arr) {
        for (int verdi : arr) System.out.print(verdi + " ");
        System.out.println();
    }
}
