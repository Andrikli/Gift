package command.actions;
import command.Command;
import repository.SweetRepository;
import service.SweetService;

import java.util.Scanner;

public class RestoreSweetCommand implements Command {
    private final SweetService sweetService;
    private final Scanner in;
    public RestoreSweetCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }
    @Override
    public String name(){
        return "Відновити солодощі";
    }
    @Override
    public void execute() {
        System.out.println("Введіть id солодощів для відновлення ");
        String line = in.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(line);
        }catch(NumberFormatException e){
            System.out.println("Помилка : id має бути цілим числом");
            return;
        }
        boolean result = sweetService.restoreId(id);
        if(!result){
            System.out.println("Не вдалося відновити: солодощі не були видаленими або солодощів з таким Id не існує");
        }else{
            System.out.println("Солодощі" + id + "успішно відновлено");
        }
    }
}
