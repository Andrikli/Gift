package command.actions;

import command.Command;
import service.GiftService;
import storage.FileStorage;

import java.io.IOException;

public class LoadFromFileGiftsCommand implements Command {

    private final GiftService giftService;

    public LoadFromFileGiftsCommand(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public String name() {
        return "Завантажити подарунки з файлу";
    }

    @Override
    public void execute() {
        try {
            FileStorage.loadGifts(giftService);
            System.out.println(" Подарунки завантажено з " + FileStorage.GIFTS_FILE);
        } catch (IOException e) {
            System.out.println(" Помилка завантаження подарунків: " + e.getMessage());
        }
    }
}

