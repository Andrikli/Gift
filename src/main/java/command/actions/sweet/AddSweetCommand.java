package command.actions.sweet;

import command.Command;
import model.*;
import service.SweetService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddSweetCommand implements Command {
    private final SweetCategory category;
    private final SweetService sweetService;
    private final Scanner in;

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public AddSweetCommand( SweetCategory category, SweetService sweetService, Scanner in) {
        this.category = category;
        this.sweetService = sweetService;
        this.in = in;
    }
    @Override
    public String name() {
        return "Додати "+ switch (category){
            case CANDY -> "цукерки";
            case CHOCOLATE ->"шоколад";
            case COOKIE ->"печиво";
        };
    }
    @Override
    public void execute() {

        System.out.println("Введіть назву:");
        String name = in.nextLine().trim();

        System.out.println("Введіть вагу (г):");
        double weight = readDouble();

        System.out.println("Введіть кількість цукру (%):");
        double sugar = readDouble();

        System.out.println("Введіть ціну:");
        double price = readDouble();

        System.out.println("Введіть дату виготовлення (dd.MM.yyyy):");
        LocalDate manufactureDate = readDate();

        System.out.println("Введіть термін придатності в днях:");
        int expiryDays = readInt();

        System.out.println("Введіть дату списання / перевірки (dd.MM.yyyy):");
        LocalDate disposeDate = readDate();

        System.out.println("Введіть виробника:");
        String manufacturer = in.nextLine().trim();

        System.out.println("Введіть місто виробництва:");
        String city = in.nextLine().trim();

        double cacaoPercent = 0;
        String color = null;
        if (category == SweetCategory.CHOCOLATE) {
            System.out.println("Введіть відсоток какао:");
            cacaoPercent = readDouble();

            System.out.println("Введіть колір шоколаду:");
            color = in.nextLine().trim();
        }

        String flourType = null;
        if (category == SweetCategory.COOKIE) {
            System.out.println("Введіть тип муки:");
            flourType = in.nextLine().trim();
        }

        Sweet sweet = switch (category) {
            case CANDY -> Candy.builder()
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withManufactureDate(manufactureDate)
                    .withExpiryDays(expiryDays)
                    .withDisposeDate(disposeDate)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .build();

            case COOKIE -> Cookie.builder()
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withManufactureDate(manufactureDate)
                    .withExpiryDays(expiryDays)
                    .withDisposeDate(disposeDate)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .flourType(flourType)
                    .build();

            case CHOCOLATE -> Chocolate.builder()
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withManufactureDate(manufactureDate)
                    .withExpiryDays(expiryDays)
                    .withDisposeDate(disposeDate)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .cacaoPercent(cacaoPercent)
                    .color(color)
                    .build();
        };

        sweetService.addSweet(sweet);
        System.out.println(" Солодощі додано успішно!");
    }

    private double readDouble() {
        while (true) {
            String line = in.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Некоректне число. Спробуйте ще раз:");
            }
        }
    }

    private int readInt() {
        while (true) {
            String line = in.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Некоректне ціле число. Спробуйте ще раз:");
            }
        }
    }

    private LocalDate readDate() {
        while (true) {
            String line = in.nextLine().trim();
            try {
                return LocalDate.parse(line, DATE_FMT);
            } catch (DateTimeParseException e) {
                System.out.println("Некоректна дата. Формат dd.MM.yyyy. Спробуйте ще раз:");
            }
        }
    }
}
