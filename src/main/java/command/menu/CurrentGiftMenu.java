package command.menu;
import command.Command;
import command.actions.PrintCommand;
import java.util.List;

public class CurrentGiftMenu extends AbstractMenu {
    public CurrentGiftMenu() {
        super("Поточний подарунок");
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new PrintCommand("Показати вміст", "Показ вмісту поточного подарунка", in));
        items.add(new PrintCommand("Додати за ID", "Додавання виконано", in));
        items.add(new PrintCommand("Додати за cписком", "Додавання виконано", in));
        items.add(new PrintCommand("Видалити солодощу", "Видалення виконано", in));
        items.add(new PrintCommand("Сортувати", "Сортування виконано", in));
        items.add(new PrintCommand("Фільтрувати", "Фільтрація виконана", in));
        items.add(new PrintCommand("Обчислити вагу і ціну", "Експорт звіту(вага і ціна)", in));
        items.add(new PrintCommand("Очистити подарунок(Видалити прострочені)", "Видалено прострочені солодощі", in));
        items.add(new PrintCommand("Зібрати за вагою/бюджетом", "успішно зібрано", in));


    }
}
