package command.actions.Gift;

import model.Gift;
import service.GiftService;
import command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class DeleteGiftCommand implements Command {
    private final GiftService giftService;
    private final Scanner in;

    private static final Logger logger = LogManager.getLogger(DeleteGiftCommand.class);

    public DeleteGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }

    @Override
    public String name() {
        return "Видалити подарунок";
    }

    @Override
    public void execute() {
        List<Gift> gifts = giftService.getAll();
        if(gifts.isEmpty()){
            logger.info("Користувач спробував видалити подарунок, але список порожній");
            System.out.println("Список порожній, немає що видаляти");
            return;
        }

        for (Gift gift : gifts){
            System.out.println(gift);
        }

        System.out.println("Введіть id подарунка для видалення");
        String line = in.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            logger.warn("Користувач ввів некоректний ID для видалення: '{}'", line);
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }

        boolean result = giftService.deleteById(id);
        if(!result){
            logger.info("Не вдалося видалити подарунок, id={} (не існує або вже видалений)", id);
            System.out.println("Не вдалося видалити: або подарунок з таким Id не було");
        } else{
            logger.info("Користувач видалив подарунок id={}", id);
            System.out.println("Подарунок успішно видалено");
        }
    }
}

