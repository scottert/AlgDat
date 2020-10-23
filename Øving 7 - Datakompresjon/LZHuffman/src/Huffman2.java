import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Huffman2 {
    static class Node{
        Node left;
        Node right;
        int frequency;
        char data;
        String [] bitstring;

        Node(char data, int frequency, Node left, Node right){
            this.data = data;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
            bitstring = new String[256];
        }

        Node(){
            bitstring = new String[256];
        }

        static Node makeHuffmanTree(PriorityQueue<Node> pq){
            Node tree = new Node();
            while(pq.size() > 1){
                Node t = pq.poll();
                Node n = pq.poll();
                assert n != null;
                Node h = new Node('\0',findSum(t, n), n, t);
                pq.add(h);
                tree = h;
            }
            return tree;

        }
        private static int findSum(Node t, Node n){
            return t.frequency + n.frequency;
        }

        public void printCode(Node root, String s) {
            if (root.left != null && root.right != null) {
                printCode(root.left, s+"0");
                printCode(root.right, s+"1");

            }else bitstring[root.data] = s;

        }
    }

    static class Bitstring {
        int length;
        long bits;

        Bitstring() {
        }

        Bitstring(int length, long bits) {
            this.length = length;
            this.bits = bits;
        }

        Bitstring(int length, byte b){
            this.length = length;
            this.bits = convertByte(b, length);
        }
        long convertByte(byte b, int length){
            long temp = 0;
            for(long i = 1<<length-1; i != 0; i >>= 1){
                if((b & i) == 0){
                    temp = (temp << 1);
                }
                else temp = ((temp << 1) | 1);
            }
            return temp;
        }

        static Bitstring concat(Bitstring s1, Bitstring s2) {
            Bitstring newBitstring = new Bitstring();
            newBitstring.length = s1.length + s2.length;
            if (newBitstring.length > 64) {
                System.out.println("Bitstring is too long!");
                return null;
            }
            newBitstring.bits = s2.bits | (s1.bits << s2.length);
            return newBitstring;
        }

        public String toString() {
            String str = "";
            for (long testbit = 1L << (length - 1); testbit != 0; testbit >>= 1) {
                str += ((bits & testbit) == 0) ? "0" : "1";
            }
            return str;
        }

    }

    static DataOutputStream compress(String inputFileName, String outputFileName) throws IOException {
        int count[] = new int[256];
        DataInputStream inFile = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
        int amount = inFile.available();
        for (int i = 0; i < amount; ++i) {
            int cByte = inFile.read();
            count[cByte]++;
        }
        inFile.close();
        PriorityQueue<Node> pq = new PriorityQueue<>(256, (a, b) -> a.frequency - b.frequency);
        pq.addAll(makeNodeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        tree.printCode(tree, "");
        FileInputStream inputStream = new FileInputStream(inputFileName);
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputFileName));
        for(int byteInt: count){
            outputStream.writeInt(byteInt);
        }
        int input;
        long writeByte = 0L;
        int i = 0;
        int j = 0;
        ArrayList<Byte> bytes = new ArrayList<>();
        for (int k = 0; k < amount; ++k) {
            input =Math.abs(inputStream.read());
            j = 0;
            String bitString = tree.bitstring[input];
            while (j < bitString.length()) {
                if (bitString.charAt(j) == '0')writeByte = (writeByte<<1);
                else writeByte = ((writeByte<<1)|1);
                ++j;
                ++i;
                if (i == 8) {
                    bytes.add((byte)writeByte);
                    i = 0;
                    writeByte = 0L;
                }
            }
        }
        int lastByte = i;
        while (i < 8 && i != 0) {
            writeByte = (writeByte<<1);
            ++i;
        }
        bytes.add((byte)writeByte);
        outputStream.writeInt(lastByte);
        for(Byte cByte: bytes){
            outputStream.write(cByte);
        }
        inputStream.close();
        inputStream.close();
        return outputStream;
    }

    DataOutputStream decompress(String inputFileName, String outputFileName) throws IOException {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
        int [] count = new int [256];
        for (int i = 0; i < count.length; i++) {
            int freq = inputStream.readInt();
            count[i] = freq;
        }

        int lastByte = inputStream.readInt();
        PriorityQueue<Node> pq = new PriorityQueue<>(256, (a, b) -> a.frequency - b.frequency);
        pq.addAll(makeNodeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));
        byte cByte;
        byte [] bytes = inputStream.readAllBytes();
        inputStream.close();
        int length = bytes.length;
        Bitstring concat = new Bitstring(0, 0);
        if(lastByte>0) length--;
        for (int i = 0; i <length; i++) {
            cByte = bytes[i];
            Bitstring bitstring = new Bitstring(8, cByte);
            concat = Bitstring.concat(concat,bitstring);
            concat = writeChar(tree, concat, outputStream);
        }
        if(lastByte>0){
            Bitstring bitstring = new Bitstring(lastByte, bytes[length]>>(8-lastByte));
            concat = Bitstring.concat(concat, bitstring);
            writeChar(tree, concat, outputStream);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
        return outputStream;
    }

    private static Bitstring writeChar(Node tree ,Bitstring bitstring, DataOutputStream outputStream) throws IOException {
        Node tempTree = tree;
        int c=0;
        for (long j = 1<< bitstring.length-1; j!=0; j>>=1) {
            c++;
            if((bitstring.bits & j) == 0)tempTree = tempTree.left;
            else tempTree = tempTree.right;
            if(tempTree.left == null){
                long cha = tempTree.data;
                outputStream.write((byte)cha);
                long temp =(long) ~(0 << (bitstring.length-c));
                bitstring.bits = (bitstring.bits & temp);
                bitstring.length = bitstring.length-c;
                c = 0;
                tempTree = tree;
            }
        }
        return bitstring;
    }

    private static ArrayList<Node> makeNodeList(int[] count) {
        ArrayList<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < count.length; i++) {
            if(count[i] != 0){
                nodeList.add(new Node((char) i, count[i], null, null));
            }
        }
        return nodeList;
    }
}