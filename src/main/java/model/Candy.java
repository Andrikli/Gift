package model;
import java.time.LocalDate;

public class Candy extends Sweet {
    private Candy(Builder b) {
        super(b);
    }
    @Override
    public Candy.Builder toBuilder() {
        return new Builder()
                .withId(id)
                .withName(name)
                .withWeightGram(weightGram)
                .withSugarPercent(sugarPercent)
                .withPrice(price)
                .withExpiryDate(expiryDate)
                .withManufacturer(manufacturer)
                .withCity(city);
    }
    public static class Builder extends Sweet.Builder<Builder> {
        @Override protected Builder self() {
            return this;
        }
        @Override public Candy build() {
            return new Candy(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
