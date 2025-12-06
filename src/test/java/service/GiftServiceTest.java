package service;

import model.Gift;
import model.Sweet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.GiftRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftServiceTest {

    @Mock
    private GiftRepository giftRepository;

    @Mock
    private SweetService sweetService;

    @InjectMocks
    private GiftService giftService;

    // 1. Базові операції (Створення, Пошук, Встановлення поточного)
    @Test
    void basics_CreateFindSetCurrent() {
        // Create
        Gift g = Gift.builder().id(1).title("G").deleted(false).build();
        when(giftRepository.save(any())).thenReturn(g);
        giftService.createGift("G");
        verify(giftRepository).save(any());

        // Find & Get All
        when(giftRepository.findAll()).thenReturn(List.of(g));
        assertFalse(giftService.getAll().isEmpty());

        when(giftRepository.findById(1)).thenReturn(g);
        assertNotNull(giftService.findById(1));

        // Set Current
        assertTrue(giftService.setCurrentGiftId(1));
        assertEquals(g, giftService.getCurrentGift());

        // Clear
        giftService.clearCurrentGift();
        assertNull(giftService.getCurrentGift());
    }

    @Test
    void sweets_AddRemove() {
        Gift gift = Gift.builder().id(1).title("G").addAllSweetIds(new ArrayList<>()).build();
        when(giftRepository.findById(1)).thenReturn(gift);
        giftService.setCurrentGiftId(1);
        when(giftRepository.update(any())).thenReturn(true);

        // Add Sweet
        Sweet s = mock(Sweet.class);
        when(s.isDeleted()).thenReturn(false);
        when(sweetService.findById(100)).thenReturn(s);
        assertTrue(giftService.addSweetToGift(100));

        // Remove Sweet
        assertTrue(giftService.RemoveFromGift(100));
    }

    // 3. Розрахунки (Ціна, Вага + перевірка getGiftSweets)
    @Test
    void calculations_WeightAndPrice() {
        // Подарунок з двома цукерками
        Gift gift = Gift.builder().id(1).title("C").addAllSweetIds(List.of(10, 20)).build();
        when(giftRepository.findById(1)).thenReturn(gift);
        giftService.setCurrentGiftId(1);

        // Цукерка 1
        Sweet s1 = mock(Sweet.class);
        when(s1.getWeightGram()).thenReturn(100.0);
        when(s1.getPrice()).thenReturn(50.0);
        when(sweetService.findById(10)).thenReturn(s1);

        // Цукерка 2 (Нехай буде null або видалена, щоб перевірити фільтр)
        Sweet s2 = null;
        when(sweetService.findById(20)).thenReturn(s2);

        // Перевіряємо (тут неявно викликається getGiftSweets)
        assertEquals(100.0, giftService.getCurrentTotalWeight());
        assertEquals(50.0, giftService.getCurrentTotalPrice());
    }

    // 4. Видалення та Відновлення
    @Test
    void deleteLogic_DeleteRestoreDeleteAll() {
        // Delete by ID
        Gift g = Gift.builder().id(5).title("D").deleted(false).build();
        when(giftRepository.findById(5)).thenReturn(g);
        when(giftRepository.update(any())).thenReturn(true);
        assertTrue(giftService.deleteById(5));

        // Restore
        Gift deleted = Gift.builder().id(5).title("D").deleted(true).build();
        when(giftRepository.findById(5)).thenReturn(deleted);
        assertTrue(giftService.restoreById(5));

        // Delete All
        when(giftRepository.findAll()).thenReturn(List.of(g));
        assertEquals(1, giftService.deleteAll());
    }

    // 5. Специфічна логіка (Пошук, Перейменування, Очищення прострочених)
    @Test
    void logic_SearchRenameExpire() {
        // Search
        Gift g = Gift.builder().id(1).title("Box").deleted(false).build();
        when(giftRepository.findAll()).thenReturn(List.of(g));
        assertEquals(1, giftService.searchByTitle("box").size());

        // Rename
        when(giftRepository.findById(1)).thenReturn(g);
        when(giftRepository.update(any())).thenReturn(true);
        assertTrue(giftService.renameGift(1, "New Name"));

        // Misc
        giftService.saveLoadedGift(g);
        giftService.getAllIncludingDeleted();
    }


    @Test
    void expire_Logic() {
        Gift gift = Gift.builder().id(1).title("E").addAllSweetIds(List.of(10, 11)).build();
        when(giftRepository.findById(1)).thenReturn(gift);
        giftService.setCurrentGiftId(1);

        Sweet fresh = mock(Sweet.class); when(fresh.isExpired()).thenReturn(false);
        Sweet expired = mock(Sweet.class); when(expired.isExpired()).thenReturn(true);

        when(sweetService.findById(10)).thenReturn(fresh);
        when(sweetService.findById(11)).thenReturn(expired);

        // Має видалити 1 цукерку
        assertEquals(1, giftService.clearExpiredFromCurrentGift());
        verify(giftRepository).update(any());
    }
}