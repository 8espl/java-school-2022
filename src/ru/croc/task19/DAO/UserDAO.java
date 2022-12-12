package ru.croc.task19.DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public Map<String, String> findShippingInfo(String user_login) throws SQLException {
        Map<String, String> shippingInfo = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (PreparedStatement statement = connection.prepareStatement("SELECT orders.shipping_time, " +
                "couriers.first_name, couriers.second_name " +
                "FROM orders INNER JOIN couriers ON orders.courier_id=couriers.id " +
                "WHERE orders.user_login = ?;")) {
            statement.setString(1, user_login);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    shippingInfo.put(result.getTimestamp("shipping_time").toLocalDateTime().format(formatter),
                            result.getString("first_name") + " " + result.getString("second_name"));
                }
            }
        }
        return shippingInfo;
    }
}
