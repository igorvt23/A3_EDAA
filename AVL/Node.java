package AVL;

import java.io.Serializable;

public class Node {
    String word;
    Node left, right;
    int height;

    public Node (String word) {
        this.word = word;
        this.left = null;
        this.right = null;
        this.height = 0;
    }
}