import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextStorage {
    
    public void saveTxt(String title, String content) {
        try {
            FileWriter writer = new FileWriter("dados/txt/"+title);
            writer.write(content);
            writer.close();
            System.out.println("Documento criado com sucesso!");
        } catch (IOException e) {
            System.out.println("Falha ao criar txt!");
            e.printStackTrace();
        }
    }

    public void deleteTxt(String text) {
        File file = new File("dados/txt/"+text);

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

    // Ler o arquivo
    // Criar um obj e retornar com os atributos do txt para as outras classes trabalharem com ele
    // Criando um novo txt comprimido
}
