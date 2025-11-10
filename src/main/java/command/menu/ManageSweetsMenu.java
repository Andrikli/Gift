package command.menu;
import command.Command;
import command.actions.PrintCommand;
import java.util.List;
import model.SweetCategory;

public class ManageSweetsMenu extends AbstractMenu {
    private final SweetCategory category;
    public ManageSweetsMenu(SweetCategory category) {
        super("Керування: " + switch (category) {
            case CANDY -> "цукерками";
            case COOKIE -> "печивом";
            case CHOCOLATE -> "шоколадом";
        });
        this.category = category;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new PrintCommand("Додати","Успішно додано", in));
        items.add(new PrintCommand("Видалити","Успішно видалено", in));
        items.add(new PrintCommand("Редагувати","Успішно редаговано", in));
    }
}
