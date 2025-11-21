package command.actions.Gift;
import command.Command;
import model.Gift;
import model.Sweet;
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
        System.out.println("ID солодощів: " + gift.getSweetIds());

        List<Sweet> sweets = giftService.getGiftSweets();
        if (sweets.isEmpty()) {
            System.out.println("У подарунку немає солодощів.");
            return;
        }

        System.out.println("--- Деталі солодощів ---");
        for (Sweet s : sweets) {
            System.out.printf("ID=%d | %s | вага=%.2f | ціна=%.2f%n",
                    s.getId(), s.getName(), s.getWeightGram(), s.getPrice());
        }
    }
}
