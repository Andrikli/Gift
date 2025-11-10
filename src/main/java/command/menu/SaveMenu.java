package command.menu;
import command.Command;
import command.actions.PrintCommand;
import java.util.List;

public class SaveMenu extends AbstractMenu {
    public SaveMenu() {
        super("Файли зберегти");
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new PrintCommand("Зберегти подарунки","Подарунки збережено", in));
        items.add(new PrintCommand("Зберегти солодощі", "Солодощі збережено", in));
    }
}
