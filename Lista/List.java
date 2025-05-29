package Lista;

import java.io.Serializable;

public class List implements Serializable{
    Node start;

    public void addNode(String dado) {
        Node newNode = new Node(dado);

        if (start == null) {
            start = newNode;
            return; 
        } else {
            Node temp = start;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void showList() {
        Node temp = start;
        
        while (temp != null) {
            if (temp.next == null) {
                System.out.print(temp.data+"\n");  
            } else {
                System.out.print(temp.data+", ");
            }
            temp = temp.next;
        }

        System.out.println("Lista impressa com suceso!");
    }

    public void removeNode(String dado) {
        if (start == null) {
            System.out.println("Lista vazia impossivel remover No!");
            return;
        }
        if (start.data.equals(dado)) {
            start = start.next;
            System.out.println("No removido com sucesso (start)");
        } else {
            Node temp = start, prev = null;
            while (temp != null) {
                if (temp.data.equals(dado)) {
                    prev.next = temp.next;
                    System.out.println("No removido com sucesso");
                    return;
                } else {
                    prev = temp;
                    temp = temp.next;
                }
            }
            System.out.println("No com esse dado não encontrado!");
        }
    }
}
