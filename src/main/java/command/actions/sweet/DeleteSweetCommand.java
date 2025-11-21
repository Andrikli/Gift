package command.actions.sweet;
import command.Command;
import model.Sweet;
import service.SweetService;
import java.util.List;
import java.util.Scanner;

public class DeleteSweetCommand implements Command {
    private SweetService sweetService;
    private final Scanner in;
    public DeleteSweetCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }
    @Override
    public String name(){
        return "Видалити солодощі за ID";
    }
    @Override
    public void execute() {
        List<Sweet> sweets = sweetService.getAll();
        if(sweets.isEmpty()){
            System.out.println("Склад порожній, немає що видаляти");
            return;

        }
        for (Sweet sweet : sweets){
            System.out.println(sweet);
        }
        System.out.println("Введіть id солодощів для видалення");
        String line = in.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e){
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }

        boolean result = sweetService.deleteById(id);
        if(!result){
            System.out.println("Не вдалося видалити: або солодощів з таким Id не було");
        }else{
            System.out.println("Солодощі успішно видалено");
        }

    }
}

