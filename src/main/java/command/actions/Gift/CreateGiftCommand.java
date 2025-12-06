package command.actions.Gift;
import service.GiftService;

import java.util.Scanner;
import command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateGiftCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateGiftCommand.class);

    private final GiftService giftService;
    private final Scanner in;

    public CreateGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Створити подарунок";
    }
    @Override
    public void execute() {
        System.out.println("Введіть назву подарунка:");
        String title = in.nextLine().trim();

        try {
            var gift = giftService.createGift(title);
            logger.info("Користувач створив подарунок title='{}', id={}", title, gift.getId());
            System.out.println(" Подарунок створено. ID = " + gift.getId());
        } catch (IllegalArgumentException e) {
            logger.warn("Користувач ввів некоректну назву подарунка ('{}')", title);
            System.out.println("Помилка: " + e.getMessage());
        }
    }

}

