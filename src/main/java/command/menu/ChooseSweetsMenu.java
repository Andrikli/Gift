package command.menu;
import command.Command;
import command.actions.OpenMenuCommand;
import java.util.List;
import model.SweetCategory;
import service.SweetService;

public class ChooseSweetsMenu extends AbstractMenu {
    private final SweetService sweetService;
    public ChooseSweetsMenu(SweetService sweetService) {

        super("Обрати категорію");
        this.sweetService = sweetService;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new OpenMenuCommand(
                "Цукерки",
                () -> new ManageSweetsMenu(SweetCategory.CANDY, sweetService),
                in
        ));

        items.add(new OpenMenuCommand(
                "Печиво",
                () -> new ManageSweetsMenu(SweetCategory.COOKIE, sweetService),
                in));

        items.add(new OpenMenuCommand(
                "Шоколад",
                () -> new ManageSweetsMenu(SweetCategory.CHOCOLATE, sweetService),
                in));
    }
}


