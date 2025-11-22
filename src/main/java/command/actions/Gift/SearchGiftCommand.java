package command.actions.Gift;
import command.Command;
import model.Gift;
import service.GiftService;
import java.util.List;
import java.util.Scanner;

public class SearchGiftCommand implements Command {
    private final GiftService giftService;
    private final Scanner in;

    public SearchGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Пошук подарунка за назвою";
    }

    @Override
    public void execute() {
        System.out.println("Введіть частину назви подарунка:");
        String query = in.nextLine().trim();
        if (query.isEmpty()) {
            System.out.println("Рядок пошуку не може бути порожнім.");
            return;
        }

        List<Gift> found = giftService.searchByTitle(query);
        if (found.isEmpty()) {
            System.out.println("Подарунків, що відповідають запиту, не знайдено.");
            return;
        }

        System.out.println("Знайдені подарунки:");
        for (Gift g : found) {
            System.out.println("ID: " + g.getId()
                    + " | Назва: " + g.getTitle()
                    + " | sweetIds: " + g.getSweetIds());
        }
    }
}
