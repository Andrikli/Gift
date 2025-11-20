package repository;
import model.Gift;
import java.util.List;

public interface GiftRepository {
    List<Gift> findAll();
    Gift findById(int id);
    Gift save(Gift gift);
    boolean update(Gift gift);
}
