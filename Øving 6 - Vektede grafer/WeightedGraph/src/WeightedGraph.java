import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class WeightedGraph {

    Node[] nodes;
    int nodeAmount;
    int edgeAmount;

    public void new_WGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.nodeAmount = Integer.parseInt(st.nextToken());
        this.nodes = new Node[nodeAmount];
        for(int i = 0; i < nodeAmount; i++) nodes[i] = new Node(i);

        this.edgeAmount = Integer.parseInt(st.nextToken());
        for(int i = 0; i < edgeAmount; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            WEdge e = new WEdge(nodes[to], nodes[from].edge, weight);
            nodes[from].edge = e;
        }
    }

    public void dijkstra(Node node){
        initPre(node);
        PriorityQueue<Node> priQ = new PriorityQueue<>(nodeAmount, new PriComparator());
        for(int i = 0; i < nodeAmount; i++){
            priQ.add(nodes[i]);
        }

        for(int i = nodeAmount; i > 1; i--){
            Node n = priQ.poll();
            for(WEdge e = n.edge; e != null; e = e.next){
                shorten(n, e, priQ);
            }
        }
        printDijkstra(node);
    }

    public void initPre(Node node){
        for(int i = nodeAmount; i-- > 0;){
            nodes[i].prev = new PreNode();
        }
        node.prev.dist = 0;
    }

    public void shorten(Node node, WEdge edge, PriorityQueue<Node> priQ){
        PreNode pre1 = node.prev;
        PreNode pre2 = edge.to.prev;
        if(pre2.dist > pre1.dist + edge.weight){
            pre2.dist = pre1.dist + edge.weight;
            pre2.pre = node;
            priQ.remove(edge.to);
            priQ.add(edge.to);
        }
    }

    public void printDijkstra(Node node){
        for(int i = 0; i < nodeAmount; i++){
            StringBuilder str = new StringBuilder();
            str.append(node.node).append(" --> ").append(i).append(" | Distance: ");

            if(nodes[i].prev.dist == 1000000000){
                str.append("unable to reach");
            }
            else{
                if(nodes[i] == node){
                    str.append("same node");
                }
                else{
                    str.append(nodes[i].prev.dist);
                }
            }

            str.append(printPath(node, i));
            System.out.println(str.toString());
        }
    }

    public StringBuilder printPath(Node node, int i){
        StringBuilder str = new StringBuilder();
        if(nodes[i].prev.dist == 1000000000 || nodes[i] == node) return str;

        str.append(" | Path: ").append(node.node);

        Node n = nodes[i];
        ArrayList<Node> nodeList = new ArrayList<>();
        while(n != null){
            nodeList.add(n);
            n = n.prev.pre;
        }
        Collections.reverse(nodeList);

        for(int j = 1; j < nodeList.size(); j++){
            str.append(" -> ").append(nodeList.get(j).node);
        }
        return str;
    }
}
