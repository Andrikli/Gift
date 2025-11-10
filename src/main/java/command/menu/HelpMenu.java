package command.menu;
import command.Command;
import command.actions.PrintCommand;
import java.util.List;

public class HelpMenu extends AbstractMenu {
    public HelpMenu() {
        super("Довідка");
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new command.actions.PrintCommand("Показати довідку", "Довідка відображена", in));
        items.add(new command.actions.PrintCommand("Про програму", "Candy Gift Builder — каркас меню", in));
    }
}
