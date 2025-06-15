package AVL;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AVLService implements Serializable   {
    AVL avl;

    public AVLService() {
        try (FileInputStream fileIn = new FileInputStream("dados/objs/avl.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            avl = (AVL) in.readObject();
            System.out.println("AVL carregada com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo de AVL não encontrado, iniciando nova AVL!");
            avl = new AVL();
        }
    }
    public void addNode(String word) {
        avl.addNode(word);
    }

    public void removeNode(String word) {
        avl.removeNode(word);
    }

    public String hasWord(String word) {
        return avl.hasWord(word);
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

    public String saveAVL() {
        try (FileOutputStream fileOut = new FileOutputStream("dados/objs/avl.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(avl);
            return "AVL salva com sucesso!";
        } catch (IOException e) {
            return "Problemas ao salvar a AVL!";
        }
    }
    // converte a AVL para uma visulição em dot. para ser visualizada em html
    // utilizado apenas par mostrar a lista encadeada
    public String convertDot() {
        return avl.convertToDot();
    }

    public void saveVisualTree(String title) {
        String StringTree = convertDot();
        VisualTree tree = new VisualTree();
        tree.saveVisualTree(title, StringTree);
    } 
}
