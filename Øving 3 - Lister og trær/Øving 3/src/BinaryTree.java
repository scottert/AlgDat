import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BinaryTree {
    private TreeNode root;

    class TreeNode{
        String key;
        TreeNode left;
        TreeNode right;

        public TreeNode(String key){
            this.key = key;
            left = null;
            right = null;
        }
    }


    public BinaryTree(){
        root = null;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        BinaryTree tree = new BinaryTree();
        String answer;
        do{
            System.out.println("Write a word:");
            String word = scanner.nextLine();
            tree.insert(word.toLowerCase());
            System.out.println("Add word? Y/N");
            answer = scanner.nextLine();
        }
        while(answer.equalsIgnoreCase("Y"));

        System.out.println("\nThe tree is sortet alphabetically: ");
        tree.inorder();

        System.out.println("\nTree: ");
        tree.printTree(tree.root);
        //tree.levelOrderTraversalUsingRecursion(tree.root);
        //tree.display();
    }

    //Public calling function, which calls the private insertRec() function
    public void insert(String key) {
        root = insertRec(root, key);
    }

    //A recursive function to insert a new key into the binary tree
    private TreeNode insertRec(TreeNode root, String key) {
        //If the tree is empty, return a new node
        if (root == null) {
            root = new TreeNode(key);
            return root;
        }

        if (key.compareTo(root.key) < 0){
            root.left = insertRec(root.left, key);
        }
        else if (key.compareTo(root.key) > 0){
            root.right = insertRec(root.right, key);
        }

        return root;
    }

    //Public calling function, which calls inorderRec() function
    public void inorder()  {
        inorderRec(root);
    }

    //A utility function to do inorder traversal of binary tree
    private void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.key);
            inorderRec(root.right);
        }
    }

    //A printing function, using queues
    private void printTree(TreeNode root) {

        Queue<TreeNode> currentLevel = new LinkedList<>();
        Queue<TreeNode> nextLevel = new LinkedList<>();

        currentLevel.add(root);

        int counter = 2;
        while (!currentLevel.isEmpty()) {
            for (TreeNode currentNode : currentLevel) {
                if (currentNode.left != null) {
                    nextLevel.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    nextLevel.add(currentNode.right);
                }
                for(int i = 0; i < (100/counter); i++){
                    System.out.print(" ");
                }
                System.out.print(currentNode.key);
                for(int i = 0; i < (100/counter); i++){
                    System.out.print(" ");
                }
            }
            System.out.println();
            currentLevel = nextLevel;
            nextLevel = new LinkedList<>();
            counter *= 2;
        }
    }
}
