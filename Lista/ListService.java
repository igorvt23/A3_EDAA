package Lista;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ListService {
    List list;

    public ListService () {
        try (FileInputStream fileIn = new FileInputStream("dados/objs/lista.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            list = (List) in.readObject();  // Corrigido: não redeclarar
            System.out.println("Lista carregada com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo de lista não encontrado, iniciando nova lista.");
            list = new List();
        }
    }

    public void addNode(String dado) {
        list.addNode(dado);
    }

    public void showList() {
        list.showList();
    }

    public void removeNode(String dado) {
        list.removeNode(dado);
    }

    public void saveList() {
        try (FileOutputStream fileOut = new FileOutputStream("dados/objs/lista.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(list);  // serializa o objeto lista completo
                System.out.println("Lista salva com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
