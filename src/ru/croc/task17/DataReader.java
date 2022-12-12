package ru.croc.task17;

import ru.croc.task17.entity.Order;
import ru.croc.task17.entity.OrderItem;
import ru.croc.task17.entity.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataReader {

    private String source;

    DataReader(String source) {
        this.source = source;
    }

    public void readData(Set<Product> products, Set<Order> orders, Set<OrderItem> orderItems) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            String[] info;

            while ((line = reader.readLine()) != null) {
                info = line.split(",");

                String productCode = info[2];
                String productName = info[3];
                int productPrice = Integer.parseInt(info[4]);
                int orderID = Integer.parseInt(info[0]);
                String userLogin = info[1];


                Product productInLine = readProduct(products, productCode, productName, productPrice);
                Order orderInLine = readOrder(orders, orderID, userLogin);
                readOrderItem(orderItems, orderID, productCode, productInLine, orderInLine);
            }
        }
    }

    /**
     * @param products     набор уникальных товаров
     * @param productCode  артикул товара в данной строке таблицы
     * @param productName  название товара в данной строке таблицы
     * @param productPrice цена товара в данной строке таблицы
     * @return новый созданный товар, если прежде такой не встречался,
     * или созданный прежде товар с данным артикулом
     */
    private Product readProduct(Set<Product> products, String productCode, String productName, int productPrice) {
        Product productInLine;

        if (products.stream().noneMatch(product -> product.getCode().equals(productCode))) {
            productInLine = new Product(productCode, productName, productPrice);
            products.add(productInLine);
        } else {
            productInLine = products.stream().filter(product -> product.getCode().equals(productCode)).findFirst().get();
        }

        return productInLine;
    }

    /**
     * @param orders    набор уникальных заказов
     * @param orderID   номер заказа в данной строке таблицы
     * @param userLogin логин пользователя в данной строке таблицы
     * @return новый созданный заказ, если прежде такой не встречался,
     * или созданный прежде заказ с данным номером
     */
    private Order readOrder(Set<Order> orders, int orderID, String userLogin) {
        Order orderInLine;

        if (orders.stream().noneMatch(order -> order.getID() == orderID)) {
            orderInLine = new Order(orderID, userLogin);
            orders.add(orderInLine);
        } else {
            orderInLine = orders.stream().filter(order -> order.getID() == orderID).findFirst().get();
        }

        return orderInLine;
    }

    /**
     * Если встречался заказ с идентичным составляющим, то увеличиваем в составляющих заказа количество товара на 1
     *
     * @param orderItems    набор уникальный составляющих заказа
     * @param orderID       номер заказа в данной строке таблицы
     * @param productCode   артикул товара в данной строке таблицы
     * @param productInLine идентифицированный товар из данной строки таблицы
     * @param orderInLine   идентифицированный заказ из данной строки таблицы
     */
    private void readOrderItem(Set<OrderItem> orderItems, int orderID, String productCode, Product productInLine, Order orderInLine) {
        if (orderItems.stream().noneMatch(orderItem -> orderItem.getOrder().getID() == orderID && orderItem.getProduct().getCode().equals(productCode))) {
            orderItems.add(new OrderItem(orderInLine, productInLine));
        } else {
            orderItems.stream()
                    .filter(orderItem -> orderItem.getOrder().getID() == orderID && orderItem.getProduct().getCode().equals(productCode))
                    .findFirst().get().addItem();
        }
    }
}
