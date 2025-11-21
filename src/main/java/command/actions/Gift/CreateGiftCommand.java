package command.actions.Gift;
import service.GiftService;

import java.util.Scanner;
import command.Command;

public class CreateGiftCommand implements Command {
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
            System.out.println(" Подарунок створено. ID = " + gift.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}

