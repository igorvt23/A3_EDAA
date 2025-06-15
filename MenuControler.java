import AVL.AVLService;
import Lista.ListService;
import Lista.SortTest;
import HashMap.HashService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;
import java.util.regex.*;



public class MenuControler {
    MenuView menu;
    ListService list;
    AVLService avl;
    HashService hash;
    TextStorage storage;
    SortTest sortTest;


    MenuControler (MenuView painter) {
        this.menu = painter;
        this.list = new ListService();
        this.avl = new AVLService();
        this.hash = new HashService();
        this.storage = new TextStorage();
        this.sortTest = new SortTest();
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
                case 2 -> listDoc(); // literal n funciona
                // case 3 -> saveList(); // Falta mostrar trecho e número de ocorrências; DEVE SER descutido a logica de quando o progama deve salvar em memoria alterações na lista
                case 4 -> ordenarArquivos();  // Nova opção para ordenação
                case 5 -> hash();
                // case 6 -> showList();
                // case 7 -> saveList();
                // case 8 -> removeNode();
                // case 10 ->  menu.teste(); //casee usado para testes de impressão deve ser apagado ppsteriormente
                case 0 -> {
                    menu.end();
                    return;
                }
                case 11 -> printTxt("Piratas do Carribe");
                case 12 -> TestesComAvl();
                default -> {
                    menu.optIvalid(); menu.cls();
                }
            }
        }
    }
    
    public void Cadastro() {
        menu.cls();
        String title = menu.cadastro1();
        String description = menu.cadastro2();

        if (menu.confirmarion("Criar Novo .Txt").equals("-0")) {
            menu.cancel();
            menu.cls();
            return;
        }
        
        // Cria o txt com titulo e descrição
        menu.printRight("");
        menu.printRight("");
        menu.printRight(storage.saveTxt(title, description));

        //adiona o txt na lista encadeada
        File file = new File("dados/txt/" + title + ".txt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDateTime.now().format(formatter);
        list.addNode(title, dataFormatada, file.length());
        menu.printRight(".txt Adicionado a lista encadeada!");
        
        String fullText = title + "\n" + description;

        Pattern pattern = Pattern.compile("\\b\\p{L}+\\b", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(fullText);

        while (matcher.find()) {
            String word = matcher.group().toLowerCase();
            int index = matcher.start();

            // Adiciona na AVL
            avl.addNode(word);
            

            int start = Math.max(0, index - 20);
            int end = Math.min(fullText.length(), matcher.end() + 20);
            String context = fullText.substring(start, end).strip();

            // Adiciona no HashMap com título e contexto
            hash.addword(word, title, context);
        }

        menu.printRight("Palavras adicionadas a AVL!");
        menu.printRight("Palavras adicionadas ao HashMap!");
        menu.printRight("");
        menu.printRight("");
        menu.printRight(hash.saveHash());
        menu.printRight(avl.saveAVL());
        menu.printRight(list.saveList());
        
        // BTREE+ tambem precisa ser adicionado e são 2 ainda CARALHO 9
        // MALDITO SFIX LSDS vai tomar no cu  5
        // heap sort do hash 2

        // 21 pontos 29
        // teorico 29 -21
        // texto criado com sucesso
        menu.cadastro3();
        menu.pause();
        menu.cls();
    }

    // Teoricamente cada estutura tera um show diferente, é necessario  dar um jeito de fazer com q ele trabalhe com o view de menus ao receber confirmações da lista
    // Ideia atual ele passa atraves de um for os elementos da lista na ordem para serem montados em tela
    
    public void listDoc() {
        while (true) {
            menu.cls();
            int opt = menu.listDocs();
            switch (opt) {
                case 1 -> showList();
                case 2 -> showAvl();
                case 3 -> showHash();
                // case 4 -> showSufix();
                // case 5 -> showBtreeDocs();
                // case 6 -> showBtreeWords();
                case 0 -> {
                    return;
                }
                default -> menu.optIvalid();
            }
        }
    }

    public void showList() {
        menu.cls();
        String nomeArquivo = list.showList(new Scanner(System.in));

        if(nomeArquivo != null){
            // Retorna a ação escolhida pelo usuário
            int acao = actionsFile();
            switch (acao) {
                case 1 -> removeNode(nomeArquivo); // Removendo o arquivo / No
                case 2 -> compressTxt(nomeArquivo); // Comprimindo o arquivo (não testado)
                case 3 -> showList(); // Visualizando o índice do arquivo
                default -> System.out.println("Ação inválida!");
            }
        }
        menu.geString();
        menu.cls();
        saveList(); // Salva a lista após as alterações
    }

    public void showAvl() {
        menu.cls();
        while (true) {
            int opt = menu.showAvl1();
            switch (opt) {
                case 1 -> menu.showAvl2(avl.inOrder());
                case 2 -> menu.showAvl2(avl.inReverse());
                case 3 -> avlSpecial();
                case 0 -> {
                    return;
                }
                default -> menu.optIvalid();
            }
         }
    }

    public void avlSpecial() {
        String name = menu.showAvlspecial();
        avl.saveVisualTree(name);
        String[][] tree = avl.printTree();
        for (String[] line : tree) {
            for (String cell : line) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public void showHash() {
        menu.cls();
        menu.showHash(hash.hashString());
        menu.pause();
    }

    
    // Provavelmente deveria estar no final de todas as alterações feitas nas estruturas de dados
    public void saveList() {
        list.saveList();
        menu.pause();
        menu.cls();
    }

    // Teoricamente se havera so um "add" tbm deve ter apenas um "remove" (discutir a logica disto)
    public void removeNode(String nomeArquivo) {
        menu.cls();
        storage.deleteTxt(nomeArquivo);
        storage.deleteGz(nomeArquivo);
        list.removeNode(nomeArquivo);
        menu.geString();
        menu.cls();
    }

    public int actionsFile() {
        menu.cls();
        System.out.println("Você deseja realizar alguma ação?");
        System.out.println("1 - Excluir");
        System.out.println("2 - Recomprimir");
        System.out.println("3 - Visualizar ìndices");
        System.out.println("0 - Voltar");
        int choice = menu.getInt();
        
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

                File arquivoComprido = new File(outputFile);
                long compressSize = arquivoComprido.length();

                list.addSizeFileCompressed(nomeArquivo, compressSize);

                System.out.println("Arquivo comprimido com sucesso: " + outputFile);
            }

        } catch (IOException e) {
            System.out.println("Erro ao comprimir: " + e.getMessage());
        }
    }

    public void TestesComAvl() {
        menu.cls();
       
        avl.saveVisualTree("Tree1");
        avl.removeNode("corinthian");
        avl.removeNode("Luke");
        avl.saveVisualTree("Tree2");
        avl.removeNode("Luke");

        avl.saveVisualTree("Tree3");

        avl.removeNode("Luke");

        System.out.println("Arvore impresa!!!");
        System.out.println(avl.inOrder());
        System.out.println();
        System.out.println(avl.countNodes());
        menu.pause();
        menu.cls();
    }

    public void printTxt(String title) {
        menu.cls();
        menu.printTxt(title, storage.readTxt(title));
        menu.pause();
        menu.cls();
    }

    // testes com o HashMap
    public void hash() {
        menu.cls();
        String title = "Star Wars";
        String[] lines = storage.readTxt(title).split("\n");

        for (String line : lines) {
            line = line.strip();
            if (line.isBlank()) continue;

            Pattern pattern = Pattern.compile("\\b\\p{L}+\\b", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                String word = matcher.group();
                int index = matcher.start();

                int start = Math.max(0, index - 15);
                int end = Math.min(line.length(), matcher.end() + 15);

                String context = line.substring(start, end).strip();
                hash.addword(word, title, context);
            }
        }

        title = "Piratas Do Carribe";
        lines = storage.readTxt(title).split("\n");

        for (String line : lines) {
            line = line.strip();
            if (line.isBlank()) continue;

            Pattern pattern = Pattern.compile("\\b\\p{L}+\\b", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                String word = matcher.group().toLowerCase();
                int index = matcher.start();

                int start = Math.max(0, index - 25);
                int end = Math.min(line.length(), matcher.end() + 25);

                String context = line.substring(start, end).strip();
                hash.addword(word, title, context);
            }
        }

        menu.printLine("-", true);
        lines = hash.haString().split("\n");
        for (String line : lines) {
            menu.printRight(line);
        }
        menu.printLine("-", true);

        lines = hash.wordOcur("a").split("\n");
        for (String line : lines) {
            menu.printRight(line);
        }


        hash.removeWord("Luke", "Star Wars");

        menu.printLine("-", true);
        lines = hash.haString().split("\n");
        for (String line : lines) {
            menu.printRight(line);
        }
        menu.printLine("-", true);

        lines = hash.wordOcur("Luke").split("\n");
        for (String line : lines) {
            menu.printRight(line);
        }

        menu.printTxt("Star Wars", storage.readTxt("Star Wars"));

        menu.pause();
        menu.cls();
    }

    public void ordenarArquivos() {
        menu.cls();
        System.out.println("Escolha o critério de ordenação:");
        System.out.println("1 - Nome");
        System.out.println("2 - Data");
        System.out.println("3 - Tamanho");

        int opt = menu.getInt();
        switch (opt) {
            case 1 -> sortTest.listarOrdenado("nome");
            case 2 -> sortTest.listarOrdenado("data");
            case 3 -> sortTest.listarOrdenado("tamanho");
            default -> System.out.println("Opção inválida!");
        }
        menu.pause();
        menu.cls();
    }

//     public void hashMap() {
//         String content = storage.readTxt("Piratas do Carribe");
//         storage.invertedIndex.indexDocument("Piratas do Carribe", content);

//         content =  storage.readTxt("carros");
//         storage.invertedIndex.indexDocument("carro", content);
        
//         content =  storage.readTxt("O Lobo de Wall Street");
//         storage.invertedIndex.indexDocument("O Lobo de Wall Street", content);

//         while (true) {
//             System.out.println("Digite uma palavra a ser buscada: (PARA SAIR SIGITE -0)");
//             Scanner scan = new Scanner(System.in);
//             String word = scan.nextLine();
//             if (word.equals("-0")) {
//                 scan.close(); 
//                 break;
//             } 
//             storage.invertedIndex.search(word);
//         }


//         storage.getInvertedIndex().saveIndexToFile("dados/index.idx");
//     }
}