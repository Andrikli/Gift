package command.actions.sweet;
import command.Command;
import model.Sweet;
import service.GiftService;
import service.SweetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import util.SweetUtils;
public class ListAllSweetsCommand implements Command{
    private final SweetService sweetService;
    private static final Logger logger = LogManager.getLogger(GiftService.class);

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
            System.out.println();
        }
    }
}
