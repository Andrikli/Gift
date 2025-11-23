package command.actions.Gift;

import command.Command;
import model.Gift;
import model.Sweet;
import service.GiftService;
import service.SweetService;
import util.SweetUtils;

import java.util.List;
import java.util.Scanner;

public class SearchInGiftCommand implements Command {

    private final GiftService giftService;
    private final SweetService sweetService;
    private final Scanner in;

    public SearchInGiftCommand(GiftService giftService, SweetService sweetService, Scanner in) {
        this.giftService = giftService;
        this.sweetService = sweetService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Пошук у поточному подарунку";
    }

    @Override
    public void execute() {
        Gift gift = giftService.getCurrentGift();
        if (gift == null) {
            System.out.println("Поточний подарунок не обраний.");
            return;
        }

        List<Sweet> source = giftService.getGiftSweets();
        if (source.isEmpty()) {
            System.out.println("У подарунку немає солодощів.");
            return;
        }

        int mode = askMode();
        List<Sweet> found = switch (mode) {
            case 1 -> { // за ID
                int id = askInt("Введіть ID: ");
                yield SweetService.searchById(source, id);
            }
            case 2 -> { // за містом
                System.out.print("Введіть місто: ");
                String city = in.nextLine().trim();
                yield SweetService.searchByCity(source, city);
            }
            case 3 -> { // за виробником
                System.out.print("Введіть виробника: ");
                String mfr = in.nextLine().trim();
                yield SweetService.searchByManufacturer(source, mfr);
            }
            case 4 -> { // за вмістом цукру
                double min = askDouble("Мінімальний % цукру: ");
                double max = askDouble("Максимальний % цукру: ");
                yield SweetService.searchBySugar(source, min, max);
            }
            default -> List.of();
        };

        if (found.isEmpty()) {
            System.out.println("У подарунку немає солодощів, що відповідають критерію.");
            return;
        }

        System.out.println("=== Знайдені солодощі в подарунку ===");
        for (Sweet s : found) {
            System.out.println(SweetUtils.format(s));
            System.out.println();
        }
    }

    private int askMode() {
        System.out.println("Оберіть тип пошуку:");
        System.out.println("1) За ID");
        System.out.println("2) За містом");
        System.out.println("3) За виробником");
        System.out.println("4) За вмістом цукру");
        System.out.print("Ваш вибір: ");
        String line = in.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private int askInt(String msg) {
        while (true) {
            System.out.print(msg);
            String line = in.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("ID має бути цілим числом, спробуйте ще раз.");
            }
        }
    }

    private double askDouble(String msg) {
        while (true) {
            System.out.print(msg);
            String line = in.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Введіть число, спробуйте ще раз.");
            }
        }
    }
}
