package command.actions;

import command.Command;
import service.GiftService;
import storage.FileStorage;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadFromFileGiftsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoadFromFileGiftsCommand.class);

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
            logger.info("Подарунки успішно завантажено з файлу {}", FileStorage.GIFTS_FILE);
            System.out.println(" Подарунки завантажено з " + FileStorage.GIFTS_FILE);
        } catch (IOException e) {
            logger.error("Помилка завантаження подарунків з файлу {}: {}",
                    FileStorage.GIFTS_FILE, e.getMessage(), e);
            System.out.println(" Помилка завантаження подарунків: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неочікувана помилка при завантаженні подарунків з файлу {}",
                    FileStorage.GIFTS_FILE, e);
            System.out.println(" Сталася неочікувана помилка при завантаженні подарунків.");
        }
    }
}


