package command.actions;
import command.Command;

    public class AboutCommand implements Command {

        @Override
        public String name() {
            return "Про програму";
        }

        @Override
        public void execute() {
            System.out.println("===== ДОВІДКА =====");
            System.out.println("Candy Gift Builder — консольна програма для роботи зі складом солодощів та подарунками.");
            System.out.println();
            System.out.println("Програма дозволяє:");
            System.out.println("• Створювати різні типи солодощів");
            System.out.println("• Редагувати та видаляти солодощі");
            System.out.println("• Створювати подарунки та додавати в них солодощі");
            System.out.println("• Відновлювати видалені елементи");
            System.out.println("• Зберігати та завантажувати дані у файли");
            System.out.println();
            System.out.println("Розробник: Andriy Ilkiv");
            System.out.println("=====================");
        }
    }
