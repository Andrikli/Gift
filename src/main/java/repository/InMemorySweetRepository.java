package repository;

import model.Sweet;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class InMemorySweetRepository implements SweetRepository {

    private final List<Sweet> sweets = new ArrayList<>();
    private int nextId = 1;


    @Override
    public Sweet save(Sweet sweet) {
        // якщо в об'єкта вже є id — нехай собі буде, не перестворюємо
        if (sweet.getId() != null) {
            // можна тут зробити update, але давай поки що просто додаємо як є
            sweets.add(sweet);
            return sweet;
        }

        int id = nextId++;

        Sweet withId = sweet.toBuilder()
                .withId(id)
                .build();

        sweets.add(withId);
        return withId;
    }
    @Override
    public void add(Sweet sweet) {
        // щоб не плодити два різних способи, делегуємо в save
        save(sweet);
    }

    @Override
    public List<Sweet> findAll() {
        // віддаємо копію, щоб ніхто не ламав список напряму
        return new ArrayList<>(sweets);
    }
    @Override
    public Sweet findById(int id) {
        for (Sweet s : sweets) {
            if (!s.isDeleted() && s.getId() != null && s.getId() == id) {
                return s;
            }
        }
        return null; // або Optional<Sweet>
    }
}

