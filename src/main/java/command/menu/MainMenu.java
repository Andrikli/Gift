package command.menu;
import command.actions.Gift.CreateGiftCommand;
import command.actions.Gift.SetCurrentGiftCommand;
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
        items.add(new OpenMenuCommand("Файли", ()-> new FileMenu(sweetService, giftService),
                in));
        items.add(new OpenMenuCommand("Cклад солодощів", () -> new SweetsMenu(sweetService),
                in
        ));
        items.add(new CreateGiftCommand(giftService, in));
        items.add(new SetCurrentGiftCommand(giftService, in));
        items.add(new OpenMenuCommand(
                "Поточний подарунок",
                () -> new CurrentGiftMenu(giftService, sweetService, in),
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
