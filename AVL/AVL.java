package AVL;
import java.io.Serializable;

public class AVL implements Serializable {
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
            tempRoot.frequency++;
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

    public void removeNode(String word) {
        root = removeNodeRec(root, word);
    }

    public Node removeNodeRec(Node tempRoot, String word) {
        if (tempRoot == null) return tempRoot;

        if (word.compareToIgnoreCase(tempRoot.word) < 0) {
            tempRoot.left = removeNodeRec(tempRoot.left, word);
        } else if (word.compareToIgnoreCase(tempRoot.word) > 0) { 
            tempRoot.right = removeNodeRec(tempRoot.right, word);
        } else {
            tempRoot.frequency--;
            if (tempRoot.frequency <= 0) {
                // verifica a existencias de filhos
                if (tempRoot.left == null) {
                    Node aux = tempRoot.right;
                    tempRoot = null;
                    return aux; 
                } else if (tempRoot.right == null) {
                    Node aux = tempRoot.left;
                    tempRoot = null;
                    return aux; 
                } else {
                    // acha o in order sucessor
                    Node aux = minNode(tempRoot.right);
                    tempRoot.word = aux.word;
                    tempRoot.frequency = aux.frequency;
                    tempRoot.right = removeNodeRec(tempRoot.right, aux.word);
                }
                
                // atualiza a altura
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
            }
            return tempRoot;
        }
        return tempRoot;
    }

    public String hasWord(String word) {
        return hasWordRec(root, word);
    }

    public String hasWordRec(Node tempRoot, String word) {
        if (tempRoot == null)  return "Palavra não encontrada!";
        if (tempRoot.word.equals(word)) return word +" | freqeuncia: "+ tempRoot.frequency;
        else if (word.compareTo(tempRoot.word) < 0) {
            return hasWordRec(tempRoot.left, word);
        } else {
            return hasWordRec(tempRoot.right, word);
        }
    }

    public Node minNode(Node tempRoot) {
        while (tempRoot.left != null) {
            tempRoot = tempRoot.left;
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
        actual.append(String.format("[Nos: %-20s | Frequencia: %03d | Altura: %02d]\n", tempRoot.word, tempRoot.frequency, tempRoot.height));
        if(tempRoot.right != null) actual.append(printTreeInOrderRec(tempRoot.right));
        return actual.toString();
    }

    public String printTreeReverse() {
        return printTreeReverseRec(root);
    }

    public String printTreeReverseRec(Node tempRoot) {
        StringBuilder actual = new StringBuilder();
        actual.append(String.format("[No: %-20s | Frequencia: %-03d | Altura: %-02d]\n", tempRoot.word, tempRoot.frequency, tempRoot.height));
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

    // deve ser exvluido provavelmente
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

    // ´por conta do ,Dot a 2 ultimas funções saõ meio inuteois
    public String convertToDot() {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph AVL {\n");
        builder.append("  node [fontname=\"Arial\"];\n");

        if (root == null) {
            builder.append("\n");
        } else {
            builder.append(convertToDotRec(root));
        }

        builder.append("}\n");
        return builder.toString();
    } 

    public String convertToDotRec(Node tempRoot) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("  \"%s\" [label=\"%s (%d)\"];\n", tempRoot.word, tempRoot.word, tempRoot.frequency));
        
        if (tempRoot.left != null) {
            builder.append(String.format("  \"%s\" -> \"%s\";\n", tempRoot.word, tempRoot.left.word));
            builder.append(convertToDotRec(tempRoot.left));
        }
        if (tempRoot.right != null) {
            builder.append(String.format("  \"%s\" -> \"%s\";\n", tempRoot.word, tempRoot.right.word));
            builder.append(convertToDotRec(tempRoot.right));
        }
        return builder.toString();
    }
}
