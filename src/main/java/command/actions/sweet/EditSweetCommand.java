package command.actions.sweet;

import command.Command;
import model.*;
import service.SweetService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// 🔹 log4j2
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditSweetCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditSweetCommand.class);

    private final SweetService sweetService;
    private final Scanner in;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public EditSweetCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Редагувати солодощі";
    }

    private LocalDate readDate(String msg) {
        System.out.println(msg);
        while (true) {
            String line = in.nextLine().trim();
            try {
                return LocalDate.parse(line, FMT);
            } catch (DateTimeParseException e) {
                System.out.println("Некоректна дата. Формат dd.MM.yyyy. Спробуйте ще раз:");
            }
        }
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введіть ID солодощів: ");
            String idStr = in.nextLine().trim();

            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                logger.warn("Некоректний ввід ID при редагуванні солодощів: '{}'", idStr);
                System.out.println("Помилка: ID має бути цілим числом.");
                return;
            }

            Sweet old = sweetService.findById(id);
            if (old == null || old.isDeleted()) {
                logger.warn("Спроба редагувати неіснуючі/видалені солодощі id={}", id);
                System.out.println("Немає такого ID.");
                return;
            }

            SweetCategory cat;
            if (old instanceof Candy) cat = SweetCategory.CANDY;
            else if (old instanceof Cookie) cat = SweetCategory.COOKIE;
            else if (old instanceof Chocolate) cat = SweetCategory.CHOCOLATE;
            else {
                logger.error("Невідомий тип солодощів при редагуванні id={}, class={}",
                        id, old.getClass().getName());
                throw new IllegalStateException("Unknown sweet type: " + old.getClass());
            }

            System.out.println("Введіть назву:");
            String name = in.nextLine();

            System.out.println("Введіть вагу:");
            double weight = Double.parseDouble(in.nextLine().trim());

            System.out.println("Введіть кількість цукру:");
            double sugar = Double.parseDouble(in.nextLine().trim());

            System.out.println("Введіть ціну:");
            double price = Double.parseDouble(in.nextLine().trim());

            LocalDate manufacture = readDate("Введіть дату виготовлення (dd.MM.yyyy):");

            System.out.println("Введіть термін придатності (днів):");
            int expiryDays = Integer.parseInt(in.nextLine().trim());

            LocalDate dispose = readDate("Введіть дату списання (dd.MM.yyyy):");

            System.out.println("Введіть виробника:");
            String manufacturer = in.nextLine();

            System.out.println("Введіть місто:");
            String city = in.nextLine();

            Double cacao = null;
            String color = null;
            String flour = null;

            if (cat == SweetCategory.CHOCOLATE) {
                System.out.println("Введіть % какао:");
                cacao = Double.parseDouble(in.nextLine().trim());
                System.out.println("Введіть колір:");
                color = in.nextLine();
            }

            if (cat == SweetCategory.COOKIE) {
                System.out.println("Введіть тип муки:");
                flour = in.nextLine();
            }

            boolean ok = sweetService.editSweet(
                    id, name, weight, sugar, price,
                    manufacture, expiryDays, dispose,
                    manufacturer, city,
                    cacao, color, flour
            );

            if (ok) {
                System.out.println("Солодощі успішно змінено.");
            } else {
                logger.error("SweetService повернув false при редагуванні солодощів id={}", id);
                System.out.println("Помилка редагування.");
            }

        } catch (NumberFormatException e) {
            logger.warn("Некоректне числове значення при редагуванні солодощів: {}", e.getMessage());
            System.out.println("Помилка: одне з числових значень введено некоректно.");
        } catch (Exception e) {
            logger.error("Неочікувана помилка при редагуванні солодощів", e);
            System.out.println("Сталася неочікувана помилка при редагуванні солодощів.");
        }
    }
}

