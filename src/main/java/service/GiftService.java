package service;

import model.Gift;
import repository.GiftRepository;

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
        return giftRepository.findAll();
    }
    public Gift findById(int id){
        return giftRepository.findById(id);
    }

}
