package command.menu;
import command.Command;
import command.actions.OpenMenuCommand;
import command.actions.PrintCommand;
import java.util.List;
public class SweetsMenu extends AbstractMenu {
    public SweetsMenu() {
        super("Склад солодощів");
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new PrintCommand("Переглянути всі", "Перелік солодощів показано", in));
        items.add(new OpenMenuCommand("Керування солодощами", ChooseSweetsMenu::new, in));
        items.add(new PrintCommand("Редагувати", "Редагування у складі виконано", in));
        items.add(new PrintCommand("Видалити", "Видалено з складу", in));
        items.add(new PrintCommand("Пошук у складі", "Пошук здійснено", in));
    }
}