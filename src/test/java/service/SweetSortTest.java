package service;

import model.Candy;
import model.Chocolate;
import model.Cookie;
import model.SortKey;
import model.SortOrder;
import model.Sweet;
import model.SweetCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SweetSortTest {


    private Candy candy(
            int id,
            String name,
            double price,
            double sugar,
            double weight,
            LocalDate manufactureDate,
            int expiryDays
    ) {
        return Candy.builder()
                .withId(id)
                .withName(name)
                .withPrice(price)
                .withSugarPercent(sugar)
                .withWeightGram(weight)
                .withManufactureDate(manufactureDate)
                .withExpiryDays(expiryDays)
                // disposeDate просто ставимо пізніше за expiryDate
                .withDisposeDate(manufactureDate.plusDays(expiryDays + 5))
                .withManufacturer("M" + id)
                .withCity("City" + id)
                .build();
    }

    private Cookie cookie(
            int id,
            String name,
            double price,
            double sugar,
            double weight,
            LocalDate manufactureDate,
            int expiryDays,
            String flourType
    ) {
        return Cookie.builder()
                .withId(id)
                .withName(name)
                .withPrice(price)
                .withSugarPercent(sugar)
                .withWeightGram(weight)
                .withManufactureDate(manufactureDate)
                .withExpiryDays(expiryDays)
                .withDisposeDate(manufactureDate.plusDays(expiryDays + 5))
                .withManufacturer("M" + id)
                .withCity("City" + id)
                .flourType(flourType)
                .build();
    }

    private Chocolate chocolate(
            int id,
            String name,
            double price,
            double sugar,
            double weight,
            LocalDate manufactureDate,
            int expiryDays,
            double cacaoPercent,
            String color
    ) {
        return Chocolate.builder()
                .withId(id)
                .withName(name)
                .withPrice(price)
                .withSugarPercent(sugar)
                .withWeightGram(weight)
                .withManufactureDate(manufactureDate)
                .withExpiryDays(expiryDays)
                .withDisposeDate(manufactureDate.plusDays(expiryDays + 5))
                .withManufacturer("M" + id)
                .withCity("City" + id)
                .cacaoPercent(cacaoPercent)
                .color(color)
                .build();
    }


    @Test
    void sortByIdAsc_ignoresDeleted() {
        LocalDate base = LocalDate.of(2024, 1, 1);

        Sweet s1 = cookie(3, "Cookie", 30, 15, 40, base, 10, "flour");
        Sweet s2 = candy(1, "Candy", 10, 20, 30, base.plusDays(1), 15);
        Sweet s3 = chocolate(2, "Choc", 20, 30, 25, base.plusDays(2), 20, 70, "dark");
        Sweet deleted = candy(4, "Deleted", 5, 10, 10, base.plusDays(3), 5);
        deleted.markDeleted(); // цей елемент має бути проігнорований

        List<Sweet> source = List.of(s1, s2, s3, deleted);

        List<Sweet> result = SweetSort.sort(
                source,
                SortKey.ID,
                SortOrder.ASC,
                null // без фільтра за категорією
        );

        assertEquals(3, result.size());
        // Перевіряємо порядок id: 1, 2, 3
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
    }

    @Test
    void sortByPriceDesc_filtersOnlyCandy() {
        LocalDate base = LocalDate.of(2024, 2, 1);

        Sweet c1 = candy(1, "Candy1", 10, 20, 30, base, 10);
        Sweet c2 = candy(2, "Candy2", 25, 25, 35, base.plusDays(1), 15);
        Sweet cookie = cookie(3, "Cookie", 100, 10, 40, base.plusDays(2), 20, "wheat");
        Sweet chocolate = chocolate(4, "Choc", 50, 30, 25, base.plusDays(3), 25, 60, "milk");

        List<Sweet> source = List.of(c1, c2, cookie, chocolate);

        List<Sweet> result = SweetSort.sort(
                source,
                SortKey.PRICE,
                SortOrder.DESC,
                SweetCategory.CANDY // залишаємо тільки Candy
        );

        // Має бути тільки 2 цукерки
        assertEquals(2, result.size());
        assertTrue(result.get(0) instanceof Candy);
        assertTrue(result.get(1) instanceof Candy);


        assertEquals(25.0, result.get(0).getPrice(), 1e-9);
        assertEquals(10.0, result.get(1).getPrice(), 1e-9);
    }

    @Test
    void sortByFreshnessAsc_usesExpiryDate() {
        LocalDate base = LocalDate.of(2024, 3, 1);

        // expiryDate = manufacture + expiryDays
        Sweet s1 = candy(1, "A", 10, 10, 10, base, 30);          // 2024-03-31
        Sweet s2 = candy(2, "B", 10, 10, 10, base.plusDays(5), 5); // 2024-03-11
        Sweet s3 = candy(3, "C", 10, 10, 10, base.plusDays(2), 10); // 2024-03-13

        List<Sweet> source = List.of(s1, s2, s3);

        List<Sweet> result = SweetSort.sort(
                source,
                SortKey.FRESHNESS,
                SortOrder.ASC,
                null
        );

        // Найраніший expiryDate має бути першим
        assertEquals(2, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

    @Test
    void sortByCacaoAsc_nonChocolateGoFirst_chocolatesOrderedByCacao() {
        LocalDate base = LocalDate.of(2024, 4, 1);

        Sweet candy = candy(1, "Candy", 10, 20, 30, base, 10);
        Sweet cookie = cookie(2, "Cookie", 15, 10, 25, base, 10, "wheat");
        Chocolate ch1 = chocolate(3, "Choc70", 20, 30, 20, base, 10, 70, "dark");
        Chocolate ch2 = chocolate(4, "Choc85", 25, 30, 20, base, 10, 85, "dark");

        List<Sweet> source = List.of(candy, cookie, ch1, ch2);

        List<Sweet> result = SweetSort.sort(
                source,
                SortKey.CACAO,
                SortOrder.ASC,
                null
        );

        assertTrue(result.get(result.size() - 2) instanceof Chocolate);
        assertTrue(result.get(result.size() - 1) instanceof Chocolate);

        Chocolate lastMinusOne = (Chocolate) result.get(result.size() - 2);
        Chocolate last = (Chocolate) result.get(result.size() - 1);

        assertEquals(70.0, lastMinusOne.getCacaoPercent(), 1e-9);
        assertEquals(85.0, last.getCacaoPercent(), 1e-9);
    }

    @Test
    void sortByCacaoDesc_chocolatesFirst() {
        LocalDate base = LocalDate.of(2024, 4, 1);

        Sweet candy = candy(1, "Candy", 10, 20, 30, base, 10);
        Chocolate ch1 = chocolate(2, "Choc70", 20, 30, 20, base, 10, 70, "dark");
        Chocolate ch2 = chocolate(3, "Choc85", 25, 30, 20, base, 10, 85, "dark");

        List<Sweet> source = List.of(candy, ch1, ch2);

        List<Sweet> result = SweetSort.sort(
                source,
                SortKey.CACAO,
                SortOrder.DESC,
                null
        );

        // Першими повинні бути шоколадки, за спаданням какао
        assertTrue(result.get(0) instanceof Chocolate);
        assertTrue(result.get(1) instanceof Chocolate);

        Chocolate first = (Chocolate) result.get(0);
        Chocolate second = (Chocolate) result.get(1);

        assertEquals(85.0, first.getCacaoPercent(), 1e-9);
        assertEquals(70.0, second.getCacaoPercent(), 1e-9);
    }
}

