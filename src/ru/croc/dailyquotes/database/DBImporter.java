package ru.croc.dailyquotes.database;

import ru.croc.dailyquotes.entity.Quote;
import ru.croc.dailyquotes.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBImporter {
    private final String QUOTE_SOURCE;
    private final String USER_INFO_SOURCE;

    public DBImporter(String quotesSource, String userInfoSource) {
        this.QUOTE_SOURCE = quotesSource;
        this.USER_INFO_SOURCE = userInfoSource;
    }

    public void importDB() throws IOException, SQLException {
        List<Quote> quotes = new ArrayList<>();
        Set<User> users = new HashSet<>();

        FileDataReader dataReader = new FileDataReader(QUOTE_SOURCE, USER_INFO_SOURCE);
        dataReader.readFiles(quotes, users);

        Connection connection = DBConnectionGetter.getConnection();
        String createQuoteTable = "CREATE TABLE IF NOT EXISTS quote_info" +
                "(id INT NOT NULL PRIMARY KEY," +
                "text CLOB(5000) NOT NULL," +
                "author VARCHAR(255) NOT NULL," +
                "category VARCHAR(20));";

        String createUserTable = "CREATE TABLE IF NOT EXISTS user_info" +
                "(id INT NOT NULL PRIMARY KEY," +
                "daily_quote_id INT NOT NULL," +
                "FOREIGN KEY (daily_quote_id) REFERENCES quote_info (id));";

        String createLikeTable = "CREATE TABLE IF NOT EXISTS like_info" +
                "(user_id INT NOT NULL," +
                "quote_id INT NOT NULL," +
                "FOREIGN KEY (quote_id) REFERENCES quote_info (id)," +
                "FOREIGN KEY (user_id) REFERENCES user_info (id));";

        createTableDB(connection, createQuoteTable);
        createTableDB(connection, createUserTable);
        createTableDB(connection, createLikeTable);
        importQuotesData(connection, quotes);
        importUsersData(connection, users);
    }

    private void createTableDB(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    private void importQuotesData(Connection connection, List<Quote> quotes) throws SQLException {
        String sqlQuotes = "INSERT INTO quote_info VALUES(?, ?, ?, ?)";
        for (Quote quote : quotes) {
            try (PreparedStatement statement = connection.prepareStatement(sqlQuotes)) {
                statement.setInt(1, quote.getID());
                statement.setString(2, quote.getText());
                statement.setString(3, quote.getAuthor());
                statement.setString(4, quote.getCategory().toString().toLowerCase());
                statement.executeUpdate();
            }
        }
    }

    private void importUsersData(Connection connection, Set<User> users) throws SQLException {
        String sqlUsers = "INSERT INTO user_info VALUES(?, ?)";
        for (User user : users) {
            try (PreparedStatement statement = connection.prepareStatement(sqlUsers)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, user.getDailyQuoteId());
                statement.executeUpdate();
            }
        }
    }
}
