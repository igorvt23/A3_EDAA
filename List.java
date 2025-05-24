//Lista encadeada simples
import java.io.Serializable;

public class List implements Serializable {
    No start;

    public void addNode(String dado) {
        No newNode = new No(dado);

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
            System.out.println("=========================");
            System.out.println("Lista Impresa");
        }
    }
}