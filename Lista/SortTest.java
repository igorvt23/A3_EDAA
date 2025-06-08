package Lista;

import java.io.*;
import java.text.SimpleDateFormat;

public class SortTest {

    public static void main(String[] args) throws IOException {
        File pastaTXT = new File("dados/txt");
        File pastaZIP = new File("dados/zip");

        File[] arquivos = pastaTXT.listFiles((dir, name) -> name.endsWith(".txt"));
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo .txt encontrado.");
            return;
        }

        Node[] nodes = new Node[arquivos.length];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < arquivos.length; i++) {
            File txt = arquivos[i];
            String nome = txt.getName();
            String dataCriacao = sdf.format(txt.lastModified());
            long tamanhoTXT = txt.length();

            File gz = new File(pastaZIP, nome.replace(".txt", ".gz"));
            long tamanhoZIP = gz.exists() ? gz.length() : -1;

            nodes[i] = new Node(nome, dataCriacao, tamanhoTXT, tamanhoZIP);
        }

        // Executa comparações para os três critérios
        compararAlgoritmos(nodes, "tamanho");
        System.out.println("\n============================\n");
        compararAlgoritmos(nodes, "data");
        System.out.println("\n============================\n");
        compararAlgoritmos(nodes, "nome");  // ordenação alfabética
    }

    public static void compararAlgoritmos(Node[] original, String criterio) {
        System.out.println("Comparando algoritmos para o critério: " + criterio.toUpperCase() + "\n");

        String[] algoritmos = { "selection", "merge", "quick", "heap" };
        double[] tempos = new double[algoritmos.length];
        Node[][] resultados = new Node[algoritmos.length][];

        for (int i = 0; i < algoritmos.length; i++) {
            Node[] copia = new Node[original.length];
            System.arraycopy(original, 0, copia, 0, original.length);

            long inicio = System.nanoTime();
            switch (algoritmos[i]) {
                case "selection" -> SortingAlgorithms.selectionSort(copia, criterio);
                case "merge"    -> SortingAlgorithms.mergeSort(copia, criterio);
                case "quick"    -> SortingAlgorithms.quickSort(copia, 0, copia.length - 1, criterio);
                case "heap"     -> SortingAlgorithms.heapSort(copia, criterio);
            }
            long fim = System.nanoTime();
            tempos[i] = (fim - inicio) / 1e6;
            resultados[i] = copia;

            System.out.printf("Tempo (%s): %.4f ms%n", algoritmos[i], tempos[i]);
        }

        // Encontra o mais rápido
        int melhorIdx = 0;
        for (int i = 1; i < tempos.length; i++) {
            if (tempos[i] < tempos[melhorIdx]) {
                melhorIdx = i;
            }
        }

        System.out.printf("\nAlgoritmo mais rápido: %s (%.4f ms)%n\n",
                algoritmos[melhorIdx], tempos[melhorIdx]);

        // Imprime o resultado ordenado
        for (Node n : resultados[melhorIdx]) {
            String tamTXT = formatarTamanho(n.size);
            String tamGZ = (n.size_zip >= 0) ? formatarTamanho(n.size_zip) : "--";
            System.out.printf("Nome: %-30s | Data: %s | TXT: %6s | GZ: %6s%n",
                    n.data, n.date, tamTXT, tamGZ);
        }
    }

    public static String formatarTamanho(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char prefix = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), prefix);
    }
}