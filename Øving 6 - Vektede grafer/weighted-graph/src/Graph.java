import java.io.BufferedReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Graph {

    int NODE_AMOUNT;
    int EDGE_AMOUNT;
    Node[] node;



    public void new_WGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        NODE_AMOUNT = Integer.parseInt(st.nextToken());
        node = new Node[NODE_AMOUNT];

        for(int i = 0; i < NODE_AMOUNT; i ++){
            node[i] = new Node(i);
        }

        EDGE_AMOUNT = Integer.parseInt(st.nextToken());
        for(int i = 0; i < EDGE_AMOUNT; i ++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            WEdge wEdge = new WEdge(node[to], (WEdge)node[from].edge1, weight);
            node[from].edge1 = wEdge;
        }
    }


    public void initializePredecessors(Node start){
        for(int i = NODE_AMOUNT; i-- > 0;){
            node[i].d = new Predecessor();
        }
        ((Predecessor)start.d).distance = 0;
    }


    public void abbreviate(Node n, WEdge wEdge, PriorityQueue<Node> priorityQueue){
        Predecessor nd = (Predecessor)n.d;
        Predecessor md = (Predecessor)wEdge.to.d;

        if(md.distance > nd.distance + wEdge.weight){
            md.distance = nd.distance + wEdge.weight;
            md.predecessor = n;
            priorityQueue.remove(wEdge.to);
            priorityQueue.add(wEdge.to);
        }
    }


    public void dijkstra(Node start){
        initializePredecessors(start);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(NODE_AMOUNT, new PriorityComparator());
        for(int i = 0; i < NODE_AMOUNT; i ++){
            priorityQueue.add(node[i]);
        }

        for(int i = NODE_AMOUNT; i > 1; --i){
            Node n = priorityQueue.poll();
            for(WEdge wEdge = (WEdge)n.edge1; wEdge != null; wEdge = (WEdge) wEdge.next){
                abbreviate(n, wEdge, priorityQueue);
            }
        }
    }

    public void printRoads(){
        System.out.format("%-7s%-7s%-7s%n", "Node", "From", "Distance");

        for(int i = 0; i < NODE_AMOUNT; i ++){

            String predecessor;
            if(((Predecessor)node[i].d).predecessor == null){
                predecessor = "";
            }else if(((Predecessor)node[i].d).predecessor.nodeNumber == 0){
                predecessor = "Start";
            }else{
                predecessor = Integer.toString(((Predecessor)node[i].d).predecessor.nodeNumber);
            }

            String distance;
            if(((Predecessor)node[i].d).distance == 1000000){
                distance = "can't reach";
            }else{
                distance = Integer.toString(((Predecessor)node[i].d).distance);
            }
            System.out.format("%-7s%-7s%-7s%n", i, predecessor, distance);
        }
    }
}
