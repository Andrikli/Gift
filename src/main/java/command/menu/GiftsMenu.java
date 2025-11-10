package command.menu;

import command.Command;
import command.actions.PrintCommand;
import java.util.List;

public class GiftsMenu extends AbstractMenu {
    public GiftsMenu() { super("Керування подарунками"); }

    @Override
    protected void build(List<Command> items) {
        items.add(new PrintCommand("Створити", "Створення подарунка", in));
        items.add(new PrintCommand("Обрати поточний", "Поточний подарунок обрано", in));
        items.add(new PrintCommand("Видалити", "Подарунок видалено", in));
        items.add(new PrintCommand("Список подарунків", "Список подарунків показано", in));
        items.add(new PrintCommand("Пошук подарунка", "Пошук здійснено", in));
        items.add(new PrintCommand("Редагувати назву", "Змінено назву", in));
        items.add(new PrintCommand("Відновити подарунок", "Успігно відновлено", in));
    }
}