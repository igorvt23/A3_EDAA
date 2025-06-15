package HashMap;
import java.io.Serializable;

public class listMap implements Serializable {
    NodeMap start;

    public void addNode(String word, String txt, String ocur) {
        if (start == null) {
            NodeMap newNode = new NodeMap(word); 
            start = newNode;
            start.ocur.addNode(txt, ocur);
            return;
        }

        NodeMap temp = start;
        while (temp != null) {
            if (temp.word.equals(word)) {
                temp.ocur.addNode(txt, ocur);
                return;
            }
            if (temp.next == null) break;
            temp = temp.next;
        }

        temp.next = new NodeMap(word);
        temp.next.ocur.addNode(txt, ocur);
    }

    public void removeNode(String word, String title) {
        if (start == null) return;
        
        if (start.word.equals(word)) {
            start.ocur.removeAllNodes(title);
            if (start.ocur.count <= 0) {
                start = start.next;
            }
            return;
        }

        NodeMap temp = start.next, prev = start;

        while (temp != null) {
            if (temp.word.equals(word)) {
                temp.ocur.removeAllNodes(title);
                if (temp.ocur.count <= 0) {
                    prev.next = temp.next;
                }
                return;
            }
            prev = temp;
            temp = temp.next;
        }
    }
}
