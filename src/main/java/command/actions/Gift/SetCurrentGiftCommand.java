package command.actions.Gift;
import command.Command;
import service.GiftService;

import java.util.Scanner;
public class SetCurrentGiftCommand implements Command {
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
            System.out.println("ID має бути цілим числом");
            return;
        }

        boolean ok = giftService.setCurrentGiftId(id);
        if (!ok) {
            System.out.println("Подарунок з таким ID не знайдено.");
        } else {
            System.out.println(" Поточний подарунок = " + id);
        }
    }
}
