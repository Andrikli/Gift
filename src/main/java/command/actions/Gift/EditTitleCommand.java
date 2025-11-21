package command.actions.Gift;
import command.Command;
import service.GiftService;

import java.util.Scanner;

public class EditTitleCommand implements Command {
    private GiftService giftService;
    private final Scanner in;

    public EditTitleCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Редагувати назву подарунка";
    }

    @Override
    public void execute() {
        System.out.println("Введіть ID подарунка, назву якого хочете змінити:");
        String line = in.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: ID має бути цілим числом.");
            return;
        }

        System.out.println("Введіть нову назву подарунка:");
        String newTitle = in.nextLine().trim();

        try {
            boolean ok = giftService.renameGift(id, newTitle);
            if (!ok) {
                System.out.println("Не вдалося змінити назву: подарунка з таким ID нема або він видалений.");
            } else {
                System.out.println(" Назву подарунка успішно змінено.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(" Помилка: " + e.getMessage());
        }
    }
}
