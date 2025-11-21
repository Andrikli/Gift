package command.actions.Gift;
import command.Command;
import service.GiftService;

import java.util.Scanner;
public class AddSweetToGiftCommand implements Command {
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
            return;
        }

        System.out.println("Введіть ID солодощів:");
        String line = in.nextLine().trim();
        int sweetId;
        try {
            sweetId = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("ID має бути цілим числом");
            return;
        }

        boolean ok = giftService.addSweetToGift(sweetId);
        if (ok) {
            System.out.println(" Додано солодощі ID " + sweetId + " до подарунка.");
        } else {
            System.out.println(" Не вдалося додати (нема такого солодкого або воно видалене).");
        }
    }
}
