package command.actions.Gift;
import model.Gift;
import service.GiftService;
import command.Command;

import java.util.List;
import java.util.Scanner;

public class DeleteGiftCommand implements Command {
    private GiftService giftService;
    private final Scanner in;
    public DeleteGiftCommand(GiftService giftService, Scanner in) {
        this.giftService = giftService;
        this.in = in;
    }
    @Override
    public String name(){

        return "Видалити подарунок";
    }
    @Override
    public void execute() {
        List<Gift> gifts = giftService.getAll();
        if(gifts.isEmpty()){
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
        } catch (NumberFormatException e){
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }

        boolean result = giftService.deleteById(id);
        if(!result){
            System.out.println("Не вдалося видалити: або подарунок з таким Id не було");
        }else{
            System.out.println("Подарунок успішно видалено");
        }

    }
}
