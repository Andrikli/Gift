package command.actions;

import command.Command;
import model.*;
import service.SweetService;

import java.time.LocalDate;
import java.util.Scanner;

public class AddSweetCommand implements Command {
    private final SweetCategory category;
    private final SweetService sweetService;
    private final Scanner in;

    public AddSweetCommand( SweetCategory category, SweetService sweetService, Scanner in) {
        this.category = category;
        this.sweetService = sweetService;
        this.in = in;
    }
    @Override
    public String name() {
        return "Add Sweet"+ switch (category){
            case CANDY -> "цукерки";
            case CHOCOLATE ->"шоколад";
            case COOKIE ->"печиво";
        };
    }
    @Override
    public void execute() {

        System.out.println("Введіть назву");
        String name = in.nextLine();
        System.out.println("Введіть вагу");
        double weight = in.nextDouble();
        in.nextLine();
        System.out.println("Введіть кількість цукру");
        double sugar = in.nextDouble();
        in.nextLine();
        System.out.println("Введіть ціну");
        double price = in.nextDouble();
        in.nextLine();
        LocalDate date = LocalDate.now();
        System.out.println("Введіть виробника:");
        String manufacturer = in.nextLine();
        System.out.println("Введіть місто виробництва");
        String city = in.nextLine();

        double cacaoPercent = 0;
        String color = null;
        if (category == SweetCategory.CHOCOLATE) {
            System.out.println("Введіть відсоток какао");
            cacaoPercent = in.nextDouble();
            System.out.println("Введіть колір шоколаду");
            color = in.nextLine();
        }
        String flourtype = null;
        if (category == SweetCategory.COOKIE) {
            System.out.println("Введіть тип муки");
            flourtype = in.nextLine();

        }

        Sweet sweet = switch (category) {
            case CANDY -> Candy.builder()
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withExpiryDate(date)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .build();

            case COOKIE -> Cookie.builder()
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withExpiryDate(date)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .flourType(flourtype)
                    .build();

            case CHOCOLATE -> Chocolate.builder()
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withExpiryDate(date)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .cacaoPercent(cacaoPercent)
                    .color(color)
                    .build();
        };

        sweetService.addSweet(sweet);
    }

}
