package SalvarTXToriginal;

// classe No
import java.io.Serializable;

public class No implements Serializable {
    public String indice;
    public String conteudo;
    public String data;
    public long tamanho;
    public No next;

    public No (String indice, String conteudo, String data, long tamanho) {
        this.data = data;
        this.conteudo = conteudo;
        this.indice = indice;
        this.tamanho = tamanho;
        this.next = null;
    }
}