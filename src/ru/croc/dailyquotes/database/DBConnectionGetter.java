package ru.croc.dailyquotes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionGetter {
    private static final String CONNECTION_URL = "jdbc:h2:~/LanguageLearningApp";
    private static final String USER = "elvina";
    private static final String PASSWORD = "elvina";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
    }
}
