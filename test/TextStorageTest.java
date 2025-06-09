import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Importa todos os métodos estáticos de Assertions
import org.junit.jupiter.api.BeforeEach; // Para configurar coisas antes de cada teste
import java.io.File; // Para verificar a existência do arquivo

// Esta classe de teste também está no pacote padrão (sem 'package ...;')
// e, portanto, pode "enxergar" TextStorage diretamente.
public class TextStorageTest {

    private TextStorage textStorage; // A instância da classe que vamos testar
    private final String TEST_FILE_NAME = "test_file_to_delete";
    private final String TEST_FILE_PATH = "dados/txt/" + TEST_FILE_NAME + ".txt";

    @BeforeEach // Este método será executado antes de cada teste
    void setUp() {
        textStorage = new TextStorage();
        // Garante que o diretório 'dados/txt' exista para que os testes não falhem por isso
        new File("dados/txt/").mkdirs();
        // Cria um arquivo de teste antes de cada método de teste, para que deleteTxt possa agir sobre ele
        textStorage.saveTxt(TEST_FILE_NAME, "Conteúdo de teste para exclusão.");
        assertTrue(new File(TEST_FILE_PATH).exists(), "O arquivo de teste deve existir antes da exclusão.");
    }

    @Test
    void testDeleteTxtSuccess() {
        // Testa a exclusão bem-sucedida de um arquivo existente
        textStorage.deleteTxt(TEST_FILE_NAME);

        // Verifica se o arquivo foi realmente deletado
        assertFalse(new File(TEST_FILE_PATH).exists(), "O arquivo deveria ter sido deletado.");
    }

    @Test
    void testDeleteTxtNotFound() {
        // Testa a tentativa de deletar um arquivo que não existe
        // Para isso, deletamos o arquivo criado no setup antes de chamar o método de teste
        new File(TEST_FILE_PATH).delete(); // Deleta o arquivo criado pelo @BeforeEach

        // Captura a saída do console para verificar a mensagem
        // (Isso é um pouco mais avançado, mas útil para testar System.out.println)
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        textStorage.deleteTxt("non_existent_file"); // Tenta deletar um arquivo que não existe

        // Restaura a saída padrão
        System.setOut(System.out);

        // Verifica se a mensagem de "Arquivo não encontrado!" foi impressa
        assertTrue(outContent.toString().contains("Arquivo não encontrado!"), "Deveria imprimir 'Arquivo não encontrado!'");
        assertFalse(new File("dados/txt/non_existent_file.txt").exists(), "O arquivo inexistente não deveria existir.");
    }

    // Você também pode adicionar um @AfterEach para limpar depois de cada teste,
    // mas o @BeforeEach já cria um novo arquivo para cada teste.
    // Se preferir uma limpeza explícita:
    // @AfterEach
    // void tearDown() {
    //     new File(TEST_FILE_PATH).delete(); // Tenta deletar o arquivo de teste no final
    // }
}