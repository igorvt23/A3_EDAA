package AVL;

import java.io.Serializable;

public class Node implements Serializable {
    String word;
    Node left, right;
    int frequency;
    int height;

    public Node (String word) {
        this.word = word;
        this.left = null;
        this.right = null;
        this.height = 0;
        this.frequency = 1;
    }
}