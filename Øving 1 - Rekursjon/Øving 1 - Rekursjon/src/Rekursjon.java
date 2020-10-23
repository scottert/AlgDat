//Øving 1

public class Rekursjon {
    public static void main(String[] args){
        double x = 1.001;
        int n = 5000;

        //Oppgave 1 (oppgave 2.1-1) --> Program 1
        long starttid = System.nanoTime();
        System.out.println(findPower(x, n));
        long sluttid = System.nanoTime();
        long tidNano = (sluttid - starttid);
        double tidMS = (double) tidNano/1000000;
        System.out.println("Tid i ms: " + tidMS);

        //Oppgave 2 (oppgave 2.2-3) --> Program 2
        starttid = System.nanoTime();
        System.out.println(findPower2(x, n));
        sluttid = System.nanoTime();
        tidNano = sluttid - starttid;
        tidMS = (double) tidNano/1000000;
        System.out.println("Tid i ms: " + tidMS);

        //Oppgave 3 --> Math.pow()
        starttid = System.nanoTime();
        System.out.println(Math.pow(x, n));
        sluttid = System.nanoTime();
        tidNano = sluttid - starttid;
        tidMS = (double) tidNano/1000000;
        System.out.println("Tid i ms: " + tidMS);
    }

    private static double findPower(double x, int n){
        if(n == 0){
            return 1;
        }
        return (x * findPower(x, n-1));
    }

    private static double findPower2(double x, int n){
        if(n == 0){
            return 1;
        }
        else if(n % 2 == 0){
            return (findPower2(x*x, (n/2)));
        }
        else{
            return (x * findPower2(x*x, (n-1)/2));
        }
    }

    //NB: Programmet fungerer ikke for n < 0
}
