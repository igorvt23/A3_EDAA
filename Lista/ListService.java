package Lista;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ListService {
    public List list;

    public ListService() {
        try (FileInputStream fileIn = new FileInputStream("dados/objs/lista.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            list = (List) in.readObject();
            System.out.println("Lista carregada com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Arquivo de lista não encontrado, iniciando nova lista.");
            list = new List();
        }
    }

    public void addNode(String dado, String data, long size, long sizeCompressed) {
        list.addNode(dado, data, size, sizeCompressed);
    }
    
    public void removeNode(String dado) {
        list.removeNode(dado);
    }


    public String showList() {
        return list.showList();
    }

    public String saveList() {
        try (FileOutputStream fileOut = new FileOutputStream("dados/objs/lista.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(list);
            return "Lista salva com sucesso!";
        } catch (IOException e) {
            return "Problemas ao salvar a lista!";
        }
    }

    public String showListOPICIONAL(Scanner scanner) {
        int totalArquivos = list.countList();

        if (totalArquivos > 0) {
            String arquivo = list.showListOPCIONAL(totalArquivos, scanner);
            if (arquivo != null) {
                list.chosenFile(arquivo);
                return arquivo;
            }
        } else {
            System.out.println("Não tem arquivos salvos!");
        }

        return null;
    }
    @Deprecated
    public String getArquivo(int index) {
        return getArquivoByIndex(index);
    }

    // Usa método da classe List
    public String getArquivoByIndex(int index) {
        Node node = list.getNodeByIndex(index);
        return node != null ? node.name : null;
    }

    public Node getNodeIndex(int index) {
        Node node = list.getNodeByIndex(index);
        return node;
    }

    public int getCount() {
        return list.countList();
    }

    public void addSizeFileCompressed(String nomeArquivo, long compressSize) {
        list.addSizeFileCompressed(nomeArquivo, compressSize);
    }

    public Node getStart() {
        return list.getStar();
    }
}
