package Lista;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ListService {
    public List list;

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

    public void addNode(String dado, String data, long size) {
        list.addNode(dado, data, size);
    }

    // Opção 2 do menu principal
    public String showList() {
        

        //Contando os arquivos
        int totalArquivos = list.countList();

        if (totalArquivos > 0) {
            // Listando os arquivos
            // Segundo menu para remover, recomprimir ou visualizar o índice
            // Retorna o nome do arquivo escolhido
            String arquivo = list.showList(totalArquivos);

            if (arquivo != null) {
                //Printa principais infos do arquivo
                list.chosenFile(arquivo);

                return arquivo;
            } else {
                return null;
            }
        } else {
            return null;
        }
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

    public String getArquivo(int index){
        return list.showList(index);
    }

    public void addSizeFileCompressed(String nomeArquivo, long compressSize) {
        list.addSizeFileCompressed(nomeArquivo, compressSize);
    }

}
