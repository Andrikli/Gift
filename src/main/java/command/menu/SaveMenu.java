package command.menu;
import command.Command;
import command.actions.PrintCommand;
import java.util.List;
import command.actions.SaveGiftsToFileCommand;
import command.actions.SaveSweetsToFileCommand;
import service.GiftService;
import service.SweetService;

public class SaveMenu extends AbstractMenu {
    private final SweetService sweetService;
    private final GiftService giftService;

    public SaveMenu(SweetService sweetService, GiftService giftService) {
        super("Файли зберегти");
        this.sweetService = sweetService;
        this.giftService = giftService;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new SaveGiftsToFileCommand(giftService));
        items.add(new SaveSweetsToFileCommand(sweetService));
    }
}
