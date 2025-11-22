package command.actions;

import command.Command;
import service.GiftService;
import storage.FileStorage;

import java.io.IOException;

public class SaveGiftsToFileCommand implements Command {

    private final GiftService giftService;

    public SaveGiftsToFileCommand(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public String name() {
        return "Зберегти подарунки у файл";
    }

    @Override
    public void execute() {
        try {
            FileStorage.saveGifts(giftService);
            System.out.println(" Подарунки збережено у " + FileStorage.GIFTS_FILE);
        } catch (IOException e) {
            System.out.println(" Помилка збереження подарунків: " + e.getMessage());
        }
    }
}
