package command.actions.Gift;
import model.Gift;
import service.GiftService;
import command.Command;
import java.util.Scanner;

public class RestoreGiftCommand implements Command {
    private final  GiftService giftService;
    private final Scanner in;

    public RestoreGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }
    @Override
    public String name(){
        return "Відновити подарунки";
    }
    public void execute() {
        System.out.println("Введіть Id подарунка для відновлення");
        String line = in.nextLine().trim();
        int id;
        try{
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }
        boolean result = giftService.restoreById(id);
        if(!result){
            System.out.println("Не вдалося відновити: подарунки не були видаленими або подарунків з таким Id не існує");
        }else{
            System.out.println("Подарунки" + id + "успішно відновлено");
        }
    }
}
