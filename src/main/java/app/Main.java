package app;
import command.menu.MainMenu;
import repository.InMemorySweetRepository;
import repository.SweetRepository;
import service.SweetService;
public class Main {
    public static void main(String[] args) {
        SweetRepository sweetRepository = new InMemorySweetRepository();

        SweetService sweetService = new SweetService(sweetRepository);
        MainMenu mainMenu = new MainMenu(sweetService);
        mainMenu.show();
        System.out.println("Вихід з програми");
    }
}

