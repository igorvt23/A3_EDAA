import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MenuView painter = new MenuView(210, scan);
        MenuControler controler = new MenuControler(painter);
        controler.start();
    }
}
