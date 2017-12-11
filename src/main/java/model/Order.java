package model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private List<Integer> gunList;
    private double price;

    public Order() {
        gunList = new ArrayList<>();
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order addGun(int gun) {
        gunList.add(gun);
        return this;
    }

    public int getId() {
        return id;
    }


    public List<Integer> getGunList() {
        return gunList;
    }



    public double getPrice() {
        return price;
    }
}
