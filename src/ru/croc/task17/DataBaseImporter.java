package ru.croc.task17;

import ru.croc.task17.entity.Order;
import ru.croc.task17.entity.OrderItem;
import ru.croc.task17.entity.Product;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DataBaseImporter {
    protected String source;

    protected DataBaseImporter(String source) {
        this.source = source;
    }

    public void loadData(String connectionURL, String user, String password) {
        Set<Product> products = new HashSet<>();
        Set<Order> orders = new HashSet<>();
        Set<OrderItem> orderItems = new HashSet<>();

        DataReader dataReader = new DataReader(source);
        try {
            dataReader.readData(products, orders, orderItems);
        } catch (IOException e) {
           e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(connectionURL, user, password)) {
            String createProductsTable = "CREATE TABLE products" +
                    "(code VARCHAR(10) NOT NULL PRIMARY KEY," +
                    "name VARCHAR(64) NOT NULL," +
                    "price INT NOT NULL);";

            String createOrdersTable = "CREATE TABLE orders" +
                    "(id INT PRIMARY KEY," +
                    "user_login VARCHAR(64) NOT NULL);";

            String createOrdersItemsTable = "CREATE TABLE order_items" +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "order_id INT NOT NULL," +
                    "product_code VARCHAR(10) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "FOREIGN KEY (order_id) REFERENCES orders (id)," +
                    "FOREIGN KEY (product_code) REFERENCES products (code));";
            createTableDB(connection, createProductsTable);
            createTableDB(connection, createOrdersTable);
            createTableDB(connection, createOrdersItemsTable);
            importProductsData(connection, products);
            importOrdersData(connection, orders);
            importOrderItemsData(connection, orderItems);

            System.out.println("Data has been imported successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void createTableDB(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    protected void importProductsData(Connection connection, Set<Product> products) throws SQLException { //, Set<Order> orders, Set<OrderItem> orderItems) throws SQLException {
        String sqlProducts = "INSERT INTO products VALUES(?, ?, ?)";
        for (Product product : products) {
            try (PreparedStatement statement = connection.prepareStatement(sqlProducts)) {
                statement.setString(1, product.getCode());
                statement.setString(2, product.getName());
                statement.setInt(3, product.getPrice());
                statement.execute();
            }
        }
    }

    private void importOrdersData(Connection connection, Set<Order> orders) throws SQLException {
        String sqlOrders = "INSERT INTO orders VALUES(?, ?)";
        for (Order order : orders) {
            try (PreparedStatement statement = connection.prepareStatement(sqlOrders)) {
                statement.setInt(1, order.getID());
                statement.setString(2, order.getUserLogin());
                statement.execute();
            }
        }
    }

    protected void importOrderItemsData(Connection connection, Set<OrderItem> orderItems) throws SQLException {
        String sqlOrderItems = "INSERT INTO order_items (order_id, product_code, quantity) VALUES(?, ?, ?)";
        for (OrderItem orderItem : orderItems) {
            try (PreparedStatement statement = connection.prepareStatement(sqlOrderItems)) {
                statement.setInt(1, orderItem.getOrder().getID());
                statement.setString(2, orderItem.getProduct().getCode());
                statement.setInt(3, orderItem.getQuantity());
                statement.execute();
            }
        }

    }

}
