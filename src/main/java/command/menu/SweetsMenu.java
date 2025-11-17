package command.menu;
import command.Command;
import command.actions.OpenMenuCommand;
import command.actions.PrintCommand;
import java.util.List;
import service.SweetService;

public class SweetsMenu extends AbstractMenu {
    private final SweetService sweetService;
    public SweetsMenu(SweetService sweetService) {
        super("Склад солодощів");
        this.sweetService = sweetService;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new PrintCommand("Переглянути всі", "Перелік солодощів показано", in));
        items.add(new OpenMenuCommand(
                "Керування солодощами",
                () -> new ChooseSweetsMenu(sweetService),
                in
        ));
        items.add(new PrintCommand("Редагувати", "Редагування у складі виконано", in));
        items.add(new PrintCommand("Видалити", "Видалено з складу", in));
        items.add(new PrintCommand("Пошук у складі", "Пошук здійснено", in));
    }
}