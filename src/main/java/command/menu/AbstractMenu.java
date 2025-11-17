package command.menu;

import command.Command;
import java.util.*;


public abstract class AbstractMenu {
    protected final Scanner in = new Scanner(System.in);
    private final List<Command> items = new ArrayList<>();
    private final String title;
    private boolean running = true;
    private boolean initialized = false;
    protected AbstractMenu(String title) {
        this.title = title;

    }
    private void initIfNeeded() {
        if (!initialized) {
            build(items);
            addBackOrExit(items);
            initialized = true;
        }
    }
    protected boolean isRoot(){
        return false;
    }
    protected abstract void build(List<Command> items);

    private void addBackOrExit(List<Command> items) {
        if (isRoot()){
            items.add(new Command() {
                @Override
                public String name() {
                    return "0) Вихід";
                }

                @Override
                public void execute() {
                    running = false;
                }
            });
        }
        else {
            items.add(new Command() {
                @Override
                public String name() {
                    return "0) Назад";
                }
                @Override
                public void execute() {
                    running = false;
                }
            });
        }
    }
    public final void show() {
        // 👇 ТУТ, перед першим показом меню, будуємо його
        initIfNeeded();

        running = true;
        while (running) {
            printHeader();
            printItems();
            int choice = readChoice();
            int backIndex = items.size() - 1;

            if (choice == 0) {
                items.get(backIndex).execute();
                continue;
            }

            int idx = choice - 1;
            if (idx < 0 || idx >= backIndex) {
                System.out.println("Невірний вибір. Спробуйте ще раз.\n");
                continue;
            }
            items.get(idx).execute();
        }
    }
    private void printHeader() {
        System.out.println("=============");
        System.out.println(title);
        System.out.println("=============");
    }
    private void printItems() {
        int backIndex = items.size() - 1;

        for (int i = 0; i < backIndex; i++) {
            String shown = stripLeadingNumber(items.get(i).name());
            System.out.printf("%d) %s%n", i + 1, shown);
        }
        System.out.println("0) " + stripLeadingNumber(items.get(backIndex).name()));


        System.out.print("Ваш вибір: ");
    }
    private static String stripLeadingNumber(String s) {
        return s.replaceFirst("^\\s*\\d+\\)\\s*", "");
    }
    private int readChoice() {
        String line = in.nextLine().trim();
        try{
            return Integer.parseInt(line);
        }catch (NumberFormatException e){
            return -1;
        }
    }
}