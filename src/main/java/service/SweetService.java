package service;

import model.*;
import repository.SweetRepository;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
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
        if (sweet == null || sweet.isDeleted()) {
            return false;
        }
        sweet.markDeleted();
        return true;
    }
    public boolean restoreId(int id) {
        Sweet sweet = sweetRepository.findById(id);
        if (sweet == null|| !sweet.isDeleted()) {
            return false;
        }
        sweet.restore();
        return true;
    }

    public int deleteAll() {
        int deletedCount = 0;
        for (Sweet sweet : sweetRepository.findAll()) {
           if (!sweet.isDeleted()) {
                sweet.markDeleted();
               deletedCount++;
           }
        }
        return deletedCount;
    }

    public boolean editSweet(int id,
                             String name,
                             double weight,
                             double sugar,
                             double price,
                             LocalDate manufactureDate,
                             int expiryDays,
                             LocalDate disposeDate,
                             String manufacturer,
                             String city,
                             Double cacaoPercent,
                             String color,
                             String flourType
    ){
        Sweet old = sweetRepository.findById(id);
        if (old == null || old.isDeleted()) {
            return false;
        }
        SweetCategory cat;
        if (old instanceof Candy) {
            cat = SweetCategory.CANDY;
        } else if (old instanceof Cookie) {
            cat = SweetCategory.COOKIE;
        } else if (old instanceof Chocolate) {
            cat = SweetCategory.CHOCOLATE;
        } else {
            throw new IllegalStateException("Unknown sweet type: " + old.getClass());
        }
        Sweet updated = switch (cat) {
            case CANDY -> Candy.builder()
                    .withId(id)
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withManufactureDate(manufactureDate)
                    .withExpiryDays(expiryDays)
                    .withDisposeDate(disposeDate)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .build();

            case CHOCOLATE -> Chocolate.builder()
                    .withId(id)
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withManufactureDate(manufactureDate)
                    .withExpiryDays(expiryDays)
                    .withDisposeDate(disposeDate)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .cacaoPercent(cacaoPercent)
                    .color(color)
                    .build();

            case COOKIE -> Cookie.builder()
                    .withId(id)
                    .withName(name)
                    .withWeightGram(weight)
                    .withSugarPercent(sugar)
                    .withPrice(price)
                    .withManufactureDate(manufactureDate)
                    .withExpiryDays(expiryDays)
                    .withDisposeDate(disposeDate)
                    .withManufacturer(manufacturer)
                    .withCity(city)
                    .flourType(flourType)
                    .build();
        };

        return sweetRepository.update(updated);
    }
    public int removeExpiredSweets() {
        int count = 0;
        for (Sweet sweet : sweetRepository.findAll()) {
            if (!sweet.isDeleted() && sweet.isExpired()) {
                sweet.markDeleted();
                count++;
            }
        }
        return count;
    }
    public List<Sweet> getAllIncludingDeleted() {
        return sweetRepository.findAll();
    }
    public List<Sweet> sortStock(SortKey key,
                                 SortOrder order,
                                 SweetCategory filterCategory){
        List<Sweet> all = sweetRepository.findAll();
        return SweetSort.sort(all, key, order, filterCategory);
    }
    public List<Sweet> searchBySugarInList(List<Integer> ids, double minSugar, double maxSugar) {
        List<Sweet> result = new ArrayList<>();

        for (Integer id : ids) {
            Sweet s = findById(id);
            if (s == null || s.isDeleted()) continue;

            double sugar = s.getSugarPercent();
            if (sugar >= minSugar && sugar <= maxSugar) {
                result.add(s);
            }
        }

        return result;
    }

    public List<Sweet> searchInStorageBySugar(double minSugar, double maxSugar) {
        return sweetRepository.findAll().stream()
                .filter(s -> !s.isDeleted())
                .filter(s -> s.getSugarPercent() >= minSugar && s.getSugarPercent() <= maxSugar)
                .toList();
    }
    public static List<Sweet> searchBySugar(List<Sweet> source, double min, double max) {
        List<Sweet> res = new ArrayList<>();
        for (Sweet s : source) {
            if (s == null || s.isDeleted()) continue;
            double sp = s.getSugarPercent();
            if (sp >= min && sp <= max) {
                res.add(s);
            }
        }
        return res;
    }

    public static List<Sweet> searchByManufacturer(List<Sweet> source, String manufacturer) {
        String q = manufacturer.trim().toLowerCase();
        List<Sweet> res = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            Sweet s = source.get(i);
            if (s == null || s.isDeleted()) continue;
            if (s.getManufacturer() != null &&
                    s.getManufacturer().toLowerCase().contains(q)) {
                res.add(s);
            }
        }
        return res;
    }

    public static List<Sweet> searchByCity(List<Sweet> source, String city) {
        String q = city.trim().toLowerCase();
        List<Sweet> res = new ArrayList<>();
        for (Sweet s : source) {
            if (s == null || s.isDeleted()) continue;
            if (s.getCity() != null &&
                    s.getCity().toLowerCase().contains(q)) {
                res.add(s);
            }
        }
        return res;
    }

    public  static List<Sweet> searchById(List<Sweet> source, int id) {
        List<Sweet> res = new ArrayList<>();
        for (Sweet s : source) {
            if (s == null || s.isDeleted()) continue;
            if (s.getId() != null && s.getId() == id) {
                res.add(s);
                break; // ID у нас унікальний
            }
        }
        return res;
    }
}