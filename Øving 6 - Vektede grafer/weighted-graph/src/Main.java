import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/vg1"));
        Graph graph = new Graph();

        graph.new_WGraph(bufferedReader);

        graph.dijkstra(graph.node[1]);
        System.out.println("\nFrom graph file vg1 with start point at 1");
        graph.printRoads();


        bufferedReader = new BufferedReader(new FileReader("src/vg1"));
        graph = new Graph();

        graph.new_WGraph(bufferedReader);

        graph.dijkstra(graph.node[0]);
        System.out.println("\nFrom graph file vg1 with start point at 0");
        graph.printRoads();

        bufferedReader = new BufferedReader(new FileReader("src/vg2"));
        graph = new Graph();

        graph.new_WGraph(bufferedReader);

        graph.dijkstra(graph.node[7]);
        System.out.println("\nFrom graph file vg2 with start point at 7");
        graph.printRoads();




    }
}
