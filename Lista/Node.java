package Lista;

import java.io.Serializable;;

public class Node implements Serializable{
    public String data; //Nome
    public String date; //Data de criação
    public long size; // Tamanho do arquivo .TXT
    public long size_zip; // Tamanho do arquivo comprimido
    public Node next;   // Próximo nó na lista

    Node (String data, String date, long size, long size_zip) {
        this.size_zip = size_zip;
        this.size = size;
        this.data = data;
        this.date = date;
        this.next = null;
    }
}
