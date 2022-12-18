package ru.croc.dailyquotes.dao;

import ru.croc.dailyquotes.entity.Category;
import ru.croc.dailyquotes.entity.Quote;

import java.sql.*;

public class QuoteDAO {
    private final Connection CONNECTION;

    public QuoteDAO(Connection CONNECTION) {
        this.CONNECTION = CONNECTION;
    }

    public Quote getRandomQuoteInCategory(Category category) throws SQLException {
        Quote quote = null;
        String sql = "SELECT * FROM quote_info WHERE category = ? ORDER BY RAND() limit 1;";
        try (PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, category.toString().toLowerCase());
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    quote = new Quote(result.getInt("id"),
                            result.getString("text"),
                            result.getString("author"), category);
                }
            }
        }
        return quote;
    }
}
