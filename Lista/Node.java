package Lista;

import java.io.Serializable;;

public class Node implements Serializable{
    String data;
    Node next;

    Node (String data) {
        this.data = data;
        this.next = null;
    }
}
