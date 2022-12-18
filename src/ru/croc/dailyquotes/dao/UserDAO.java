package ru.croc.dailyquotes.dao;

import ru.croc.dailyquotes.database.DBConnectionGetter;
import ru.croc.dailyquotes.entity.Category;
import ru.croc.dailyquotes.entity.Quote;

import java.sql.*;
import java.util.*;

public class UserDAO {
    private final Connection CONNECTION;

    public UserDAO(Connection connection) {
        this.CONNECTION = connection;
    }

    public Set<Integer> getUsersId() throws SQLException {
        Set<Integer> usersID = new HashSet<>();
        String sql = "SELECT user_info.id FROM user_info";
        try (Statement statement = CONNECTION.createStatement()) {
            try (ResultSet result = statement.executeQuery(sql)) {
                while (result.next()) {
                    int id = result.getInt("id");
                    usersID.add(id);
                }
            }
        }
        return usersID;
    }

    public Quote updateDailyQuote(int userId, Category category) throws SQLException {
        QuoteDAO quoteDAO = new QuoteDAO(DBConnectionGetter.getConnection());
        Quote newDailyQuote = quoteDAO.getRandomQuoteInCategory(category);

        String sql = "UPDATE user_info SET daily_quote_id = ? WHERE id = ?;";
        try (PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setInt(1, newDailyQuote.getID());
            statement.setInt(2, userId);
            statement.execute();
        }
        return getDailyQuote(userId);
    }

    public List<Quote> getLikedQuotes(int userId) throws SQLException {
        List<Quote> likedQuotes = new ArrayList<>();
        String sql = "SELECT like_info.quote_id, quote_info.text, quote_info.author, quote_info.category " +
                "FROM like_info INNER JOIN quote_info ON like_info.quote_id=quote_info.id " +
                "WHERE like_info.user_id = ?;";
        try (PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Quote quote = new Quote(result.getInt("quote_id"),
                            result.getString("text"),
                            result.getString("author"),
                            Category.valueOf(result.getString("category").toUpperCase()));
                    likedQuotes.add(quote);
                }
            }
        }
        return likedQuotes;
    }

    public Quote getDailyQuote(int userId) throws SQLException {
        Quote dailyQuote = new Quote();
        String sql = "SELECT user_info.daily_quote_id, quote_info.text, quote_info.author, quote_info.category " +
                "FROM user_info INNER JOIN quote_info ON user_info.daily_quote_id=quote_info.id " +
                "WHERE user_info.id = ?;";
        try (PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    dailyQuote = new Quote(result.getInt("daily_quote_id"),
                            result.getString("text"),
                            result.getString("author"),
                            Category.valueOf(result.getString("category").toUpperCase()));
                }
            }
        }
        return dailyQuote;
    }
}
