package ru.croc.task17.entity;

public class Order {
    private int ID;
    private String username;

    public Order(int orderID, String customerID) {
        this.ID = orderID;
        this.username = customerID;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }
}
