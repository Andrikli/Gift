package command.menu;
import command.Command;
import command.actions.OpenMenuCommand;
import java.util.List;
import service.GiftService;
import service.SweetService;
public class FileMenu extends AbstractMenu {
    private final SweetService sweetService;
    private final GiftService giftService;
    public FileMenu(SweetService sweetService, GiftService giftService) {
        super("Файли");
        this.sweetService = sweetService;
        this.giftService = giftService;
    }

    @Override
    protected void build(List<Command> items) {
        items.add(new OpenMenuCommand(
                "Завантажити",
                () -> new LoadMenu(sweetService, giftService),
                in
        ));
        items.add(new OpenMenuCommand(
                "Зберегти",
                () -> new SaveMenu(sweetService, giftService),
                in
        ));
    }
}

