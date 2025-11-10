package app;
import command.menu.MainMenu;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        new  MainMenu().show();
        System.out.println("Вихід з програми");
    }
}
