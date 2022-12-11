package ru.croc.task17.entity;

public class OrderItem {
    private int orderID;
    private String productID;
    private int quantity = 1;

    public OrderItem(int orderID, String productID) {
        this.orderID = orderID;
        this.productID = productID;
    }

    public void addItem() {
        this.quantity += 1;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }
}
