import Lista.ListService;

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
                case 2 -> showList(); // Falta abrir e mostrar mais infos
                case 3 -> saveList(); // Falta mostrar trecho e número de ocorrências
                // case 4 -> estatisticasEOrdenacao();
                // case 4 -> removeNode();
                //case 5 ->
                //case 0 ->
                // Compressão de huffman, ordenação, estatísticas e busca por palavra-chave
                
                // case 6 -> showList();
                // case 7 -> saveList();
                // case 8 -> removeNode();
                case 10 ->  menu.teste(); //casee usado para testes de impressão deve ser apagado ppsteriormente
                case 9 -> {
                    menu.end();
                    return;
                }
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
        list.addNode(title, java.time.LocalDateTime.now().toString()); // Pegando a data atual
        menu.cadastro3();
        menu.pause();
        menu.cls();
    }

    // Teoricamente cada estutura tera um show diferente, é necessario  dar um jeito de fazer com q ele trabalhe com o view de menus ao receber confirmações da lista
    // Ideia atual ele passa atraves de um for os elementos da lista na ordem para serem montados em tela
    public void showList() {
        menu.cls();
        list.showList();
        menu.geString();
        menu.cls();
    }

    // Provavelmente deveria estar no final de todas as alterações feitas nas estruturas de dados
    public void saveList() {
        menu.cls();
        list.saveList();
        menu.pause();
        menu.cls();
    }

    // Tericamente se havera so um "add" tmb deve ter apenas um "remove" (discutir a logica disto)
    public void removeNode(){
        menu.cls();
        System.out.println("Digite o nome do arquivo a ser deletado");
        String dado = menu.geString();
        storage.deleteTxt(dado);
        list.removeNode(dado);
        menu.geString();
        menu.cls();
    }
}