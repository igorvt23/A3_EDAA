// classe No
import java.io.Serializable;

public class No implements Serializable {
    String indice;
    No next;

    No (String indice) {
        this.indice = indice;
        this.next = null;
    }
}