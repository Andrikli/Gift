package app;
import command.menu.MainMenu;
import repository.GiftRepository;
import repository.InMemoryGiftRepository;
import repository.InMemorySweetRepository;
import repository.SweetRepository;
import service.SweetService;
import service.GiftService;


public class Main {
    public static void main(String[] args) {
        SweetRepository sweetRepository = new InMemorySweetRepository();
        GiftRepository giftRepository = new InMemoryGiftRepository();

        SweetService sweetService = new SweetService(sweetRepository);
        GiftService giftService = new GiftService(giftRepository, sweetService);

        MainMenu mainMenu = new MainMenu(sweetService, giftService);
        mainMenu.show();


        System.out.println("Вихід з програми");
    }
}

