package command.menu;

import command.Command;
import command.actions.Gift.*;
import command.actions.PrintCommand;
import service.GiftService;
import java.util.List;
import java.util.Scanner;

public class GiftsMenu extends AbstractMenu {
    private final GiftService giftService;
    private final Scanner in;

    public GiftsMenu(GiftService giftService, Scanner in) {
        super("Керування подарунками");
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    protected void build(List<Command> items) {
        items.add(new ListGiftsCommand(giftService));
        items.add(new RestoreGiftCommand(giftService, in));
        items.add(new DeleteGiftCommand(giftService, in));
        items.add(new SearchGiftCommand(giftService, in));
        items.add(new EditTitleCommand(giftService, in));

    }
}