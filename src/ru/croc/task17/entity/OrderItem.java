package ru.croc.task17.entity;

public class OrderItem {
    private int orderID;
    private String productCode;
    private int quantity = 1;

    public OrderItem(int orderID, String productCode) {
        this.orderID = orderID;
        this.productCode = productCode;
    }

    public void addItem() {
        this.quantity += 1;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }
}
