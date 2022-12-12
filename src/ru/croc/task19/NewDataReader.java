package ru.croc.task19;

import ru.croc.task17.DataReader;
import ru.croc.task17.entity.Order;
import ru.croc.task17.entity.OrderItem;
import ru.croc.task17.entity.Product;
import ru.croc.task19.entity.Courier;
import ru.croc.task19.entity.ShippingOrder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class NewDataReader extends DataReader {
    public NewDataReader(String source) {
        super(source);
    }

    public void readData(Set<Product> products, Set<ShippingOrder> orders, Set<OrderItem> orderItems, Set<Courier> couriers) throws IOException {
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime shippingTime = LocalDateTime.parse(info[5], formatter);
                String courierID = info[6];
                String courierFirstName = info[7];
                String courierSecondName = info[8];

                Product productInLine = readProduct(products, productCode, productName, productPrice);
                Courier courierInLine = readCourier(couriers, courierID, courierFirstName, courierSecondName);
                Order orderInLine = readOrder(orders, orderID, userLogin, shippingTime, courierInLine);
                readOrderItem(orderItems, orderID, productCode, productInLine, orderInLine);
            }
        }
    }

    private Courier readCourier(Set<Courier> couriers, String courierID, String courierFirstName, String courierSecondName) {
        Courier courierInLine;

        if (couriers.stream().noneMatch(courier -> courier.getID().equals(courierID))) {
            courierInLine = new Courier(courierID, courierFirstName, courierSecondName);
            couriers.add(courierInLine);
        } else {
            courierInLine = couriers.stream().filter(courier -> courier.getID().equals(courierID)).findFirst().get();
        }

        return courierInLine;
    }

    private Order readOrder(Set<ShippingOrder> orders, int orderID, String userLogin, LocalDateTime shippingTime, Courier courierInLine) {
        ShippingOrder orderInLine;

        if (orders.stream().noneMatch(order -> order.getID() == orderID)) {
            orderInLine = new ShippingOrder(orderID, userLogin, shippingTime, courierInLine);
            orders.add(orderInLine);
        } else {
            orderInLine = orders.stream().filter(order -> order.getID() == orderID).findFirst().get();
        }

        return orderInLine;
    }
}
