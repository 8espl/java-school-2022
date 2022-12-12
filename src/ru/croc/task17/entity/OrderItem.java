package ru.croc.task17.entity;

public class OrderItem {
    private Order order;
    private Product product;
    private int quantity = 1;

    public OrderItem(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public void addItem() {
        this.quantity += 1;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
