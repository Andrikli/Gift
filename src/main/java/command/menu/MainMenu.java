package command.menu;
import command.actions.OpenMenuCommand;
import command.Command;
import service.SweetService;

import java.awt.*;
import java.util.List;

public class MainMenu extends AbstractMenu {
    private final SweetService sweetService;

    public MainMenu(SweetService sweetService){
        super("Головне меню");
        this.sweetService = sweetService;

    }
    @Override
    protected void build(List<Command> items) {
        items.add(new OpenMenuCommand("Файли", FileMenu::new, in));
        items.add(new OpenMenuCommand("Cклад солодощів", () -> new SweetsMenu(sweetService),
                in
        ));
        items.add(new OpenMenuCommand("Поточний подарунок", CurrentGiftMenu::new, in));
        items.add(new OpenMenuCommand("Керування подарунками", GiftsMenu::new, in));
        items.add(new OpenMenuCommand("Довідка",HelpMenu::new, in));
    }
    @Override
    protected boolean isRoot(){
        return true;
    }
}
