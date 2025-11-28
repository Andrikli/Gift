package repository;

import model.Gift;
import java.util.List;
import java.util.ArrayList;

public class InMemoryGiftRepository implements GiftRepository {

    private final List<Gift> gifts = new ArrayList<>();
    private int nextId = 1;
    @Override
    public List<Gift> findAll(){

        return new ArrayList<>(gifts);
    }
    @Override
    public Gift save(Gift gift) {
        // Якщо id вже заданий (з файлу) — додаємо і оновлюємо nextId
        if (gift.getId() != 0) { // 0 у тебе грає роль "немає id"
            gifts.add(gift);

            if (gift.getId() >= nextId) {
                nextId = gift.getId() + 1;
            }
            return gift;
        }

        // Якщо id немає — генеруємо новий
        Gift withId = gift.toBuilder()
                .id(nextId++)
                .build();

        gifts.add(withId);
        return withId;
    }

    @Override
    public Gift findById(int id) {
        for(Gift gift : gifts){
            if(gift.getId()==id){
                return gift;
            }
        }
        return null;
    }
    @Override
    public boolean update(Gift updated) {
        for(int i=0;i<gifts.size();i++){

            if(gifts.get(i).getId() == updated.getId()){
                gifts.set(i,updated);
                return true;
            }
        }
        return false;
    }


}
