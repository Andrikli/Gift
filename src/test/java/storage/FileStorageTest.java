package storage;

import model.Candy;
import model.Chocolate;
import model.Cookie;
import model.Gift;
import model.Sweet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.GiftService;
import service.SweetService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileStorageTest {


    private static final String TEST_SWEETS = "sweets.txt";
    private static final String TEST_GIFTS = "gifts.txt";

    @Mock
    private SweetService sweetService;

    @Mock
    private GiftService giftService;

    @BeforeEach
    void setUp() throws IOException {

        Files.deleteIfExists(Path.of(TEST_SWEETS));
        Files.deleteIfExists(Path.of(TEST_GIFTS));
    }

    @AfterEach
    void tearDown() throws IOException {

        Files.deleteIfExists(Path.of(TEST_SWEETS));
        Files.deleteIfExists(Path.of(TEST_GIFTS));
    }

    @Test
    void saveAndLoadSweets_ShouldPersistData() throws IOException {

        Candy candy = Candy.builder().withId(1).withName("Candy").withWeightGram(10).withPrice(5)
                .withManufactureDate(LocalDate.now()).withExpiryDays(10).withDisposeDate(LocalDate.now().plusDays(10))
                .withManufacturer("M").withCity("C").build();

        Cookie cookie = Cookie.builder().withId(2).withName("Cookie").withWeightGram(20).withPrice(10)
                .withManufactureDate(LocalDate.now()).withExpiryDays(20).withDisposeDate(LocalDate.now().plusDays(20))
                .withManufacturer("M").withCity("C").flourType("Wheat").build();

        Chocolate choco = Chocolate.builder().withId(3).withName("Choco").withWeightGram(30).withPrice(15)
                .withManufactureDate(LocalDate.now()).withExpiryDays(30).withDisposeDate(LocalDate.now().plusDays(30))
                .withManufacturer("M").withCity("C").cacaoPercent(70.0).color("Dark").build();

        Sweet deleted = Candy.builder().withId(4).withName("Deleted").withWeightGram(1).withPrice(1)
                .withManufactureDate(LocalDate.now()).withExpiryDays(1).withDisposeDate(LocalDate.now())
                .withManufacturer("M").withCity("C").build();
        deleted.markDeleted();


        when(sweetService.getAllIncludingDeleted()).thenReturn(List.of(candy, cookie, choco, deleted));


        FileStorage.saveSweets(sweetService);

        assertTrue(Files.exists(Path.of(TEST_SWEETS)));

        FileStorage.loadSweets(sweetService);

        verify(sweetService, times(4)).addSweet(any(Sweet.class));
    }

    @Test
    void saveAndLoadGifts_ShouldPersistData() throws IOException {

        Gift g1 = Gift.builder().id(1).title("Gift1").addAllSweetIds(List.of(10, 20)).deleted(false).build();
        Gift g2 = Gift.builder().id(2).title("Gift2").addAllSweetIds(List.of()).deleted(true).build(); // Видалений

        when(giftService.getAllIncludingDeleted()).thenReturn(List.of(g1, g2));

        FileStorage.saveGifts(giftService);
        assertTrue(Files.exists(Path.of(TEST_GIFTS)));

        FileStorage.loadGifts(giftService);

        verify(giftService, times(2)).saveLoadedGift(any(Gift.class));
    }

    @Test
    void load_ShouldHandleMissingFiles_Gracefully() throws IOException {
        assertDoesNotThrow(() -> FileStorage.loadSweets(sweetService));
        assertDoesNotThrow(() -> FileStorage.loadGifts(giftService));
    }
}