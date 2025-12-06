package command.actions;

import command.Command;
import service.SweetService;
import storage.FileStorage;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class SaveSweetsToFileCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SaveSweetsToFileCommand.class);

    private final SweetService sweetService;

    public SaveSweetsToFileCommand(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @Override
    public String name() {
        return "Зберегти солодощі у файл";
    }

    @Override
    public void execute() {
        try {
            FileStorage.saveSweets(sweetService);
            logger.info("Солодощі успішно збережено у файл {}", FileStorage.SWEETS_FILE);
            System.out.println(" Солодощі збережено у " + FileStorage.SWEETS_FILE);
        } catch (IOException e) {
            logger.error("Помилка збереження солодощів у файл {}: {}",
                    FileStorage.SWEETS_FILE, e.getMessage(), e);
            System.out.println(" Помилка збереження солодощів: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неочікувана помилка при збереженні солодощів у файл {}",
                    FileStorage.SWEETS_FILE, e);
            System.out.println(" Сталася неочікувана помилка при збереженні солодощів.");
        }
    }
}


