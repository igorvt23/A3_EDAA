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

    public String wordOccurrences(String word) {
        StringBuilder builder = new StringBuilder();
        int code = mapCode(word);
        listMap map = buckets[code];
        if (map.start == null) builder.append("Palavra nao encontrada");
        NodeMap temp = map.start;
        while (temp != null && !temp.word.equals(word)) {
            temp = temp.next;
        }
        if (temp != null) {
            builder.append(String.format("POS: [%03d] OCUR: [%d] -> %s:\n", code, temp.ocur.count, word));
            NodeOcur ocur = temp.ocur.start;
            while (ocur != null) {
                builder.append(String.format("└> [%s] ─> " + "'...%s...'\n", ocur.title, ocur.ocur));
                ocur = ocur.next;
            }
        } else {
            builder.append("Palavra nao encontrada");
        }
        return builder.toString();
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
}
