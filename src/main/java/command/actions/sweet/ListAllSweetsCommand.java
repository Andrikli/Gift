package command.actions.sweet;
import command.Command;
import model.Sweet;
import service.SweetService;

import java.util.List;

public class ListAllSweetsCommand implements Command{
    private final SweetService sweetService;

    public ListAllSweetsCommand(SweetService sweetService) {
        this.sweetService = sweetService;
    }
    @Override
    public String name(){
        return "Переглянути всі";
    }
    @Override
    public void execute() {
        List<Sweet> all = sweetService.getAll();

        if (all.isEmpty()){
            System.out.println("No sweets found");
            return;
        }

        System.out.println("Sweets found");
        for(Sweet s: all){
            System.out.println(s);
            System.out.println();
        }
    }
}
