package command.actions.sweet;
import command.Command;
import model.Sweet;
import service.SweetService;

import java.util.List;
import util.SweetUtils;
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
        for (Sweet s : sweetService.getAll()) {
            System.out.println(SweetUtils.format(s));
            System.out.println(); // пустий рядок
        }
    }
}
