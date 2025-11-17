package command.menu;
import command.Command;
import command.actions.PrintCommand;
import command.actions.AddSweetCommand;
import service.SweetService;
import java.util.List;
import model.SweetCategory;

public class ManageSweetsMenu extends AbstractMenu {
    private final SweetCategory category;
    private final SweetService sweetService;
    public ManageSweetsMenu(SweetCategory category, SweetService sweetService) {
        super("Керування: " + switch (category) {
            case CANDY -> "цукерками";
            case COOKIE -> "печивом";
            case CHOCOLATE -> "шоколадом";
        });
        this.category = category;
        this.sweetService = sweetService;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new AddSweetCommand(category, sweetService , in));
        items.add(new PrintCommand("Видалити","Успішно видалено", in));
        items.add(new PrintCommand("Редагувати","Успішно редаговано", in));
    }
}
