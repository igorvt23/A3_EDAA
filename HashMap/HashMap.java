package HashMap;
import java.io.Serializable;

public class HashMap implements Serializable {
    public listMap[] buckets;
    private int size = 51;

    public HashMap() {
        buckets = new listMap[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new listMap();
        }
    }
    // Classes auxiliares (MALDITO CHAT)
    static class TrechoNode implements Serializable {
        String trecho;
        TrechoNode next;
    }

    static class TitleGroup implements Serializable {
        String title;
        int count = 0;
        TrechoNode trechos = null;
        TitleGroup next = null;
    }

    public int mapCode(String word) {
        int hash = word.hashCode();
        return Math.abs(hash % buckets.length);
    }

    public void addWord(String word, String title, String ocur) {
        int pos = mapCode(word);
        buckets[pos].addNode(word, title, ocur);
    }

    public void removeWord(String word, String title) {
        int pos = mapCode(word);
        buckets[pos].removeNode(word, title);
    }

    public String hashArray() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < buckets.length; i++) {
            listMap list = buckets[i];
            NodeMap temp = list.start;
            if (temp == null) continue;
            builder.append(String.format("POS: [%03d] -> ", i));
            while (temp != null) {
                builder.append((temp == list.start ? "" : " -> ") + "[" + temp.word + " : " + temp.ocur.count + "]");
                temp = temp.next;
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public String wordOccurrences(String word) {
        StringBuilder builder = new StringBuilder();
        int code = mapCode(word);
        listMap map = buckets[code];

        NodeMap temp = map.start;
        while (temp != null && !temp.word.equals(word)) {
            temp = temp.next;
        }

    
        if (temp != null) {
            builder.append(String.format("Palavra: [%s] | Pos no Hash: %03d | Ocorencias: %d:\n", word, code, temp.ocur.count));

            // Agrupar por título
            TitleGroup grupos = null;

            NodeOcur ocur = temp.ocur.start;
            while (ocur != null) {
                TitleGroup g = grupos, anterior = null;
                while (g != null && !g.title.equals(ocur.title)) {
                    anterior = g;
                    g = g.next;
                }

                if (g == null) {
                    g = new TitleGroup();
                    g.title = ocur.title;
                    g.count = 1;
                    g.trechos = new TrechoNode();
                    g.trechos.trecho = ocur.ocur;

                    if (anterior == null) {
                        grupos = g;
                    } else {
                        anterior.next = g;
                    }
                } else {
                    g.count++;
                    TrechoNode novo = new TrechoNode();
                    novo.trecho = ocur.ocur;
                    TrechoNode t = g.trechos;
                    while (t.next != null) t = t.next;
                    t.next = novo;
                }

                ocur = ocur.next;
            }

            // Converter lista em vetor
            int totalGrupos = 0;
            TitleGroup g = grupos;
            while (g != null) {
                totalGrupos++;
                g = g.next;
            }

            TitleGroup[] vetor = new TitleGroup[totalGrupos];
            g = grupos;
            for (int i = 0; i < totalGrupos; i++) {
                vetor[i] = g;
                g = g.next;
            }

            // Heap Sort por count
            for (int i = totalGrupos / 2 - 1; i >= 0; i--) heapify(vetor, totalGrupos, i);
            for (int i = totalGrupos - 1; i > 0; i--) {
                TitleGroup tempGroup = vetor[0];
                vetor[0] = vetor[i];
                vetor[i] = tempGroup;
                heapify(vetor, i, 0);
            }

            // Imprimir ordenado
            for (int i = totalGrupos - 1; i >= 0; i--) {
                g = vetor[i];
                builder.append(String.format("\n [%s] | Num ocorrencias %d:\n", g.title, g.count));
                TrechoNode t = g.trechos;
                while (t != null) {
                    builder.append(String.format("   └> '...%s...'\n", t.trecho));
                    t = t.next;
                }
            }

            return builder.toString();
        } else {
            builder.append("Palavra nao encontrada\n");
            return builder.toString();
        }
    }

    public void heapify(TitleGroup[] vetor, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        if (esq < n && vetor[esq].count > vetor[maior].count) maior = esq;
        if (dir < n && vetor[dir].count > vetor[maior].count) maior = dir;

        if (maior != i) {
            TitleGroup temp = vetor[i];
            vetor[i] = vetor[maior];
            vetor[maior] = temp;
            heapify(vetor, n, maior);
        }
    }
}