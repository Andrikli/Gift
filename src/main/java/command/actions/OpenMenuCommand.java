package command.actions;
import command.BaseCommand;
import command.menu.AbstractMenu;
import java.util.Scanner;
import java.util.function.Supplier;
public class OpenMenuCommand extends BaseCommand {
    private final Supplier<AbstractMenu> submenuFactory;

    public OpenMenuCommand(String label, Supplier<AbstractMenu> submenuFactory, Scanner in) {
        super(label, in);
        this.submenuFactory = submenuFactory;

    }

    @Override
    public void execute() {
        AbstractMenu menu = submenuFactory.get();
        menu.show();
    }
}