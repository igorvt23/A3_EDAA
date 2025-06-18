package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Lista.ListService;
import Lista.Node;
import java.io.*;
import java.util.Scanner;

public class ListServiceTest {

    private ListService listService;
    private final String LIST_FILE_PATH = "dados/objs/lista.ser";
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private InputStream originalSystemIn;
    private ByteArrayInputStream inContent;

    @BeforeEach
    void setUp() throws IOException {
        File listFile = new File(LIST_FILE_PATH);
        if (listFile.exists()) listFile.delete();

        new File("dados/objs/").mkdirs();

        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        originalSystemIn = System.in;

        listService = new ListService();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalSystemIn);

        File listFile = new File(LIST_FILE_PATH);
        if (listFile.exists()) listFile.delete();
    }

    @Test
    void testListServiceInitialization_NewList() {
        assertNotNull(listService.list);
        assertTrue(outContent.toString().contains("Arquivo de lista não encontrado"));
        assertEquals(0, listService.list.countList());
    }

    @Test
    void testAddNodeAndSaveList() throws IOException {
        String dado = "ArquivoTeste1";
        String data = "2023-01-01";
        long size = 1024;
        long compressedSize = 800;

        listService.addNode(dado, data, size, compressedSize);
        listService.saveList();

        File savedFile = new File(LIST_FILE_PATH);
        assertTrue(savedFile.exists());
        assertTrue(savedFile.length() > 0);

        outContent.reset();
        listService = new ListService();

        assertTrue(outContent.toString().contains("Lista carregada com sucesso!"));
        assertEquals(1, listService.list.countList());
        assertEquals(dado, listService.getArquivoByIndex(1));
    }

    @Test
    void testShowListEmpty() {
        Scanner scanner = new Scanner(System.in);
        String result = listService.showListOPICIONAL(scanner);
        assertNull(result);
        assertTrue(outContent.toString().contains("Não tem arquivos salvos!"));
    }

    @Test
    void testShowListWithItems() {
        listService.addNode("ArquivoA", "2023-01-01", 100L, 80L);
        listService.addNode("ArquivoB", "2023-01-02", 200L, 150L);
        listService.addNode("ArquivoC", "2023-01-03", 300L, 250L);

        String simulatedInput = "2\n";
        inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);
        Scanner scanner = new Scanner(inContent);

        outContent.reset();
        String chosenFile = listService.showListOPICIONAL(scanner);

        assertEquals("ArquivoB", chosenFile);
    }

    @Test
    void testRemoveNode() {
        listService.addNode("RemoverEste", "2023-01-01", 100L, 80L);
        listService.addNode("ManterEste", "2023-01-02", 200L, 150L);

        assertEquals(2, listService.list.countList());

        outContent.reset();
        listService.removeNode("RemoverEste");

        assertEquals(1, listService.list.countList());
        assertEquals("ManterEste", listService.getArquivoByIndex(1));
        assertTrue(outContent.toString().contains("No removido com sucesso"));

        outContent.reset();
        listService.removeNode("NaoExiste");

        assertEquals(1, listService.list.countList());
        assertTrue(outContent.toString().contains("No com esse dado não encontrado!"));
    }
}