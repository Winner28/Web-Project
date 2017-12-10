package model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private int id;
    private List<Integer> gun_id;

    public Bucket() {
        gun_id = new ArrayList<>();
    }

    public Bucket addGun(int id) {
        gun_id.add(id);
        return this;
    }

    public Bucket setId(int id) {
        this.id = id;
        return this;
    }

    public List<Integer> getGunList() {
        return gun_id;
    }

    public int getId() {
        return id;
    }

}
