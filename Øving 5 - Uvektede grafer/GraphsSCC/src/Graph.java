import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

class Graph{
    private int corners;
    private LinkedList<Integer>[] adj;

    private Graph(int corners){
        this.corners = corners;
        this.adj = new LinkedList[corners];
        for(int i=0; i<corners; ++i){
            this.adj[i] = new LinkedList();
        }
    }

    //Function to add an edge into the graph
    private void addEdge(int corner, int edge){
        adj[corner].add(edge);
    }

    //A recursive function to print DFS starting from corner
    private void DFSUtil(int corner, boolean visited[]){
        visited[corner] = true;
        System.out.print(corner + " ");

        int n;

        //Recur for all the vertices adjacent to this vertex
        for (Integer integer : adj[corner]) {
            n = integer;
            if (!visited[n]){
                DFSUtil(n, visited);
            }
        }
    }

    //This function returns a transposed graph
    private Graph getTranspose(){
        Graph g = new Graph(corners);
        for (int i = 0; i < corners; i++){
            //Recur for all the vertices adjacent to this vertex
            Iterator<Integer> j =adj[i].listIterator();
            while(j.hasNext())
                g.adj[j.next()].add(i);
        }
        return g;
    }

    private void fillOrder(int corner, boolean[] visited, Stack stack){
        visited[corner] = true;

        //Recur for all the vertices adjacent to this vertex
        for(int n : adj[corner]){
            if (!visited[n])
                fillOrder(n, visited, stack);
        }

        //All vertices reachable from corner are processed by now, push v to Stack
        stack.push(corner);
    }

    //The main function that finds and prints all strongly connected components
    private void printSCCs() {
        Stack stack = new Stack();

        //Mark all the vertices as not visited (For first DFS)
        boolean[] visited = new boolean[corners];
        for (int i = 0; i < corners; i++){
            visited[i] = false;
        }

        //Fill vertices in stack according to their finishing times
        for (int i = 0; i < corners; i++){
            if (!visited[i]){
                fillOrder(i, visited, stack);
            }
        }
        //Create a transposed graph
        Graph gr = getTranspose();

        //Mark all the vertices as not visited (For second DFS)
        for (int i = 0; i < corners; i++){
            visited[i] = false;
        }

        //Now process all vertices in order defined by Stack
        while (!stack.empty()) {
            // Pop a vertex from stack
            int corner = (int)stack.pop();

            //Print Strongly connected component of the popped vertex
            if (!visited[corner]) {
                gr.DFSUtil(corner, visited);
                System.out.println();
            }
        }
    }

    private static Graph getGraph(URL url) throws IOException {
        int antNode;
        Graph g = null;
        String str;

        Scanner scanner = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A");

        boolean firstLine = true;
        while(scanner.hasNextLine()){
            str = scanner.nextLine().trim();
            String[] part = str.split("\\s+");
            if(firstLine){
                antNode = Integer.parseInt(part[0]);
                firstLine = false;
                g = new Graph(antNode);
            }
            else{
                g.addEdge(Integer.parseInt(part[0]), Integer.parseInt(part[1]));
            }
        }

        return g;
    }

    public static void main(String args[]) throws IOException {
        try{
            URL url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g1");
            System.out.println("Following are connected components for L7g1:");
            getGraph(url).printSCCs();

            url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g6");
            System.out.println("Following are connected components for L7g6:");
            getGraph(url).printSCCs();

            url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g2");
            System.out.println("Following are connected components for L7g2:");
            getGraph(url).printSCCs();

            url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g5");
            System.out.println("Following are connected components for L7g5:");
            getGraph(url).printSCCs();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
