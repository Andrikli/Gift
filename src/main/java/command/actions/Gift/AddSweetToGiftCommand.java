package command.actions.Gift;

import command.Command;
import service.GiftService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;

public class AddSweetToGiftCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddSweetToGiftCommand.class);

    private final GiftService giftService;
    private final Scanner in;

    public AddSweetToGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Додати солодощі до поточного подарунка";
    }

    @Override
    public void execute() {
        if (giftService.getCurrentGift() == null) {
            System.out.println("Поточний подарунок не обраний.");
            logger.warn("Спроба додати солодощі без обраного подарунка");
            return;
        }

        System.out.println("Введіть ID солодощів:");
        String line = in.nextLine().trim();
        int sweetId;
        try {
            sweetId = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("ID має бути цілим числом");
            logger.warn("Користувач ввів некоректний ID солодощів: '{}'", line);
            return;
        }

        boolean ok = giftService.addSweetToGift(sweetId);
        if (ok) {
            System.out.println(" Додано солодощі ID " + sweetId + " до подарунка.");
            logger.info("Додано солодощі id={} до поточного подарунка", sweetId);
        } else {
            System.out.println(" Не вдалося додати (нема такого солодкого або воно видалене).");
            logger.info("Не вдалося додати солодощі id={} до подарунка", sweetId);
        }
    }
}
