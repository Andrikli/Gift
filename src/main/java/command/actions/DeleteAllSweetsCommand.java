package command.actions;
import command.Command;
import service.SweetService;
import java.util.Scanner;
public class DeleteAllSweetsCommand implements Command{
    private final SweetService sweetService;
    private final Scanner in;

    public DeleteAllSweetsCommand(SweetService sweetService, Scanner in) {
        this.sweetService = sweetService;
        this.in = in;
    }
    @Override
    public String name(){
        return "Видалити всі солодощі";
    }
    @Override
    public void execute(){
        System.out.println("УВАГА: всі солодощі будуть позначені як видалені.");
        System.out.print("Ви впевнені? (y/n): ");
        String answer = in.nextLine().trim().toLowerCase();
        if (!answer.equals("y")&&!answer.equals("так")) {
            System.out.println("Операцію скасовано");
            return;
        }
        int deletedCount = sweetService.deleteAll();
        if(deletedCount == 0){
            System.out.println("немає солодощів для видалення");
        }
        else{
            System.out.println("Позначено як видалені"+ deletedCount +"солодощів");
        }
    }
}
