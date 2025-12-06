package command.actions.sweet;

import command.Command;
import model.Sweet;
import service.SweetService;
import util.SweetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class SearchInStockCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SearchInStockCommand.class);

    private final SweetService sweetService;
    private final Scanner in;

    public SearchInStockCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Пошук у складі солодощів";
    }

    @Override
    public void execute() {
        List<Sweet> source = sweetService.getAll();
        if (source.isEmpty()) {
            System.out.println("Склад порожній.");
            return;
        }


        switch (askMode()) {
            case "1" -> searchById(source);
            case "2" -> searchByManufacturer(source);
            case "3" -> searchByCity(source);
            case "4" -> searchBySugar(source);
            default -> {
                logger.warn("Користувач вибрав невідомий режим пошуку");
                System.out.println("Невідомий режим пошуку.");
            }
        }
    }

    private String askMode() {
        System.out.println("Оберіть тип пошуку:");
        System.out.println("1) За ID");
        System.out.println("2) За виробником");
        System.out.println("3) За містом");
        System.out.println("4) За вмістом цукру (діапазон)");
        System.out.print("Ваш вибір: ");
        return in.nextLine().trim();
    }

    private void printResults(List<Sweet> list) {
        if (list.isEmpty()) {

            System.out.println("Нічого не знайдено.");
            return;
        }
        System.out.println("=== Результати пошуку ===");
        for (Sweet s : list) {
            System.out.println(SweetUtils.format(s));
            System.out.println();
        }
    }

    private void searchById(List<Sweet> source) {
        System.out.print("Введіть ID: ");
        String line = in.nextLine().trim();
        try {
            int id = Integer.parseInt(line);
            List<Sweet> res = SweetService.searchById(source, id);
            printResults(res);
        } catch (NumberFormatException e) {
            logger.warn("Некоректний ввід ID при пошуку: '{}'", line);
            System.out.println("ID має бути цілим числом.");
        }
    }

    private void searchByManufacturer(List<Sweet> source) {
        System.out.print("Введіть частину назви виробника: ");
        String man = in.nextLine();

        List<Sweet> res = SweetService.searchByManufacturer(source, man);
        printResults(res);
    }

    private void searchByCity(List<Sweet> source) {
        System.out.print("Введіть частину назви міста: ");
        String city = in.nextLine();

        List<Sweet> res = SweetService.searchByCity(source, city);
        printResults(res);
    }

    private void searchBySugar(List<Sweet> source) {
        try {
            System.out.print("Мінімальний % цукру: ");
            double min = Double.parseDouble(in.nextLine().trim());
            System.out.print("Максимальний % цукру: ");
            double max = Double.parseDouble(in.nextLine().trim());

            List<Sweet> res = SweetService.searchBySugar(source, min, max);
            printResults(res);
        } catch (NumberFormatException e) {
            logger.warn("Некоректний ввід чисел при пошуку за цукром");
            System.out.println("Потрібно вводити числа.");
        }
    }
}
