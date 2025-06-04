package Lista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class List implements Serializable{
    Node start;

    public void addNode(String dado, String data) {
        Node newNode = new Node(dado, data);

        if (start == null) {
            start = newNode;
            return; 
        } else {
            Node temp = start;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void showList() {
        Node temp = start;
        
        while (temp != null) {
            if (temp.next == null) {
                System.out.print(temp.data+" - " + temp.date + "\n");  // Falta formatar data
            } else {
                System.out.print(temp.data+", - " + temp.date);  // Falta formatar data
            }
            temp = temp.next;
        }

        System.out.println("Lista impressa com suceso!");
    }

    public int countList() {
        if (start == null) {
            return 0;
        } else {
            Node temp = start;
            int count = 0;
            while (temp != null) {
                count++;
                temp = temp.next;
            }
            return count;
        }
    }
    
    public String chooseFile(int count) {
        Scanner scanner = new Scanner(System.in);
        if (count == 0 || start == null) {
            System.out.println("Lista vazia, não há arquivos escolhidos!");
            return null;
        }

        Node temp = start;
        int i = 1;
        while (temp != null) {
            System.out.printf("%d. %s - %s\n", i++, temp.data, temp.date); // FORMATAR DATA
            temp = temp.next;
        }

        System.out.print("Escolha o índice do arquivo (0 para cancelar): ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida, operação cancelada.");
            return null;
        }

        if (choice == 0) {
            System.out.println("Operação cancelada.");
            return null;
        }

        if (choice < 0 || choice >= count) {
            System.out.println("Escolha inválida, operação cancelada.");
            return null;
        }

        // Buscar o Node correspondente ao índice escolhido
        temp = start;
        for (i = 0; i < choice; i++) {
            temp = temp.next;
        }

        return temp.data; // Retorna o "título" do txt (nome do arquivo)
    }

    public void chosenFile(String title) {
        File file = new File("dados/txt/"+title);
        if (file.exists()) {
            File fileComprimido = new File("dados/zip/"+title);
            System.out.println(file.getName() + " - " + file.length() + " bytes");
            
            // Leitura do conteúdo do arquivo
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linha;
                // Imprime o conteúdo do arquivo
                while ((linha = reader.readLine()) != null) {
                    System.out.println("\""+linha+"\"");
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            }

            // Verifica se existe versão comprimida
            if (fileComprimido.exists()) {
                System.out.println("Tamanho comprimido: " + fileComprimido.length() + " bytes");
            }
            
        } else {
            System.out.println("Arquivo não encontrado!");
        }
    }

    public void removeNode(String dado) {
        if (start == null) {
            System.out.println("Lista vazia impossivel remover No!");
            return;
        }
        if (start.data.equals(dado)) {
            start = start.next;
            System.out.println("No removido com sucesso (start)");
        } else {
            Node temp = start, prev = null;
            while (temp != null) {
                if (temp.data.equals(dado)) {
                    prev.next = temp.next;
                    System.out.println("No removido com sucesso");
                    return;
                } else {
                    prev = temp;
                    temp = temp.next;
                }
            }
            System.out.println("No com esse dado não encontrado!");
        }
    }
}
