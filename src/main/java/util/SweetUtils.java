package util;
import model.*;

public class SweetUtils {
    public static String format(Sweet s) {

        String category = switch (s) {
            case Candy c -> "Цукерка";
            case Chocolate ch -> "Шоколад";
            case Cookie ck -> "Печиво";
            default -> "Солодощі";
        };
        StringBuilder sb = new StringBuilder();

        sb.append(category).append(":  ");
        sb.append("id=").append(s.getId())
                .append(", назва=").append(s.getName())
                .append(", вага=").append(String.format("%.2f", s.getWeightGram()))
                .append(", цукор=").append(String.format("%.2f", s.getSugarPercent()))
                .append(", ціна=").append(String.format("%.2f", s.getPrice()))
                .append(", дата виготовдення=").append(s.getManufactureDate())
                .append(", термін придатності=").append(s.getExpiryDays())
                .append(", дата вжитку=").append(s.getDisposeDate())
                .append(", виробник=").append(s.getManufacturer())
                .append(", місто=").append(s.getCity());

        // Додаткові поля залежно від типу
        if (s instanceof Cookie ck) {
            sb.append(", тип муки=").append(ck.getFlourType());
        }

        if (s instanceof Chocolate ch) {
            sb.append(", % какао=").append(ch.getCacaoPercent());
            sb.append(", колір=").append(ch.getColor());
        }

        return sb.append("}").toString();
    }
}
