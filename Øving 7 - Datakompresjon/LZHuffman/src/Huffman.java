import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;

public class Huffman {
    byte[] bytes;
    static class Node implements Comparable<Node> {
        byte data;
        int frequency;
        Node root = null;
        Node left = null;
        Node right = null;

        Node(byte data, int frequency){
            this.data = data;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(o.frequency, this.frequency);
        }

        @Override
        public String toString() {
            return data + ": " + frequency;
        }
    }

    private ArrayList<Node> findFrequency(byte[] bytes){
        ArrayList<Node> returnList = new ArrayList<>();
        boolean foundInReturn;

        for(int i = 0; i < bytes.length; i++){
            foundInReturn = false;
            for(Node node: returnList){
                if(node.data == bytes[i]){
                    node.frequency++;
                    foundInReturn = true;
                    break;
                }
            }
            if(!foundInReturn) returnList.add(new Node(bytes[i], 1));
        }
        Collections.sort(returnList);
        return returnList;
    }

    public DataOutputStream compress(String inputFileName, String outputFileName) throws IOException {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));

        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();

        ArrayList<Node> treeList = findFrequency(bytes);
        Node[] leafNodes = treeList.toArray(new Node[treeList.size()]);

        setNodes(treeList);


        //Creation of bitstrings
        String[] bitStrings = new String[leafNodes.length];
        Node currentNode;
        Node localRoot;

        for(int i = 0; i < bitStrings.length; i++){
            currentNode = leafNodes[i];
            bitStrings[i] = "";
            while(currentNode.root != null){
                localRoot = currentNode.root;
                if(localRoot.left == currentNode){
                    bitStrings[i] = "0" + bitStrings[i];
                    System.out.println("Generated 0");
                }
                else{
                    bitStrings[i] = "1" + bitStrings[i];
                    System.out.println("Generated 1");
                }
                currentNode = localRoot;
            }
        }

        for(int i = 0; i < bitStrings.length; i++){
            System.out.print((char) leafNodes[i].data);
            System.out.println(bitStrings[i]);
        }

        StringBuilder sb = new StringBuilder();
        byte currentByte;

        for(int i = 0; i < bytes.length; i++){
            currentByte = bytes[i];

            //Find compressed value for current byte
            for(int j = 0; j < leafNodes.length; j++){
                if(currentByte == leafNodes[j].data){
                    sb.append(bitStrings[j]);
                    break;
                }
            }
        }
        String bitString = sb.toString();

        //Convert text
        BitSet bitSet = new BitSet();
        //System.out.println("bitstring length: " + bitString.length());

        for(int i = 0; i < bitString.length(); i++){
            if(bitString.charAt(i) == 0) bitSet.set(i, false);
            else bitSet.set(i, true);
        }
        //System.out.println(bitSet.toString());

        //Export
        outputStream.writeInt(leafNodes.length); //Writes header int to output

        //Writes the frequency tree to output
        for(Node node: leafNodes){
            outputStream.writeByte(node.data);
            outputStream.writeInt(node.frequency);
        }

        byte[] exportBytes = bitSet.toByteArray();
        for(byte b: exportBytes){
            outputStream.writeByte(b);
            //System.out.println("Byte written: " + b);
        }

        outputStream.close();

        return outputStream;
    }

    public DataOutputStream decompress(String inputFileName, String outputFileName) throws Exception{
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));

        ArrayList<Node> leafNodeList = new ArrayList<>();
        int headerint = inputStream.readInt();

        for (int i = 0; i < headerint; i++) {
            byte data = inputStream.readByte();
            int frequency = inputStream.readInt();

            leafNodeList.add(new Node(data, frequency));
        }

        byte[] byteData = new byte[inputStream.available()];
        inputStream.readFully(byteData);
        inputStream.close();

        BitSet bitSet = BitSet.valueOf(byteData);

        //System.out.println(bitSet.toString());

        setNodes(leafNodeList);

        Node root = leafNodeList.get(0);

        Node currentNode = root;

        for (int i = 0; i < bitSet.length(); i++) {

            assert currentNode != null;
            if (currentNode.left == null && currentNode.right == null){
                System.out.println("Found Leaf! " + currentNode.data);
                outputStream.writeByte(currentNode.data);

                currentNode = root;
            }

            if (!bitSet.get(i)){
                currentNode = currentNode.left;
            }else {
                currentNode = currentNode.right;
            }
        }

        outputStream.close();

        return outputStream;
    }

    private ArrayList<Node> setNodes(ArrayList<Node> list) {
        while (list.size() > 1){

            Node node1 = list.get(list.size()-2);
            Node node2 = list.get(list.size()-1);

            Node localRoot = new Node((byte)0, node1.frequency + node2.frequency);

            localRoot.left = node1;
            localRoot.right = node2;
            node1.root = localRoot;
            node2.root = localRoot;

            list.remove(list.size()-2);
            list.remove(list.size()-1);

            list.add(localRoot);

            Collections.sort(list);
        }
        return list;
    }
}
