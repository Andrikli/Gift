package command.menu;
import command.Command;
import command.actions.*;

import java.util.List;

import command.actions.sweet.*;
import service.SweetService;

public class SweetsMenu extends AbstractMenu {
    private final SweetService sweetService;
    public SweetsMenu(SweetService sweetService) {
        super("Склад солодощів");
        this.sweetService = sweetService;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new ListAllSweetsCommand(sweetService));
        items.add(new OpenMenuCommand(
                "Додати солодощі",
                () -> new ChooseSweetsMenu(sweetService),
                in
        ));
        items.add(new SortSweetsCommand(sweetService, in));
        items.add(new RestoreSweetCommand(sweetService, in));
        items.add(new DeleteSweetCommand(sweetService, in));
        items.add(new DeleteAllSweetsCommand(sweetService, in));
        items.add(new SearchInStockCommand(sweetService, in));
        items.add(new EditSweetCommand( sweetService, in));
    }
}