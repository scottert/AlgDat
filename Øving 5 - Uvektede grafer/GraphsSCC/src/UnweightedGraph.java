import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Scanner;

public class UnweightedGraph {
    private int V;
    private LinkedList<Integer>[] adjListArray;

    public UnweightedGraph(int V) {
        this.V = V;
        adjListArray = new LinkedList[V];

        //Creates a new list for every vertex in the array
        //Here we can store each vertex adjacency
        for(int i = 0; i < V ; i++){
            adjListArray[i] = new LinkedList<Integer>();
        }
    }

    public void addEdge(int src, int dest) {
        adjListArray[src].add(dest);

        adjListArray[dest].add(src);
    }

    public void DFSUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v+" ");
        for (int i: adjListArray[v]) {
            if(!visited[i]) DFSUtil(i,visited);
        }

    }
    public void connectedComponents() {
        boolean[] visited = new boolean[V];
        for(int v = 0; v < V; ++v) {
            if(!visited[v]) {
                System.out.print("Component " + (v+1) + ": ");
                // print all reachable vertices from v
                DFSUtil(v,visited);
                System.out.println();
            }
        }
    }

    public static UnweightedGraph getGraph(URL url) throws IOException {
        int antNode;
        UnweightedGraph g = null;
        String str;

        Scanner scanner = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A");

        boolean firstLine = true;
        while(scanner.hasNextLine()){
            str = scanner.nextLine().trim();
            String[] part = str.split("\\s+");
            if(firstLine){
                antNode = Integer.parseInt(part[0]);
                firstLine = false;
                g = new UnweightedGraph(antNode);
            }
            else{
                g.addEdge(Integer.parseInt(part[0]), Integer.parseInt(part[1]));
            }
            //System.out.println(part[0] + "       " + part[1]);
        }

        return g;
    }

    public static UnweightedGraph getGraph(File file) throws FileNotFoundException {
        int antNode = 0;
        UnweightedGraph g = null;
        String str = "";
        Scanner scanner = new Scanner(file);

        boolean firstLine = true;
        while(scanner.hasNextLine()){
            str = scanner.nextLine().trim();
            String[] part = str.split("\\s+");
            if(firstLine){
                antNode = Integer.parseInt(part[0].trim());
                firstLine = false;
                g = new UnweightedGraph(antNode);
            }
            else{
                int a = Integer.parseInt(part[0].trim());
                int b = Integer.parseInt(part[1].trim());
                g.addEdge(a, b);
            }
        }

        return g;
    }

    public static void main(String[] args) throws IOException {
        /*UnweightedGraph g = new UnweightedGraph(5);
        g.addEdge(1, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.connectedComponents();*/

        /*File file = new File("C:\\Users\\scott\\OneDrive\\2NTNU\\Semester 1\\Algoritmer og datastrukturer\\Øvinger\\Øving 5 - Uvektede grafer\\L7g1");
        System.out.println("Following are connected components for L7g1:");
        getGraph(file).connectedComponents();

        file = new File("C:\\Users\\scott\\OneDrive\\2NTNU\\Semester 1\\Algoritmer og datastrukturer\\Øvinger\\Øving 5 - Uvektede grafer\\L7g6");
        System.out.println("Following are connected components for L7g6:");
        getGraph(file).connectedComponents();

        file = new File("C:\\Users\\scott\\OneDrive\\2NTNU\\Semester 1\\Algoritmer og datastrukturer\\Øvinger\\Øving 5 - Uvektede grafer\\L7g2");
        System.out.println("Following are connected components for L7g2:");
        getGraph(file).connectedComponents();

        file = new File("C:\\Users\\scott\\OneDrive\\2NTNU\\Semester 1\\Algoritmer og datastrukturer\\Øvinger\\Øving 5 - Uvektede grafer\\L7g5");
        System.out.println("Following are connected components for L7g5:");
        getGraph(file).connectedComponents();

        file = new File("C:\\Users\\scott\\OneDrive\\2NTNU\\Semester 1\\Algoritmer og datastrukturer\\Øvinger\\Øving 5 - Uvektede grafer\\L7Skandinavia");
        System.out.println("Following are connected components for L7Skandinavia:");
        getGraph(file).connectedComponents();

        file = new File("C:\\Users\\scott\\OneDrive\\2NTNU\\Semester 1\\Algoritmer og datastrukturer\\Øvinger\\Øving 5 - Uvektede grafer\\L7Skandinavia-navn");
        System.out.println("Following are connected components for L7Skandinavia-navn:");
        getGraph(file).connectedComponents();*/

        URL url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g1");
        System.out.println("Following are connected components for L7g1:");
        getGraph(url).connectedComponents();

        url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g6");
        System.out.println("Following are connected components for L7g6:");
        getGraph(url).connectedComponents();

        url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g2");
        System.out.println("Following are connected components for L7g2:");
        getGraph(url).connectedComponents();

        url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g5");
        System.out.println("Following are connected components for L7g5:");
        getGraph(url).connectedComponents();

        url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7Skandinavia");
        System.out.println("Following are connected components for L7Skandinavia:");
        getGraph(url).connectedComponents();

        url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7Skandinavia-navn");
        System.out.println("Following are connected components for L7Skandinavia-navn:");
        getGraph(url).connectedComponents();
    }
}
