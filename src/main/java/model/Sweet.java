package model;

import java.time.LocalDate;

public abstract class Sweet {
    protected final Integer id;
    protected final String name;
    protected final double weightGram;
    protected final double sugarPercent;
    protected final double price;
    protected final LocalDate expiryDate;
    protected final String manufacturer;
    protected final String city;
    private boolean deleted = false;

    protected Sweet(Builder<?> b) {
        this.id = b.id;
        this.name = b.name;
        this.weightGram = b.weightGram;
        this.sugarPercent = b.sugarPercent;
        this.price = b.price;
        this.expiryDate = b.expiryDate;
        this.manufacturer = b.manufacturer;
        this.city = b.city;
    }

    public abstract Builder<?> toBuilder();

    public static abstract class Builder<T extends Builder<T>>{
        protected Integer id = null;
        protected String name;
        protected double weightGram;
        protected double sugarPercent;
        protected double price;
        protected LocalDate expiryDate;
        protected String manufacturer;
        protected String city;
        public T withId(Integer id){
            this.id = id;
            return self();
        }
        public T withName(String name){
            this.name = name;
            return self();
        }
        public T withWeightGram(double weightGram){
            this.weightGram = weightGram;
            return self();
        }
        public T withSugarPercent(double sugarPercent){
            this.sugarPercent = sugarPercent;
            return self();
        }
        public T withPrice(double price){
            this.price = price;
            return self();
        }
        public T withExpiryDate(LocalDate expiryDate){
            this.expiryDate = expiryDate;
            return self();
        }
        public T withManufacturer(String manufacturer){
            this.manufacturer = manufacturer;
            return self();
        }
        public T withCity(String city){
            this.city = city;
            return self();
        }
        protected abstract T self();
        public abstract Sweet build();



    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getWeightGram() {
        return weightGram;
    }
    public double getSugarPercent() {
        return sugarPercent;
    }
    public double getPrice() {
        return price;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format("Sweet{id=%d, name=%s, weight=%.2f, sugar=%.2f, price=%.2f, expiry=%s, mfr=%s, city=%s}",
                id, name, weightGram, sugarPercent, price, expiryDate, manufacturer, city);
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void markDeleted(){
        this.deleted = true;
    }


}

