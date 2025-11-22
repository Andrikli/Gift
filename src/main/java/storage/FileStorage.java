package storage;

import model.*;
import service.GiftService;
import service.SweetService;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import model.*;
import service.GiftService;
import service.SweetService;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileStorage {

    public static final String SWEETS_FILE = "sweets.txt";
    public static final String GIFTS_FILE = "gifts.txt";

    // ================= СОЛОДОЩІ =================

    public static void saveSweets(SweetService sweetService) throws IOException {
        List<Sweet> sweets = sweetService.getAllIncludingDeleted();

        List<String> lines = new ArrayList<>();
        for (Sweet s : sweets) {
            String type;
            String extra1 = "";
            String extra2 = "";

            if (s instanceof Candy) {
                type = "CANDY";
            } else if (s instanceof Cookie c) {
                type = "COOKIE";
                extra1 = c.getFlourType();
            } else if (s instanceof Chocolate ch) {
                type = "CHOCOLATE";
                extra1 = Double.toString(ch.getCacaoPercent());
                extra2 = ch.getColor();
            } else {
                // невідомий тип — пропускаємо
                continue;
            }

            LocalDate mDate = s.getManufactureDate();
            int expiryDays = s.getExpiryDays();
            LocalDate disposeDate = s.getDisposeDate();

            String line = String.join(";", Arrays.asList(
                    type,
                    s.getId() == null ? "" : s.getId().toString(),
                    s.getName(),
                    Double.toString(s.getWeightGram()),
                    Double.toString(s.getSugarPercent()),
                    Double.toString(s.getPrice()),
                    mDate != null ? mDate.toString() : "",
                    Integer.toString(expiryDays),
                    disposeDate != null ? disposeDate.toString() : "",
                    s.getManufacturer(),
                    s.getCity(),
                    extra1,
                    extra2,
                    Boolean.toString(s.isDeleted())   //
            ));
            lines.add(line);
        }

        Files.write(Paths.get(SWEETS_FILE), lines);
    }

    public static void loadSweets(SweetService sweetService) throws IOException {
        Path path = Paths.get(SWEETS_FILE);
        if (!Files.exists(path)) {
            System.out.println("Файл з солодощами не знайдено: " + SWEETS_FILE);
            return;
        }

        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.isBlank()) continue;

            String[] parts = line.split(";");
            if (parts.length < 11) continue;

            String type = parts[0];
            String idStr = parts[1];
            Integer id = idStr.isEmpty() ? null : Integer.parseInt(idStr);

            String name = parts[2];
            double weight = Double.parseDouble(parts[3]);
            double sugar = Double.parseDouble(parts[4]);
            double price = Double.parseDouble(parts[5]);

            LocalDate manufactureDate = parts[6].isEmpty()
                    ? null
                    : LocalDate.parse(parts[6]);

            int expiryDays = Integer.parseInt(parts[7]);

            LocalDate disposeDate = parts[8].isEmpty()
                    ? LocalDate.now()
                    : LocalDate.parse(parts[8]);

            String manufacturer = parts[9];
            String city = parts[10];

            String extra1 = parts.length > 11 ? parts[11] : "";
            String extra2 = parts.length > 12 ? parts[12] : "";

            boolean deleted = false;
            if (parts.length > 13) {
                deleted = Boolean.parseBoolean(parts[13]);
            }

            Sweet sweet;
            switch (type) {
                case "CANDY" -> sweet = Candy.builder()
                        .withId(id)
                        .withName(name)
                        .withWeightGram(weight)
                        .withSugarPercent(sugar)
                        .withPrice(price)
                        .withManufactureDate(manufactureDate)
                        .withExpiryDays(expiryDays)
                        .withDisposeDate(disposeDate)
                        .withManufacturer(manufacturer)
                        .withCity(city)
                        .build();

                case "COOKIE" -> sweet = Cookie.builder()
                        .withId(id)
                        .withName(name)
                        .withWeightGram(weight)
                        .withSugarPercent(sugar)
                        .withPrice(price)
                        .withManufactureDate(manufactureDate)
                        .withExpiryDays(expiryDays)
                        .withDisposeDate(disposeDate)
                        .withManufacturer(manufacturer)
                        .withCity(city)
                        .flourType(extra1)
                        .build();

                case "CHOCOLATE" -> {
                    double cacao = extra1.isEmpty() ? 0.0 : Double.parseDouble(extra1);
                    sweet = Chocolate.builder()
                            .withId(id)
                            .withName(name)
                            .withWeightGram(weight)
                            .withSugarPercent(sugar)
                            .withPrice(price)
                            .withManufactureDate(manufactureDate)
                            .withExpiryDays(expiryDays)
                            .withDisposeDate(disposeDate)
                            .withManufacturer(manufacturer)
                            .withCity(city)
                            .cacaoPercent(cacao)
                            .color(extra2)
                            .build();
                }

                default -> {
                    continue;
                }
            }

            if (deleted) {
                sweet.markDeleted();   // відновлюємо стан
            }

            sweetService.addSweet(sweet);
        }
    }

    public static void saveGifts(GiftService giftService) throws IOException {

        List<Gift> gifts = giftService.getAllIncludingDeleted();
        List<String> lines = new ArrayList<>();

        for (Gift g : gifts) {
            String sweetIdsStr = g.getSweetIds().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));

            String line = String.join(";", Arrays.asList(
                    Integer.toString(g.getId()),
                    g.getTitle(),
                    sweetIdsStr,
                    Boolean.toString(g.isDeleted())
            ));
            lines.add(line);
        }

        Files.write(Paths.get(GIFTS_FILE), lines);
    }

    public static void loadGifts(GiftService giftService) throws IOException {
        Path path = Paths.get(GIFTS_FILE);
        if (!Files.exists(path)) {
            System.out.println("Файл з подарунками не знайдено: " + GIFTS_FILE);
            return;
        }

        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.isBlank()) continue;

            String[] parts = line.split(";", 4);
            if (parts.length < 2) continue;

            int id = Integer.parseInt(parts[0]);
            String title = parts[1];

            List<Integer> sweetIds = new ArrayList<>();
            if (parts.length >= 3 && !parts[2].isBlank()) {
                String[] idParts = parts[2].split(",");
                for (String sId : idParts) {
                    sId = sId.trim();
                    if (!sId.isEmpty()) {
                        sweetIds.add(Integer.parseInt(sId));
                    }
                }
            }

            boolean deleted = false;
            if (parts.length == 4) {
                deleted = Boolean.parseBoolean(parts[3]);
            }

            Gift gift = Gift.builder()
                    .id(id)
                    .title(title)
                    .addAllSweetIds(sweetIds)
                    .deleted(deleted)
                    .build();

            giftService.saveLoadedGift(gift);
        }
    }
}
