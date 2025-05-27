import java.util.Scanner;

public class MenuControler {
    Scanner scan = new Scanner(System.in);
    MenuView menu = new MenuView(50);
    
    public void start() {
        menu.start();// falta a função de leitura
        startMenu();
    }

    public void startMenu() {
        while (true) {    
            menu.startMenu();
            int opt = scan.nextInt();
            scan.nextLine();
            switch (opt) {
                case 1 -> menuCadastro();
                //case 2 -> 
                //case 3 ->
                //case 4 ->
                //case 5 ->
                //case 0 ->
                case 7 -> {
                    for(int a = 0; a < 10; a++) {
                        menu.teste();
                    }
                }
                default -> menu.optIvalid();
            }
        }
    }
    
    public void menuCadastro() {
        menu.cls();
        menu.cadastro1();
        String title = scan.nextLine();
        if (title.equals(-0)) {
            menu.cancel();
            scan.nextLine();
            menu.cls();
            return;
        }
        // logica de criação do txt
        // inicar writer, com nome do titulo
        menu.cadastro2();
        String description = scan.nextLine();
        // inserir a descrição no writer e salvar o txt
        menu.cadastro3();
        scan.nextLine();
        menu.cls();
    }
}
