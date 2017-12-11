package model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private List<Gun> gunList;
    private double price;

    public Order() {
        gunList = new ArrayList<>();
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public Order addGun(Gun gun) {
        gunList.add(gun);
        return this;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}
