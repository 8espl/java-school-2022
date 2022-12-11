package ru.croc.task17.entity;

public class Product {
    private String ID;
    private String name;
    private int price;

    public Product(String ID, String name, int price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
