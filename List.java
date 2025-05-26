//Lista encadeada simples
import java.io.File;
import java.io.Serializable;

public class List implements Serializable {
    No start;

    public void addNode(String dado, String conteudo, String data, long tamanho) {
        No newNode = new No(dado, conteudo, data, tamanho);

        if (start == null) {
            start = newNode;
        } else {
            No temp = start;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void showList() {
        if (start == null) {
            System.out.println("=========================");
            System.out.println("= Não possui Documentos =");
            System.out.println("=========================");
            return;
        } else {
            No temp = start;
            int i = 0;
            System.out.println("=========================");
            while (temp != null) {
                System.out.printf("No %d: %s\n", i++, temp.indice);
                temp = temp.next;
            }
            System.out.println("====================================");
            System.out.println("Lista Impresa");
        }
    }

    public int size() {
        if (start == null) {
            return 0;
        } else {
            No temp = start;
            int i = 0;
            while (temp != null) {
                i++;
                temp = temp.next;
            }
            return i;
        }
    }
    
    public boolean showEspecifico(int idx) {
        if (start == null) {
            System.out.println("=========================");
            System.out.println("= Não possui este índice =");
            System.out.println("=========================");
            return false;
        } else {
            No temp = start;
            int i = 1;
            while (temp != null) {
                File f = new File("dados/" + temp.indice);
                long tamanhoAtual = f.length();
                if (i == idx) {
                    System.out.println("==============================");
                    System.out.println("Nome do arquivo: " + temp.indice);
                    System.out.println("Conteúdo do arquivo: " + temp.conteudo);
                    System.out.println("Data de criação: " + temp.data);
                    System.out.println("Tamanho Original: " + temp.tamanho + " bytes");
                    if (tamanhoAtual < temp.tamanho) {
                        System.out.println("Tamanho Compactado: " + tamanhoAtual + " bytes");
                    } else {
                        System.out.println("Tamanho Compactado: Não Compactado");
                    }
                    System.out.println("====================================");
                    return true;
                }
                i++;
                temp = temp.next;
            }
            System.out.println("====================================");
            System.out.println("Lista Impresa");
            return false;
        }
    }

}