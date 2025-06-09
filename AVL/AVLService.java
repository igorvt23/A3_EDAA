package AVL;

import java.io.Serializable;

public class AVLService {
    AVL avl;

    public AVLService() {
        avl = new AVL();
    }

    public String addNode(String word) {
        avl.addNode(word);
        return "Nodo Adicionado";
    }

    public String inOrder() {
        return avl.printTreeInOrder();
    }

    public String inReverse() {
        return avl.printTreeReverse();
    }

    public int countNodes() {
        return avl.countNodes();
    }

    public int heightTree() {
        return avl.getHeight(avl.root);
    }

    public String[][] printTree() {
        return avl.prinTree();
    }
}
