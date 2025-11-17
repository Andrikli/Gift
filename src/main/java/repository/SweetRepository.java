package repository;
import model.Sweet;
import java.util.List;

public interface SweetRepository {
    void add(Sweet sweet);
    List<Sweet> findAll();
}
