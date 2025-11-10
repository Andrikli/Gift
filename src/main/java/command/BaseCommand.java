package command;
import java.util.Scanner;

public abstract class BaseCommand implements Command {
    protected final String label;
    protected final Scanner in;

    protected BaseCommand(String label, Scanner in) {
        this.label = label;
        this.in = in;
    }

    @Override
    public String name() {
        return label;
    }
}
