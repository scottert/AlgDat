public class WEdge extends Edge{
    int weight;


    public WEdge(Node to, Edge next, int weight) {
        super(to, next);
        this.weight = weight;
    }
}
