package service;

import model.Gift;
import repository.GiftRepository;
import model.Sweet;
import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GiftService {
    private static final Logger logger = LogManager.getLogger(GiftService.class);

    private final GiftRepository giftRepository;
    private final SweetService sweetService;
    private Integer currentGiftId = null;

    public GiftService(GiftRepository giftRepository, SweetService sweetService) {
        this.giftRepository = giftRepository;
        this.sweetService = sweetService;
    }
    public Gift createGift(String title){
        Gift gift= Gift.builder().title(title).build();
        Gift saved = giftRepository.save(gift);
        logger.info("Створено подарунок: id={}, title='{}'", saved.getId(), saved.getTitle());
        return saved;

    }
    public List<Gift> getAll(){
        return giftRepository.findAll().stream().filter(g -> !g.isDeleted()).toList();
    }
    public Gift findById(int id){
        return giftRepository.findById(id);
    }
    public boolean setCurrentGiftId(int currentId){
        Gift gift = giftRepository.findById(currentId);
        if(gift==null||gift.isDeleted()){
            logger.warn("Не можна встановити поточний подарунок: id={}(не існує або видалений)", currentId);
            return false;
        }
        currentGiftId = currentId;
        logger.info("Поточний подарунок встановлено: id={}", currentGiftId);
        return true;
    }
    public Gift getCurrentGift(){
        if(currentGiftId==null){
            return null;
        }
        Gift gift = giftRepository.findById(currentGiftId);
        if(gift==null||gift.isDeleted()){
            logger.error("Поточний подарунок id={} не існує або видалений — скинуто", currentGiftId);
            currentGiftId = null;
            return null;
        }
        return giftRepository.findById(currentGiftId);
    }
    public void clearCurrentGift(){
        if (currentGiftId != null) {
            logger.info("Поточний подарунок скинуто (id={})", currentGiftId);
        }
        currentGiftId = null;
    }


    public boolean addSweetToGift(int sweetId) {
        Gift gift = getCurrentGift();
        if (gift == null) {
            logger.warn("Спроба додати солодощі без поточного подарунка");
            return false;
        }
        Sweet sweet = sweetService.findById(sweetId);
        if (sweet == null|| sweet.isDeleted()) {
            logger.warn("Спроба додати неіснуючі/видалені солодощі id={}", sweetId);
            return false;
        }
        boolean ok = giftRepository.update(gift.toBuilder().addSweetId(sweetId).build());
        if (ok) logger.info("Солодощі id={} додано до подарунка id={}", sweetId, gift.getId());
        else logger.error("Помилка при оновленні подарунка id={} (додавання солодощів)", gift.getId());

        return ok;
    }

    public boolean RemoveFromGift(int sweetId){
        Gift gift = getCurrentGift();
        if (gift == null) {
            logger.warn("Спроба видалити солодощі без поточного подарунка");
            return false;
        }
        boolean ok = giftRepository.update(gift.toBuilder().removeSweetId(sweetId).build());
        if (ok) logger.info("Солодощі id={} видалено з подарунка id={}", sweetId, gift.getId());
        else logger.error("Помилка при оновленні подарунка id={} (видалення солодощів)", gift.getId());

        return ok;
    }
    public List<Sweet> getGiftSweets(){
        Gift gift = getCurrentGift();
        if (gift == null) {
            return List.of();
        }
        List<Sweet> result = new ArrayList<>();
        for (Integer sweetId : gift.getSweetIds()) {
            Sweet sweet = sweetService.findById(sweetId);
            if (sweet != null&& !sweet.isDeleted()) {
                result.add(sweet);
            }
        }
        return result;
    }
    public double getCurrentTotalWeight() {
        double total = 0.0;
        for (Sweet s : getGiftSweets()) {
            total += s.getWeightGram();
        }
        return total;
    }
    public double getCurrentTotalPrice() {
        double total = 0.0;
        for (Sweet s : getGiftSweets()) {
            total += s.getPrice();
        }
        return total;
    }
    public boolean deleteById(int id) {
        Gift g = giftRepository.findById(id);
        if (g == null || g.isDeleted()) {
            logger.warn("Спроба видалити неіснуючий подарунок id={}", id);
            return false;
        }

        boolean ok = giftRepository.update(g.toBuilder().deleted(true).build());
        if (ok) {
            logger.info("Подарунок id={} видалено", id);
            if (currentGiftId != null && currentGiftId.equals(id)) {
                currentGiftId = null;
                logger.info("Поточний подарунок скинуто (було id={})", id);
            }
        } else {
            logger.error("Не вдалося видалити подарунок id={}", id);
        }
        return ok;
    }
    public boolean restoreById(int id) {
        Gift g = giftRepository.findById(id);
        if (g == null || !g.isDeleted()) {
            logger.warn("Спроба відновити подарунок id={} який не видалений або не існує", id);
            return false;
        }
        boolean ok = giftRepository.update(g.toBuilder().deleted(false).build());
        if (ok) logger.info("Подарунок id={} відновлено", id);
        else logger.error("Не вдалося відновити подарунок id={}", id);

        return ok;
    }
    public int deleteAll() {
        int count = 0;
        for (Gift g : giftRepository.findAll()) {
            if (!g.isDeleted()) {
                Gift deletedGift = g.toBuilder()
                        .deleted(true)
                        .build();
                giftRepository.update(deletedGift);
                count++;
            }
        }
        currentGiftId = null;
        return count;
    }
    public List<Gift> searchByTitle(String part) {
        String q = part.toLowerCase();
        return giftRepository.findAll().stream()
                .filter(g -> !g.isDeleted())
                .filter(g -> g.getTitle().toLowerCase().contains(q))
                .toList();
    }
    public boolean renameGift(int id, String newTitle) {
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("Нова назва не може бути порожньою");
        }

        Gift g = giftRepository.findById(id);
        if (g == null || g.isDeleted()) {
            return false;
        }

        Gift updated = g.toBuilder()
                .title(newTitle)
                .build();

        return giftRepository.update(updated);
    }
    public int clearExpiredFromCurrentGift() {
        Gift gift = getCurrentGift();
        if (gift == null) return -1;

        List<Integer> ids = gift.getSweetIds();
        List<Integer> newIds = new ArrayList<>();

        int removed = 0;

        for (Integer sweetId : ids) {
            Sweet s = sweetService.findById(sweetId);

            if (s == null || s.isDeleted()) {
                // якщо не існує або видалений у складі — пропускаємо, але не рахуємо як "прострочений"
                continue;
            }

            if (s.isExpired()) {
                removed++;      // рахуємо прострочені
            } else {
                newIds.add(sweetId);  // залишаємо свіжі
            }
        }

        if (removed == 0) {
            return 0;
        }

        // будуємо подарунок із новим списком ID
        Gift updated = gift.toBuilder()
                .clearSweetIds()
                .addAllSweetIds(newIds)
                .build();

        giftRepository.update(updated);
        return removed;
    }

    public void saveLoadedGift(Gift gift) {
        giftRepository.save(gift);
        logger.info("Подарунок '{}' завантажено з файлу", gift.getTitle());
    }
    public List<Gift> getAllIncludingDeleted() {
        return giftRepository.findAll();
    }

}
