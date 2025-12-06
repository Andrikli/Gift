package command.actions.Gift;

import command.Command;
import model.Gift;
import service.GiftService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class SearchGiftCommand implements Command {
    private final GiftService giftService;
    private final Scanner in;

    private static final Logger logger = LogManager.getLogger(SearchGiftCommand.class);

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
            logger.warn("Користувач спробував шукати подарунок з порожнім запитом");
            System.out.println("Рядок пошуку не може бути порожнім.");
            return;
        }

        List<Gift> found = giftService.searchByTitle(query);

        logger.info("Користувач здійснив пошук подарунків по запиту '{}', знайдено: {}", query, found.size());

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

