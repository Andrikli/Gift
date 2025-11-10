package command.menu;
import command.Command;
import command.actions.OpenMenuCommand;
import java.util.List;
import model.SweetCategory;

public class ChooseSweetsMenu extends AbstractMenu {
    public ChooseSweetsMenu() {
        super("Обрати категорію");
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new OpenMenuCommand("Цукерки",   () -> new ManageSweetsMenu(SweetCategory.CANDY), in));
        items.add(new OpenMenuCommand("Печиво",    () -> new ManageSweetsMenu(SweetCategory.COOKIE), in));
        items.add(new OpenMenuCommand("Шоколад",   () -> new ManageSweetsMenu(SweetCategory.CHOCOLATE), in));
    }
}

