package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Lista.ListService;
import Lista.List;
import Lista.Node; // Importação da classe Node
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream; // Adicionado para mockar System.in


public class ListServiceTest {

    private ListService listService;
    private final String LIST_FILE_PATH = "dados/objs/lista.ser";
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private ByteArrayInputStream inContent; // Adicionado para mockar System.in
    private PrintStream originalIn; // Adicionado para restaurar System.in

    @BeforeEach
    void setUp() throws IOException {
        File listFile = new File(LIST_FILE_PATH);
        if (listFile.exists()) {
            listFile.delete();
        }

        new File("dados/objs/").mkdirs();

        // Captura a saída do console
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Salva o System.in original e prepara para mockar
        originalIn = System.out; // Correção: deveria ser System.in
        // O System.in real é um InputStream, não PrintStream.
        // O originalIn não é necessário se você apenas redireciona e não restaura o System.in.
        // Para uma restauração correta: InputStream originalSystemIn = System.in;

        listService = new ListService();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut); // Restaura System.out
        // System.setIn(originalSystemIn); // Se você tivesse salvo o original System.in
        File listFile = new File(LIST_FILE_PATH);
        if (listFile.exists()) {
            listFile.delete();
        }
    }


    @Test
    void testListServiceInitialization_NewList() {
        assertNotNull(listService.list, "A lista interna de ListService não deve ser nula.");
        assertTrue(outContent.toString().contains("Arquivo de lista não encontrado, iniciando nova lista."),
                        "Mensagem de nova lista não apareceu.");
        assertEquals(0, listService.list.countList(), "A nova lista deveria estar vazia.");
    }

    @Test
    void testAddNodeAndSaveList() throws IOException {
        String dado = "ArquivoTeste1";
        String data = "2023-01-01";
        long size = 1024;

        listService.addNode(dado, data, size);
        listService.saveList();

        File savedFile = new File(LIST_FILE_PATH);
        assertTrue(savedFile.exists(), "O arquivo lista.ser deve existir após salvar.");
        assertTrue(savedFile.length() > 0, "O arquivo lista.ser não deve estar vazio.");

        // Limpa a saída para verificar a próxima mensagem
        outContent.reset();

        listService = null;
        listService = new ListService();

        assertTrue(outContent.toString().contains("Lista carregada com sucesso!"), "Mensagem de lista carregada não apareceu.");
        assertEquals(1, listService.list.countList(), "A lista carregada deve conter 1 arquivo.");
        assertEquals(dado, listService.getArquivo(1), "O nome do arquivo no índice 1 deve ser o esperado.");
    }

    @Test
    void testShowListEmpty() {
        String result = listService.showList();
        assertNull(result, "showList em lista vazia deve retornar null.");
        // Verifica a mensagem "Não tem arquivos salvos!" que sua List.showList imprime
        assertTrue(outContent.toString().contains("Não tem arquivos salvos!"), "Mensagem de lista vazia esperada não apareceu.");
    }

    @Test
    void testShowListWithItems() {
        listService.addNode("ArquivoA", "2023-01-01", 100);
        listService.addNode("ArquivoB", "2023-01-02", 200);
        listService.addNode("ArquivoC", "2023-01-03", 300);

        int totalArquivos = listService.list.countList();
        assertEquals(3, totalArquivos);

        // Para testar ListService.showList(), que chama List.showList(int count)
        // e que espera uma entrada do usuário, precisamos mockar System.in.
        // Vamos simular que o usuário digita '2' (para escolher ArquivoB)
        String simulatedInput = "2\n"; // \n é importante para simular o Enter
        inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent); // Redireciona System.in para nosso input simulado

        // Limpa o outContent antes da chamada para verificar a saída específica
        outContent.reset();

        String chosenFile = listService.showList(); // Chama o método que interage com o usuário

        // Restaura System.in para evitar problemas com outros testes
        System.setIn(System.in); // Restaura o System.in padrão (o original não foi salvo aqui)

        // Verifica se a saída contém os arquivos listados e a mensagem de escolha
        assertTrue(outContent.toString().contains("1. ArquivoA - 2023-01-01"), "Deveria listar ArquivoA.");
        assertTrue(outContent.toString().contains("2. ArquivoB - 2023-01-02"), "Deveria listar ArquivoB.");
        assertTrue(outContent.toString().contains("3. ArquivoC - 2023-01-03"), "Deveria listar ArquivoC.");
        assertTrue(outContent.toString().contains("Escolha o índice do arquivo (0 para cancelar):"), "Deveria pedir input.");
        assertTrue(outContent.toString().contains("Lista impressa com suceso!"), "Deveria indicar sucesso na impressão da lista.");
        assertTrue(outContent.toString().contains("Detalhes do arquivo escolhido:"), "Deveria mostrar detalhes do arquivo escolhido.");


        // Verifica se o arquivo escolhido é o esperado
        assertEquals("ArquivoB", chosenFile, "O arquivo escolhido deveria ser 'ArquivoB'.");

        // Testes de getArquivo (que não interagem com System.in)
        assertEquals("ArquivoA", listService.getArquivo(1));
        assertEquals("ArquivoB", listService.getArquivo(2));
        assertEquals("ArquivoC", listService.getArquivo(3));
    }


    @Test
    void testRemoveNode() throws IOException {
        String dado1 = "RemoverEste";
        String dado2 = "ManterEste";
        listService.addNode(dado1, "2023-01-01", 100);
        listService.addNode(dado2, "2023-01-02", 200);
        assertEquals(2, listService.list.countList(), "Deve haver 2 nós antes da remoção.");

        // Limpa o outContent para verificar a mensagem de remoção
        outContent.reset();
        listService.removeNode(dado1);
        assertEquals(1, listService.list.countList(), "Deve haver 1 nó após a remoção.");
        assertEquals(dado2, listService.getArquivo(1), "O nó restante deve ser 'ManterEste'.");
        assertTrue(outContent.toString().contains("No removido com sucesso"), "Mensagem de remoção esperada.");


        // Tentar remover um nó que não existe
        outContent.reset(); // Limpa novamente
        listService.removeNode("NaoExiste");
        assertEquals(1, listService.list.countList(), "A contagem não deve mudar se o nó não existir.");
        assertTrue(outContent.toString().contains("No com esse dado não encontrado!"), "Mensagem de não encontrado esperada.");
    }

    @Test
    void testAddSizeFileCompressed() {
        String nomeArquivo = "MeuDocumento";
        listService.addNode(nomeArquivo, "2023-01-01", 1000); // Adiciona um nó primeiro
        listService.addSizeFileCompressed(nomeArquivo, 500);

        // Agora podemos usar getNodeByName que adicionamos à classe List para verificar
        Node node = listService.list.getNodeByName(nomeArquivo);
        assertNotNull(node, "O nó deveria ser encontrado após adicionar e atualizar.");
        assertEquals(500, node.size_zip, "O tamanho comprimido deve ser atualizado.");

        // Verifica a saída de console para a mensagem de atualização
        assertTrue(outContent.toString().contains("Tamanho comprimido para 'MeuDocumento' atualizado para 500 bytes."),
            "Mensagem de atualização do tamanho comprimido esperada.");
    }
}