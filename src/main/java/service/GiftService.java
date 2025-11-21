package service;

import model.Gift;
import repository.GiftRepository;
import model.Sweet;
import java.util.List;
import java.util.ArrayList;


public class GiftService {
    private final GiftRepository giftRepository;
    private final SweetService sweetService;

    private Integer currentGiftId = null;

    public GiftService(GiftRepository giftRepository, SweetService sweetService) {
        this.giftRepository = giftRepository;
        this.sweetService = sweetService;
    }
    public Gift createGift(String title){
        Gift gift= Gift.builder().title(title).build();
        return giftRepository.save(gift);
    }
    public List<Gift> getAll(){
        return giftRepository.findAll().stream().filter(g -> !g.isDeleted()).toList();
    }
    public Gift findById(int id){
        return giftRepository.findById(id);
    }
    //Поточний подарунок
    public boolean setCurrentGiftId(int currentId){
        Gift gift = giftRepository.findById(currentId);
        if(gift==null||gift.isDeleted()){
            return false;
        }
        currentGiftId = currentId;
        return true;
    }
    public Gift getCurrentGift(){
        if(currentGiftId==null){
            return null;
        }
        Gift gift = giftRepository.findById(currentGiftId);
        if(gift==null||gift.isDeleted()){
            currentGiftId = null;
            return null;
        }
        return giftRepository.findById(currentGiftId);
    }
    public void clearCurrentGift(){
        currentGiftId = null;
    }


    //Робота з подарунком

    public boolean addSweetToGift(int sweetId) {
        Gift gift = getCurrentGift();
        if (gift == null) {
            return false;
        }
        Sweet sweet = sweetService.findById(sweetId);
        if (sweet == null|| sweet.isDeleted()) {
            return false;
        }
        Gift updated = gift.toBuilder().addSweetId(sweetId).build();
        return giftRepository.update(updated);
    }

    public boolean RemoveFromGift(int sweetId){
        Gift gift = getCurrentGift();
        if (gift == null) {
            return false;
        }
        Gift updated = gift.toBuilder().removeSweetId(sweetId).build();
        return giftRepository.update(updated);

    }
    public List<Sweet> getGiftSweets(){
        Gift gift = getCurrentGift();
        if (gift == null) {
            return List.of();
        }
        List<Sweet> result = new ArrayList<>();
        for (Integer sweetId : gift.getSweetIds()) {
            Sweet sweet = sweetService.findById(sweetId);
            if (sweet == null&& !sweet.isDeleted()) {
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
            return false; // нема або вже видалений
        }

        Gift deletedGift = g.toBuilder()
                .deleted(true)
                .build();

        boolean ok = giftRepository.update(deletedGift);

        if (ok && currentGiftId != null && currentGiftId == id) {
            currentGiftId = null; // якщо видалили поточний
        }

        return ok;
    }
    public boolean restoreById(int id) {
        Gift g = giftRepository.findById(id);
        if (g == null || !g.isDeleted()) {
            return false; // нема або не був видалений
        }

        Gift restored = g.toBuilder()
                .deleted(false)
                .build();

        return giftRepository.update(restored);
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

        List<Integer> ids = new ArrayList<>(gift.getSweetIds());
        int removed = 0;

        for (Integer sweetId : ids) {
            Sweet s = sweetService.findById(sweetId);
            if (s != null && !s.isDeleted() && s.isExpired()) { // isExpired() ти ще доробиш у Sweet
                removed++;
            }
        }

        if (removed == 0) return 0;

        // будуємо новий список ID без прострочених
        Gift updated = gift.toBuilder()
                .addAllSweetIds(
                        ids.stream()
                                .filter(id -> {
                                    Sweet s = sweetService.findById(id);
                                    return s == null || s.isDeleted() || !s.isExpired();
                                })
                                .toList()
                )
                .build();

        giftRepository.update(updated);
        return removed;
    }

}
