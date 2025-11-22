package command.actions;

import command.Command;
import service.SweetService;
import storage.FileStorage;

import java.io.IOException;

public class LoadFromFileSweetsCommand implements Command {

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
            System.out.println(" Солодощі завантажено з " + FileStorage.SWEETS_FILE);
        } catch (IOException e) {
            System.out.println(" Помилка завантаження солодощів: " + e.getMessage());
        }
    }
}

