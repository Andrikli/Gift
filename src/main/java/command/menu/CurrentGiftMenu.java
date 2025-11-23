package command.menu;
import command.Command;
import command.actions.Gift.AddSweetToGiftCommand;
import command.actions.PrintCommand;
import service.GiftService;
import command.actions.Gift.*;
import service.SweetService;

import java.util.List;
import java.util.Scanner;

public class CurrentGiftMenu extends AbstractMenu {
    private final GiftService giftService;
    private final SweetService sweetService;

    private final Scanner in;
    public CurrentGiftMenu(GiftService giftService,SweetService sweetService, Scanner in)
    {
        super("Поточний подарунок");
        this.giftService = giftService;
        this.sweetService = sweetService;
        this.in = in;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new ShowCurrentGiftContentCommand(giftService));
        items.add(new AddSweetToGiftCommand(giftService, in));
        items.add(new DeleteFromGiftCommand(giftService, in));
        items.add(new SortSweetsInGiftCommand(giftService, in));
        items.add(new SearchInGiftCommand(giftService ,sweetService, in));
        items.add(new ShowCurrentGiftTotalsCommand(giftService));
        items.add(new ClearGiftCommand(giftService));
    }
}
