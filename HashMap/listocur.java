package HashMap;
import java.io.Serializable;

public class listocur implements Serializable {
    NodeOcur start;
    int count;

    public void addNode(String title, String ocur) {
        NodeOcur newNode = new NodeOcur(title, ocur);
        count++;
        if (start == null) {
            start = newNode;
            return; 
        } else {
            NodeOcur temp = start;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void removeAllNodes(String title) {
        while (start != null && start.title.equals(title)) {
            count--;
            start = start.next;
        }
        
        if (start == null) return;
        
        NodeOcur temp = start;
        
        while (temp.next != null) {
            if (temp.next.title.equals(title)) {
                count--;
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
    }
}
