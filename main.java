import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MenuView painter = new MenuView(76, scan);
        MenuControler controler = new MenuControler(painter);
        controler.start();
    }
}
