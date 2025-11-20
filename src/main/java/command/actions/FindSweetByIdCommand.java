package command.actions;

import command.Command;
import model.Sweet;
import service.SweetService;

import java.util.Scanner;

public class FindSweetByIdCommand implements Command {

    private final SweetService sweetService;
    private final Scanner in;

    public FindSweetByIdCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Знайти солодощі за ID";
    }

    @Override
    public void execute() {
        System.out.print("Введіть ID солодощів: ");
        String line = in.nextLine().trim();

        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: ID має бути цілим числом.");
            return;
        }

        Sweet sweet = sweetService.findById(id);

        if (sweet == null) {
            System.out.println("Солодощів з таким ID не знайдено.");
        } else {
            System.out.println("Знайдено солодощі:");
            System.out.println(sweet); // використовуємо твій toString()
        }
    }
}
