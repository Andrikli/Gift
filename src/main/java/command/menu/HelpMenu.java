package command.menu;
import command.Command;
import command.actions.AboutCommand;
import command.actions.HelpCommand;
import command.actions.PrintCommand;
import java.util.List;

public class HelpMenu extends AbstractMenu {
    public HelpMenu() {
        super("Довідка");
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new HelpCommand());
        items.add(new AboutCommand());
    }

}
