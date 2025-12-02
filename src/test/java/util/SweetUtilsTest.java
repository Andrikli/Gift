package util;

import model.Candy;
import model.Chocolate;
import model.Cookie;
import model.Sweet;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SweetUtilsTest {

    @Test
    void format_ShouldHandleAllTypes() {
        LocalDate now = LocalDate.now();

        Sweet candy = Candy.builder().withId(1).withName("C").withWeightGram(10).withPrice(5)
                .withManufactureDate(now).withExpiryDays(10).withDisposeDate(now)
                .withManufacturer("M").withCity("City").build();

        String resCandy = SweetUtils.format(candy);
        assertTrue(resCandy.contains("Цукерка"));
        assertTrue(resCandy.contains("назва=C"));

        Sweet cookie = Cookie.builder().withId(2).withName("Co").withWeightGram(10).withPrice(5)
                .withManufactureDate(now).withExpiryDays(10).withDisposeDate(now)
                .withManufacturer("M").withCity("City")
                .flourType("Wheat").build();

        String resCookie = SweetUtils.format(cookie);
        assertTrue(resCookie.contains("Печиво"));
        assertTrue(resCookie.contains("тип муки=Wheat"));

        Sweet choco = Chocolate.builder().withId(3).withName("Ch").withWeightGram(10).withPrice(5)
                .withManufactureDate(now).withExpiryDays(10).withDisposeDate(now)
                .withManufacturer("M").withCity("City")
                .cacaoPercent(75.5).color("Dark").build();

        String resChoco = SweetUtils.format(choco);
        assertTrue(resChoco.contains("Шоколад"));
        assertTrue(resChoco.contains("75.5"));
        assertTrue(resChoco.contains("Dark"));

    }
}