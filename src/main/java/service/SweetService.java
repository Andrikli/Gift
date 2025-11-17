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
        sweetRepository.add(sweet);


    }
    public List<Sweet> getAll() {
        return sweetRepository.findAll();
    }
}
