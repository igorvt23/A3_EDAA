package Lista;

public class SortingAlgorithms {

    public static void selectionSort(Node[] arr, String criterio) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (comparar(arr[j], arr[minIdx], criterio) < 0) {
                    minIdx = j;
                }
            }
            Node temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    public static void mergeSort(Node[] arr, String criterio) {
        if (arr.length < 2) return;
        int mid = arr.length / 2;
        Node[] left = new Node[mid];
        Node[] right = new Node[arr.length - mid];

        for (int i = 0; i < mid; i++) left[i] = arr[i];
        for (int i = mid; i < arr.length; i++) right[i - mid] = arr[i];

        mergeSort(left, criterio);
        mergeSort(right, criterio);
        merge(arr, left, right, criterio);
    }

    private static void merge(Node[] arr, Node[] left, Node[] right, String criterio) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (comparar(left[i], right[j], criterio) <= 0) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }

    public static void quickSort(Node[] arr, int low, int high, String criterio) {
        if (low < high) {
            int pi = partition(arr, low, high, criterio);
            quickSort(arr, low, pi - 1, criterio);
            quickSort(arr, pi + 1, high, criterio);
        }
    }

    private static int partition(Node[] arr, int low, int high, String criterio) {
        Node pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparar(arr[j], pivot, criterio) <= 0) {
                i++;
                Node temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Node temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void heapSort(Node[] arr, String criterio) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, criterio);
        }
        for (int i = n - 1; i >= 0; i--) {
            Node temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0, criterio);
        }
    }

    private static void heapify(Node[] arr, int n, int i, String criterio) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && comparar(arr[l], arr[largest], criterio) > 0)
            largest = l;
        if (r < n && comparar(arr[r], arr[largest], criterio) > 0)
            largest = r;

        if (largest != i) {
            Node swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest, criterio);
        }
    }

    public static int comparar(Node a, Node b, String criterio) {
        switch (criterio) {
            case "nome":
                return a.name.compareToIgnoreCase(b.name);
            case "data":
                return a.date.compareTo(b.date);
            case "tamanho":
                return Long.compare(a.size, b.size);
            case "compactado":
                return Long.compare(a.size_zip, b.size_zip);
            default:
                throw new IllegalArgumentException("Critério inválido: " + criterio);
        }
    }
}