//Oppgave 2 - Øving 4 Hashtabeller

public class HashTable2 {
    private int[] hashTable;
    private int elementCount = 0;
    private int collisionCount = 0;

    public HashTable2(int size){
        this.hashTable = new int[size];
    }

    public int[] getHashTable() {
        return hashTable;
    }

    public int getElementCount() {
        return elementCount;
    }

    public int getCollisionCount() {
        return collisionCount;
    }

    public int getArraySize(){
        return hashTable.length;
    }

    //Returns preferred index position
    public int hashFunc1(int value){
        int hashValue = value % getArraySize();
        return (hashValue == 0) ? 1 : hashValue; //The ideal index position we would like to insert
    }

    //Returns step size
    public int hashFunc2(int value){
        int hashValue = (749993 - (value % 749993));
        if(hashValue == 0 || hashValue == getArraySize()){
            hashValue = (452953 - (value % 452953));
        }
        return hashValue; //Using a prime number which is definitely smaller than the array size //452953 749993 999983 499979
    }

    public void insert(int value){
        int hashValue = hashFunc1(value);

        while(hashTable[hashValue] != 0){
            int stepSize = hashFunc2(value);
            hashValue = (hashValue + stepSize) % getArraySize();
            collisionCount++;
        }

        hashTable[hashValue] = value;
        elementCount++;
    }
}
