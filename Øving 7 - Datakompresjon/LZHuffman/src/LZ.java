import java.io.*;

public class LZ {
    final int BUFFERSIZE = 32*1024; //AKA 32kB as said in the task description
    byte[] data;
    Queue buffer; //First-in-first-out queue as a search buffer

    public LZ(){
        this.buffer = new Queue(BUFFERSIZE);
    }

    public DataInputStream readInput(String inputFileName) throws IOException {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
        data = new byte[inputStream.available()];
        return inputStream;
    }

    public DataOutputStream compress(String outputFileName) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));

        int pos = 0, last = 0;

        //For every character in the byte array
        for(int i = 0; i < data.length; i++){
            byte b = data[i];
            int posInBuffer = buffer.contains(b, buffer.getEndByte());
            if(posInBuffer == -1){
                buffer.add(b);
                pos++;
            }
            else{
                //buildSequence returns the length of a sequence with equal bytes
                int seqLength = buildSequence(posInBuffer, i);
                int maxIndex = posInBuffer;

                //Continue backwards in the buffer to look for larger sequences
                while(posInBuffer != -1){
                    posInBuffer = buffer.contains(b, posInBuffer-1);
                    if(posInBuffer == -1) break;
                    int temp = buildSequence(posInBuffer, i);
                    if(temp > seqLength){
                        seqLength = temp;
                        maxIndex = posInBuffer;
                    }
                }

                //if sequence length is bigger than 6 bytes
                if(seqLength > 6){
                    outputStream.writeByte(pos-last);

                    //Skriver ut alle ukomprimerte bytes i intervallet mellom start og bakover referansen
                    byte[] tempBytes = new byte[pos-last];
                    int count = 0;
                    for(int j = last; j < pos; j++){
                        tempBytes[count] = data[j];
                        count++;
                    }

                    outputStream.write(tempBytes);

                    outputStream.writeByte(-(i-maxIndex)); //The negative sign in front marks the backward reference
                    outputStream.writeByte(seqLength);

                    for(int j = 0; j < seqLength; j++){
                        buffer.add(b);
                        i++;
                        pos++;
                    }

                    last = i;
                    i--;
                }
                else{
                    buffer.add(b);
                    pos++;
                }
            }
        }

        //Adding the last uncompressed data
        outputStream.writeByte((pos-last));

        for(int i = last; i < pos; i++) outputStream.writeByte(data[i]);
        outputStream.close();

        return outputStream;
    }

    public DataOutputStream decompress(String inputFileName, String outputFileName) throws IOException {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFileName)));
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)));

        //The uncompressed data in the start of the input file
        byte start = inputStream.readByte();
        data = new byte[start];
        //The first parts of the uncompressed data is read
        inputStream.readFully(data);
        outputStream.write(data);
        for(int j = 0; j<start; j++){
            buffer.add(data[j]);
        }
        while(inputStream.available() > 0){
            byte back = inputStream.readByte(); //Backward reference
            byte copyAmount = inputStream.readByte(); //The amount we will copy from the input file

            //End value of buffer
            int end = buffer.getEndByte();
            data = new byte[copyAmount];
            int index = 0;

            //Adding backward references and bytes from buffer.
            for(int tempIndex = (end + back); tempIndex < (end + back) + copyAmount; tempIndex++){
                byte byteIndex = buffer.getByte(tempIndex);
                data[index++] = byteIndex;
                buffer.add(byteIndex);
            }
            outputStream.write(data);

            //Uncompressed data
            start = inputStream.readByte();
            data = new byte[start];
            //System.out.println(inputStream.available());
            inputStream.readFully(data);
            for(int j = 0; j<start; j++){
                buffer.add(data[j]);
            }
            outputStream.write(data);
        }
        inputStream.close();
        outputStream.close();

        return outputStream;
    }

    //Checking how many bytes are equal to the original bytes from the input file
    private int buildSequence(int posInBuffer, int bytePos){
        byte b1 = data[bytePos];
        byte b2 = buffer.getByte(posInBuffer);
        int counter = 0;
        while(b1 == b2 && bytePos != (data.length - 1)){
            counter++;
            bytePos++;
            posInBuffer++;
            b1 = data[bytePos];
            b2 = buffer.getByte(posInBuffer);
        }
        return counter;
    }


    //Using a first-in-first-out queue as a buffer
    static class Queue{
        byte[] bytes;
        int startByte = 0;
        int endByte = 0;
        int byteAmount = 0;
        int lastByte = -1;

        Queue(int size){
            this.bytes = new byte[size];
        }

        byte getByte(int index){
            if(index >= bytes.length){
                int i = index % bytes.length;
                return bytes[i];
            }
            else if(index < 0){
                int i = bytes.length + (index % bytes.length);
                if(i == bytes.length) i = 0;
                return bytes[i];
            }
            return bytes[index];
        }

        int getEndByte() {
            return endByte;
        }

        boolean isEmpty(){
            return bytes == null;
        }

        boolean isFull(){
            return byteAmount == bytes.length;
        }

        boolean add(byte b){
            if(isFull()) pop();
            bytes[endByte] = b;
            endByte = (endByte + 1) % bytes.length;
            byteAmount++;
            return true;
        }

        //pop() returns the head of the queue
        byte pop(){
            if(!isEmpty()){
                byte b = bytes[startByte];
                startByte = (startByte + 1) % bytes.length;
                byteAmount--;
                return b;
            }
            return -128;
        }

        int contains(byte b, int pos){
            if(isEmpty() || pos == -1) return -1;
            int i = pos;
            while(i != startByte && i != -1){
                if(bytes[i] == b) return i;
                i--;
                if(i < 0 && startByte != 0) i = bytes.length-1;
            }
            if(bytes[startByte] == b && lastByte != startByte){
                lastByte = startByte;
                return startByte;
            }
            return -1;
        }
    }
}
