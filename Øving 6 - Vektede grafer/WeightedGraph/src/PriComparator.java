import java.util.Comparator;

public class PriComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2){
        return n1.prev.dist - n2.prev.dist;
    }
}
