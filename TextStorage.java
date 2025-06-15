import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


// LIDA DIRETAMENTE COM OS ARQUIVOS TXT
public class TextStorage {
    // salva txt
    public String saveTxt(String title, String content) {
        try {
            FileWriter writer = new FileWriter("dados/txt/"+title+".txt");
            writer.write(content);
            writer.close();
            return "Documento criado com sucesso!";
        } catch (IOException e) {
            return "Falha ao criar txt!";
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

    public void deleteGz(String text) {
        File file = new File("dados/zip/"+text+".gz");

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Gz deletado com sucesso!");
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
}
