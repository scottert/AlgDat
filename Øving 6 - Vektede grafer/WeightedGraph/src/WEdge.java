public class WEdge {
    public WEdge next;
    public Node to;
    public int weight;

    public WEdge(Node n, WEdge next, int weight){
        this.to = n;
        this.next = next;
        this.weight = weight;
    }
}
