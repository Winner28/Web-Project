package model;

public class User {


    private int id;
    private String name;
    private String userName;
    private String password;

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getName() + " " + this.getUserName();
    }
}
