package command.menu;
import command.Command;
import command.actions.PrintCommand;

import java.util.List;

public class LoadMenu extends AbstractMenu {
    public LoadMenu() {
        super("Файли завантажити");

    }
    @Override
    protected void build(List<Command> items) {
        items.add(new PrintCommand("Завантажити подарунки","Подарунки завантажено", in));
        items.add(new PrintCommand("Завантажити солодощі","Солодощі завантажено", in));
    }
}
