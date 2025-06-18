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
        this.storage = new TextStorage();
        
        this.list = new ListService();
        this.avl = new AVLService();
        this.hash = new HashService();
        

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
                case 2 -> listDoc();
                case 3 -> searchWord();
                case 4 -> ordenarArquivos();
                case 5 -> Gerenciamento();
                case 0 -> {
                    menu.end();
                    return;
                }
                default -> {
                    menu.optIvalid(); menu.cls();
                }
            }
        }
    }
    
    public void Cadastro() {
        menu.cls();
        String title = menu.cadastro1();
        menu.printRight("");
        String description = menu.cadastro2();

        if (menu.confirmarion("Criar Novo .Txt").equals("-0")) {
            menu.cancel();
            menu.pause();
            menu.cls();
            return;
        }
        
        menu.printRight("");
        menu.printRight("");

        // Cria o txt com titulo e descrição
        menu.printRight(storage.saveTxt(title, description));

        //adiona o txt na lista encadeada
        File file = new File("dados/txt/" + title + ".txt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDateTime.now().format(formatter);
        compressTxt(title);
        File fileZip = new File("dados/zip/" + title + ".gz");
        
        list.addNode(title, dataFormatada, file.length(), fileZip.length());
        menu.printRight(".txt Adicionado a lista encadeada!");
        
        String fullText = title + "\n" + description;

        Pattern pattern = Pattern.compile("\\b\\p{L}+\\b", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(fullText);

        while (matcher.find()) {
            String word = matcher.group().toLowerCase();

            // Adiciona na AVL
            avl.addNode(word);

            int start = Math.max(0, matcher.start() - 20);
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
        
        // texto criado com sucesso
        menu.cadastro3();
        menu.pause();
        menu.cls();
    }
    
    public void listDoc() {
        while (true) {
            menu.cls();
            int opt = menu.listDocs();
            switch (opt) {
                case 1 -> showList();
                case 2 -> showAvl();
                case 3 -> showHash();
                case 0 -> {
                    menu.close();
                    menu.cls();
                    return;
                }
                default -> menu.optIvalid();
            }
        }
    }

    public void showList() {
        menu.cls();
        menu.showList(list.showList(), true);
        menu.pause();
        menu.cls();
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

    public void searchWord() {
        menu.cls();
        String word = menu.searchWord1();
        menu.searchWord2(avl.hasWord(word));
        menu.pause();
        menu.searchWord3(hash.wordOcur(word));
        menu.pause();
        menu.cls();
    }

    public void Gerenciamento() {
        menu.cls();
        if (list.getStart() == null) {
            menu.printRight("Lista vazia");
            return;
        }
        
        menu.Gerenciamento1(list.showList());

        String nomeArquivo; 
        int index = menu.Gerenciamento2();

        if (index <= 0 || index > list.getCount()) {
            menu.cancel();
            return;
        }

        System.out.println(list.getCount());

        nomeArquivo = list.getNodeIndex(index).name;
        if (nomeArquivo == null) {
            menu.printRight("Arquivo não encontrado");
            menu.cancel();
            return;
        }

        String data = list.getNodeIndex(index).date;
        long size = list.getNodeIndex(index).size;
        long sizeCompressed = list.getNodeIndex(index).size_zip;

        String description = storage.readTxt(nomeArquivo);
        menu.printTxt(nomeArquivo, description, data, size, sizeCompressed);

        int acao = menu.Gerenciamento3();
        switch (acao) {
            case 1 -> removeNode(nomeArquivo); // Remover o arquivo
            case 2 -> compressTxt(nomeArquivo); // Comprimir o arquivo
            case 3 -> list.showListOPICIONAL(new Scanner(System.in));
            case 0 ->  {
                break;
            }// Ver índice do arquivo
            default -> menu.optIvalid(); // Opção inválida
        }

        menu.pause();
        menu.cls();
        list.saveList();
    }

    public void Gerenciamento1() {
        menu.cls();
       
        String nomeArquivo = list.showListOPICIONAL(new Scanner(System.in));
        if(nomeArquivo != null){
            // Retorna a ação escolhida pelo usuário
            
        }
     // Salva a lista após as alterações
    }

    public void removeNode(String nomeArquivo) {
        String description = storage.readTxt(nomeArquivo);

        String fullText = nomeArquivo + "\n" + description;

        Pattern pattern = Pattern.compile("\\b\\p{L}+\\b", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(fullText);

        while (matcher.find()) {
            String word = matcher.group().toLowerCase();

            // Remove na AVL
            avl.removeNode(word);

            // Remove no HashMap com título e contexto
            hash.removeWord(word, nomeArquivo);
        }
        menu.printRight("");
        menu.printRight("Palvras do texto removidads da AVL");
        menu.printRight("Palvras do texto removidads do HashMAp");

        // Apaga .txt
        storage.deleteTxt(nomeArquivo);
        storage.deleteGz(nomeArquivo);
        // remove o nodo da lsita
        list.removeNode(nomeArquivo);
        menu.pause();
        menu.cls();
    }

    public void compressTxt(String nomeArquivo) {
        nomeArquivo = nomeArquivo.trim();

        String inputFile = "dados/txt/" + nomeArquivo + ".txt";
        String outputFile = "dados/zip/" + nomeArquivo + ".gz";

        File input = new File(inputFile);
        if (!input.exists()) {
            menu.printRight(("Arquivo NÃO encontrado: " + input.getAbsolutePath()));
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
                menu.printRight("Arquivo comprimido com sucesso: " + outputFile);
            }
        } catch (IOException e) {
            menu.printRight("Erro ao comprimir: " + e.getMessage());
        }
    }

    public void ordenarArquivos() {
        while (true) {
            int opt = menu.orderOpt();
            switch (opt) {
                // case 1 -> System.out.println(sortTest.listarOrdenado("nome"));
                // case 2 -> System.out.println(sortTest.listarOrdenado("data"));
                // case 3 -> System.out.println(sortTest.listarOrdenado("tamanho"));
                case 1 -> menu.orderPrint(sortTest.listarOrdenado("nome"));
                case 2 -> menu.orderPrint(sortTest.listarOrdenado("data"));
                case 3 -> menu.orderPrint(sortTest.listarOrdenado("tamanho"));
                case 0 ->  {
                    menu.cls();
                    return;
                }
                default -> menu.optIvalid();
            }
            menu.pause();
            menu.cls();
        }
    }
}