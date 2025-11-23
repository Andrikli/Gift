package command.actions.sweet;

import command.Command;
import model.Sweet;
import model.SweetCategory;
import service.SweetService;
import service.SweetSort;
import model.SortKey;
import model.SortOrder;
import util.SweetUtils;

import java.util.Scanner;

import java.util.List;

public class SortSweetsCommand implements Command{
    private final SweetService sweetService;
    private final Scanner in;
public SortSweetsCommand(SweetService sweetService,Scanner in){
    this.sweetService=sweetService;
    this.in=in;
}

    @Override
    public String name() {
        return "Сортування складу";
    }

    @Override
    public void execute() {
        SweetCategory filterCategory = askCategory();
        SortKey key = askSortKey(filterCategory);
        SortOrder order = askOrder();

        List<Sweet> source = sweetService.getAll();
        List<Sweet> sorted = SweetSort.sort(source, key, order, filterCategory);
        if (sorted.isEmpty()) {
            System.out.println("Немає солодощів, що відповідають фільтру.");
            return;
        }
        System.out.println(" Відсортований склад солодощів ");
        for (Sweet sweet : sorted) {
            System.out.println(SweetUtils.format(sweet));
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
            default -> null; // null = всі
        };
    }
    private SortKey askSortKey(SweetCategory filterCategory) {
        System.out.println("Сортувати за:");
        System.out.println("1) ID");
        System.out.println("2) Ціною");
        System.out.println("3) Вмістом цукру");
        System.out.println("4) Вага");
        System.out.println("5) Свіжість (дата закінчення терміну)");
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
