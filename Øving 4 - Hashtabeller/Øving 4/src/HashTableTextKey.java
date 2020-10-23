import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashTableTextKey {
    public static void main(String[] args){

        HashTable hashTable = new HashTable(97);

        try{
            File file = new File("C:\\Users\\scott\\OneDrive\\2NTNU\\Semester 1\\Algoritmer og datastrukturer\\Øvinger\\Øving 4 - Hashtabeller\\navn.txt");
            Scanner scanner = new Scanner(file);
            int students = 0;
            int collisions = 0;
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                int unicodeKey = hashTable.getUnicodeKey(data);
                int bucketIndex = hashTable.getHashKey(unicodeKey);
                boolean collision = hashTable.checkCollision(bucketIndex, data);
                if(collision){
                    collisions++;
                }
                else{
                    hashTable.add(bucketIndex, data);
                }
                students++;
            }
            System.out.println("\nAmount of students: " + students);
            System.out.println("Amount of collisions: " + collisions + "\n");

            String studentData = "Scott Rydberg,Sonen";
            Node student = hashTable.getStudent(studentData);

            if(student != null){
                System.out.println("Student '" + studentData + "' was found. Index: " + hashTable.getIndex(studentData));
            }
            else{
                System.out.println("Could not find '" + studentData + "'");
            }

            int elementsAmount = hashTable.getAmountOfElements();
            double lastfaktor = (double) elementsAmount/hashTable.getSize();
            System.out.println("Lastfaktor: " + lastfaktor);
        }
        catch(FileNotFoundException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}
