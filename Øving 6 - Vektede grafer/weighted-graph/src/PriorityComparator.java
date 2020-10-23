import java.util.Comparator;

public class PriorityComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return ((Predecessor)o1.d).distance - ((Predecessor)o2.d).distance;
    }
}
