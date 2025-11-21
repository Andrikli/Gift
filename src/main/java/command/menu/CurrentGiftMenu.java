package command.menu;
import command.Command;
import command.actions.Gift.AddSweetToGiftCommand;
import command.actions.PrintCommand;
import service.GiftService;
import command.actions.Gift.*;
import java.util.List;
import java.util.Scanner;

public class CurrentGiftMenu extends AbstractMenu {
    private final GiftService giftService;

    private final Scanner in;
    public CurrentGiftMenu(GiftService giftService, Scanner in)
    {
        super("Поточний подарунок");
        this.giftService = giftService;
        this.in = in;
    }
    @Override
    protected void build(List<Command> items) {
        items.add(new ShowCurrentGiftContentCommand(giftService));
        items.add(new AddSweetToGiftCommand(giftService, in));// додати за id
        items.add(new DeleteFromGiftCommand(giftService, in));
        // відновити солодощі в подарунку
        items.add(new PrintCommand("Сортувати", "Сортування виконано", in));
        items.add(new PrintCommand("Фільтрувати", "Фільтрація виконана", in));
        items.add(new ShowCurrentGiftTotalsCommand(giftService));
        items.add(new ClearGiftCommand(giftService));
        items.add(new PrintCommand("Очистити подарунок(Видалити прострочені)", "Видалено прострочені солодощі", in));
        items.add(new PrintCommand("Зібрати за вагою/бюджетом", "успішно зібрано", in));


    }
}
