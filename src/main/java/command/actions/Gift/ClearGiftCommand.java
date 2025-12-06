package command.actions.Gift;

import command.Command;
import service.GiftService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClearGiftCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ClearGiftCommand.class);
    private final GiftService giftService;

    public ClearGiftCommand(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public String name() {
        return "Очистити подарунок від прострочених солодощів";
    }

    @Override
    public void execute() {
        int removed = giftService.clearExpiredFromCurrentGift();

        if (removed == -1) {
            System.out.println("Поточний подарунок не обраний.");
            logger.warn("Спроба очистити не обраний подарунок");
        } else if (removed == 0) {
            System.out.println("У поточному подарунку немає прострочених солодощів.");
            logger.info("Очищення: прострочених солодощів не знайдено");
        } else {
            System.out.println("Видалено " + removed + " прострочених солодощів з подарунка.");
            logger.info("Очищено подарунок: видалено {} прострочених солодощів", removed);
        }
    }
}
