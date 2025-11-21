package command.actions.Gift;

import command.Command;
import model.Gift;
import service.GiftService;

public class ShowCurrentGiftTotalsCommand implements Command {

    private final GiftService giftService;

    public ShowCurrentGiftTotalsCommand(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public String name() {
        return "Обчислити вагу і ціну поточного подарунка";
    }

    @Override
    public void execute() {
        Gift gift = giftService.getCurrentGift();
        if (gift == null) {
            System.out.println("Поточний подарунок не обраний.");
            return;
        }

        double totalWeight = giftService.getCurrentTotalWeight();
        double totalPrice  = giftService.getCurrentTotalPrice();

        System.out.println("Поточний подарунок: " + gift.getTitle());
        System.out.println("Загальна вага: " + totalWeight + " г");
        System.out.println("Загальна ціна: " + totalPrice);
    }
}

