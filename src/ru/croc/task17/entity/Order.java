package ru.croc.task17.entity;

public class Order {
    private int ID;
    private String userLogin;

    public Order(int orderID, String userLogin) {
        this.ID = orderID;
        this.userLogin = userLogin;
    }

    public int getID() {
        return ID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    @Override
    public String toString() {
        return "Order: " + ID + ", " + userLogin + ';';
    }
}
