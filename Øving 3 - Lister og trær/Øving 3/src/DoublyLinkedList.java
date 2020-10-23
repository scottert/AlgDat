import java.math.BigInteger;
import java.util.Scanner;

public class DoublyLinkedList {
    Node head;

    class Node{
        int data;
        Node prev;
        Node next;

        Node(int data){
            this.data = data;
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        DoublyLinkedList dll1 = new DoublyLinkedList();
        DoublyLinkedList dll2 = new DoublyLinkedList();
        System.out.println("Input a big number: ");
        String nr1 = scanner.nextLine();
        BigInteger bigI1 = new BigInteger(nr1);
        dll1.splitNumber(dll1, nr1);

        System.out.println("Input another big number:");
        String nr2 = scanner.nextLine();
        BigInteger bigI2 = new BigInteger(nr2);
        dll2.splitNumber(dll2, nr2);

        DoublyLinkedList additionList = addition(dll1, dll2);
        System.out.println("\nBigInteger addition method: " + bigI1.add(bigI2));
        System.out.println("Addition list/number:       " + additionList.display()); //Displaying the sum

        //NB: If nr2 is bigger, the program will basically fail because resultList is not able to hold a negative value.
        //Therefore, to ensure that there are no errors, the program checks if nr1 is bigger than nr2.
        //If it is, the boolean will be true, and if not, the boolean will be false.
        //You can see in subtraction, how the different values are initialized.
        boolean nr1BiggerThanNr2;
        if(bigI1.compareTo(bigI2) >= 0){
            nr1BiggerThanNr2 = true;
            System.out.println("\nBigInteger subtraction method: " + bigI1.subtract(bigI2));
        }
        else{
            nr1BiggerThanNr2 = false;
            System.out.println("\nBigInteger subtraction method: " + bigI2.subtract(bigI1));
        }
        DoublyLinkedList subtractionList = subtraction(dll1, dll2, nr1BiggerThanNr2);
        System.out.println("Subtraction list/number:       " + subtractionList.display()); //Displaying the difference
    }

    private DoublyLinkedList splitNumber(DoublyLinkedList dll, String tall){
        String[] numberArray = tall.split("");
        for(String s : numberArray){
            dll.addToHead(Integer.parseInt(s));
            //You can change addToHead() to addToTail() if you want to display the number in the right order.
            //addToHead() adds the next node at the front
            //If we use dll1.display() or dll2.display() in main after splitting number,
            // the method will output the complete reverse of the list you want to display.
            //By using the reversed list, we can add the doubly linked lists in a more efficient way
        }
        return dll;
    }

    private int getLength(){
        int count = 0;
        Node n = head;
        while(n != null){
            n = n.next;
            count++;
        }
        return count;
    }

    private void addToHead(int element) {
        Node node = new Node(element);
        node.next = head; //The next node is now the head
        node.prev = null; //Changing the previous node to null

        if(head != null){ //The previous node before the head is the new node
            head.prev = node;
        }

        head = node; //Move the head node so it points to the new node
    }

    private void addToTail(int element) {
        Node node = new Node(element);
        Node tail = head;
        node.next = null;

        if(head == null) { //Checking if head is null (AKA if the list is empty)
            node.prev = null;
            head = node;
            return;
        }

        while(tail.next != null) { //Checking if each element is the tail node
            tail = tail.next;
        }

        tail.next = node; //The new node is made the tail node of the list

        node.prev = tail; //Changing the previous tail node to the previous node of the new tail node
    }

