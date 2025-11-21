package command.actions.Gift;


import command.Command;
import service.GiftService;

import java.util.Scanner;

public class DeleteFromGiftCommand implements Command {

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
            System.out.println("Поточний подарунок не обраний.");
            return;
        }

        System.out.println("Введіть ID солодощів для видалення з подарунка:");
        String line = in.nextLine().trim();
        int sweetId;
        try {
            sweetId = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("ID має бути цілим числом.");
            return;
        }

        boolean ok = giftService.RemoveFromGift(sweetId);
        if (ok) {
            System.out.println(" Солодощі ID " + sweetId + " видалено з подарунка.");
        } else {
            System.out.println(" У поточному подарунку немає солодощів з таким ID.");
        }
    }
}

