package command.menu;
import command.Command;
import command.actions.OpenMenuCommand;
import command.actions.PrintCommand;
import command.actions.ListAllSweetsCommand;
import command.actions.FindSweetByIdCommand;
import command.actions.DeleteSweetCommand;
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
        items.add(new ListAllSweetsCommand(sweetService));
        items.add(new OpenMenuCommand(
                "Керування солодощами",
                () -> new ChooseSweetsMenu(sweetService),
                in
        ));
        items.add(new PrintCommand("Редагувати", "Редагування у складі виконано", in));
        items.add(new DeleteSweetCommand(sweetService, in));
        items.add(new FindSweetByIdCommand(sweetService, in));
    }
}