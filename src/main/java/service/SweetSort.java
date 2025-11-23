package service;
import java.util.ArrayList;
import java.util.List;
import model.Sweet;
import model.SweetCategory;
import model.Candy;
import model.Cookie;
import model.Chocolate;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.SortKey;
import model.SortOrder;
public class SweetSort {

    public static List<Sweet> sort(
            List<Sweet> source,
            SortKey key,
            SortOrder order,
            SweetCategory filterCategory
    ) {
        var stream = source.stream()
                .filter(s -> !s.isDeleted());

        if (filterCategory != null) {
            stream = stream.filter(s -> switch (filterCategory) {
                case CANDY -> s instanceof Candy;
                case COOKIE -> s instanceof Cookie;
                case CHOCOLATE -> s instanceof Chocolate;
            });
        }
        Comparator<Sweet> cmp = switch (key) {
            case ID      -> Comparator.comparing(s -> s.getId());
            case PRICE   -> Comparator.comparing(Sweet::getPrice);
            case SUGAR   -> Comparator.comparing(Sweet::getSugarPercent);
            case WEIGHT  -> Comparator.comparing(Sweet::getWeightGram);
            case FRESHNESS -> Comparator.comparing(Sweet::getExpiryDate);
            case CACAO -> Comparator.comparing(s->{
                if(s instanceof Chocolate ch) return ch.getCacaoPercent();
                return -1.0;
            });
        };
        if (order == SortOrder.DESC) {
            cmp = cmp.reversed();
        }
        return stream
                .sorted(cmp)
                .collect(Collectors.toList());
    }

}
