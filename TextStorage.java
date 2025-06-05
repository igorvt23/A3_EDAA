import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextStorage {
    
    public void saveTxt(String title, String content) {
        try {
            FileWriter writer = new FileWriter("dados/txt/"+title+".txt");
            writer.write(content);
            writer.close();
            System.out.println("Documento criado com sucesso!");
        } catch (IOException e) {
            System.out.println("Falha ao criar txt!");
            e.printStackTrace();
        }
    }

    public void deleteTxt(String text) {
        File file = new File("dados/txt/"+text+".txt");

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Txt deletado com sucesso!");
            } else {
                System.out.println("Falha ao deletar arquivo!");
            }
        } else {
            System.out.println("Arquivo não encontrado!");
        }
    }

    // talvez valha a pena criar um objeto doc para essa ocasião
    public String readTxt(String title) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader leitor = new BufferedReader(new FileReader("dados/txt/"+title+".txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                content.append(linha).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        if (content.length() > 0) content.setLength(content.length() - 1); // Remove o último '\n'
        return content.toString();
    }

    // Ler o arquivo
    // Criar um obj e retornar com os atributos do txt para as outras classes trabalharem com ele
    // Criando um novo txt comprimido
}
