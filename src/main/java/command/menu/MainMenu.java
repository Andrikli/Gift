package command.menu;
import command.actions.OpenMenuCommand;
import command.Command;

import java.awt.*;
import java.util.List;

public class MainMenu extends AbstractMenu {
    public MainMenu(){
        super("Головне меню");

    }
    @Override
    protected void build(List<Command> items) {
        items.add(new OpenMenuCommand("Файли", FileMenu::new, in));
        items.add(new OpenMenuCommand("Cклад солодощів", SweetsMenu::new, in));
        items.add(new OpenMenuCommand("Поточний подарунок", CurrentGiftMenu::new, in));
        items.add(new OpenMenuCommand("Керування подарунками", GiftsMenu::new, in));
        items.add(new OpenMenuCommand("Довідка",HelpMenu::new, in));
    }
    @Override
    protected boolean isRoot(){
        return true;
    }
}
