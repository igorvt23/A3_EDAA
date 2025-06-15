package HashMap;
import java.io.Serializable;

public class NodeOcur implements Serializable {
    String title;
    String ocur;
    NodeOcur next;

    public NodeOcur(String title, String ocur) {
        this.title = title;
        this.ocur = ocur;
        this.next = null;
    }
}
