//Øving 2 - Sortering - ALTERNATIV 1

import java.util.Random;

public class QuickSortPivot {

    public static void main(String[] args) {
        int n = 1000;
        int[] arr = new int[n];

        System.out.println("Sum før: " + sumArray(fyllArray(arr, n)));
        //printArray(arr);

        //quickSort(arr, 0, n-1); //Sorterer arrayet før tidtakingen

        long startTid = System.nanoTime();
        quickSort(arr, 0, n-1);
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

    private static void quickSort(int[] arr, int minst, int storst) {
        if (minst < storst) { //Hvis mindre element er mindre enn større element så finner man en ny pivot og kjører rekursjon
            int pivot = oppdeling(arr, minst, storst);
            quickSort(arr, minst, pivot-1);
            quickSort(arr, pivot+1, storst);
        }
    }

    private static int oppdeling(int[] arr, int minst, int storst) {
        int pivot = arr[storst]; //Setter pivot lik verdien til element på siste plass i arrayet
        int i = (minst-1); //Indeks av et mindre nr
        for (int j = minst; j < storst; j++) {
            if (arr[j] < pivot) { //Hvis elementet er mindre enn pivot så bytter arr[i] og arr[j] plass
                i++;
                int midlertidig = arr[i]; //Lager en backup for arr[i] slik at arr[j] bytter med rett arr[i]
                arr[i] = arr[j];
                arr[j] = midlertidig;
            }
        }

        // Bytte arr[i+1] og arr[storst]
        int midlertidig = arr[i+1];
        arr[i+1] = arr[storst];
        arr[storst] = midlertidig;

        return i+1;
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
