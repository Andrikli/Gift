package command.actions;

import command.Command;
import service.GiftService;
import storage.FileStorage;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class SaveGiftsToFileCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SaveGiftsToFileCommand.class);

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
            logger.info("Подарунки успішно збережено у файл {}", FileStorage.GIFTS_FILE);
            System.out.println(" Подарунки збережено у " + FileStorage.GIFTS_FILE);
        } catch (IOException e) {
            logger.error("Помилка збереження подарунків у файл {}: {}",
                    FileStorage.GIFTS_FILE, e.getMessage(), e);
            System.out.println(" Помилка збереження подарунків: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неочікувана помилка при збереженні подарунків у файл {}",
                    FileStorage.GIFTS_FILE, e);
            System.out.println(" Сталася неочікувана помилка при збереженні подарунків.");
        }
    }
}

