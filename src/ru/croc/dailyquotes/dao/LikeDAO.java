package ru.croc.dailyquotes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LikeDAO {
    private final Connection CONNECTION;

    public LikeDAO(Connection connection) {
        this.CONNECTION = connection;
    }

    public void addToFavorites(int userId, int quoteId) throws SQLException {
        String sql = "INSERT INTO like_info VALUES(?, ?);";
        try (PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, quoteId);
            statement.execute();
        }
    }
}
