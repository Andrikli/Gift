package model;

import java.time.LocalDate;

public class Chocolate extends Sweet {
    private final double cacaoPercent;
    private final String color;

    private Chocolate(Builder b) {
        super(b);
        this.cacaoPercent = b.cacaoPercent;
        this.color = b.color;
    }
    public double getCacaoPercent() {
        return cacaoPercent;
    }
    public String getColor() {
        return color;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Chocolate.Builder toBuilder() {

        return new Builder()
                .withId(id)
                .withName(name)
                .withWeightGram(weightGram)
                .withSugarPercent(sugarPercent)
                .withPrice(price)
                .withManufactureDate(getManufactureDate())
                .withExpiryDays(getExpiryDays())
                .withDisposeDate(getDisposeDate())
                .withManufacturer(manufacturer)
                .withCity(city)
                .cacaoPercent(cacaoPercent)
                .color(color);

    }
    public static class Builder extends Sweet.Builder<Builder> {
        private double cacaoPercent;
        private String color;
        public Builder cacaoPercent(double percent) {
            this.cacaoPercent = percent;
            return this;
        }
        public Builder color(String color) {
            this.color = color;
            return this;
        }

        @Override protected Builder self() {
            return this;
        }
        @Override public Chocolate build() {
            return new Chocolate(this);
        }
        @Override
        public String toString() {
            return super.toString() +
                    String.format(", cacao=%.1f%%, color=%s", cacaoPercent, color);
        }
    }

}