import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CompressMain {
    public static void main(String[] args) {
        try{
            LZ lz = new LZ();
            Huffman2 huffman = new Huffman2();
            String inputFileName = "src/diverse.txt";
            String outputFileName = "src/compressedLZ.txt";

            DataInputStream inputStream = lz.readInput(inputFileName);
            System.out.println("Input file size: " + inputStream.read(lz.data));

            DataOutputStream outputStreamLZ = lz.compress(outputFileName);
            System.out.println("Lempel Ziv compressed file size: " + outputStreamLZ.size());

            inputFileName = "src/compressedLZ.txt";
            outputFileName = "src/compressedHuffman.txt";
            DataOutputStream outputStreamHuffman = huffman.compress(inputFileName, outputFileName);
            System.out.println("Huffman compressed file size: " + outputStreamHuffman.size());
        }
        catch(IOException e){
            System.out.println("An error occurred! :(");
            e.printStackTrace();
        }
    }
}
