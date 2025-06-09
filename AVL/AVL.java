package AVL;

import java.io.Serializable;

public class AVL {
    Node root;

    public void addNode(String word) { 
        root = addNodeRec(root, word);
    }

    // atualmente ignora palavras repitidas // sem anotar repitições
    public Node addNodeRec(Node tempRoot, String word) {
        if (tempRoot == null) return new Node(word); 
        
        if (word.compareToIgnoreCase(tempRoot.word) < 0) {
            tempRoot.left = addNodeRec(tempRoot.left, word);
        } else if (word.compareToIgnoreCase(tempRoot.word) > 0) { 
            tempRoot.right = addNodeRec(tempRoot.right, word);
        } else {
            return tempRoot;
        }

        tempRoot.height = 1 + Math.max(getHeight(tempRoot.left), getHeight(tempRoot.right));

        int balance = balance(tempRoot);

        if (balance == -2 && word.compareToIgnoreCase(tempRoot.right.word) > 0) {
            return rotateLeft(tempRoot);

        } else if (balance == -2 && word.compareToIgnoreCase(tempRoot.right.word) < 0) {
            tempRoot.right = rotateRight(tempRoot.right);
            return rotateLeft(tempRoot);

        } else if (balance == +2 && word.compareToIgnoreCase(tempRoot.left.word) < 0) {
            return rotateRight(tempRoot);

        } else if (balance == +2 && word.compareToIgnoreCase(tempRoot.left.word) > 0) {
            tempRoot.left = rotateLeft(tempRoot.left);
            return rotateRight(tempRoot);
        }
        return tempRoot;
    }

    public int getHeight(Node tempNode) {
        return (tempNode == null) ? -1 : tempNode.height;
    }

    public int balance(Node tempRoot) {
        if (tempRoot == null) return 0;
        return getHeight(tempRoot.left) - getHeight(tempRoot.right);
    }

    public Node rotateLeft(Node tempNode) {
        Node center = tempNode.right;
        Node aux = center.left;
        center.left = tempNode;
        tempNode.right = aux;

        tempNode.height = 1 + Math.max(getHeight(tempNode.left), getHeight(tempNode.right));
        center.height = 1 + Math.max(getHeight(center.left), getHeight(center.right));
        return center;
    }

    public Node rotateRight(Node tempNode) {
        Node center = tempNode.left;
        Node aux = center.right;
        center.right = tempNode;
        tempNode.left = aux;

        tempNode.height = 1 + Math.max(getHeight(tempNode.left), getHeight(tempNode.right));
        center.height = 1 + Math.max(getHeight(center.left), getHeight(center.right));
        return center;
    }

    public String printTreeInOrder() {
        return printTreeInOrderRec(root);
    }

    public String printTreeInOrderRec(Node tempRoot) {
        StringBuilder actual = new StringBuilder();
        if(tempRoot.left != null) actual.append(printTreeInOrderRec(tempRoot.left));
        actual.append("Root atual: " + tempRoot.word + 
                      " | Ponteiro root atual: " + tempRoot + 
                      " | left: " + tempRoot.left +
                      " | right: " + tempRoot.right +
                      " | heigtht: " + tempRoot.height+"\n");
        if(tempRoot.right != null) actual.append(printTreeInOrderRec(tempRoot.right));
        return actual.toString();
    }

    public String printTreeReverse() {
        return printTreeReverseRec(root);
    }

    public String printTreeReverseRec(Node tempRoot) {
        StringBuilder actual = new StringBuilder();
        if(tempRoot.right != null) actual.append(printTreeReverseRec(tempRoot.right));
        actual.append("Root atual: " + tempRoot.word + 
                      " | Ponteiro root atual: " + tempRoot + 
                      " | left: " + tempRoot.left +
                      " | right: " + tempRoot.right +
                      " | heigtht: " + tempRoot.height+"\n");
        if(tempRoot.left != null) actual.append(printTreeReverseRec(tempRoot.left));
        return actual.toString();
    }

    public int countNodes() {
        int a = 0;
        return countNodesRec(root, a);
    }

    public int countNodesRec(Node tempRoot, int atual) {
        atual++;
        if(tempRoot.left != null) atual = countNodesRec(tempRoot.left, atual);
        if(tempRoot.right != null) atual = countNodesRec(tempRoot.right, atual);
        return atual;
    }

    public String[][] prinTree() {
        int altura =  root.height + 1;
        int largura = (int) Math.pow(2, altura) - 1;
        String[][] matrixTree = new String[altura][largura];
        for (int i = 0;  i < matrixTree.length; i++) {
            for (int j = 0; j < matrixTree[i].length; j++) {
                matrixTree[i][j] = " ";
            }
        }
        place(root, 0, (largura/2), altura, matrixTree);
        return matrixTree;
    }

    public void place(Node node, int row, int col, int height, String[][] matrix) {
        if (node == null) return;
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) return;

        matrix[row][col] = ("["+node.word+"]");

        int offset = (int) Math.pow(2, height - row - 2);
        if (offset < 1) offset = 1;

        place(node.left, row + 1, col - offset, height, matrix);
        place(node.right, row + 1, col + offset, height, matrix);
    }
}
