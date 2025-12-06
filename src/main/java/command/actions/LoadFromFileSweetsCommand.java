package command.actions;

import command.Command;
import service.SweetService;
import storage.FileStorage;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadFromFileSweetsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoadFromFileSweetsCommand.class);

    private final SweetService sweetService;

    public LoadFromFileSweetsCommand(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @Override
    public String name() {
        return "Завантажити солодощі з файлу";
    }

    @Override
    public void execute() {
        try {
            FileStorage.loadSweets(sweetService);
            logger.info("Солодощі успішно завантажено з файлу {}", FileStorage.SWEETS_FILE);
            System.out.println(" Солодощі завантажено з " + FileStorage.SWEETS_FILE);
        } catch (IOException e) {
            logger.error("Помилка завантаження солодощів з файлу {}: {}",
                    FileStorage.SWEETS_FILE, e.getMessage(), e);
            System.out.println(" Помилка завантаження солодощів: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неочікувана помилка при завантаженні солодощів з файлу {}",
                    FileStorage.SWEETS_FILE, e);
            System.out.println(" Сталася неочікувана помилка при завантаженні солодощів.");
        }
    }
}


