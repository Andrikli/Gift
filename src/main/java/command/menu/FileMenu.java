package command.menu;
import command.Command;
import command.actions.OpenMenuCommand;
import java.util.List;

public class FileMenu extends AbstractMenu {
    public FileMenu(){
        super("Файли");
    }

    @Override
    protected void build(List<Command> items) {
        items.add(new OpenMenuCommand("Завантажити", LoadMenu::new, in));
        items.add(new OpenMenuCommand("Зберегти", SaveMenu::new, in));
    }
}
