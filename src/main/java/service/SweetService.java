package service;

import model.*;
import repository.SweetRepository;
import java.util.List;

public class SweetService {
    private final SweetRepository sweetRepository;
    public SweetService(SweetRepository sweetRepository) {

        this.sweetRepository = sweetRepository;
    }

    public void addSweet(Sweet sweet) {
        if (sweet.getWeightGram() <= 0) {
            throw new IllegalArgumentException("Вага має бути > 0");
        }
        if (sweet.getPrice() < 0) {
            throw new IllegalArgumentException("Ціна не може бути від’ємною");
        }
        sweetRepository.save(sweet);


    }
    public List<Sweet> getAll() {
        return sweetRepository.findAll().stream().filter(sweet -> !sweet.isDeleted())
                .toList();
    }
    public Sweet findById(int id) {
        Sweet sweet = sweetRepository.findById(id);
        if (sweet == null || sweet.isDeleted()) {
            return null;
        }
        return sweet;
    }
    public boolean deleteById(int id) {
        Sweet sweet = sweetRepository.findById(id);
        if (sweet == null) {
            return false;
        }
        if (sweet.isDeleted()) {
            return false;
        }
        sweet.markDeleted();
        return true;
    }
}
