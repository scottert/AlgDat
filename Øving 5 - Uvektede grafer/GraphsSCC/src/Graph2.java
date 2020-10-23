import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Graph2 {

    private String graphName;
    private int numberOfSCC = 0;
    //Number of nodes
    private int N;
    //Number of lines
    private int K;
    //An arraylist containing a linked list
    //The thought here is that each index in the arraylist represents a node
    //and the linked list on each index contains all the nodes that the index node goes too.
    private ArrayList<LinkedList<Integer>> graph;

    //Initialize the arraylist and linkedlists when the
    //createUGraph method is called
    private void initializeGraph(){
        graph = new ArrayList<>();
        for(int i = 0; i < N; i ++){
            graph.add(new LinkedList<>());
        }
    }

    //Adds a node to the linked list at the given "from" node.
    //The node in the linked list is an integer that points to the
    //node the edge is going to.
    private void addLine(int from, int too){
        graph.get(from).add(too);
    }

    //Reads the number of nodes and lines from the file. Then reads the following
    //N lines that contains data about "from-too" lines.
    public void createUGraph(BufferedReader br, String fileName) throws IOException {
        this.graphName = fileName;

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        initializeGraph();
        K = Integer.parseInt(st.nextToken());
        for(int i = 0; i < K; i ++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int too = Integer.parseInt(st.nextToken());
            addLine(from, too);
        }
    }

    //Just a simple method to display the graph
    public void printCurrentGraph(){
        System.out.println("The graph displayed as each node with connections to one or more other nodes: ");
        for(int i = 0; i < N; i ++){
            System.out.print(i + " -> ");
            for(int node : graph.get(i)){
                System.out.print(node + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //This recursive method is a depth first search method, which is
    //very similar to the DFSPrinter() method below. The difference is that
    //this method does not focus on printing out the different components, but instead
    //focus on filling the stack in such an order that the node that is found lastly, is
    //the next one in the stack.
    public void traverseNode(int node, int found[], Stack<Integer> stack){
        //registerts that the current node is discovered
        //this makes sure the node is not added to the stack again
        //if it gets discovered in another "run".
        found[node] = 1;

        //Find the next node that hasen't been discovered already
        Iterator<Integer> iterator = graph.get(node).iterator();
        while(iterator.hasNext()){
            int next = iterator.next();
            if(found[next] == -1){
                traverseNode(next, found, stack);
            }
        }

        //Push the node to the stack.
        stack.push(node);
    }

    //Just a helper method to traverse all the nodes that hasen't
    //been discovered when traversing previous nodes.
    private void fillStack(int[] found, Stack<Integer> stack){
        for(int i = 0; i < N; i ++){
            if(found[i] == -1){
                traverseNode(i, found, stack);
            }
        }
    }

    //Method for transposing the current graph. Taken from GeeksForGeeks
    //Basically does the same as creating a new graph, except switching the
    //too and from places.
    private Graph2 createTransposedGraph(){
        Graph2 transposedGraph = new Graph2();
        transposedGraph.setN(N);
        transposedGraph.initializeGraph();
        for(int i = 0; i < N; i ++){
            Iterator<Integer> iterator = this.getGraph().get(i).listIterator();
            while(iterator.hasNext()){
                transposedGraph.addLine(iterator.next(), i);
            }
        }
        return transposedGraph;
    }

    //This is the method that is similiar to traverseNode, excepts this one is focused on
    //printing out the strongly connected components, and it uses DFS on the TRANSPOSED graph
    //and traverses the nodes in the order we put in the stack. Therefore we use the integer
    //we get from popping the stack as the index.
    private void DFSPrinter(int[] found, Stack<Integer> stack, Graph2 transposedGraph){
        while(!stack.empty()){
            int node = stack.pop();

            if(found[node] == -1){
                numberOfSCC ++;
                System.out.print(numberOfSCC + "                          ");
                transposedGraph.DFSPrinterUtil(node, found);
                System.out.println();
            }
        }
    }

    //This is the last part of finding the strongly connected components.
    //So finally, after doing DPS on the graph and sorting it into a stack,
    //creating the transposed graph, the nodes we get when using DPS on the
    //transposed graph in the order from the stack, we get the strongly connected
    //components.
    private void DFSPrinterUtil(int node, int[] found){
        found[node] = 1;
        System.out.print(node + " ");

        int next;
        Iterator<Integer> iterator = this.getGraph().get(node).iterator();
        while (iterator.hasNext())
        {
            next = iterator.next();
            if (found[next] == -1)
                DFSPrinterUtil(next, found);
        }
    }

    //This is the method that is called to find all the strongly connected components
    //which uses all of the methods explained above.
    public void SCC(){
        Stack<Integer> stack = new Stack<>();

        int[] found = new int[N];
        for(int i = 0; i < N; i ++){
            found[i] = -1;
        }

        fillStack(found, stack);

        Graph2 transposedGraph = createTransposedGraph();

        for(int i = 0; i < N; i ++){
            found[i] = -1;
        }


        System.out.println("The graph " + graphName + " strongly connected components");
        System.out.println("Component nr              Nodes in component");
        DFSPrinter(found, stack, transposedGraph);

    }

    public ArrayList<LinkedList<Integer>> getGraph() {
        return graph;
    }

    public void setN(int n) {
        N = n;
    }

}
