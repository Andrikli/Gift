package repository;
import model.Sweet;
import java.util.List;

public interface SweetRepository {
    Sweet save(Sweet sweet);;
    List<Sweet> findAll();
    Sweet findById(int id);
    boolean update(Sweet updated);
}
