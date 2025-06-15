package HashMap;
import java.io.Serializable;

public class NodeMap implements Serializable {
    public String word;
    public listocur ocur;
    public NodeMap next;

    public NodeMap(String word) {
        this.word = word;
        this.next = null;
        this.ocur = new listocur();
    }
}
