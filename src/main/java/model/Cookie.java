package model;
import java.time.LocalDate;
public class Cookie extends Sweet {
    private final String flourType;

    private Cookie(Builder b) {
        super(b);
        this.flourType = b.flourType;
    }
    public String getFlourType() {
        return flourType;
    }

    @Override
    public Cookie.Builder toBuilder() {
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
                .flourType(flourType);
    }

    public static Cookie.Builder builder() {
        return new Cookie.Builder();
    }
    public static class Builder extends Sweet.Builder<Builder> {
        private String flourType;
        public Builder flourType(String flourType) {
            this.flourType = flourType;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }
        @Override
        public Cookie build() {
            return new Cookie(this);
        }
        @Override
        public String toString() {
            return "Cookie.Builder(тип муки=" + flourType + ")";
        }

    }
}

