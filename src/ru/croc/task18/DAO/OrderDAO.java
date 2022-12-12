package ru.croc.task18.DAO;

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
        int ID = findLastOrderID() + 1;
        Map<String, OrderItem> orderItemsMap = new HashMap<>();
        ProductDAO productDAO = new ProductDAO(connection);

        // <product_code, orderItem> чтобы посчитать сколько раз в одном заказе приобрели данный товар
        for (Product product : products) {
            if (productDAO.findProduct(product.getCode()) == null) {
                throw new Exception("Product \"" + product.getName() + "\" doesn't exist in DB.");
            } else {
                String productCode = product.getCode();
                if (orderItemsMap.containsKey(productCode)) {
                    orderItemsMap.get(productCode).addItem();
                } else {
                    orderItemsMap.put(productCode, new OrderItem(ID, productCode));
                }
            }
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO orders VALUES(?, ?);")) {
            statement.setInt(1, ID);
            statement.setString(2, userLogin);
            statement.execute();
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO order_items (order_id, product_code, quantity) VALUES(?, ?, ?);")) {
            for (OrderItem orderItem : orderItemsMap.values()) {
                statement.setInt(1, ID);
                statement.setString(2, orderItem.getProductCode());
                statement.setInt(3, orderItem.getQuantity());
                statement.execute();
            }

        }
        return new Order(ID, userLogin);
    }
}

