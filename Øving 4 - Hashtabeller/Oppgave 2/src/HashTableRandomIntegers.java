//Oppgave 2 - Øving 4 Hashtabeller

import java.util.Random;
import java.util.HashMap;

public class HashTableRandomIntegers {
    public static void main(String[] args){
        int n = 10000000; //Amount of numbers and length of random array
        HashTable2 hashTable = new HashTable2(n+19); //10000019 is the next prime number
        int[] numberArray = new int[n];
        Random random = new Random();

        for(int i = 0; i < numberArray.length; i++){
            numberArray[i] = random.nextInt(n*1000);
        }

        long startTime = System.nanoTime();
        for(int i = 0; i < numberArray.length; i++){
            hashTable.insert(numberArray[i]);
        }
        long endTime = System.nanoTime();
        long nanoTime = endTime - startTime;
        double time = (double) nanoTime/1000000000;
        System.out.println("Time consumption by self-made hash functions: " + time + " seconds");

        HashMap<Integer, Integer> hashMap = new HashMap();
        startTime = System.nanoTime();
        for(int i = 0; i < numberArray.length; i++){
            hashMap.put(hashTable.hashFunc1(numberArray[i]), numberArray[i]);
        }
        endTime = System.nanoTime();
        nanoTime = endTime - startTime;
        time = (double) nanoTime/1000000000;
        System.out.println("Time consumption by HashMap: " + time + " seconds");

        double loadFactor = (double) hashTable.getElementCount()/hashTable.getArraySize();
        System.out.println("Amount of elements in hashtable: " + hashTable.getElementCount() + "\nLoad factor: " + loadFactor);

        System.out.println("Collisions: " + hashTable.getCollisionCount());
    }
}
