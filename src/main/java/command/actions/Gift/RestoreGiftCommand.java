package command.actions.Gift;

import service.GiftService;
import command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class RestoreGiftCommand implements Command {
    private final GiftService giftService;
    private final Scanner in;

    private static final Logger logger = LogManager.getLogger(RestoreGiftCommand.class);

    public RestoreGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name(){
        return "Відновити подарунки";
    }

    @Override
    public void execute() {
        System.out.println("Введіть Id подарунка для відновлення");
        String line = in.nextLine().trim();
        int id;
        try{
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            logger.warn("Користувач ввів некоректний ID для відновлення подарунка: '{}'", line);
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }

        boolean result = giftService.restoreById(id);
        if(!result){
            logger.info("Не вдалося відновити подарунок id={} (не видалений або не існує)", id);
            System.out.println("Не вдалося відновити: подарунки не були видаленими або подарунків з таким Id не існує");
        } else{
            logger.info("Користувач відновив подарунок id={}", id);
            System.out.println("Подарунок " + id + " успішно відновлено");
        }
    }
}

