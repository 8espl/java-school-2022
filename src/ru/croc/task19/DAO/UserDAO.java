package ru.croc.task19.DAO;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public Map<Integer, String> findShippingInfo(String user_login) throws SQLException {
        Map<Integer, String> shippingInfo = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (PreparedStatement statement = connection.prepareStatement("SELECT orders.ID, orders.shipping_time, " +
                "couriers.first_name, couriers.second_name " +
                "FROM orders INNER JOIN couriers ON orders.courier_id=couriers.id " +
                "WHERE orders.user_login = ?;")) {
            statement.setString(1, user_login);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int orderID= result.getInt("ID");
                   String time = result.getTimestamp("shipping_time").toLocalDateTime().format(formatter);
                   String fullName = result.getString("first_name") + " " + result.getString("second_name");
                   shippingInfo.put(orderID,  time + " by " + fullName);
                }
            }
        }
        return shippingInfo;
    }
}
