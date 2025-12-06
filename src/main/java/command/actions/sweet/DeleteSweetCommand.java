package command.actions.sweet;

import command.Command;
import model.Sweet;
import service.SweetService;
import util.SweetUtils;

import java.util.List;
import java.util.Scanner;

// 🔹 log4j2 logger
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteSweetCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeleteSweetCommand.class);

    private final SweetService sweetService;
    private final Scanner in;

    public DeleteSweetCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Видалити солодощі за ID";
    }

    @Override
    public void execute() {
        List<Sweet> sweets = sweetService.getAll();

        if (sweets.isEmpty()) {
            logger.warn("Спроба видалення солодощів — склад порожній");
            System.out.println("Склад порожній, немає що видаляти");
            return;
        }

        for (Sweet sweet : sweets) {
            System.out.println(SweetUtils.format(sweet));
        }

        System.out.println("Введіть id солодощів для видалення");
        String line = in.nextLine().trim();
        int id;

        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            logger.warn("Некоректний ввід ID для видалення: '{}'", line);
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }

        boolean result = sweetService.deleteById(id);

        if (!result) {
            logger.warn("Не вдалося видалити солодощі — ID={} не знайдено або вже видалені", id);
            System.out.println("Не вдалося видалити: або солодощів з таким Id не було");
        } else {
            System.out.println("Солодощі успішно видалено");
        }
    }
}


