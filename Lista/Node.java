package Lista;

import java.io.Serializable;

public class Node implements Serializable{
    public String name;     //Nome
    public String date;     //Data de criação
    public long size;       // Tamanho do arquivo .TXT
    public long size_zip;   // Tamanho do arquivo comprimido
    public Node next;       // Próximo nó na lista

    Node (String name, String date, long size, long size_zip) {
        this.size_zip = size_zip;
        this.size = size;
        this.name = name;
        this.date = date;
        this.next = null;
    }
}
