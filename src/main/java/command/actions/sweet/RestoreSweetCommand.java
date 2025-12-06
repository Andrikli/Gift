package command.actions.sweet;

import command.Command;
import service.SweetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class RestoreSweetCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RestoreSweetCommand.class);

    private final SweetService sweetService;
    private final Scanner in;

    public RestoreSweetCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Відновити солодощі";
    }

    @Override
    public void execute() {
        System.out.println("Введіть id солодощів для відновлення: ");
        String line = in.nextLine().trim();

        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            logger.warn("Некоректний ввід ID при відновленні солодощів: '{}'", line);
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }

        boolean result = sweetService.restoreId(id);
        if (!result) {
            logger.warn("Не вдалося відновити солодощі id={} — не існує або не були видалені", id);
            System.out.println("Не вдалося відновити: солодощі не були видаленими або солодощів з таким Id не існує");
        } else {
            System.out.println("Солодощі " + id + " успішно відновлено");
        }
    }
}

