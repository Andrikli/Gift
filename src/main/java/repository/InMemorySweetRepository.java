package repository;

import model.Sweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemorySweetRepository implements SweetRepository {

    private final List<Sweet> sweets = new ArrayList<>();

    @Override
    public void add(Sweet sweet) {
        sweets.add(sweet);
    }

    @Override
    public List<Sweet> findAll() {
        // віддаємо копію, щоб ніхто не ламав список напряму
        return new ArrayList<>(sweets);
    }
}

