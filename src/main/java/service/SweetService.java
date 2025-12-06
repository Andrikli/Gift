package service;

import model.*;
import repository.SweetRepository;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.SweetCategory;


public class SweetService {
    private static final Logger logger = LogManager.getLogger(SweetService.class);
    private final SweetRepository sweetRepository;
    public SweetService(SweetRepository sweetRepository) {

        this.sweetRepository = sweetRepository;
    }

    public void addSweet(Sweet sweet) {
        if (sweet.getWeightGram() <= 0) {
            logger.error("Спроба додати солодощі з некоректною вагою: {} г (id={})",
                    sweet.getWeightGram(), sweet.getId());
            throw new IllegalArgumentException("Вага має бути > 0");
        }
        if (sweet.getPrice() < 0) {
            logger.error("Спроба додати солодощі з від’ємною ціною: {} (id={})",
                    sweet.getPrice(), sweet.getId());
            throw new IllegalArgumentException("Ціна не може бути від’ємною");
        }
        sweetRepository.save(sweet);
        logger.info("Солодощі додано в склад: id={}, name='{}', ",
                sweet.getId(), sweet.getName());

    }
    public List<Sweet> getAll() {
        List<Sweet> list = sweetRepository.findAll().stream().filter(sweet -> !sweet.isDeleted())
                .toList();
        logger.debug("Отримано {} не видалених солодощів зі складу", list.size());
        return list;
    }
    public Sweet findById(int id) {
        Sweet sweet = sweetRepository.findById(id);
        if (sweet == null) {
            logger.warn("Солодощі з id={} не знайдені у складі", id);
            return null;
        }
        if( sweet.isDeleted()){
            logger.warn("Солодощі з id={} позначені як видалені", id);
            return null;

        }
        return sweet;
    }
    public boolean deleteById(int id) {
        Sweet sweet = sweetRepository.findById(id);
        if (sweet == null || sweet.isDeleted()) {
            logger.warn("Спроба видалити неіснуючі/вже видалені солодощі id={}", id);
            return false;
        }
        sweet.markDeleted();
        logger.info("Солодощі id={} позначені як видалені", id);
        return true;
    }
    public boolean restoreId(int id) {
        Sweet sweet = sweetRepository.findById(id);
        if (sweet == null|| !sweet.isDeleted()) {
            logger.warn("Спроба відновити солодощі id={}, які не існують або не видалені", id);
            return false;
        }
        sweet.restore();
        logger.info("Солодощі id={} успішно відновлено", id);
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
        logger.info("Позначено як видалені {} солодощів зі складу", deletedCount);
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
            logger.warn("Спроба редагувати неіснуючі/видалені солодощі id={}", id);
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
            logger.error("Невідомий тип солодощів при редагуванні id={}, class={}",
                    id, old.getClass().getName());
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

        boolean ok = sweetRepository.update(updated);
        if (ok) {
            logger.info("Солодощі id={} успішно відредаговано (name='{}')", id, name);
        } else {
            logger.error("Не вдалося оновити солодощі id={} при редагуванні", id);
        }
        return ok;
    }
    public int removeExpiredSweets() {
        int count = 0;
        for (Sweet sweet : sweetRepository.findAll()) {
            if (!sweet.isDeleted() && sweet.isExpired()) {
                sweet.markDeleted();
                count++;
            }
        }
        if (count > 0) {
            logger.info("Зі складу видалено {} прострочених солодощів", count);
        } else {
            logger.info("Прострочених солодощів на складі не знайдено");
        }
        return count;
    }
    public List<Sweet> getAllIncludingDeleted() {
        List<Sweet> all = sweetRepository.findAll();
        logger.debug("Отримано {} солодощів (включно з видаленими)", all.size());
        return all;
    }
    public List<Sweet> sortStock(SortKey key,
                                 SortOrder order,
                                 SweetCategory filterCategory){
        List<Sweet> all = sweetRepository.findAll();
        logger.info("Сортування складу: key={}, order={}, filterCategory={}",
                key, order, filterCategory);
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
        logger.debug("Пошук за цукром у списку ID: знайдено {} солодощів ({}..{}%)",
                result.size(), minSugar, maxSugar);
        return result;
    }

    public List<Sweet> searchInStorageBySugar(double minSugar, double maxSugar) {
        List<Sweet> res = sweetRepository.findAll().stream()
                .filter(s -> !s.isDeleted())
                .filter(s -> s.getSugarPercent() >= minSugar && s.getSugarPercent() <= maxSugar)
                .toList();
        logger.info("Пошук на складі за цукром у діапазоні {}..{}%: знайдено {} солодощів",
                minSugar, maxSugar, res.size());
        return res;
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