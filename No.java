// classe No
import java.io.Serializable;

public class No implements Serializable {
    String indice;
    String conteudo;
    String data;
    long tamanho;
    No next;

    No (String indice, String conteudo, String data, long tamanho) {
        this.data = data;
        this.conteudo = conteudo;
        this.indice = indice;
        this.tamanho = tamanho;
        this.next = null;
    }
}