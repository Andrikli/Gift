package command.actions.sweet;

import command.Command;
import model.Sweet;
import model.SweetCategory;
import service.SweetService;
import service.SweetSort;
import model.SortKey;
import model.SortOrder;
import util.SweetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.List;

public class SortSweetsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SortSweetsCommand.class);

    private final SweetService sweetService;
    private final Scanner in;

    public SortSweetsCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
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

        System.out.println("=== Відсортований склад солодощів ===");
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

        SweetCategory cat = switch (line) {
            case "1" -> SweetCategory.CANDY;
            case "2" -> SweetCategory.COOKIE;
            case "3" -> SweetCategory.CHOCOLATE;
            default -> null;
        };

        logger.debug("Обрана категорія для сортування: {}", cat);
        return cat;
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
        SortKey key = switch (line) {
            case "1" -> SortKey.ID;
            case "2" -> SortKey.PRICE;
            case "3" -> SortKey.SUGAR;
            case "4" -> SortKey.WEIGHT;
            case "5" -> SortKey.FRESHNESS;
            case "6" -> chocolateExtra ? SortKey.CACAO : null;
            default -> null;
        };

        if (key == null) {
            logger.warn("Невірний ключ сортування: '{}'. Використано ID за замовчуванням.", line);
            System.out.println("Невірний вибір, використовую ID.");
            key = SortKey.ID;
        }

        logger.debug("Обраний ключ сортування: {}", key);
        return key;
    }

    private SortOrder askOrder() {
        System.out.println("Порядок сортування:");
        System.out.println("1) За зростанням");
        System.out.println("2) За спаданням");
        String line = in.nextLine().trim();

        SortOrder order = "2".equals(line) ? SortOrder.DESC : SortOrder.ASC;

        logger.debug("Обраний порядок сортування: {}", order);
        return order;
    }
}

