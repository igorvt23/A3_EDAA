package Lista;

import java.io.Serializable;

public class Node implements Serializable{
    String data;
    String date;
    Node next;

    Node (String data, String date) {
        this.data = data;
        this.date = date;
        this.next = null;
    }
}
