//Oppgave 1 - Øving 4 Hashtabeller

public class HashTable {
    private Node[] hashTable;

    public HashTable(int size) {
        this.hashTable = new Node[size];
    }

    public HashTable(Node[] array){
        this.hashTable = array;
    }

    public Node[] getHashTable() {
        return hashTable;
    }

    public String getData(int key){
        return hashTable[key].getData();
    }

    public int getIndex(String data){
        return getUnicodeKey(data) % hashTable.length;
    }

    public int getSize(){
        return hashTable.length;
    }

    public int getUnicodeKey(String data){
        int unicodeKey = 0;
        for(int i = 0; i < data.length(); i++){
            int letterValue = data.charAt(i); //Convert char to int value
            unicodeKey += letterValue; //Adding all values to a key, which we will use as a key in the HashTable
        }
        return unicodeKey;
    }

    public int getHashKey(int key){
        return key % hashTable.length;
    }

    public void add(int key, String data){
        hashTable[key] = new Node(data);
    }

    public boolean checkCollision(int key, String data){
        if(hashTable[key] != null){
            handleCollision(key, data);
            return true;
        }
        return false;
    }

    private void handleCollision(int key, String data){
        Node prevNode = hashTable[key];
        while(prevNode.getNext() != null){
            prevNode = prevNode.getNext();
        }
        Node currentNode = new Node(data);
        System.out.println("COLLISION at index " + key + " --> Prev Node: " + prevNode.getData() + " - Current Node: " + currentNode.getData());
        prevNode.setNext(currentNode); //Adding the current node as a Node next for the head node of array index "key"
    }

    public Node getStudent(String data){
        int key = getIndex(data);
        Node node = hashTable[key];
        while(node != null){
            if(data.equals(node.getData())){
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    public int getAmountOfElements(){
        int count = 0;
        for(Node node : hashTable){
            if(node != null){
                count++;
            }
        }
        return count;
    }
}
