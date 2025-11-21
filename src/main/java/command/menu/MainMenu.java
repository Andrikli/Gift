package command.menu;
import command.actions.OpenMenuCommand;
import command.Command;
import service.GiftService;
import service.SweetService;

import java.awt.*;
import java.util.List;

public class MainMenu extends AbstractMenu {
    private final SweetService sweetService;
    private final GiftService giftService;
    public MainMenu(SweetService sweetService,  GiftService giftService) {
        super("Головне меню");
        this.sweetService = sweetService;
        this.giftService = giftService;

    }
    @Override
    protected void build(List<Command> items) {
        items.add(new OpenMenuCommand("Файли", FileMenu::new, in));
        items.add(new OpenMenuCommand("Cклад солодощів", () -> new SweetsMenu(sweetService),
                in
        ));
        items.add(new OpenMenuCommand(
                "Поточний подарунок",
                () -> new CurrentGiftMenu(giftService, in),
                in
        ));

        items.add(new OpenMenuCommand(
                "Керування подарунками",
                () -> new GiftsMenu(giftService, in),
                in
        ));
        items.add(new OpenMenuCommand("Довідка",HelpMenu::new, in));
    }
    @Override
    protected boolean isRoot(){
        return true;
    }
}
