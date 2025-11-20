package command.actions;
import command.actions.*;
import command.Command;
import model.*;
import service.SweetService;
import java.util.List;
import java.util.Scanner;
public class EditSweetCommand implements Command {
    private final SweetService sweetService;
    private final Scanner in;

    public EditSweetCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Редагувати солодощі";
    }

    @Override
    public void execute() {
        System.out.print("Введіть ID солодощів: ");
        int id = Integer.parseInt(in.nextLine().trim());

        Sweet old = sweetService.findById(id);
        if (old == null || old.isDeleted()) {
            System.out.println("Немає такого ID.");
            return;
        }

        SweetCategory cat;
        if (old instanceof Candy) {
            cat = SweetCategory.CANDY;
        } else if (old instanceof Cookie) {
            cat = SweetCategory.COOKIE;
        } else if (old instanceof Chocolate) {
            cat = SweetCategory.CHOCOLATE;
        } else {
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

        boolean ok = sweetService.editSweet(id, name, weight, sugar, price, manufacturer, city, cacao, color, flour);

        if (ok)
            System.out.println(" Солодощі успішно змінено.");
        else
            System.out.println(" Помилка редагування.");
    }
}
