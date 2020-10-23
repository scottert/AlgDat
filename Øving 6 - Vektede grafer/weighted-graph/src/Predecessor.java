public class Predecessor {
    int distance;
    Node predecessor;
    static int infinity = 1000000;

    public int findDistance(){
        return distance;
    }

    public Node findPredecessor(){
        return predecessor;
    }

    public Predecessor(){
        distance = infinity;
    }
}
