package command.actions.Gift;

import command.Command;
import model.Sweet;
import model.SweetCategory;
import service.GiftService;
import service.SweetSort;
import model.SortKey;
import model.SortOrder;
import util.SweetUtils;

import java.util.List;
import java.util.Scanner;

public class SortSweetsInGiftCommand implements Command {
    private final GiftService giftService;
    private final Scanner in;

    public SortSweetsInGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Сортувати солодощі в поточному подарунку";
    }

    @Override
    public void execute() {
        if (giftService.getCurrentGift() == null) {
            System.out.println("Поточний подарунок не обраний.");
            return;
        }

        List<Sweet> source = giftService.getGiftSweets();
        if (source.isEmpty()) {
            System.out.println("У подарунку немає солодощів.");
            return;
        }

        SweetCategory filterCategory = askCategory();
        SortKey key = askSortKey(filterCategory);
        SortOrder order = askOrder();

        List<Sweet> sorted = SweetSort.sort(source, key, order, filterCategory);

        System.out.println("=== Відсортовані солодощі в подарунку ===");
        for (Sweet s : sorted) {
            System.out.println(SweetUtils.format(s));
            System.out.println();
        }
    }

    private SweetCategory askCategory() {
        System.out.println("Оберіть категорію:");
        System.out.println("0) Усі");
        System.out.println("1) Цукерки");
        System.out.println("2) Печиво");
        System.out.println("3) Шоколад");
        String line = in.nextLine().trim();

        return switch (line) {
            case "1" -> SweetCategory.CANDY;
            case "2" -> SweetCategory.COOKIE;
            case "3" -> SweetCategory.CHOCOLATE;
            default -> null; // всі
        };
    }

    private SortKey askSortKey(SweetCategory filterCategory) {
        System.out.println("Сортувати за:");
        System.out.println("1) ID");
        System.out.println("2) Ціною");
        System.out.println("3) Вмістом цукру");
        System.out.println("4) Вага");
        System.out.println("5) Свіжість");

        boolean chocolateExtra = (filterCategory == SweetCategory.CHOCOLATE);

        if (chocolateExtra) {
            System.out.println("6) Відсотком какао");
        }

        String line = in.nextLine().trim();

        switch (line) {
            case "1":
                return SortKey.ID;
            case "2":
                return SortKey.PRICE;
            case "3":
                return SortKey.SUGAR;
            case "4":
                return SortKey.WEIGHT;
            case "5":
                return SortKey.FRESHNESS;
            case "6":
                if (chocolateExtra) return SortKey.CACAO;
                break;
        }

        System.out.println("Невірний вибір, використовую ID.");
        return SortKey.ID;
    }


    private SortOrder askOrder() {
        System.out.println("Порядок сортування:");
        System.out.println("1) За зростанням");
        System.out.println("2) За спаданням");
        String line = in.nextLine().trim();
        return "2".equals(line) ? SortOrder.DESC : SortOrder.ASC;
    }
}
