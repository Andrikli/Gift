package service;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.SweetRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private SweetService sweetService;

    // --- Create ---
    @Test
    void addSweet_Valid() {
        Sweet sweet = mock(Sweet.class);
        when(sweet.getWeightGram()).thenReturn(10.0);
        when(sweet.getPrice()).thenReturn(10.0);
        sweetService.addSweet(sweet);
        verify(sweetRepository).save(sweet);
    }

    @Test
    void addSweet_Invalid() {
        Sweet s1 = mock(Sweet.class);
        when(s1.getWeightGram()).thenReturn(0.0);
        assertThrows(IllegalArgumentException.class, () -> sweetService.addSweet(s1));

        Sweet s2 = mock(Sweet.class);
        when(s2.getWeightGram()).thenReturn(10.0);
        when(s2.getPrice()).thenReturn(-5.0);
        assertThrows(IllegalArgumentException.class, () -> sweetService.addSweet(s2));
    }

    // --- Read ---
    @Test
    void getAll_FiltersDeleted() {
        Sweet s1 = mock(Sweet.class); when(s1.isDeleted()).thenReturn(false);
        Sweet s2 = mock(Sweet.class); when(s2.isDeleted()).thenReturn(true);
        when(sweetRepository.findAll()).thenReturn(List.of(s1, s2));

        assertEquals(1, sweetService.getAll().size());
    }

    @Test
    void findById() {
        Sweet s = mock(Sweet.class);
        when(s.isDeleted()).thenReturn(false);
        when(sweetRepository.findById(1)).thenReturn(s);
        assertNotNull(sweetService.findById(1));

        when(sweetRepository.findById(2)).thenReturn(null);
        assertNull(sweetService.findById(2));
    }

    // --- Delete / Restore ---
    @Test
    void deleteById() {
        Sweet s = mock(Sweet.class);
        when(sweetRepository.findById(1)).thenReturn(s);
        assertTrue(sweetService.deleteById(1));
        verify(s).markDeleted();

        when(sweetRepository.findById(2)).thenReturn(null);
        assertFalse(sweetService.deleteById(2));
    }



    @Test
    void deleteAll() {
        Sweet s1 = mock(Sweet.class); when(s1.isDeleted()).thenReturn(false);
        Sweet s2 = mock(Sweet.class); when(s2.isDeleted()).thenReturn(true);
        when(sweetRepository.findAll()).thenReturn(List.of(s1, s2));

        assertEquals(1, sweetService.deleteAll());
    }

    @Test
    void removeExpiredSweets() {
        Sweet s1 = mock(Sweet.class); when(s1.isExpired()).thenReturn(true);
        when(sweetRepository.findAll()).thenReturn(List.of(s1));
        assertEquals(1, sweetService.removeExpiredSweets());
        verify(s1).markDeleted();
    }

    // --- EDIT (High Coverage Logic) ---
    @Test
    void editSweet_Candy() {
        Candy old = mock(Candy.class); // Mock конкретного класу
        when(sweetRepository.findById(1)).thenReturn(old);
        when(sweetRepository.update(any())).thenReturn(true);

        sweetService.editSweet(1, "N", 1, 1, 1, LocalDate.now(), 1, null, "M", "C", null, null, null);

        ArgumentCaptor<Sweet> captor = ArgumentCaptor.forClass(Sweet.class);
        verify(sweetRepository).update(captor.capture());
        assertTrue(captor.getValue() instanceof Candy);
    }

    @Test
    void editSweet_Cookie() {
        Cookie old = mock(Cookie.class);
        when(sweetRepository.findById(1)).thenReturn(old);
        when(sweetRepository.update(any())).thenReturn(true);

        sweetService.editSweet(1, "N", 1, 1, 1, LocalDate.now(), 1, null, "M", "C", null, null, "Flour");

        ArgumentCaptor<Sweet> captor = ArgumentCaptor.forClass(Sweet.class);
        verify(sweetRepository).update(captor.capture());
        assertTrue(captor.getValue() instanceof Cookie);
    }

    @Test
    void editSweet_Chocolate() {
        Chocolate old = mock(Chocolate.class);
        when(sweetRepository.findById(1)).thenReturn(old);
        when(sweetRepository.update(any())).thenReturn(true);

        sweetService.editSweet(1, "N", 1, 1, 1, LocalDate.now(), 1, null, "M", "C", 50.0, "Dark", null);

        ArgumentCaptor<Sweet> captor = ArgumentCaptor.forClass(Sweet.class);
        verify(sweetRepository).update(captor.capture());
        assertTrue(captor.getValue() instanceof Chocolate);
    }

    @Test
    void editSweet_Fail() {
        when(sweetRepository.findById(1)).thenReturn(null);
        assertFalse(sweetService.editSweet(1, "N", 1, 1, 1, null, 1, null, "M", "C", null, null, null));
    }

    @Test
    void editSweet_UnknownType() {
        // Якщо раптом з'явиться тип, який не є Candy/Cookie/Chocolate
        Sweet unknown = mock(Sweet.class); // Просто Sweet, не спадкоємець
        when(sweetRepository.findById(1)).thenReturn(unknown);

        assertThrows(IllegalStateException.class, () ->
                sweetService.editSweet(1, "N", 1, 1, 1, null, 1, null, "M", "C", null, null, null)
        );
    }

    // --- Search Logic ---
    @Test
    void searchBySugarInList() {
        Sweet s1 = mock(Sweet.class);
        when(s1.getSugarPercent()).thenReturn(10.0);
        when(sweetRepository.findById(1)).thenReturn(s1);

        List<Sweet> res = sweetService.searchBySugarInList(List.of(1), 5, 15);
        assertEquals(1, res.size());
    }

    @Test
    void searchInStorageBySugar() {
        Sweet s1 = mock(Sweet.class);
        when(s1.getSugarPercent()).thenReturn(10.0);
        when(sweetRepository.findAll()).thenReturn(List.of(s1));

        List<Sweet> res = sweetService.searchInStorageBySugar(5, 15);
        assertEquals(1, res.size());
    }

    @Test
    void staticSearchMethods() {
        // Тестуємо статичні методи, передаючи реальні дані або моки
        Sweet s = mock(Sweet.class);
        when(s.getManufacturer()).thenReturn("Roshen");
        when(s.getCity()).thenReturn("Kyiv");
        when(s.getId()).thenReturn(10);
        when(s.getSugarPercent()).thenReturn(20.0);

        List<Sweet> list = List.of(s);

        assertFalse(SweetService.searchByManufacturer(list, "Roshen").isEmpty());
        assertFalse(SweetService.searchByCity(list, "Kyiv").isEmpty());
        assertFalse(SweetService.searchById(list, 10).isEmpty());
        assertFalse(SweetService.searchBySugar(list, 10, 30).isEmpty());
    }

    @Test
    void misc() {
        sweetService.getAllIncludingDeleted();
        verify(sweetRepository).findAll();

        when(sweetRepository.findAll()).thenReturn(List.of());
        sweetService.sortStock(SortKey.ID, SortOrder.ASC, null);
    }
}