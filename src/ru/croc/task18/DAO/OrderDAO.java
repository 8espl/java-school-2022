package ru.croc.task18.DAO;

import ru.croc.task17.DataReader;
import ru.croc.task17.entity.*;

import java.sql.*;
import java.util.*;

public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Возвращает номер последнего заказа.
     */
    private int findLastOrderID() throws SQLException {
        int lastID = 0;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM orders;")) {
                result.next();
                lastID = result.getInt("MAX(ID)");
            }
        }
        return lastID;
    }

    /**
     * Создание заказа. Для указанного пользователя в базе данных создается новый заказ с заданным списком товаров.
     */
    public Order createOrder(String userLogin, List<Product> products) throws Exception {
        int newOrderID = findLastOrderID() + 1;
        Set<OrderItem> orderItems = new HashSet<>();
        ProductDAO productDAO = new ProductDAO(connection);

        for (Product product : products) {
            String productCode = product.getCode();
            if (productDAO.findProduct(productCode) == null) {
                throw new Exception("Product \"" + product.getName() + "\" doesn't exist in DB.");
            } else {
                if (orderItems.stream().noneMatch(orderItem -> orderItem.getOrder().getID() == newOrderID && orderItem.getProduct().getCode().equals(productCode))) {
                    orderItems.add(new OrderItem(new Order(newOrderID, userLogin), product));
                } else {
                    orderItems.stream()
                            .filter(orderItem -> orderItem.getOrder().getID() == newOrderID && orderItem.getProduct().getCode().equals(productCode))
                            .findFirst().get().addItem();
                }
            }
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO orders VALUES(?, ?);")) {
            statement.setInt(1, newOrderID);
            statement.setString(2, userLogin);
            statement.execute();
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO order_items (order_id, product_code, quantity) VALUES(?, ?, ?);")) {
            for (OrderItem orderItem : orderItems) {
                statement.setInt(1, newOrderID);
                statement.setString(2, orderItem.getProduct().getCode());
                statement.setInt(3, orderItem.getQuantity());
                statement.execute();
            }

        }
        return new Order(newOrderID, userLogin);
    }
}