    private static DoublyLinkedList addition(DoublyLinkedList dll1, DoublyLinkedList dll2){
        DoublyLinkedList resultList = new DoublyLinkedList();
        makeSameSize(dll1, dll2); //To make the lists the same size, we use makeSameSize() method

        Node p = dll1.head;
        Node q = dll2.head;
        int addition = 0;
        int dividedBy10 = 0;
        int remainder = 0;
        int carry = 0;

        //While the nodes are not null
        //We don't actually need both p and q in the statement,
        // since we already have made the lists the same size.
        while(p != null && q != null){
            //Adds digit from p.data and q.data, and also adds a carry digit
            // (which will be value 1 in cases where the sum of the single digits is greater than 9
            addition = p.data + q.data + carry;
            dividedBy10 = (addition / 10);
            //Example: addition = 3+4 = 7 --> dividedBy10 is 7/10 = 0
            //If dividedBy10 is NOT zero,
            // the remainder will be added to the resultList
            // as a single digit of a larger result number.
            //Example: if addition = 12 --> dividedBy10 = 12/10 = 1
            if(dividedBy10 != 0){
                remainder = addition % 10; //Remainder is the addition mod 10. If addition is 12, we get that the remainder is 2
                carry = dividedBy10; //The carry integer is 1, since 12/10 = 1
                resultList.addToHead(remainder);
            }
            else{ //If there is no carry integer from the addition, we add the whole addition as a single digit
                carry = 0;
                resultList.addToHead(addition);
            }
            //In the end we want to check if carry is NOT zero, and add it as a single digit to the result list (if it is NOT zero)
            //Why? Because if we don't do it, and we are trying to perform f.ex: 9 + 9,
            // the 1 digit will not be added to the result list. Only the 8 digit.
            //Therefore, we also add the carry as a single digit in the result list.
            if(p.next == null && q.next == null && carry != 0){
                resultList.addToHead(carry);
            }
            p = p.next;
            q = q.next;
        }

        return resultList;
    }

    private static DoublyLinkedList subtraction(DoublyLinkedList dll1, DoublyLinkedList dll2, boolean nr1BiggerThanNr2){
        DoublyLinkedList resultList = new DoublyLinkedList();
        makeSameSize(dll1, dll2); //To make the lists the same size, we use makeSameSize() method

        Node p, q;

        //Here, we use the boolean to initialize the p and q nodes.
        if(nr1BiggerThanNr2){ //If nr1 is bigger than nr2, we can do normal subtraction.
            p = dll1.head;
            q = dll2.head;
        }
        else{ //If nr2 is bigger than nr1, we have to do subtraction between nr2 and nr1, and not nr1 and nr2.
            p = dll2.head;
            q = dll1.head;
        }


        int subtraction = 0;

        //While the nodes are not null
        //We don't actually need both p and q in the statement,
        // since we already have made the lists the same size.
        while(p != null && q != null){
            if(p.data >= q.data){ //If p.data is greater or equal to q.data, we can do a simple subtraction without any negative result
                subtraction = p.data - q.data;
            }
            else{ //If not, we have to make sure to follow the rules of subtraction, when subtracting a smaller number to a larger number
                subtraction = 10 + p.data - q.data;
                p.next.data--; //We also have to subtract 1 from the next digit, which is the digit BEFORE the current p.data
                // since we are using reversed lists.
            }

            resultList.addToHead(subtraction);

            p = p.next;
            q = q.next;
        }

        //We can also delete the nodes with zeros in it
        //Example: 100-99 = 1. If we don't delete the nodes with zeros in the result list
        // we will end up with 001 as the list display
        for(int i = 0; i < resultList.getLength(); i++){
            Node a = resultList.head;
            if(a.data == 0){
                resultList.deleteNode(a);
            }
        }

        return resultList;
    }

    //This method adds zeros (0) to the list with lesser elements, to make the addition and subtraction go smooth
    private static void makeSameSize(DoublyLinkedList dll1, DoublyLinkedList dll2){
        int diff = Math.abs(dll1.getLength() - dll2.getLength());
        if(dll1.getLength() > dll2.getLength()){
            for(int i = 0; i < diff; i++){
                dll2.addToTail(0);
            }
        }
        else{
            for(int i = 0; i < diff; i++){
                dll1.addToTail(0);
            }
        }
    }

    // Function to delete a node in a Doubly Linked List.

    private void deleteNode(Node node) {
        // Base case
        if (head == null || node == null) {
            return;
        }

        // If node to be deleted is head node
        if (head == node) {
            head = node.next;
        }

        // Change next only if node to be delete is NOT the last node
        if (node.next != null) {
            node.next.prev = node.prev;
        }

        // Change prev only if node to be deleted is NOT the first node
        if (node.prev != null) {
            node.prev.next = node.next;
        }
    }

    //display() will print out the nodes of the list
    private String display() {
        //Node current will point to head
        Node current = head;
        StringBuilder output = new StringBuilder();
        if(head == null) {
            output.append("List is empty");
            return output.toString();
        }
        while(current != null) {
            //Prints each node by incrementing the pointer.

            output.append(current.data);
            current = current.next;
        }
        return output.toString();
    }
}
