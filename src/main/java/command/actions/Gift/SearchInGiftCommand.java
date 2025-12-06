package command.actions.Gift;

import command.Command;
import model.Gift;
import model.Sweet;
import service.GiftService;
import service.SweetService;
import util.SweetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class SearchInGiftCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SearchInGiftCommand.class);

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
            logger.warn("Спроба пошуку в подарунку без обраного поточного подарунка");
            System.out.println("Поточний подарунок не обраний.");
            return;
        }

        List<Sweet> source = giftService.getGiftSweets();
        if (source.isEmpty()) {
            logger.info("Пошук у подарунку — але він порожній (id={})", gift.getId());
            System.out.println("У подарунку немає солодощів.");
            return;
        }

        int mode = askMode();
        logger.info("Користувач розпочав пошук у подарунку id={} (mode={})", gift.getId(), mode);

        List<Sweet> found = switch (mode) {
            case 1 -> { // за ID
                int id = askInt("Введіть ID: ");
                logger.info("Пошук у подарунку id={} за sweetId={}", gift.getId(), id);
                yield SweetService.searchById(source, id);
            }
            case 2 -> { // за містом
                System.out.print("Введіть місто: ");
                String city = in.nextLine().trim();
                logger.info("Пошук у подарунку id={} за містом '{}'", gift.getId(), city);
                yield SweetService.searchByCity(source, city);
            }
            case 3 -> { // за виробником
                System.out.print("Введіть виробника: ");
                String mfr = in.nextLine().trim();
                logger.info("Пошук у подарунку id={} за виробником '{}'", gift.getId(), mfr);
                yield SweetService.searchByManufacturer(source, mfr);
            }
            case 4 -> { // за вмістом цукру
                double min = askDouble("Мінімальний % цукру: ");
                double max = askDouble("Максимальний % цукру: ");
                logger.info("Пошук у подарунку id={} за цукром у діапазоні {}–{}", gift.getId(), min, max);
                yield SweetService.searchBySugar(source, min, max);
            }
            default -> {
                logger.warn("Невідомий режим пошуку mode={}, повертаємо порожній результат", mode);
                yield List.<Sweet>of();
            }
        };

        if (found.isEmpty()) {
            logger.info("Пошук у подарунку id={} (mode={}) — результат порожній", gift.getId(), mode);
            System.out.println("У подарунку немає солодощів, що відповідають критерію.");
            return;
        }

        logger.info("Пошук у подарунку id={} (mode={}) — знайдено {} елемент(ів)",
                gift.getId(), mode, found.size());

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
            logger.warn("Некоректний вибір режиму пошуку '{}', використано mode=1 (за ID)", line);
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
                logger.warn("Некоректне ціле число '{}', очікувався int. Повторний ввід.", line);
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
                logger.warn("Некоректне число '{}', очікувався double. Повторний ввід.", line);
                System.out.println("Введіть число, спробуйте ще раз.");
            }
        }
    }
}
