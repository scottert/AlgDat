import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        BufferedReader br;
        WeightedGraph wgraph;
        try{
            wgraph = new WeightedGraph();
            br = new BufferedReader(new FileReader("src/vg1"));
            wgraph.new_WGraph(br);

            System.out.println("Dijkstra Algorithm for vg1 starting in node 1:");
            wgraph.dijkstra(wgraph.nodes[1]);

            System.out.println("\nDijkstra Algorithm for vg1 starting in node 0:");
            wgraph.dijkstra(wgraph.nodes[0]);

            wgraph = new WeightedGraph();
            br = new BufferedReader(new FileReader("src/vg2"));
            wgraph.new_WGraph(br);

            System.out.println("\nDijkstra Algorithm for vg2 starting in node 0:");
            wgraph.dijkstra(wgraph.nodes[7]);

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
