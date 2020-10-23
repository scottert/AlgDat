import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DecompressMain {
    public static void main(String[] args){
        try{
            LZ lz = new LZ();
            Huffman2 huffman = new Huffman2();
            String inputFileName = "src/compressedHuffman.txt";
            String outputFileName = "src/decompressedHuffman.txt";

            DataInputStream inputStream = lz.readInput(inputFileName);
            System.out.println("Input file size: " + inputStream.read(lz.data));

            DataOutputStream outputStreamHuffman = huffman.decompress(inputFileName, outputFileName);
            System.out.println("Huffman decompressed file size: " + outputStreamHuffman.size());

            inputFileName = "src/decompressedHuffman.txt";
            outputFileName = "src/decompressedLZ.txt";
            DataOutputStream outputStreamLZ = lz.decompress(inputFileName, outputFileName);
            System.out.println("Lempel Ziv decompressed file size: " + outputStreamLZ.size());

            System.out.println("Original/Decompressed file size: " + outputStreamLZ.size());
        }
        catch(IOException e){
            System.out.println("An error occurred! :(");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
