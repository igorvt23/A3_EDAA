import Lista.ListService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;

public class MenuControler {
    MenuView menu;
    ListService list;
    TextStorage storage;

    MenuControler (MenuView painter) {
        this.menu = painter;
        this.list = new ListService();
        this.storage = new TextStorage();
    }
    
    public void start() {
        menu.cls();
        menu.start();
        startMenu();
    }

    public void startMenu() {
        while (true) {    
            int opt = menu.startMenu();
            switch (opt) {
                case 1 -> Cadastro();
                case 2 -> showList();
                // case 3 -> saveList(); // Falta mostrar trecho e número de ocorrências; DEVE SER descutido a logica de quando o progama deve salvar em memoria alterações na lista
                // case 4 -> estatisticasEOrdenacao();
                // case 4 -> removeNode();
                //case 5 ->
                //case 0 ->
                // Compressão de huffman, ordenação, estatísticas e busca por palavra-chave
                
                // case 6 -> showList();
                // case 7 -> saveList();
                // case 8 -> removeNode();
                // case 10 ->  menu.teste(); //casee usado para testes de impressão deve ser apagado ppsteriormente
                case 0 -> {
                    menu.end();
                    return;
                }
                case 11 -> printTxt("Piratas do Carribe");
                default -> menu.optIvalid();
            }
        }
    }
    
    // Quando novas estruturasd de dados forem adicionadas cadastro deve ser atualizado para englobar elas,
    // talvez seja util crirar uma função que gera estruturas com bse nos txt's da pasta dados/txt 
    public void Cadastro() {
        menu.cls();
        String title = menu.cadastro1();
        // deve ser alterado para apos o menu de cadastro assim como está no salvar txt tnks @igorvt23
        if (title.equals("-0")) {
            menu.cancel();
            menu.geString();
            menu.cls();
            return;
        }
        String description = menu.cadastro2();
        storage.saveTxt(title, description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDateTime.now().format(formatter);
        list.addNode(title, dataFormatada);
        menu.cadastro3();
        menu.pause();
        menu.cls();
    }

    // Teoricamente cada estutura tera um show diferente, é necessario  dar um jeito de fazer com q ele trabalhe com o view de menus ao receber confirmações da lista
    // Ideia atual ele passa atraves de um for os elementos da lista na ordem para serem montados em tela
    public void showList() {
        menu.cls();
        String nomeArquivo = list.showList();

        if(nomeArquivo != null){
            // Retorna a ação escolhida pelo usuário
            int acao = actionsFile();
            switch (acao) {
                case 1 -> removeNode(); // Removendo o arquivo / No
                case 2 -> compressTxt(nomeArquivo); // Comprimindo o arquivo (não testado)
                case 3 -> System.out.println(nomeArquivo); // Visualizando o índice do arquivo
                default -> System.out.println("Ação inválida!");
            }
        }
        menu.geString();
        menu.cls();
        saveList(); // Salva a lista após as alterações
    }

    // Provavelmente deveria estar no final de todas as alterações feitas nas estruturas de dados
    public void saveList() {
        menu.cls();
        list.saveList();
        menu.pause();
        menu.cls();
    }

    // Teoricamente se havera so um "add" tbm deve ter apenas um "remove" (discutir a logica disto)
    public void removeNode(){
        menu.cls();
        System.out.println("Digite o nome do arquivo a ser deletado");
        String dado = menu.geString();
        storage.deleteTxt(dado);
        list.removeNode(dado);
        menu.geString();
        menu.cls();
    }

    public int actionsFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Você deseja realizar alguma ação?");
        System.out.println("1 - Excluir");
        System.out.println("2 - Recomprimir");
        System.out.println("3 - Visualizar ìndices");
        System.out.println("0 - Voltar");
        int choice = Integer.parseInt(scanner.nextLine());
        return choice;
    }

    public void compressTxt(String nomeArquivo) {
        nomeArquivo = nomeArquivo.trim();

        String inputFile = "dados/txt/" + nomeArquivo + ".txt";
        String outputFile = "dados/zip/" + nomeArquivo + ".gz";

        File input = new File(inputFile);
        if (!input.exists()) {
            System.out.println("Arquivo NÃO encontrado: " + input.getAbsolutePath());
            return;
        }

        try {
            File zipDir = new File("dados/zip/");
            if (!zipDir.exists()) zipDir.mkdirs();

            try (
                FileInputStream fis = new FileInputStream(inputFile);
                FileOutputStream fos = new FileOutputStream(outputFile);
                GZIPOutputStream gzipOS = new GZIPOutputStream(fos)
            ) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    gzipOS.write(buffer, 0, len);
                }
                System.out.println("Arquivo comprimido com sucesso: " + outputFile);
            }

        } catch (IOException e) {
            System.out.println("Erro ao comprimir: " + e.getMessage());
        }
    }

    public void printTxt(String title) {
        menu.cls();
        menu.printTxt(title, storage.readTxt(title));
        menu.pause();
        menu.cls();
    }
}