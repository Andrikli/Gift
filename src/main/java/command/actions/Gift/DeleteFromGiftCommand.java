package command.actions.Gift;
import command.Command;
import model.Sweet;
import service.GiftService;
import util.SweetUtils;

import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class DeleteFromGiftCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteFromGiftCommand.class);
    private final GiftService giftService;
    private final Scanner in;

    public DeleteFromGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Видалити солодощу з подарунка за ID";
    }

    @Override
    public void execute() {
        if (giftService.getCurrentGift() == null) {
            logger.warn("Користувач спробував видалити солодощі, але подарунок не обраний");
            System.out.println("Поточний подарунок не обраний.");
            return;
        }

        List<Sweet> sweets = giftService.getGiftSweets();
        if (sweets.isEmpty()) {
            logger.info("Поточний подарунок порожній, видаляти нічого");
            System.out.println("У подарунку немає солодощів.");
            return;
        }

        System.out.println(" Список солодощів");
        for (Sweet s : sweets) {
            System.out.println(SweetUtils.format(s));
            System.out.println();
        }

        System.out.println("Введіть ID солодощів для видалення з подарунка:");
        String line = in.nextLine().trim();
        int sweetId;
        try {
            sweetId = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            logger.warn("Користувач ввів некоректний ID для видалення з подарунка: '{}'", line);
            System.out.println("ID має бути цілим числом.");
            return;
        }

        boolean ok = giftService.RemoveFromGift(sweetId);
        if (ok) {
            logger.info("Користувач видалив солодощі з подарунка, id={}", sweetId);
            System.out.println(" Солодощі ID " + sweetId + " видалено з подарунка.");
        } else {
            logger.info("Користувач намагався видалити неіснуючі солодощі id={} з подарунка", sweetId);
            System.out.println(" У поточному подарунку немає солодощів з таким ID.");
        }
    }
}

