package command.actions.Gift;
import command.Command;
import model.*;
import service.GiftService;
import java.util.List;

public class ShowCurrentGiftContentCommand implements Command {

    private final GiftService giftService;

    public ShowCurrentGiftContentCommand(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public String name() {
        return "Показати вміст поточного подарунка";
    }

    @Override
    public void execute() {

        Gift gift = giftService.getCurrentGift();
        if (gift == null) {
            System.out.println("Поточний подарунок не обраний.");
            return;
        }

        System.out.println("=== Поточний подарунок ===");
        System.out.println("ID: " + gift.getId());
        System.out.println("Назва: " + gift.getTitle());
        System.out.println("Список солодощів (ID): " + gift.getSweetIds());
        System.out.println("-----------------------------");

        List<Sweet> sweets = giftService.getGiftSweets();
        if (sweets.isEmpty()) {
            System.out.println("У подарунку немає солодощів.");
            return;
        }

        System.out.println("--- Деталі солодощів ---");
        for (Sweet s : sweets) {

            System.out.println("ID: " + s.getId());
            System.out.println("Назва: " + s.getName());
            System.out.println("Вага: " + s.getWeightGram());
            System.out.println("Цукор: " + s.getSugarPercent());
            System.out.println("Ціна: " + s.getPrice());
            System.out.println("Дата виготовлення: " + s.getManufactureDate());
            System.out.println("Термін придатності (днів): " + s.getExpiryDays());
            System.out.println("Дата списання: " + s.getDisposeDate());
            System.out.println("Виробник: " + s.getManufacturer());
            System.out.println("Місто: " + s.getCity());

            // CATEGORY-SPECIFIC FIELDS
            if (s instanceof Chocolate ch) {
                System.out.println("Какао %: " + ch.getCacaoPercent());
                System.out.println("Колір шоколаду: " + ch.getColor());
            }

            if (s instanceof Cookie ck) {
                System.out.println("Тип муки: " + ck.getFlourType());
            }

            if (s instanceof Candy) {
                System.out.println("Категорія: Цукерка");
            }

            System.out.println("-----------------------------");
        }
    }
}
