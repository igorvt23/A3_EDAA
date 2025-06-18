import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuView {
    int tamanhoMenu;
    Scanner scan;
    
    MenuView(int tamanho, Scanner scan) {
        this.tamanhoMenu = tamanho;
        this.scan = scan;
    }

    // funções usadas para printar o = x =  M E N U  = x = (l_l)

    public String centralized(String text, int totalSpace) {
        int realSpcae = totalSpace - text.length();
        int blankSpace = realSpcae/2;
        return (" ".repeat(blankSpace)+text+" ".repeat(blankSpace)); 
    }
    
    public String righAling(String text, int totalSpace) {
        return String.format("%-"+totalSpace+"s", text);
    }

    public void printLine(String text, Boolean inside) {
        if (inside) System.out.printf("= "+text.repeat((tamanhoMenu-4)/text.length())+" =\n");  
        else System.out.println(text.repeat(tamanhoMenu/text.length()));
    }

    public void printRight(String text) {
        System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", text);
    }

    public void cls() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public String ajsutContent(String content) {
        int tamMax = tamanhoMenu-4;
        String[] lines = content.split("\n");
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            while (line.length() > tamMax) {
                int cutPoint = line.lastIndexOf(" ", tamMax);
                if (cutPoint == -1) cutPoint = tamMax;
                builder.append(line, 0, cutPoint).append("\n");
                line = line.substring(cutPoint).trim();
            }
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

    // funções que captura entradas do usuario 
    public int getInt() {
        while (true) {
            try {
                int opt = scan.nextInt();
                scan.nextLine();
                return opt;
            } catch (InputMismatchException e) {
                scan.nextLine();
                printRight(" > FORMATO de digitação invalido!!!");
            }
        }
    }

    public String geString() {
        return scan.nextLine();
    }

    // Get string sem retorno simula uma pausa
    public void pause() {
        geString();
    }
    
    // funções que imprimem menus e mensagens na tela q os formam
    public void cancel() {
        printLine("=", false);
        printRight(centralized("C A N C E L A N D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public void close() {
        printLine("=", false);
        printRight(centralized("S A I N D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public void end() {
        printLine("=", false);
        printRight(centralized("P R O G A M A  E N C E R R A D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public void optIvalid() {
        printLine("=", false);
        printRight("Opcao invalida Por Favor digite uma opcao valida");
        printLine("=", false);
        pause();
    }

    public void start() {
        printLine("=", false);
        printRight(centralized("P R O G A M A   I N I C I A D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public String confirmarion(String word) {
        printLine("-", true);
        printRight(" > DESEJA CANCELAR A ACAO: " + word);
        printRight(" > SE SIM DIGITE: '-0'");
        System.out.print("=  > ");
        return geString();
    }

    public int startMenu() {
        printLine("=", false);
        printRight(centralized("M E N U  P R I N C I P A L", tamanhoMenu-4));
        printLine("-", true);
        printRight("1 - Cadastrar novo documento");
        printRight("2 - Listar documentos cadastrados");
        printRight("3 - Busca Por Palavra chave");
        printRight("4 - Estatísticas e ordenação");
        printRight("5 - Gerenciamento dos Txt");
        printRight("0 - Fechar o progama");
        printLine("-", true);
        System.out.print("=  > Digite a opcao desejada: ");
        return getInt();
    }

    public String cadastro1() {
        printLine("=", false);
        printRight(centralized("C A D A S T R O  D E  D O C U M E N T O", tamanhoMenu-4));
        printLine("-", true);
        printRight("Novo cadastro iniciado:");
        printRight(" > Digite o titulo do Novo .txt");
        System.out.print("= > ");
        return geString();
    }

    public String cadastro2() {
        printLine("-", true);
        printRight(" > Digite a descricao do documento:");
        System.out.print("= > ");
        return geString();
    }

    public void cadastro3() {
        printLine("-", true);
        printRight(centralized("N O V O  D O C U M E N T O  C A D A S T R A D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public int listDocs() {
        printLine("=", false);
        printRight(centralized("L I S T A G E M  D E  D O C U M E N T O S", tamanhoMenu-4));
        printLine("-", true);
        printRight(" > Tipos de listagem disponiiveis:");
        printRight("1 - Lista encadeda (Nos e metadados)");
        printRight("2 - AVL de Palvras (Nos em 'Ordem' Ou 'Orderm Reversa')");
        printRight("3 - Hash Map (Array do hash com numero de ocorrencias das Palavras)");
        printRight("0 - Para voltar ao menu anterior");
        printRight("");
        printRight("");
        printRight("> Outras formas de visualizacao das arvores fora do progama");
        System.out.print("= > ");
        return getInt();
    }

    public int showAvl1() {
        printLine("=", false);
        printRight(centralized("A V L  I M P R E S A", tamanhoMenu-4));
        printLine("-", true);
        printRight("> 2 Formas de a mostra-la no terminal:");
        printRight("1 - Em 'Ordem' ou seja Left -> Root -> Right");
        printRight("2 - Em 'Ordem' ou seja Right -> Root -> Left");
        printRight("3 - Mostar a Estutura da Arvore...");
        printRight("0 - Para voltar");
        System.out.print("= > ");
        return getInt();
    }

    public void showAvl2(String avl) {
        printLine("-", true);
        String[] lines = avl.split("\n");
        for (String line : lines) {
            printRight(line);
        }
        printLine("=", false);
        pause();
    }

    public String showAvlspecial() {
        printRight("> Forma especial de visualicao da AVL");
        printRight("> Digite o nome da VisualTree: ");
        return geString();
    }

    public void showHash(String hashArray) {
        printLine("-", true);
        printRight(centralized("A R R A Y  H A S H M A P", tamanhoMenu-4));
        printLine("-", true);
        String[] lines = hashArray.split("\n");
        for (String line : lines) {
            printRight(line);
        }
        printLine("=", false);
    }

    public void showList(String list, boolean gerenciamento) {
        if (gerenciamento) {
            printLine("=", false);
            printRight(centralized("L I S T A  D E  D O C S", tamanhoMenu-4));
            printLine("-", true);
        }
        String[] nodes = list.split("\n");
        for (int i = 0; i < nodes.length; i++) {
            printDocInfo(nodes[i].split(","), i+1);
        }
        if (gerenciamento) {
            printRight(centralized("L I S T A  I M P R E S A !", tamanhoMenu-4));
            printLine("=", false);
        } else {
            printRight(centralized("L I S T A  I M P R E S A !", tamanhoMenu-4));
            printLine("-", false);
        }
        
    }

    public void printDocInfo(String[] docInfo, int num) {
        printLine("-", true);
        String name = docInfo[0].toUpperCase();
        String date = docInfo[1];
        String size = docInfo[2];
        String compresSize = docInfo[3];
        printRight(String.format("Num: %d | Nome: %s", num, name));
        printRight("    > Data de criacao: " + date);
        printRight("    > Tamanho: " + size + "mb");
        printRight("    > Tamanho Comprido: " + compresSize + "mb");
        printRight("    ");
        printLine("-", true);
    }

    public void printTxt(String title, String content, String data, long size, long compresedSize) {
        printLine("=", false);
        String newTitle = title.replaceAll(".", "$0 ").trim().replaceAll(" +", " ");
        printRight(centralized("T I T U L O :  "+newTitle, tamanhoMenu-4));
        printLine("-", true);
        printRight("DADOS:");
        printRight("> Data de criacao: " + data);
        printRight("> Tamanho: " + size);
        printRight("> Tamanho Comprido: " + compresedSize);
        printLine("-", true);
        printRight("CONTEUDO:");
        String formatedLine = ajsutContent(content);
        String[] lines = formatedLine.split("\n");
        for (String line : lines) printRight(line);
        printLine("=", false);
    }

    public String searchWord1() {
        printLine("=", false);
        printRight(centralized("B U S C A R  P A L A V R A -  C H A V E", tamanhoMenu-4));
        printLine("-", true);
        printRight("Digite a palavra a ser buscada: ");
        System.out.println("= > ");
        return geString();
    }

    public void searchWord2(String word) {
        printLine("-", true);
        printRight(centralized("R E S U L T A D O  D A  A V L", tamanhoMenu-4));;
        printRight("    > " + word);
    }

    public void searchWord3(String hash) {
        printLine("-", true);
        printRight(centralized("R E S U L T A D A  H A S H", tamanhoMenu-4));
        printLine("-", true);
        String[] lines = hash.split("\n");
        for (String line : lines) {
            printRight(line);
        }

        printLine("-", true);
        printRight(centralized("B U S C A  C O N C L U I D A", tamanhoMenu-4));
        printLine("=", false);
    }

    public void Gerenciamento1(String list) {
        printLine("=", true);
        printRight(centralized("M E N U  D E  G E R E N C I A M E N T O", tamanhoMenu-4));
        printLine("-", false);
        showList(list, false);
    }

    public int Gerenciamento2() {
        printRight("Selecione um .Txt por index par gerenciar ou 0 para sair");
        System.out.println("= > ");
        return getInt();
    }

    public int Gerenciamento3() {
        printRight("Você deseja realizar alguma ação?");
        printRight("1 - Excluir");
        printRight("2 - Recomprimir");
        printRight("3 - Visualizar ìndices");
        printRight("0 - Voltar");
        printRight("");
        System.out.println("= > ");
        return getInt();
    }

    public int orderOpt() {
        cls();
        printLine("=", true);
        printRight(centralized("M E N U  D E  G E R E N C I A M E N T O", tamanhoMenu-4));
        printLine("-", false);
        printRight("Escolha o critério de ordenação:");
        printRight("1 - Nome");
        printRight("2 - Data");
        printRight("3 - Tamanho");
        printRight("0 - Sair");
        System.out.println("= > ");
        return getInt();
    }

    public void orderPrint(String ordenado) {
        String[] lines = ordenado.split("%n");
        for (String line : lines) printRight(line);
        printLine("=", false);
    }
}