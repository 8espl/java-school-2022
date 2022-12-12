package ru.croc.task19;

import ru.croc.task17.DataBaseImporter;
import ru.croc.task17.entity.OrderItem;
import ru.croc.task17.entity.Product;
import ru.croc.task19.entity.Courier;
import ru.croc.task19.entity.ShippingOrder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class NewDataBaseImporter extends DataBaseImporter {
    protected NewDataBaseImporter(String source) {
        super(source);
    }

    public void loadData(String connectionURL, String user, String password) {
        Set<Product> products = new HashSet<>();
        Set<Courier> couriers = new HashSet<>();
        Set<ShippingOrder> orders = new HashSet<>();
        Set<OrderItem> orderItems = new HashSet<>();

        NewDataReader dataReader = new NewDataReader(source);

        try {
            dataReader.readData(products, orders, orderItems, couriers);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(connectionURL, user, password)) {
            String createProductsTable = "CREATE TABLE products" +
                    "(code VARCHAR(10) NOT NULL PRIMARY KEY," +
                    "name VARCHAR(64) NOT NULL," +
                    "price INT NOT NULL);";

            String createCouriersTable = "CREATE TABLE couriers" +
                    "(ID VARCHAR(11) NOT NULL PRIMARY KEY," +
                    "first_name VARCHAR(64) NOT NULL," +
                    "second_name VARCHAR(64) NOT NULL);";

            String createOrdersTable = "CREATE TABLE orders" +
                    "(id INT PRIMARY KEY," +
                    "user_login VARCHAR(64) NOT NULL," +
                    "shipping_time SMALLDATETIME NOT NULL," +
                    "courier_id VARCHAR(11) NOT NULL," +
                    "FOREIGN KEY (courier_id) REFERENCES couriers (id));";

            String createOrdersItemsTable = "CREATE TABLE order_items" +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "order_id INT NOT NULL," +
                    "product_code VARCHAR(10) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "FOREIGN KEY (order_id) REFERENCES orders (id)," +
                    "FOREIGN KEY (product_code) REFERENCES products (code));";
            createTableDB(connection, createProductsTable);
            createTableDB(connection, createCouriersTable);
            createTableDB(connection, createOrdersTable);
            createTableDB(connection, createOrdersItemsTable);
            importProductsData(connection, products);
            importCouriersData(connection, couriers);
            importOrdersData(connection, orders);
            importOrderItemsData(connection, orderItems);

            System.out.println("Data has been imported successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void importCouriersData(Connection connection, Set<Courier> couriers) throws SQLException {
        String sqlCouriers = "INSERT INTO couriers VALUES(?, ?, ?)";
        for (Courier courier : couriers) {
            try (PreparedStatement statement = connection.prepareStatement(sqlCouriers)) {
                statement.setString(1, courier.getID());
                statement.setString(2, courier.getFirstName());
                statement.setString(3, courier.getSecondName());
                statement.execute();
            }
        }
    }

    private void importOrdersData(Connection connection, Set<ShippingOrder> orders) throws SQLException {
        String sqlOrders = "INSERT INTO orders VALUES(?, ?, ?, ?)";
        for (ShippingOrder order : orders) {
            try (PreparedStatement statement = connection.prepareStatement(sqlOrders)) {
                statement.setInt(1, order.getID());
                statement.setString(2, order.getUserLogin());
                statement.setObject(3, order.getShippingTime());
                statement.setString(4, order.getCourier().getID());
                statement.execute();
            }
        }
    }
}
