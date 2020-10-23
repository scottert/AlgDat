import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args){

        BufferedReader bufferedReader;
        Graph2 graph;

        try {
            graph = new Graph2();
            bufferedReader = new BufferedReader(new FileReader("src/L7g1"));
            graph.createUGraph(bufferedReader, "L7g1");
            graph.SCC();

            graph = new Graph2();
            bufferedReader = new BufferedReader(new FileReader("src/L7g2"));
            graph.createUGraph(bufferedReader, "L7g2");
            graph.SCC();

            graph = new Graph2();
            bufferedReader = new BufferedReader(new FileReader("src/L7g5"));
            graph.createUGraph(bufferedReader, "L7g5");
            graph.SCC();

            graph = new Graph2();
            bufferedReader = new BufferedReader(new FileReader("src/L7g6"));
            graph.createUGraph(bufferedReader, "L7g6");
            graph.SCC();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
