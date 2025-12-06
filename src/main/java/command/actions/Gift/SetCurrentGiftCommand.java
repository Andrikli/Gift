package command.actions.Gift;

import command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.GiftService;

import java.util.Scanner;

public class SetCurrentGiftCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SetCurrentGiftCommand.class);

    private final GiftService giftService;
    private final Scanner in;

    public SetCurrentGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Обрати поточний подарунок за ID";
    }

    @Override
    public void execute() {
        System.out.println("Введіть ID подарунка:");
        String line = in.nextLine().trim();
        int id;

        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            logger.warn("Користувач ввів некоректний ID для вибору подарунка: '{}'", line);
            System.out.println("ID має бути цілим числом");
            return;
        }

        boolean ok = giftService.setCurrentGiftId(id);
        if (!ok) {
            logger.info("Користувач намагався обрати неіснуючий або видалений подарунок id={}", id);
            System.out.println("Подарунок з таким ID не знайдено.");
        } else {
            logger.info("Користувач обрав поточний подарунок id={}", id);
            System.out.println(" Поточний подарунок = " + id);
        }
    }
}
