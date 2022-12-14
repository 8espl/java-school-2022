package ru.croc.task19.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CourierDAO {
    private Connection connection;

    public CourierDAO(Connection connection) {
        this.connection = connection;
    }

    public Map<Integer, String> findOrderToShip(String courierID) throws SQLException {
        Map<Integer, String> ordersToShip = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT orders.id, orders.user_login " +
                "FROM orders WHERE courier_id = ?;")) {
            statement.setString(1, courierID);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int orderID = result.getInt("id");
                    String userLogin =  result.getString("user_login");
                    ordersToShip.put(orderID, userLogin);
                }
            }
        }
        return ordersToShip;
    }
}