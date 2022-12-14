package ru.croc.task19.entity;

import ru.croc.task17.entity.Order;

import java.time.LocalDateTime;

public class ShippingOrder extends Order {
    LocalDateTime shippingTime;
    Courier courier;

    public ShippingOrder(int orderID, String userLogin, LocalDateTime shippingTime, Courier courier) {
        super(orderID, userLogin);
        this.shippingTime = shippingTime;
        this.courier = courier;
    }

    public LocalDateTime getShippingTime() {
        return shippingTime;
    }

    public Courier getCourier() {
        return courier;
    }
}