package command.actions;

import command.BaseCommand;

import java.util.Scanner;

public class PrintCommand extends BaseCommand {
    private final String successText;

    public PrintCommand( String label, String successText, Scanner in) {
        super(label, in);
        this.successText = successText;
    }
    @Override
    public void execute() {
        System.out.println(successText + " - все спрацювало\n");
        System.out.println("Натисніть Enter, щоб повернутися у меню");
        in.nextLine();
    }
}
