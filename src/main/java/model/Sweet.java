package model;

import repository.SweetRepository;

import java.time.LocalDate;

public abstract class Sweet {
    protected final Integer id;
    protected final String name;
    protected final double weightGram;
    protected final double sugarPercent;
    protected final double price;
    private final LocalDate manufactureDate;
    private final int expiryDays;
    private final LocalDate disposeDate;
    protected final String manufacturer;
    protected final String city;
    private boolean deleted ;

    protected Sweet(Builder<?> b) {
        this.id = b.id;
        this.name = b.name;
        this.weightGram = b.weightGram;
        this.sugarPercent = b.sugarPercent;
        this.price = b.price;
        this.manufactureDate = b.manufactureDate;
        this.expiryDays = b.expiryDays;
        this.disposeDate = b.disposeDate;
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
        protected LocalDate manufactureDate;
        protected int expiryDays;
        protected LocalDate disposeDate;
        protected String manufacturer;
        protected String city;
        protected boolean deleted = false;

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
        public T withManufactureDate(LocalDate manufactureDate) {
            this.manufactureDate = manufactureDate;
            return self();
        }

        public T withExpiryDays(int expiryDays) {
            this.expiryDays = expiryDays;
            return self();
        }

        public T withDisposeDate(LocalDate disposeDate) {
            this.disposeDate = disposeDate;
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
        public T withDeleted(boolean deleted){
            this.deleted = deleted;
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
    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public int getExpiryDays() {
        return expiryDays;
    }

    public LocalDate getDisposeDate() {
        return disposeDate;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format(
                "id=%d, назва=%s, вага=%.2f, цукор=%.2f, ціна=%.2f, дата виготовдення=%s, термін придатності=%d, дата вжитку=%s, виробник=%s, місто=%s}",
                id, name, weightGram, sugarPercent, price,
                manufactureDate, expiryDays, disposeDate, manufacturer, city
        );
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public void markDeleted(){
        this.deleted = true;
    }
    public void restore(){
        this.deleted = false;
    }

    public boolean isExpired() {
        LocalDate expiryDate = manufactureDate.plusDays(expiryDays);
        return expiryDate.isBefore(disposeDate);
    }
    public LocalDate getExpiryDate() {
        return manufactureDate.plusDays(expiryDays);
    }



}

