package command.actions.Gift;
import command.Command;
import model.Gift;
import service.GiftService;
import java.util.List;
public class ListGiftsCommand implements Command {
    private final GiftService giftService;

    public ListGiftsCommand(GiftService giftService) {
        this.giftService = giftService;
    }
    @Override
    public String name() {
        return "Список подарунків";
    }

    @Override
    public void execute() {
        List<Gift> gifts = giftService.getAll();
        if (gifts.isEmpty()) {
            System.out.println("Наразі немає жодного подарунка.");
            return;
        }

        System.out.println("Список подарунків:");
        for (Gift g : gifts) {
            System.out.println("ID: " + g.getId()
                    + " | Назва: " + g.getTitle()
                    + " | sweetIds: " + g.getSweetIds());
        }
    }
}
