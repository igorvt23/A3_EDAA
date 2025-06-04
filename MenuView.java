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
                builder.append(content, 0, cutPoint).append("\n");
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

    public void pause() {
        geString();
    }
    
    // funções que imprimem menus e mensagens na tela q os formam

    public void cancel() {
        printLine("=", false);
        printRight(centralized("C A N C E L A N D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public void optIvalid() {
        printLine("=", false);
        printRight("Opcao invalida Por Favor digite uma opcao valida");
        printLine("=", false);
    }

    public void start() {
        printLine("=", false);
        printRight(centralized("P R O G A M A   I N I C I A D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public int startMenu() {
        printLine("=", false);
        printRight(centralized("M E N U  P R I N C I P A L", tamanhoMenu-4));
        printLine("-", true);
        printRight("1 - Cadastrar novo documento");
        printRight("2 - Listar documentos cadastrados");
        printRight("3 - Buscar por palavra-chave");
        printRight("4 - Estatísticas e ordenação");
        printRight("5 - Gerenciar índice ou compressão");
        printRight("0 - Fechar o progama");
        printLine("=", false);
        System.out.print("=  > Digite a opcao desejada: ");
        return getInt();
    }

    public String cadastro1() {
        printLine("=", false);
        printRight(centralized("C A D A S T R O   D E  D O C U M E N T O", tamanhoMenu-4));
        printLine("-", true);
        printRight("Novo cadastro iniciado (-0 para cancelar):");
        printRight(" > Digite o titulo do Novo .txt");
        System.out.print("=  > ");
        return geString();
    }

    public String cadastro2() {
        printLine("-", true);
        printRight(" > Digite a descricao do documento:");
        System.out.print("=  > ");
        return geString();
    }

    public void cadastro3() {
        printLine("-", true);
        printRight(centralized("N O V O  D O C U M E N T O  C A D A S T R A D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public void end() {
        printLine("=", false);
        printRight(centralized("P R O G A M A  E N C E R R A D O", tamanhoMenu-4));
        printLine("=", false);
    }

    // data deveria ser string? int? float? bollean?  (ou é hoje pu não é hoje) // insira sua Figurinha pensativa aqui 
    // public void imprimirTxt(String title, String content, ?String data?, ?long size?, ?long compresedSize?) {
    public void imprimirTxt(String title, String content) {
        printLine("=", false);
        printRight(centralized(".txt: "+title, tamanhoMenu-4));
        printLine("-", true);
        
        printLine("=", false);
    }

    // IMPRESSÕES EXPERIMENTAIS (testes das outras funções de maneira geral)

    public void  teste() {
        printLine("=", false);
        printRight(centralized("COLUNA A", (tamanhoMenu-6)/2)+"| "+centralized("COLUNA B", (tamanhoMenu-6)/2));
        printLine("-", true);
        for(int a = 0; a < 10; a++) {
            printRight(righAling("teste de a"+a, (tamanhoMenu-8)/2)+"| "+righAling("teste de b"+a, (tamanhoMenu-6)/2));
        }
        printLine("=", false);
    }
}

// tecnicas de menu q talvez sejam uteis
// > Criar linhas com padrores ´padro fica entre os + e é divido do tamanho pelo numero de letras
// System.out.println("= "+"=x=".repeat((tamanhoMenu-4)/3)+" ="); 

// ============== estruturas basica de menu ==============
// System.out.println("=".repeat(tamanhoMenu));
// System.out.printf("= %72s =\n", centralized("", tamanhoMenu-4));
// System.out.println("= "+"=-".repeat((tamanhoMenu-4)/2)+" =");
// System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "");
// System.out.println("=".repeat(tamanhoMenu));

// talves criar uma função que recebe o titulo e as opçoes de um menu

// MENU Q TROCA DE OPÇÂO AO USAR W para cima E S para baixo, mas precisa ainda de enter ent talvez n valha a a pena!
// public void testeMenu1() {
//     int opt = 1;
//     String input; 

//     do {
//         cls();
//         testeMultoptions(opt);
//         System.out.println("w sobe, s dece, q sai!");
//         input = scan.nextLine();

//         if (input.equalsIgnoreCase("W") && opt > 1) opt--;
//         if (input.equalsIgnoreCase("s") && opt < 3) opt++;


//     } while(!input.equalsIgnoreCase("q"));
// }


// public void testeMultoptions(int opt) {
//     printLine("=", false);
//     printRight(opt == 1 ? "> 1 - opt 1" : "1 - opt 1");
//     printRight(opt == 2 ? "> 2 - opt 2" : "2 - opt 2");
//     printRight(opt == 3 ? "> 3 - opt 3" : "3 - opt 3");
//     printLine("=", false);
// }