package command.actions;

import command.Command;
import service.SweetService;
import storage.FileStorage;

import java.io.IOException;

public class SaveSweetsToFileCommand implements Command {

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
            System.out.println(" Солодощі збережено у " + FileStorage.SWEETS_FILE);
        } catch (IOException e) {
            System.out.println(" Помилка збереження солодощів: " + e.getMessage());
        }
    }
}

