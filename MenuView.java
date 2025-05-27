public class MenuView {
    int tamanhoMenu;
    
    MenuView(int tamanho) {
        this.tamanhoMenu = tamanho;
    }

    public String centralized(String text, int totalSpace) {
        int realSpcae = totalSpace - text.length();
        int blankSpace = realSpcae/2;
        return (" ".repeat(blankSpace)+text+" ".repeat(blankSpace)); 
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

    public void cancel() {
        printLine("=", false);
        printRight(centralized("C A N C E L A N D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public void optIvalid() {
        System.out.println("=".repeat(tamanhoMenu));
        System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "Opcao invalida Por Favor digite uma opcao valida");
        System.out.println("=".repeat(tamanhoMenu));
    }

    public void start() {
        System.out.println("=".repeat(tamanhoMenu));
        System.out.printf("= %72s =\n", centralized("P R O G A M A   I N I C I A D O", tamanhoMenu-4));
        System.out.println("=".repeat(tamanhoMenu));
    }

    // opcao 2 escolher uma das 3
    public void startMenu() {
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
    }

    //opcao 1
    // public void startMenu() {
    //     System.out.println("=".repeat(tamanhoMenu));
    //     System.out.printf("= %72s =\n", centralized("M E N U  P R I N C I P A L", tamanhoMenu-4));
    //     System.out.println("= "+"-".repeat((tamanhoMenu-4))+" =");
    //     System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "1 - Cadastrar novo documento");
    //     System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "2 - Listar documentos cadastrados");
    //     System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "3 - Buscar por palavra-chave");
    //     System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "4 - Estatísticas e ordenação");
    //     System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "5 - Gerenciar índice ou compressão");
    //     System.out.println("=".repeat(tamanhoMenu));
    // }

    public void cadastro1() {
        System.out.println("=".repeat(tamanhoMenu));
        System.out.printf("= %72s =\n", centralized("C A D A S T R O   D E  D O C U M E N T O", tamanhoMenu-4));
        System.out.println("= "+"-".repeat((tamanhoMenu-4))+" =");
        System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", "Novo cadastro iniciado (-0 para cancelar):");
        System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", " > Digite o titulo do Novo .txt");
    }

    public void cadastro2() {
        System.out.println("= "+"-".repeat((tamanhoMenu-4))+" =");
        System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", " > Digite a descricao do documento: ");   
    }

    public void cadastro3() {
        printLine("-", true);
        printRight(centralized("N O V O  D O C U M E N T O  C A D A S T R A D O", tamanhoMenu-4));
        printLine("=", false);
    }

    public void  teste() {
        printRight(centralized("COLUNA A", (tamanhoMenu-4)/2)+centralized("COLUNA B", (tamanhoMenu-4)/2));
    }

    // Não tem pq existir afinal tera ser feito uma função que mostra o txt q pode ser usada no lugar dela
    // public void menuCadastro3(String title, String descprition) {
    //     System.out.println("= "+"-".repeat((tamanhoMenu-4))+" =");
    //     System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", title);
    //     System.out.printf("= %-"+(tamanhoMenu-4)+"s =\n", descprition);
    //     System.out.println("=".repeat(tamanhoMenu));
    // }
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

// talves criar uma dunção que recebe o titulo e as opçoes de um menu