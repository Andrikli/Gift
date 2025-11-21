package command.actions.Gift;
import command.Command;
import service.GiftService;
public class ClearGiftCommand implements  Command {
    private final GiftService giftService;

    public ClearGiftCommand(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public String name() {
        return "Очистити подарунок від прострочених солодощів";
    }

    @Override
    public void execute() {
        int removed = giftService.clearExpiredFromCurrentGift();
        if (removed == -1) {
            System.out.println("Поточний подарунок не обраний.");
        } else if (removed == 0) {
            System.out.println("У поточному подарунку немає прострочених солодощів.");
        } else {
            System.out.println("Видалено " + removed + " прострочених солодощів з подарунка.");
        }
    }
}
