package btreeplus;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InvertedIndex {

    private Map<String, Map<String, Integer>> index = new HashMap<>();

    // class que guarda o nome do documento e quantas vezes a palavra aparece nele
    // node
    private static class DocFreq {
        String docName;
        int frequency;

        DocFreq(String docName, int frequency) {
            this.docName = docName;
            this.frequency = frequency;
        }
    }

    // na teoria adiciona um documento ao indice
    public void indexDocument(String docName, String content) {
        String cleaned = cleanText(content);
        String[] tokens = cleaned.split("\\s+");

        for (String word : tokens) {
            if (!word.isEmpty()) {
                word = word.toLowerCase();
                index.putIfAbsent(word, new HashMap<>());
                Map<String, Integer> docMap = index.get(word);
                docMap.put(docName, docMap.getOrDefault(docName, 0) + 1);
            }
        }
    }

    // usa o heap para buscar uma palavra e mostra documentos ordenados por frequencia
    public void search(String word) {
        word = word.toLowerCase();
        if (!index.containsKey(word)) {
            System.out.println("Palavra não encontrada");
            return;
        }

        System.out.println("Documentos para a palavra: " + word);
        Map<String, Integer> docs = index.get(word);
        DocFreq[] docArray = new DocFreq[docs.size()];

        int i = 0;
        for (Map.Entry<String, Integer> entry : docs.entrySet()) {
            docArray[i++] = new DocFreq(entry.getKey(), entry.getValue());
        }

        heapSort(docArray);

        for (int j = docArray.length - 1; j >= 0; j--) {
            System.out.println("- " + docArray[j].docName + " (x" + docArray[j].frequency + ")");
        }
    }

    // grava o índice no arquivo e deixa as palavras em ordem
    public void saveIndexToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            index.keySet().stream().sorted().forEach(word -> {
                try {
                    writer.write(word + ": ");
                    for (Map.Entry<String, Integer> entry : index.get(word).entrySet()) {
                        writer.write(entry.getKey() + "(" + entry.getValue() + ") ");
                    }
                    writer.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Indice salvo em: " + fileName);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o indice.");
            e.printStackTrace();
        }
    }
    
    private String cleanText(String text) {
        return text.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();
    }

    //comeco do heap sort
    private void heapSort(DocFreq[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            DocFreq temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    private void heapify(DocFreq[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l].frequency > arr[largest].frequency) {
            largest = l;
        }

        if (r < n && arr[r].frequency > arr[largest].frequency) {
            largest = r;
        }

        if (largest != i) {
            DocFreq swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }
}