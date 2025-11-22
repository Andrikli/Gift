package command.menu;
import command.Command;
import command.actions.PrintCommand;
import command.actions.LoadFromFileGiftsCommand;
import command.actions.LoadFromFileSweetsCommand;
import service.GiftService;
import service.SweetService;
import java.util.List;

public class LoadMenu extends AbstractMenu {
    private final SweetService sweetService;
    private final GiftService giftService;
    public LoadMenu(SweetService sweetService, GiftService giftService) {
        super("Файли завантажити");
        this.sweetService = sweetService;
        this.giftService = giftService;

    }
    @Override
    protected void build(List<Command> items) {
        items.add(new LoadFromFileGiftsCommand(giftService));
        items.add(new LoadFromFileSweetsCommand(sweetService));
    }
}
