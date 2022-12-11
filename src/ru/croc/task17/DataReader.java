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
        // чтобы отслеживать уникальность значений в коллекциях
        Set<Integer> ordersID = new HashSet<>();
        Set<String> productsID = new HashSet<>();
        Map<String, OrderItem> orderItemsInfo = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            String[] info;

            while ((line = reader.readLine()) != null) {
                info = line.split(",");

                if (productsID.add(info[2])){
                    products.add(new Product(info[2], info[3], Integer.parseInt(info[4])));
                }

                if (ordersID.add(Integer.valueOf(info[0]))) {
                    orders.add(new Order(Integer.parseInt(info[0]), info[1]));
                }

                // если товар уже содержится в заказе, то увеличиваем его количество в заказе, а не добавляем новый
                String tempID = info[0] + info[2];
                if (orderItemsInfo.containsKey(tempID)){
                    orderItemsInfo.get(tempID).addItem();
                }else{
                    orderItemsInfo.put(tempID, new OrderItem(Integer.parseInt(info[0]), info[2]));
                }
            }
            orderItems.addAll(orderItemsInfo.values());
        }
    }
}
