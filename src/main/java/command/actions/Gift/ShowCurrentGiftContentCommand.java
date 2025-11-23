package command.actions.Gift;
import command.Command;
import model.*;
import service.GiftService;
import service.SweetService;
import util.SweetUtils;

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
            System.out.println(SweetUtils.format(s));
            System.out.println();
        }

    }
}

