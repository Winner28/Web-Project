package model;


public class Gun {

    private int id;
    private String name;
    private double caliber;
    private double price;

    public Gun() {

    }


    public Gun setId(int id) {
        this.id = id;
        return this;
    }

    public Gun setName(String name) {
        this.name = name;
        return this;
    }

    public Gun setCaliber(double caliber) {
        this.caliber = caliber;
        return this;
    }

    public Gun setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCaliber() {
        return caliber;
    }

    public double getPrice() {
        return price;
    }
}
